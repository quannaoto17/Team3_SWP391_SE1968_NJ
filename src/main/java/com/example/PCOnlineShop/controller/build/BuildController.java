package com.example.PCOnlineShop.controller.build;

import com.example.PCOnlineShop.dto.build.BuildItemDto;
import com.example.PCOnlineShop.dto.cart.CartItemDTO;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.ProductRepository;
import com.example.PCOnlineShop.repository.build.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.support.SessionStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes({"buildItems"})
@RequestMapping("/build")
public class BuildController {

    private final ProductRepository productRepository;
    private final CaseRepository caseRepository;
    private final StorageRepository storageRepository;
    private final PowerSupplyRepository powerSupplyRepository;
    private final CoolingRepository coolingRepository;

    public BuildController(ProductRepository productRepository,
                          CaseRepository caseRepository,
                          StorageRepository storageRepository,
                          PowerSupplyRepository powerSupplyRepository,
                          CoolingRepository coolingRepository) {
        this.productRepository = productRepository;
        this.caseRepository = caseRepository;
        this.storageRepository = storageRepository;
        this.powerSupplyRepository = powerSupplyRepository;
        this.coolingRepository = coolingRepository;
    }

    @ModelAttribute("buildItems")
    public BuildItemDto buildItem() {
        return new BuildItemDto();
    }

    @GetMapping("/start" )
    public String startBuild() {
        return "/build/build-pc";
    }

    @GetMapping("/debug-images")
    public String debugImages(Model model) {
        model.addAttribute("cases", caseRepository.findAll());
        model.addAttribute("storages", storageRepository.findAll());
        model.addAttribute("psus", powerSupplyRepository.findAll());
        model.addAttribute("coolings", coolingRepository.findAll());
        return "/build/debug-images";
    }

    @GetMapping("/preset-result")
    public String showPresetResult() {
        return "/build/preset-result";
    }

    @GetMapping("/startover")
    public String startOver(SessionStatus sessionStatus, org.springframework.ui.Model model) {
        // Tell Spring to clear the session-managed `buildItems`
        sessionStatus.setComplete();

        // Provide a fresh empty DTO so the next request's model is clean
        model.addAttribute("buildItems", new BuildItemDto());

        // Redirect to the first build page (avoids reusing form values)
        return "redirect:/build/mainboard";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/finish")
    public String finishBuild(@ModelAttribute("buildItems") BuildItemDto buildItems,
                              HttpSession session,
                              SessionStatus sessionStatus,
                              RedirectAttributes redirectAttributes, Model model) {

        List<Integer> productIds = new ArrayList<>();

        if (buildItems.getMainboard() != null ) {
            productIds.add(buildItems.getMainboard().getProductId());
        }
        if (buildItems.getCpu() != null ) {
            productIds.add(buildItems.getCpu().getProductId());
        }
        if (buildItems.getGpu() != null ) {
            productIds.add(buildItems.getGpu().getProductId());
        }
        if (buildItems.getMemory() != null ) {
            productIds.add(buildItems.getMemory().getProductId());
        }
        if (buildItems.getStorage() != null ) {
            productIds.add(buildItems.getStorage().getProductId());
        }
        if (buildItems.getPowerSupply() != null ) {
            productIds.add(buildItems.getPowerSupply().getProductId());
        }
        if (buildItems.getPcCase() != null ) {
            productIds.add(buildItems.getPcCase().getProductId());
        }
        if (buildItems.getCooling() != null ) {
            productIds.add(buildItems.getCooling().getProductId());
        }

        // Dùng flash attribute để giữ qua redirect và chắc chắn tên là "productIds"
        redirectAttributes.addFlashAttribute("productIds", productIds);

        // Option: clear the session-managed buildItems if bạn muốn
        sessionStatus.setComplete();

        // redirect tuyệt đối tới controller /cart/addListItem
        return "redirect:/cart/addListItem";
    }

}
