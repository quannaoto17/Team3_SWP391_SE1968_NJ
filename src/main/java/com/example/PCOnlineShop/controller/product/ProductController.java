package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.*;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.product.*;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.service.product.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/staff/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;
    private final ImageRepository imageRepository;

    // Repos thông số build
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;
    private final MainboardRepository mainboardRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final CaseRepository caseRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoolingRepository coolingRepository;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryRepository.findAll();
    }

    @ModelAttribute("brands")
    public List<Brand> brands() {
        return brandRepository.findAll();
    }


    // ===== DANH SÁCH =====
    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.getProducts();
        model.addAttribute("products", products);
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/product-list";
    }

    // ===== FORM THÊM =====
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        return "product/product-form";
    }

    // ===== SPEC FORM (ADD) — ĐÃ SỬA ĐỂ GỘP =====
    @GetMapping("/spec-form")
    public String getSpecForm(@RequestParam("categoryId") int categoryId, Model model) {

        switch (categoryId) {
            case 1 -> model.addAttribute("mb", new Mainboard());
            case 2 -> model.addAttribute("cpu", new CPU());
            case 3 -> model.addAttribute("gpu", new GPU());
            case 4 -> model.addAttribute("mem", new Memory());
            case 5 -> model.addAttribute("storage", new Storage());
            case 6 -> model.addAttribute("pcase", new Case());
            case 7 -> model.addAttribute("psu", new PowerSupply());
            case 8 -> model.addAttribute("cl", new Cooling());
            // case 9 -> model.addAttribute("fan", new Fan());
            default -> {}
        }

        // Trả về thư mục "specs" CHUẨN (đã gộp)
        return switch (categoryId) {
            case 1 -> "product/specs/mainboard-spec-form";
            case 2 -> "product/specs/cpu-spec-form";
            case 3 -> "product/specs/gpu-spec-form";
            case 4 -> "product/specs/memory-spec-form";
            case 5 -> "product/specs/storage-spec-form";
            case 6 -> "product/specs/case-spec-form";
            case 7 -> "product/specs/powersupply-spec-form";
            case 8 -> "product/specs/cooling-spec-form";
            case 9 -> "product/specs/fan-spec-form";
            default -> "product/specs/default-spec-form";
        };
    }

    // ===== SPEC FORM (EDIT) — ĐÃ SỬA ĐỂ GỘP =====
    @GetMapping("/spec-form-edit")
    public String getSpecFormForEdit(@RequestParam("categoryId") int categoryId,
                                     @RequestParam("productId") int productId,
                                     Model model) {

        // Dùng orElseGet để đảm bảo object không bao giờ null
        switch (categoryId) {
            case 1 -> model.addAttribute("mb", mainboardRepository.findByProduct_ProductId(productId).orElseGet(Mainboard::new));
            case 2 -> model.addAttribute("cpu", cpuRepository.findByProduct_ProductId(productId).orElseGet(CPU::new));
            case 3 -> model.addAttribute("gpu", gpuRepository.findByProduct_ProductId(productId).orElseGet(GPU::new));
            case 4 -> model.addAttribute("mem", memoryRepository.findByProduct_ProductId(productId).orElseGet(Memory::new));
            case 5 -> model.addAttribute("storage", storageRepository.findByProduct_ProductId(productId).orElseGet(Storage::new));
            case 6 -> model.addAttribute("pcase", caseRepository.findByProduct_ProductId(productId).orElseGet(Case::new));
            case 7 -> model.addAttribute("psu", powerSupplyRepository.findByProduct_ProductId(productId).orElseGet(PowerSupply::new));
            case 8 -> model.addAttribute("cl", coolingRepository.findByProduct_ProductId(productId).orElseGet(Cooling::new));
            // case 9 -> model.addAttribute("fan", fanRepository.findByProduct_ProductId(productId).orElseGet(Fan::new));
            default -> {}
        }

        // Trả về thư mục "specs" CHUẨN (GIỐNG HỆT getSpecForm)
        return switch (categoryId) {
            case 1 -> "product/specs/mainboard-spec-form";
            case 2 -> "product/specs/cpu-spec-form";
            case 3 -> "product/specs/gpu-spec-form";
            case 4 -> "product/specs/memory-spec-form";
            case 5 -> "product/specs/storage-spec-form";
            case 6 -> "product/specs/case-specform";
            case 7 -> "product/specs/powersupply-spec-form";
            case 8 -> "product/specs/cooling-spec-form";
            case 9 -> "product/specs/fan-spec-form";
            default -> "product/specs/default-spec-form";
        };
    }

    // ===== LƯU (THÊM MỚI) - ĐÃ SỬA LỖI VALIDATION =====
    @PostMapping("/save")
    @Transactional
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              @RequestParam("category.categoryId") Integer categoryId,
                              @RequestParam("brand.brandId") Integer brandId,
                              @RequestParam Map<String, String> params,
                              @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                              Model model) throws IOException {

        // --- Validation tùy chỉnh ---
        if (productService.existsActiveProductName(product.getProductName())) {
            result.rejectValue("productName", "error.product", "Product name already exists.");
        }
        if (brandId == null) {
            model.addAttribute("brandError", "Please select brand");
        }
        if (categoryId == null) {
            model.addAttribute("categoryError", "Please select category");
        }

        // --- Kết thúc Validation ---


        // SỬA LỖI: Gộp tất cả điều kiện lỗi
        if (result.hasErrors() || brandId == null || categoryId == null) {
            model.addAttribute("isEdit", false);
            // Gửi lại ID để JS tự động mở lại form
            model.addAttribute("submittedCategoryId", categoryId);
            model.addAttribute("submittedBrandId", brandId);
            return "product/product-form"; // Ở lại trang
        }

        Category category = categoryRepository.findById(categoryId).orElseThrow();
        Brand brand = brandRepository.findById(brandId).orElseThrow();
        product.setCategory(category);
        product.setBrand(brand);

        Product saved = productService.addProduct(product);

        // Save images
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> imgs = saveImagesToStatic(imageFiles, saved);
            imageRepository.saveAll(imgs);
            saved.setImages(imgs);
            productService.updateProduct(saved); // Lưu lại tham chiếu ảnh
        }

        // Save spec
        upsertSpec(saved, categoryId, params, true);

        return "redirect:/staff/products/list";
    }

    // ===== FORM SỬA =====
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) return "redirect:/staff/products/list";
        model.addAttribute("product", product);
        model.addAttribute("isEdit", true);
        return "product/product-update";
    }

    // ===== CẬP NHẬT (ĐÃ SỬA LỖI VALIDATION VÀ LOGIC) =====
    @PostMapping("/edit")
    @Transactional
    public String updateProduct(@Valid @ModelAttribute("product") Product incoming,
                                BindingResult result,
                                Model model,
                                @RequestParam Map<String, String> params,
                                @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                                @RequestParam(value = "deleteImageIds", required = false) String deleteImageIds
    ) throws IOException {

        Product current = productService.getProductById(incoming.getProductId());
        if (current == null) return "redirect:/staff/products/list";

        int currentCategoryId = current.getCategory().getCategoryId();
        int incomingCategoryId = (incoming.getCategory() != null)
                ? incoming.getCategory().getCategoryId()
                : currentCategoryId;

        // --- Validation tùy chỉnh ---
        String newName = incoming.getProductName();
        if (!newName.equals(current.getProductName()) && productService.existsActiveProductName(newName)) {
            result.rejectValue("productName", "error.product", "Product name already exists.");
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            incoming.setImages(current.getImages());
            incoming.setBrand(current.getBrand());
            incoming.setCategory(current.getCategory());
            return "product/product-update";
        }

        // --- Ngăn đổi category ---
        if (incomingCategoryId != currentCategoryId) {
            model.addAttribute("error", "Cannot change category of an existing product. Specs will not be updated.");
            model.addAttribute("isEdit", true);
            incoming.setCategory(current.getCategory());
            // Vẫn cho phép update các thông tin cơ bản, nhưng bỏ qua phần spec
            // Không return ngay, để cho phép cập nhật các trường khác
        }

        // ===== Cập nhật thông tin cơ bản =====
        current.setProductName(incoming.getProductName());
        current.setDescription(incoming.getDescription());
        current.setPrice(incoming.getPrice());
        current.setStatus(incoming.isStatus());
        current.setInventoryQuantity(incoming.getInventoryQuantity());

        Product updated = productService.updateProduct(current);

        // ===== Xử lý ảnh =====
        if (deleteImageIds != null && !deleteImageIds.isBlank()) {
            List<Integer> ids = Arrays.stream(deleteImageIds.split(","))
                    .filter(s -> !s.isBlank())
                    .map(Integer::parseInt)
                    .toList();
            for (Integer id : ids) {
                imageRepository.findById(id).ifPresent(img -> {
                    try {
                        Path path = Paths.get("uploads/images" + img.getImageUrl().replace("/image", ""));
                        Files.deleteIfExists(path);
                        imageRepository.delete(img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }

        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> newImgs = saveImagesToStatic(imageFiles, updated);
            imageRepository.saveAll(newImgs);
        }

        // ===== Chỉ cập nhật SPEC nếu category không bị đổi =====
        if (incomingCategoryId == currentCategoryId &&
                params.keySet().stream().anyMatch(k -> k.matches("^(mainboard|cpu|gpu|memory|storage|pcase|psu|cl)\\..*"))) {
            upsertSpec(updated, currentCategoryId, params, false);
        } else {
            System.out.println("⚠️ Category changed or invalid → Spec update skipped.");
        }

        return "redirect:/staff/products/list";
    }

    // ===== HIDE PRODUCT  =====
    @PostMapping("/{id}/hide")
    @Transactional
    public String hideProduct(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            model.addAttribute("error", "Product not found.");
            return "redirect:/staff/products/list";
        }

        // ✅ Kiểm tra ràng buộc: không được ẩn nếu sản phẩm đang nằm trong đơn hàng hoạt động
//        boolean inActiveOrders = productService.isProductInActiveOrders(id);

//        if (inActiveOrders) {
//            model.addAttribute("error",
//                    "This product is currently in active orders and cannot be hidden.");
//            return "redirect:/staff/products/list";
//        }

        // ✅ Ẩn sản phẩm (soft hide)
        product.setStatus(false);
        // ✅ Đổi tên để tránh trùng validate (theo tài liệu: [archived-id])
        product.setProductName(product.getProductName() + " [archived-" + id + "]");
        productService.updateProduct(product);

        model.addAttribute("message", "Product has been hidden successfully.");
        return "redirect:/staff/products/list";
    }

    // ===== XÓA ẢNH (AJAX) =====
    @DeleteMapping("/image/{imageId}")
    @ResponseBody
    @Transactional
    public String deleteImage(@PathVariable int imageId) {
        imageRepository.findById(imageId).ifPresent(img -> {
            try {
                Path path = Paths.get("uploads/images" + img.getImageUrl().replace("/image", ""));
                Files.deleteIfExists(path);
                imageRepository.delete(img);
                System.out.println(">>> Deleted via AJAX: " + path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return "success";
    }

    // ========= HELPERS =========

    // HÀM UPSERT
    private void upsertSpec(Product product, int categoryId, Map<String, String> params, boolean isCreate) {
        switch (categoryId) {
            case 1 -> {
                Mainboard mb = mainboardRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Mainboard::new);
                mb.setProduct(product);
                mb.setSocket(params.get("mainboard.socket"));
                mb.setChipset(params.get("mainboard.chipset"));
                mb.setFormFactor(params.get("mainboard.formFactor"));
                mainboardRepository.save(mb);
            }
            case 2 -> {
                CPU cpu = cpuRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(CPU::new);
                cpu.setProduct(product);
                cpu.setSocket(params.get("cpu.socket"));
                cpu.setTdp(getInt(params, "cpu.tdp"));
                cpu.setMaxMemorySpeed(getInt(params, "cpu.maxMemorySpeed"));
                cpu.setMemoryChannels(getInt(params, "cpu.memoryChannels"));
                cpu.setHasIGPU(getBool(params, "cpu.hasIGPU"));
                cpu.setPcieVersion(params.get("cpu.pcieVersion"));
                cpuRepository.save(cpu);
            }
            case 3 -> {
                GPU gpu = gpuRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(GPU::new);
                gpu.setProduct(product);
                gpu.setVram(getInt(params, "gpu.vram"));
                gpu.setMemoryType(params.get("gpu.memoryType"));
                gpu.setTdp(getInt(params, "gpu.tdp"));
                gpu.setLength(getInt(params, "gpu.length"));
                gpu.setGpuInterface(params.get("gpu.gpuInterface"));
                gpu.setPcieVersion(params.get("gpu.pcieVersion"));
                gpuRepository.save(gpu);
            }
            case 4 -> {
                Memory mem = memoryRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Memory::new);
                mem.setProduct(product);
                mem.setCapacity(getInt(params, "mem.capacity"));
                mem.setType(params.get("mem.type"));
                mem.setSpeed(getInt(params, "mem.speed"));
                mem.setTdp(getInt(params, "mem.tdp"));
                memoryRepository.save(mem);
            }
            case 5 -> {
                Storage st = storageRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Storage::new);
                st.setProduct(product);
                st.setCapacity(getInt(params, "storage.capacity"));
                st.setType(params.get("storage.type"));
                st.setInterfaceType(params.get("storage.interfaceType"));
                st.setReadSpeed(getInt(params, "storage.readSpeed"));
                st.setWriteSpeed(getInt(params, "storage.writeSpeed"));
                storageRepository.save(st);
            }
            case 6 -> {
                Case pcCase = caseRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Case::new);
                pcCase.setProduct(product);
                pcCase.setFormFactor(params.get("pcase.formFactor"));
                pcCase.setGpuMaxLength(getInt(params, "pcase.gpu.maxLength"));
                pcCase.setPsuFormFactor(params.get( "pcase.psu.formFactor"));
                pcCase.setCpuMaxCoolerHeight(getInt(params, "pcase.cpu.maxCoolerHeight"));
                caseRepository.save(pcCase);
            }
            case 7 -> {
                PowerSupply psu = powerSupplyRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(PowerSupply::new);
                psu.setProduct(product);
                psu.setWattage(getInt(params, "psu.wattage"));
                psu.setEfficiency(params.get("psu.efficiency"));
                psu.setModular(getBool(params, "psu.modular"));
                psu.setFormFactor(params.get("psu.formFactor"));
                powerSupplyRepository.save(psu);
            }
            case 8 -> {
                Cooling cl = coolingRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Cooling::new);
                cl.setProduct(product);
                cl.setType(params.get("cl.type"));
                cl.setFanSize(getInt(params, "cl.fanSize"));
                cl.setRadiatorSize(getInt(params, "cl.radiatorSize"));
                cl.setTdp(getInt(params, "cl.maxTdp"));
                coolingRepository.save(cl);
            }
            // case 9 -> ... (Thêm logic cho Fan nếu cần)
            default -> { }
        }
    }

    private void cleanupOtherSpecs(int productId, int keepCategoryId) {
        if (keepCategoryId != 1) mainboardRepository.findByProduct_ProductId(productId).ifPresent(mainboardRepository::delete);
        if (keepCategoryId != 2) cpuRepository.findByProduct_ProductId(productId).ifPresent(cpuRepository::delete);
        if (keepCategoryId != 3) gpuRepository.findByProduct_ProductId(productId).ifPresent(gpuRepository::delete);
        if (keepCategoryId != 4) memoryRepository.findByProduct_ProductId(productId).ifPresent(memoryRepository::delete);
        if (keepCategoryId != 5) storageRepository.findByProduct_ProductId(productId).ifPresent(storageRepository::delete);
        if (keepCategoryId != 6) caseRepository.findByProduct_ProductId(productId).ifPresent(caseRepository::delete);
        if (keepCategoryId != 7) powerSupplyRepository.findByProduct_ProductId(productId).ifPresent(powerSupplyRepository::delete);
        if (keepCategoryId != 8) coolingRepository.findByProduct_ProductId(productId).ifPresent(coolingRepository::delete);
    }

    private Integer getInt(Map<String,String> params, String key) {
        try {
            String v = params.get(key);
            return (v == null || v.isBlank()) ? null : Integer.parseInt(v.trim());
        } catch (Exception e) { return null; }
    }
    private Boolean getBool(Map<String,String> params, String key) {
        String v = params.get(key);
        // Sửa lại logic: "true" (từ checkbox) là true, mọi thứ khác (null, blank, "false") là false.
        // Điều này là quan trọng vì hidden input gửi "false"
        return "true".equalsIgnoreCase(v);
    }

    private List<Image> saveImagesToStatic(List<MultipartFile> imageFiles, Product product) throws IOException {
        Path uploadPath = Paths.get("uploads/images");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        List<Image> images = new ArrayList<>();

        for (MultipartFile file : imageFiles) {
            if (file != null && !file.isEmpty()) {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                Image img = new Image();
                img.setImageUrl("/image/" + fileName);
                img.setProduct(product);
                images.add(img);
                System.out.println(">>> Saved image to: " + filePath.toAbsolutePath());
            }
        }

        return images;
    }
}