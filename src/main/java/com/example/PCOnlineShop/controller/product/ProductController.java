package com.example.PCOnlineShop.controller.product;

import com.example.PCOnlineShop.model.product.*;
import com.example.PCOnlineShop.model.build.*;
import com.example.PCOnlineShop.repository.product.*;
import com.example.PCOnlineShop.repository.build.*;
import com.example.PCOnlineShop.service.product.ProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
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

    // Repository các bảng build
    private final CpuRepository cpuRepository;
    private final GpuRepository gpuRepository;
    private final MainboardRepository mainboardRepository;
    private final MemoryRepository memoryRepository;
    private final StorageRepository storageRepository;
    private final CaseRepository caseRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoolingRepository coolingRepository;

    // ===== DANH SÁCH SẢN PHẨM (có keyword để search, giữ phân trang + sort) =====
    @GetMapping("/list")
    public String listProducts(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(defaultValue = "productId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Integer brandId,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,   // NEW
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
        model.addAttribute("keyword", keyword); // NEW

        model.addAttribute("brands", brandRepository.findAll());
        model.addAttribute("categories", categoryRepository.findAll());
        return "product/product-list";
    }
    // ===== LIVE SEARCH: Trả về fragment <tbody> đã render để JS thay trực tiếp =====
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
        // Nếu muốn update cả pagination theo kết quả search, có thể add currentPage/totalPages và trả fragment lớn hơn
        return "product/product-list :: tbodyRows";
    }
    // Trả về cả bảng + pagination theo keyword/filter/sort/page hiện thời
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

        return "product/product-list :: listWrapper"; // fragment mới (xem bước 2)
    }
    // ============ FORM THÊM SẢN PHẨM =============
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("brands", brandRepository.findAll());
        return "product/product-form";
    }

    // ============ LOAD FORM THÔNG SỐ DỰA TRÊN CATEGORY =============
    @GetMapping("/spec-form")
    public String getSpecForm(@RequestParam("categoryId") int categoryId) {
        switch (categoryId) {
            case 1: return "product/specs/mainboard-spec-form";
            case 2: return "product/specs/cpu-spec-form";
            case 3: return "product/specs/gpu-spec-form";
            case 4: return "product/specs/memory-spec-form";
            case 5: return "product/specs/storage-spec-form";
            case 6: return "product/specs/case-spec-form";
            case 7: return "product/specs/powersupply-spec-form";
            case 8: return "product/specs/cooling-spec-form";
            case 9: return "product/specs/fan-spec-form";
            case 10: return "product/specs/other-spec-form";
            default: return "product/specs/default-spec-form";
        }
    }

    // ============ LƯU PRODUCT VÀ THÔNG SỐ KỸ THUẬT ============
    @PostMapping("/save")
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

        // 1️⃣ Lưu Product trước
        Product savedProduct = productService.addProduct(product);

        // 2️⃣ Lưu ảnh (giữ nguyên logic cũ)
        if (imageFiles != null && !imageFiles.isEmpty()) {
            List<Image> images = saveImagesToStatic(imageFiles, savedProduct);
            imageRepository.saveAll(images);
            savedProduct.setImages(images);
            productService.updateProduct(savedProduct);
        }

        // 3️⃣ Lưu bảng kỹ thuật theo Category
        switch (categoryId) {
            case 1 -> { // Mainboard
                Mainboard mb = new Mainboard();
                mb.setProduct(savedProduct);
                mb.setSocket(params.get("mainboard.socket"));
                mb.setChipset(params.get("mainboard.chipset"));
                mb.setFormFactor(params.get("mainboard.formFactor"));
                mainboardRepository.save(mb);
            }
            case 2 -> { // CPU
                CPU cpu = new CPU();
                cpu.setProduct(savedProduct);
                cpu.setSocket(params.get("cpu.socket"));
                cpu.setTdp(Integer.parseInt(params.get("cpu.tdp")));
                cpu.setMaxMemorySpeed(Integer.parseInt(params.get("cpu.maxMemorySpeed")));
                cpu.setMemoryChannels(Integer.parseInt(params.get("cpu.memoryChannels")));
                cpu.setHasIGPU(Boolean.parseBoolean(params.get("cpu.hasIGPU")));
                cpu.setPcieVersion(params.get("cpu.pcieVersion"));
                cpuRepository.save(cpu);
            }
            case 3 -> { // GPU
                GPU gpu = new GPU();
                gpu.setProduct(savedProduct);
                gpu.setVram(Integer.parseInt(params.get("gpu.vram")));
                gpu.setMemoryType(params.get("gpu.memoryType"));
                gpu.setTdp(Integer.parseInt(params.get("gpu.tdp")));
                gpu.setLength(Integer.parseInt(params.get("gpu.length")));
                gpu.setGpuInterface(params.get("gpu.gpuInterface"));
                gpu.setPcieVersion(params.get("gpu.pcieVersion"));
                gpuRepository.save(gpu);
            }
            case 4 -> { // Memory
                Memory mem = new Memory();
                mem.setProduct(savedProduct);
                mem.setCapacity(Integer.parseInt(params.get("mem.capacity")));
                mem.setType(params.get("mem.type"));
                mem.setSpeed(Integer.parseInt(params.get("mem.speed")));
                mem.setTdp(Integer.parseInt(params.get("mem.tdp")));
                memoryRepository.save(mem);
            }
            case 5 -> { // Storage
                Storage storage = new Storage();
                storage.setProduct(savedProduct);
                storage.setCapacity(Integer.parseInt(params.get("storage.capacity")));
                storage.setType(params.get("storage.type"));
                storage.setInterfaceType(params.get("storage.interfaceType"));
                storage.setReadSpeed(Integer.parseInt(params.get("storage.readSpeed")));
                storage.setWriteSpeed(Integer.parseInt(params.get("storage.writeSpeed")));
                storageRepository.save(storage);
            }
            case 6 -> { // Case
                Case pcCase = new Case();
                pcCase.setProduct(savedProduct);
                pcCase.setFormFactor(params.get("pcase.formFactor"));
                pcCase.setGpuMaxLength(Integer.parseInt(params.get("pcase.gpu.maxLength")));
                pcCase.setPsuMaxLength(Integer.parseInt(params.get("pcase.psu.maxLength")));

                caseRepository.save(pcCase);
            }
            case 7 -> { // Power Supply
                PowerSupply psu = new PowerSupply();
                psu.setProduct(savedProduct);
                psu.setWattage(Integer.parseInt(params.get("psu.wattage")));
                psu.setEfficiency(params.get("psu.efficiency"));
                psu.setModular(Boolean.parseBoolean(params.get("psu.modular")));
                powerSupplyRepository.save(psu);
            }
            case 8 -> { // Cooling
                Cooling cl = new Cooling();
                cl.setProduct(savedProduct);
                cl.setType(params.get("cl.type"));
                cl.setFanSize(Integer.parseInt(params.get("cl.fanSize")));
                cl.setRadiatorSize(params.get("cl.radiatorSize"));
                cl.setTdp(Integer.parseInt(params.get("cl.maxTdp")));
                coolingRepository.save(cl);
            }

        }

        return "redirect:/staff/products/list";
    }

    // ===== Giữ nguyên logic lưu ảnh =====
    private List<Image> saveImagesToStatic(List<MultipartFile> imageFiles, Product product) throws IOException {
        Path uploadPath = Paths.get(new File("src/main/resources/static/image").getAbsolutePath());
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
            }
        }
        return images;
    }
}
