package com.example.PCOnlineShop.controller.brand;

import com.example.PCOnlineShop.model.product.Brand;
import com.example.PCOnlineShop.service.product.BrandService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    // 🔹 DANH SÁCH
    @GetMapping("/list")
    public String showBrandPage(Model model) {
        model.addAttribute("brand", brandService.getAllBrands());
        return "brand/brand-list";
    }

    // 🔹 TRANG THÊM
    @GetMapping("/add")
    public String showAddBrandPage(Model model) {
        model.addAttribute("brand", new Brand());
        model.addAttribute("isEdit", false); // giúp thymeleaf biết đang ở chế độ thêm
        return "brand/brand-form";
    }

    // 🔹 XỬ LÝ THÊM
    @PostMapping("/add")
    public String createBrand(@Valid @ModelAttribute("brand") Brand brand,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "brand/brand-form";
        }

        if (brandService.existsBrandByName(brand.getName())) {
            model.addAttribute("error", "Brand name already exists");
            model.addAttribute("isEdit", false);
            return "brand/brand-form";
        }

        brand.setStatus(true); // mặc định active
        brandService.addBrand(brand);
        return "redirect:/admin/brand/list";
    }

    // 🔹 TRANG SỬA
    @GetMapping("/update/{id}")
    public String showEditBrandPage(@PathVariable int id, Model model) {
        Brand brand = brandService.getBrandById(id);
        if (brand == null) {
            throw new RuntimeException("Brand not found");
        }
        model.addAttribute("brand", brand);
        model.addAttribute("isEdit", true); // flag để form biết đang sửa
        return "brand/brand-form";
    }

    // 🔹 XỬ LÝ CẬP NHẬT
    @PostMapping("/update/{id}")
    public String updateBrand(@PathVariable int id,
                              @Valid @ModelAttribute("brand") Brand brand,
                              BindingResult result,
                              Model model) {

        Brand existing = brandService.getBrandById(id);
        if (existing == null) {
            throw new RuntimeException("Brand not found");
        }

        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "brand/brand-form";
        }

        if (!existing.getName().equalsIgnoreCase(brand.getName()) &&
                brandService.existsBrandByName(brand.getName())) {
            model.addAttribute("error", "Brand name already exists");
            model.addAttribute("isEdit", true);
            return "brand/brand-form";
        }

        existing.setName(brand.getName());
        existing.setDescription(brand.getDescription());
        existing.setStatus(brand.getStatus());

        brandService.updateBrand(existing);
        return "redirect:/admin/brand/list";
    }

    // 🔹 ĐỔI TRẠNG THÁI
    @PostMapping("/{id}/status")
    public String changeStatus(@PathVariable Integer id,
                               @RequestParam boolean status,
                               Model model) {
        Brand brand = brandService.getBrandById(id);
        if (brand == null) {
            throw new RuntimeException("Brand not found");
        }

        long productCount = brandService.countProductsOfBrand(id);
        if (!status && productCount > 0) {
            model.addAttribute("error", "Cannot deactivate brand that still has products");
            model.addAttribute("brand", brandService.getAllBrands());
            return "brand/brand-list";
        }

        brand.setStatus(status);
        brandService.updateBrand(brand);
        return "redirect:/admin/brand/list";
    }

    // 🔹 GỘP THƯƠNG HIỆU
    @PostMapping("/merge")
    public String merge(@RequestParam Integer sourceId,
                        @RequestParam Integer targetId,
                        Model model) {
        if (sourceId.equals(targetId)) {
            model.addAttribute("error", "Cannot merge a brand into itself");
            model.addAttribute("brand", brandService.getAllBrands());
            return "brand/brand-list";
        }

        long count = brandService.countProductsOfBrand(sourceId);
        brandService.reassignProducts(sourceId, targetId);

        Brand source = brandService.getBrandById(sourceId);
        if (source == null) {
            model.addAttribute("error", "Brand does not exist");
            model.addAttribute("brand", brandService.getAllBrands());
            return "brand/brand-list";
        }

        source.setStatus(false);
        brandService.updateBrand(source);

        model.addAttribute("merged", count);
        return "redirect:/admin/brand/list?merged=" + count;
    }
}
