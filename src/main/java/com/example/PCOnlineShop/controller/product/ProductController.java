package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.*;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.product.*;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.service.product.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional; // <— dùng Spring Transactional
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

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
    public String getSpecForm(@RequestParam("categoryId") int categoryId,
                              Model model) { // <-- [1] Thêm Model

        // [2] Thêm các đối tượng RỖNG vào model
        switch (categoryId) {
            case 1 -> model.addAttribute("mb", new Mainboard());
            case 2 -> model.addAttribute("cpu", new CPU());
            case 3 -> model.addAttribute("gpu", new GPU());
            case 4 -> model.addAttribute("mem", new Memory());
            case 5 -> model.addAttribute("storage", new Storage());
            case 6 -> model.addAttribute("pcase", new Case()); // Đổi tên từ 'pc'
            case 7 -> model.addAttribute("psu", new PowerSupply());
            case 8 -> model.addAttribute("cl", new Cooling());
            case 9 -> {} // model.addAttribute("fan", new Fan());
            case 10 -> {} // model.addAttribute("other", new Other());
            default -> {}
        }

        // [3] Trả về đường dẫn TSKT đã gộp
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
            //case 10 -> "product/specs/other-form";
            default -> "product/specs/default-form";
        };
    }

    // ===== SPEC FORM (EDIT) — dùng folder spec-edit, có prefill =====
    @GetMapping("/spec-form-edit")
    public String getSpecFormForEdit(@RequestParam("categoryId") int categoryId,
                                     @RequestParam("productId") int productId,
                                     Model model) {

        switch (categoryId) {
            case 1 -> mainboardRepository.findByProduct_ProductId(productId).ifPresent(mb -> model.addAttribute("mb", mb));
            case 2 -> cpuRepository.findByProduct_ProductId(productId).ifPresent(cpu -> model.addAttribute("cpu", cpu));
            case 3 -> gpuRepository.findByProduct_ProductId(productId).ifPresent(gpu -> model.addAttribute("gpu", gpu));
            case 4 -> memoryRepository.findByProduct_ProductId(productId).ifPresent(mem -> model.addAttribute("mem", mem));
            case 5 -> storageRepository.findByProduct_ProductId(productId).ifPresent(st -> model.addAttribute("storage", st));
            case 6 -> caseRepository.findByProduct_ProductId(productId).ifPresent(pc -> model.addAttribute("pcase", pc));
            case 7 -> powerSupplyRepository.findByProduct_ProductId(productId).ifPresent(psu -> model.addAttribute("psu", psu));
            case 8 -> coolingRepository.findByProduct_ProductId(productId).ifPresent(cl -> model.addAttribute("cl", cl));
            default -> {}
        }

        return switch (categoryId) {
            case 1 -> "product/spec-edit/mainboard-spec-edit";
            case 2 -> "product/spec-edit/cpu-spec-edit";
            case 3 -> "product/spec-edit/gpu-spec-edit";
            case 4 -> "product/spec-edit/memory-spec-edit";
            case 5 -> "product/spec-edit/storage-spec-edit";
            case 6 -> "product/spec-edit/case-spec-edit";
            case 7 -> "product/spec-edit/powersupply-spec-edit";
            case 8 -> "product/spec-edit/cooling-spec-edit";
            default -> "product/spec-edit/empty-spec-edit";
        };
    }

    // ===== LƯU (THÊM MỚI) =====
    @PostMapping("/save")
    @Transactional
    public String saveProduct(@Valid @ModelAttribute("product") Product product,
                              BindingResult result,
                              @RequestParam("category.categoryId") Integer categoryId,
                              @RequestParam("brand.brandId") Integer brandId,
                              @RequestParam Map<String, String> params,
                              @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                              Model model) throws IOException {

        if (productService.existsByProductName(product.getProductName())) {
            result.rejectValue("productName", "error.product", "Product name already exists.");
            model.addAttribute("isEdit", false);
            model.addAttribute("submittedCategoryId", categoryId);
            model.addAttribute("submittedBrandId", brandId);
            return "product/product-form";
        }
        if (brandId == null)
            model.addAttribute("brandError", "Please select brand");


        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            model.addAttribute("submittedCategoryId", categoryId);
            model.addAttribute("submittedBrandId", brandId);
            return "product/product-form";
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
            productService.updateProduct(saved);
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

    // ===== CẬP NHẬT =====
    // ===== CẬP NHẬT (ĐÃ SỬA) =====
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
        int categoryId = current.getCategory().getCategoryId();
        int brandId = current.getBrand().getBrandId();

        String newName = incoming.getProductName();
        if (!newName.equals(current.getProductName()) && productService.existsByProductName(newName)) {
            result.rejectValue("productName", "error.product", "Product name already exists.");
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);

            incoming.setImages(current.getImages());
            incoming.setBrand(current.getBrand());
            incoming.setCategory(current.getCategory());

            return "product/product-update"; // <-- Trả về view, KHÔNG redirect
        }




        // Cập nhật thông tin cơ bản
        current.setProductName(incoming.getProductName());
        current.setDescription(incoming.getDescription());
        current.setPrice(incoming.getPrice());
        current.setStatus(incoming.isStatus());

        Product updated = productService.updateProduct(current);

        // ===== Delete images (nếu có chọn) =====
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
                    } catch (IOException e) { e.printStackTrace(); }
                });
            }
        }

        // ===== Add new images =====
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> newImgs = saveImagesToStatic(imageFiles, updated);
            imageRepository.saveAll(newImgs);
        }

        // ===== Cập nhật spec (nếu có form spec gửi lên) =====
        if (params.keySet().stream().anyMatch(k -> k.startsWith("mainboard.") || k.startsWith("cpu.")
                || k.startsWith("gpu.") || k.startsWith("memory.") || k.startsWith("storage.")
                || k.startsWith("pcase.") || k.startsWith("psu.") || k.startsWith("cl."))) {
            upsertSpec(updated, categoryId, params, false);
        }

        return "redirect:/staff/products/list";
    }

    // ===== NEW FEATURE: XÓA ẢNH NGAY (AJAX) =====
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
                cl.setRadiatorSize(params.get("cl.radiatorSize"));
                cl.setTdp(getInt(params, "cl.maxTdp"));
                coolingRepository.save(cl);
            }
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
        return (v == null || v.isBlank()) ? null : Boolean.parseBoolean(v);
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