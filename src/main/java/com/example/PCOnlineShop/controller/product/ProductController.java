package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.*;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.product.*;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.service.product.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional; // <— dùng Spring Transactional
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    // ===== DANH SÁCH =====
    @GetMapping("/list")
    public String listProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "productId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            Model model) {

        Page<Product> productPage;

        boolean hasKeyword = (keyword != null && !keyword.isBlank());
        if (hasKeyword) {
            productPage = productService.search(keyword, brandId, categoryId, page, size, sortField, sortDir);
        } else if (brandId != null) {
            productPage = productService.getProductsByBrand(brandId, page, size, sortField, sortDir);
        } else if (categoryId != null) {
            productPage = productService.getProductsByCategory(categoryId, page, size, sortField, sortDir);
        } else {
            productPage = productService.getProducts(page, size, sortField, sortDir);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        model.addAttribute("brandId", brandId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("pageSize", size);
        return "product/product-list";
    }

    // ===== LIVE SEARCH fragment =====
    @GetMapping("/search-fragment")
    public String searchFragment(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) Integer brandId,
                                 @RequestParam(required = false) Integer categoryId,
                                 @RequestParam(defaultValue = "productId") String sortField,
                                 @RequestParam(defaultValue = "asc") String sortDir,
                                 @RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "8") int size,
                                 Model model) {
        Page<Product> productPage =
                productService.search(keyword, brandId, categoryId, page, size, sortField, sortDir);
        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        return "product/product-list :: tbodyRows";
    }

    // ===== LIVE SEARCH block =====
    @GetMapping("/search-block")
    public String searchBlock(@RequestParam(required = false) String keyword,
                              @RequestParam(required = false) Integer brandId,
                              @RequestParam(required = false) Integer categoryId,
                              @RequestParam(defaultValue = "productId") String sortField,
                              @RequestParam(defaultValue = "asc") String sortDir,
                              @RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "8") int size,
                              Model model) {
        Page<Product> productPage = productService.search(keyword, brandId, categoryId, page, size, sortField, sortDir);

        model.addAttribute("products",     productPage.getContent());
        model.addAttribute("currentPage",  page);
        model.addAttribute("totalPages",   productPage.getTotalPages());
        model.addAttribute("totalItems",   productPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir",   sortDir);
        model.addAttribute("reverseSortDir", "asc".equalsIgnoreCase(sortDir) ? "desc" : "asc");
        model.addAttribute("brandId",  brandId);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword",  keyword);
        model.addAttribute("pageSize", size);
        return "product/product-list :: listWrapper";
    }

    // ===== FORM THÊM =====
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-form";
    }

    // ===== SPEC FORM (ADD) — rỗng/mặc định =====
    @GetMapping("/spec-form")
    public String getSpecForm(@RequestParam("categoryId") int categoryId) {
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
            case 10 -> "product/specs/other-spec-form";
            default -> "product/specs/default-spec-form";
        };
    }

    // ===== SPEC FORM (EDIT) — dùng folder spec-edit, có prefill =====
    @GetMapping("/spec-form-edit")
    public String getSpecFormForEdit(@RequestParam("categoryId") int categoryId,
                                     @RequestParam("productId") int productId,
                                     Model model) {

        // Prefill object vào model
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

        // Trả về fragment trong folder spec-edit
        return switch (categoryId) {
            case 1 -> "product/spec-edit/mainboard-spec-edit";
            case 2 -> "product/spec-edit/cpu-spec-edit";
            case 3 -> "product/spec-edit/gpu-spec-edit";
            case 4 -> "product/spec-edit/memory-spec-edit";
            case 5 -> "product/spec-edit/storage-spec-edit";
            case 6 -> "product/spec-edit/case-spec-edit";
            case 7 -> "product/spec-edit/powersupply-spec-edit";
            case 8 -> "product/spec-edit/cooling-spec-edit";
            // Không có bảng cho 9/10 trong upsert -> trả về trống/fallback
            default -> "product/spec-edit/empty-spec-edit";
        };
    }

    // ===== LƯU (THÊM MỚI) =====
    @PostMapping("/save")
    @Transactional
    public String saveProduct(@ModelAttribute("product") Product product,
                              @RequestParam("category.categoryId") int categoryId,
                              @RequestParam("brand.brandId") int brandId,
                              @RequestParam Map<String, String> params,
                              @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles
    ) throws IOException {

        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        product.setCategory(category);
        product.setBrand(brand);

        // 1) Lưu Product trước
        Product savedProduct = productService.addProduct(product);

        // 2) Lưu ảnh
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> images = saveImagesToStatic(imageFiles, savedProduct);
            imageRepository.saveAll(images);
            savedProduct.setImages(images);
            productService.updateProduct(savedProduct);
        }

        // 3) Lưu thông số theo category
        upsertSpec(savedProduct, categoryId, params, true);

        return "redirect:/staff/products/list";
    }

    // ===== FORM SỬA =====
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) return "redirect:/staff/products/list";

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-update";
    }

    // ===== CẬP NHẬT =====
    @PostMapping("/edit")
    @Transactional
    public String updateProduct(@ModelAttribute("product") Product incoming,
                                @RequestParam("category.categoryId") int categoryId,
                                @RequestParam("brand.brandId") int brandId,
                                @RequestParam Map<String, String> params,
                                @RequestParam(value = "imageFiles", required = false) List<MultipartFile> imageFiles
    ) throws IOException {

        Product current = productService.getProductById(incoming.getProductId());
        if (current == null) return "redirect:/staff/products/list";

        Integer oldCatId = (current.getCategory() != null) ? current.getCategory().getCategoryId() : null;

        // Cập nhật field cơ bản
        current.setProductName(incoming.getProductName());
        current.setDescription(incoming.getDescription());
        current.setSpecification(incoming.getSpecification());
        current.setPrice(incoming.getPrice());
        current.setStatus(incoming.isStatus());

        // Cập nhật category/brand
        Category category = categoryRepository.findById(categoryId).orElse(null);
        Brand brand = brandRepository.findById(brandId).orElse(null);
        current.setCategory(category);
        current.setBrand(brand);

        Product updated = productService.updateProduct(current);

        // Thêm ảnh mới (nếu có)
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> newImages = saveImagesToStatic(imageFiles, updated);
            imageRepository.saveAll(newImages);
        }

        // Nếu đổi category -> xoá spec cũ không còn dùng
        if (oldCatId == null || !oldCatId.equals(categoryId)) {
            cleanupOtherSpecs(updated.getProductId(), categoryId);
        }

        // Upsert spec theo category hiện tại
        upsertSpec(updated, categoryId, params, false);

        return "redirect:/staff/products/list";
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
                pcCase.setPsuMaxLength(getInt(params, "pcase.psu.maxLength"));
                caseRepository.save(pcCase);
            }
            case 7 -> {
                PowerSupply psu = powerSupplyRepository.findByProduct_ProductId(product.getProductId())
                        .orElseGet(PowerSupply::new);
                psu.setProduct(product);
                psu.setWattage(getInt(params, "psu.wattage"));
                psu.setEfficiency(params.get("psu.efficiency"));
                psu.setModular(getBool(params, "psu.modular"));
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
            default -> { /* không có spec tương ứng -> bỏ qua */ }
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
        Path uploadPath = Paths.get(new File("src/main/resources/static/image").getAbsolutePath());
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);

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
            }
        }
        return images;
    }
}
