package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.*;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.product.*;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.service.product.ProductService;
import com.example.PCOnlineShop.service.product.SpecToCategoryMappingService;
import com.example.PCOnlineShop.service.validation.ComponentSpecValidationService;

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
    private final ComponentSpecValidationService specValidationService;
    private final SpecToCategoryMappingService specToCategoryMappingService;

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

    // Exception handler for validation errors
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleValidationException(IllegalArgumentException ex, Model model) {
        model.addAttribute("error", ex.getMessage());
        model.addAttribute("product", new Product());
        model.addAttribute("isEdit", false);
        return "product/product-form";
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
    public String saveProduct(/* @Valid */ @ModelAttribute("product") Product product,
                              BindingResult result,
                              @RequestParam(value = "categoryIds", required = false) List<Integer> categoryIds,
                              @RequestParam(value = "brand.brandId", required = false) Integer brandId,
                              @RequestParam Map<String, String> params,
                              @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                              Model model) throws IOException {

        // DEBUG: Log all parameters
        System.out.println("=== DEBUG ADD PRODUCT START ===");
        System.out.println("Product name: " + product.getProductName());
        System.out.println("Product price: " + product.getPrice());
        System.out.println("Product inventory: " + product.getInventoryQuantity());
        System.out.println("Brand ID: " + brandId);
        System.out.println("Category IDs: " + categoryIds);
        System.out.println("Has binding errors: " + result.hasErrors());
        if (result.hasErrors()) {
            System.out.println("Binding errors: " + result.getAllErrors());
        }
        System.out.println("All params keys: " + params.keySet());
        System.out.println("Image files: " + (imageFiles != null ? imageFiles.size() : 0));

        // DEBUG: Print spec params specifically
        System.out.println("--- SPEC PARAMS ---");
        params.entrySet().stream()
            .filter(e -> e.getKey().contains("."))
            .forEach(e -> System.out.println("  " + e.getKey() + " = " + e.getValue()));
        System.out.println("-------------------");
        System.out.println("================================");

        // --- Validation tùy chỉnh ---
        if (productService.existsActiveProductName(product.getProductName())) {
            System.out.println("❌ Product name already exists!");
            result.rejectValue("productName", "error.product", "Product name already exists.");
        }
        if (brandId == null) {
            System.out.println("❌ Brand is null!");
            model.addAttribute("brandError", "Please select brand");
        }
        if (categoryIds == null || categoryIds.isEmpty()) {
            System.out.println("❌ Category is null or empty!");
            model.addAttribute("categoryError", "Please select at least one category");
        }

        // SỬA LỖI: Gộp tất cả điều kiện lỗi
        if (result.hasErrors() || brandId == null || categoryIds == null || categoryIds.isEmpty()) {
            System.out.println("❌ Validation failed! Returning to form...");
            model.addAttribute("isEdit", false);
            model.addAttribute("submittedCategoryId", categoryIds != null && !categoryIds.isEmpty() ? categoryIds.get(0) : null);
            model.addAttribute("submittedBrandId", brandId);
            return "product/product-form";  // CHỈ return 1 lần
        }

        System.out.println("✅ Validation passed! Proceeding to save...");

        // Get all selected categories
        List<Category> categories = categoryIds.stream()
                .map(id -> categoryRepository.findById(id).orElseThrow())
                .toList();

        Brand brand = brandRepository.findById(brandId).orElseThrow();
        product.setCategories(new ArrayList<>(categories));
        product.setBrand(brand);

        Product saved = productService.addProduct(product);
        System.out.println("✅ Product saved with ID: " + saved.getProductId());

        // Save images
        if (imageFiles != null && !imageFiles.isEmpty()) {
            System.out.println("💾 Saving " + imageFiles.size() + " images...");
            List<Image> imgs = saveImagesToStatic(imageFiles, saved);
            imageRepository.saveAll(imgs);
            saved.setImages(imgs);
            productService.updateProduct(saved); // Lưu lại tham chiếu ảnh
            System.out.println("✅ Images saved!");
        }

        // Save spec - use primary category (first one)
        Integer primaryCategoryId = categoryIds.get(0);
        System.out.println("💾 Saving spec for category: " + primaryCategoryId);

        // Validate spec fields BEFORE saving
        List<String> specErrors = validateSpecFields(primaryCategoryId, params);
        if (!specErrors.isEmpty()) {
            System.out.println("❌ Spec validation failed: " + specErrors);

            // Rollback: delete the product and images that were just saved
            if (saved.getImages() != null) {
                for (Image img : saved.getImages()) {
                    try {
                        Path path = Paths.get("uploads/images" + img.getImageUrl().replace("/image", ""));
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            productService.deleteProduct(saved.getProductId());

            // Return to form with errors
            model.addAttribute("specErrors", specErrors);
            model.addAttribute("isEdit", false);
            model.addAttribute("submittedCategoryId", primaryCategoryId);
            model.addAttribute("submittedBrandId", brandId);
            return "product/product-form";
        }

        try {
            upsertSpec(saved, primaryCategoryId, params, true);
            System.out.println("✅ Spec saved!");
        } catch (Exception e) {
            System.out.println("❌ Error saving spec: " + e.getMessage());
            e.printStackTrace();
            // Rollback
            productService.deleteProduct(saved.getProductId());
            model.addAttribute("error", "Failed to save specification: " + e.getMessage());
            model.addAttribute("isEdit", false);
            return "product/product-form";
        }

        System.out.println("✅ Product creation completed! Redirecting to list...");
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
                                @RequestParam(value = "categoryIds", required = false) List<Integer> categoryIds,
                                @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles,
                                @RequestParam(value = "deleteImageIds", required = false) String deleteImageIds
    ) throws IOException {

        Product current = productService.getProductById(incoming.getProductId());
        if (current == null) return "redirect:/staff/products/list";

        // Get primary category (first in list)
        Integer currentPrimaryCategoryId = current.getCategories().isEmpty() ? null : current.getCategories().getFirst().getCategoryId();
        Integer incomingPrimaryCategoryId = (categoryIds != null && !categoryIds.isEmpty()) ? categoryIds.get(0) : currentPrimaryCategoryId;

        // --- Validation tùy chỉnh ---
        String newName = incoming.getProductName();
        if (!newName.equals(current.getProductName()) && productService.existsActiveProductName(newName)) {
            result.rejectValue("productName", "error.product", "Product name already exists.");
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            incoming.setImages(current.getImages());
            incoming.setBrand(current.getBrand());
            incoming.setCategories(current.getCategories());
            return "product/product-update";
        }

        // --- Ngăn đổi primary category ---
        if (incomingPrimaryCategoryId != null && !incomingPrimaryCategoryId.equals(currentPrimaryCategoryId)) {
            model.addAttribute("error", "Cannot change primary category of an existing product. Specs will not be updated.");
            model.addAttribute("isEdit", true);
            incoming.setCategories(current.getCategories());
            // Vẫn cho phép update các thông tin cơ bản, nhưng bỏ qua phần spec
        } else if (categoryIds != null && !categoryIds.isEmpty()) {
            // Update categories if primary category not changed
            List<Category> newCategories = categoryIds.stream()
                    .map(id -> categoryRepository.findById(id).orElseThrow())
                    .toList();
            current.setCategories(new ArrayList<>(newCategories));
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

        // ===== Chỉ cập nhật SPEC nếu primary category không bị đổi =====
        if (incomingPrimaryCategoryId != null && incomingPrimaryCategoryId.equals(currentPrimaryCategoryId) &&
                params.keySet().stream().anyMatch(k -> k.matches("^(mainboard|cpu|gpu|memory|storage|pcase|psu|cl)\\..*"))) {
            upsertSpec(updated, currentPrimaryCategoryId, params, false);
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

    // VALIDATE SPEC FIELDS BEFORE SAVING
    private List<String> validateSpecFields(int categoryId, Map<String, String> params) {
        List<String> errors = new ArrayList<>();

        switch (categoryId) {
            case 1 -> { // Mainboard
                if (isBlankOrNull(params.get("mainboard.socket")))
                    errors.add("Socket is required");
                if (isBlankOrNull(params.get("mainboard.chipset")))
                    errors.add("Chipset is required");
                if (isBlankOrNull(params.get("mainboard.formFactor")))
                    errors.add("Form Factor is required");
                if (isBlankOrNull(params.get("mainboard.memoryType")))
                    errors.add("Memory Type is required");
                if (getInt(params, "mainboard.memorySlots") == null || getInt(params, "mainboard.memorySlots") < 1)
                    errors.add("Memory Slots must be at least 1");
                if (getInt(params, "mainboard.maxMemorySpeed") == null || getInt(params, "mainboard.maxMemorySpeed") < 1)
                    errors.add("Max Memory Speed must be at least 1");
                if (isBlankOrNull(params.get("mainboard.pcieVersion")))
                    errors.add("PCIe Version is required");
            }
            case 2 -> { // CPU
                if (isBlankOrNull(params.get("cpu.socket")))
                    errors.add("Socket is required");
                if (getInt(params, "cpu.tdp") == null || getInt(params, "cpu.tdp") < 1)
                    errors.add("TDP must be at least 1");
                if (isBlankOrNull(params.get("cpu.pcieVersion")))
                    errors.add("PCIe Version is required");
            }
            case 3 -> { // GPU
                if (getInt(params, "gpu.vram") == null || getInt(params, "gpu.vram") < 1)
                    errors.add("VRAM is required");
                if (isBlankOrNull(params.get("gpu.memoryType")))
                    errors.add("Memory Type is required");
                if (getInt(params, "gpu.tdp") == null || getInt(params, "gpu.tdp") < 1)
                    errors.add("TDP must be at least 1");
            }
            case 4 -> { // Memory
                if (getInt(params, "mem.capacity") == null || getInt(params, "mem.capacity") < 1)
                    errors.add("Capacity is required");
                if (isBlankOrNull(params.get("mem.type")))
                    errors.add("Type is required");
                if (getInt(params, "mem.speed") == null || getInt(params, "mem.speed") < 1)
                    errors.add("Speed is required");
            }
            case 5 -> { // Storage
                if (getInt(params, "storage.capacity") == null || getInt(params, "storage.capacity") < 1)
                    errors.add("Capacity is required");
                if (isBlankOrNull(params.get("storage.type")))
                    errors.add("Type is required");
                if (isBlankOrNull(params.get("storage.interfaceType")))
                    errors.add("Interface Type is required");
            }
            case 6 -> { // Case
                if (isBlankOrNull(params.get("pcase.formFactor")))
                    errors.add("Form Factor is required");
                if (getInt(params, "pcase.gpu.maxLength") == null)
                    errors.add("GPU Max Length is required");
            }
            case 7 -> { // PSU
                if (getInt(params, "psu.wattage") == null || getInt(params, "psu.wattage") < 1)
                    errors.add("Wattage is required");
                if (isBlankOrNull(params.get("psu.efficiency")))
                    errors.add("Efficiency is required");
            }
            case 8 -> { // Cooling
                if (isBlankOrNull(params.get("cl.type")))
                    errors.add("Type is required");
                if (getInt(params, "cl.tdp") == null || getInt(params, "cl.tdp") < 1)
                    errors.add("TDP is required");
            }
        }

        return errors;
    }

    private boolean isBlankOrNull(String value) {
        return value == null || value.trim().isEmpty();
    }

    // HÀM UPSERT
    private void upsertSpec(Product product, int categoryId, Map<String, String> params, boolean isCreate) {
        switch (categoryId) {
            case 1 -> {
                Mainboard mb = mainboardRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Mainboard::new);
                mb.setProduct(product);
                mb.setSocket(params.getOrDefault("mainboard.socket", ""));
                mb.setChipset(params.getOrDefault("mainboard.chipset", ""));
                mb.setFormFactor(params.getOrDefault("mainboard.formFactor", ""));
                mb.setMemoryType(params.getOrDefault("mainboard.memoryType", ""));

                Integer memSlots = getInt(params, "mainboard.memorySlots");
                mb.setMemorySlots(memSlots != null ? memSlots : 0);

                Integer maxMemSpeed = getInt(params, "mainboard.maxMemorySpeed");
                mb.setMaxMemorySpeed(maxMemSpeed != null ? maxMemSpeed : 0);

                mb.setPcieVersion(params.getOrDefault("mainboard.pcieVersion", ""));

                Integer m2 = getInt(params, "mainboard.m2Slots");
                mb.setM2Slots(m2 != null ? m2 : 0);

                Integer sata = getInt(params, "mainboard.sataPorts");
                mb.setSataPorts(sata != null ? sata : 0);

                // Validate mainboard specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateMainboard(mb);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("Mainboard validation failed: " + String.join(", ", errors));
                // }

                mainboardRepository.save(mb);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, mb);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 2 -> {
                CPU cpu = cpuRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(CPU::new);
                cpu.setProduct(product);
                cpu.setSocket(params.getOrDefault("cpu.socket", ""));

                Integer tdp = getInt(params, "cpu.tdp");
                cpu.setTdp(tdp != null ? tdp : 0);

                Integer maxMemSpeed = getInt(params, "cpu.maxMemorySpeed");
                cpu.setMaxMemorySpeed(maxMemSpeed != null ? maxMemSpeed : 0);

                Integer memChannels = getInt(params, "cpu.memoryChannels");
                cpu.setMemoryChannels(memChannels != null ? memChannels : 0);

                cpu.setHasIGPU(getBool(params, "cpu.hasIGPU"));
                cpu.setPcieVersion(params.getOrDefault("cpu.pcieVersion", ""));

                // Validate CPU specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateCpu(cpu);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("CPU validation failed: " + String.join(", ", errors));
                // }

                cpuRepository.save(cpu);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, cpu);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 3 -> {
                GPU gpu = gpuRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(GPU::new);
                gpu.setProduct(product);

                Integer vram = getInt(params, "gpu.vram");
                gpu.setVram(vram != null ? vram : 0);

                gpu.setMemoryType(params.getOrDefault("gpu.memoryType", ""));

                Integer tdp = getInt(params, "gpu.tdp");
                gpu.setTdp(tdp != null ? tdp : 0);

                Integer length = getInt(params, "gpu.length");
                gpu.setLength(length != null ? length : 0);

                gpu.setGpuInterface(params.getOrDefault("gpu.gpuInterface", ""));
                gpu.setPcieVersion(params.getOrDefault("gpu.pcieVersion", ""));

                // Validate GPU specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateGpu(gpu);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("GPU validation failed: " + String.join(", ", errors));
                // }

                gpuRepository.save(gpu);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, gpu);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 4 -> {
                Memory mem = memoryRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Memory::new);
                mem.setProduct(product);

                Integer capacity = getInt(params, "mem.capacity");
                mem.setCapacity(capacity != null ? capacity : 0);

                mem.setType(params.getOrDefault("mem.type", ""));

                Integer speed = getInt(params, "mem.speed");
                mem.setSpeed(speed != null ? speed : 0);

                Integer tdp = getInt(params, "mem.tdp");
                mem.setTdp(tdp != null ? tdp : 0);

                Integer modules = getInt(params, "mem.modules");
                mem.setModules(modules != null ? modules : 0);

                // Validate Memory specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateMemory(mem);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("Memory validation failed: " + String.join(", ", errors));
                // }

                memoryRepository.save(mem);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, mem);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 5 -> {
                Storage st = storageRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Storage::new);
                st.setProduct(product);

                Integer capacity = getInt(params, "storage.capacity");
                st.setCapacity(capacity != null ? capacity : 0);

                st.setType(params.getOrDefault("storage.type", ""));
                st.setInterfaceType(params.getOrDefault("storage.interfaceType", ""));

                Integer readSpeed = getInt(params, "storage.readSpeed");
                st.setReadSpeed(readSpeed != null ? readSpeed : 0);

                Integer writeSpeed = getInt(params, "storage.writeSpeed");
                st.setWriteSpeed(writeSpeed != null ? writeSpeed : 0);

                // Validate Storage specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateStorage(st);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("Storage validation failed: " + String.join(", ", errors));
                // }

                storageRepository.save(st);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, st);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 6 -> {
                Case pcCase = caseRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Case::new);
                pcCase.setProduct(product);
                pcCase.setFormFactor(params.getOrDefault("pcase.formFactor", ""));

                Integer gpuMax = getInt(params, "pcase.gpu.maxLength");
                pcCase.setGpuMaxLength(gpuMax != null ? gpuMax : 0);

                pcCase.setPsuFormFactor(params.getOrDefault("pcase.psuFormFactor", ""));

                Integer cpuMax = getInt(params, "pcase.cpu.maxCoolerHeight");
                pcCase.setCpuMaxCoolerHeight(cpuMax != null ? cpuMax : 0);

                // Validate Case specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateCase(pcCase);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("Case validation failed: " + String.join(", ", errors));
                // }

                caseRepository.save(pcCase);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, pcCase);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 7 -> {
                PowerSupply psu = powerSupplyRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(PowerSupply::new);
                psu.setProduct(product);

                Integer wattage = getInt(params, "psu.wattage");
                psu.setWattage(wattage != null ? wattage : 0);

                psu.setEfficiency(params.getOrDefault("psu.efficiency", ""));
                psu.setModular(getBool(params, "psu.modular"));
                psu.setFormFactor(params.getOrDefault("psu.formFactor", ""));

                // Validate PSU specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validatePowerSupply(psu);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("PSU validation failed: " + String.join(", ", errors));
                // }

                powerSupplyRepository.save(psu);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, psu);
                product.setCategories(allCategories);
                productService.updateProduct(product);
            }
            case 8 -> {
                Cooling cl = coolingRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(Cooling::new);
                cl.setProduct(product);
                cl.setType(params.getOrDefault("cl.type", ""));

                Integer fanSize = getInt(params, "cl.fanSize");
                cl.setFanSize(fanSize != null ? fanSize : 0);

                Integer radiatorSize = getInt(params, "cl.radiatorSize");
                cl.setRadiatorSize(radiatorSize != null ? radiatorSize : 0);

                Integer tdp = getInt(params, "cl.tdp");
                cl.setTdp(tdp != null ? tdp : 0);

                // Validate Cooling specs (TEMPORARILY DISABLED FOR DEBUG)
                // List<String> errors = specValidationService.validateCooling(cl);
                // if (!errors.isEmpty()) {
                //     throw new IllegalArgumentException("Cooling validation failed: " + String.join(", ", errors));
                // }

                coolingRepository.save(cl);

                // Auto-map specs to categories
                List<Category> allCategories = specToCategoryMappingService.getAllCategoriesForProduct(product, categoryId, cl);
                product.setCategories(allCategories);
                productService.updateProduct(product);
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


