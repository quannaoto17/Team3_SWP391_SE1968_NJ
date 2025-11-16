package com.example.PCOnlineShop.controller.address;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.repository.account.AccountRepository;
import com.example.PCOnlineShop.service.address.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.regex.Pattern; // ✅ Thêm import

@RestController
public class AddressController {

    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountRepository accountRepository;

    private static final Pattern NAME_REGEX = Pattern.compile("^[a-zA-ZÀ-ỹ\\s]+$");
    // 2. SĐT Việt Nam: 10 số, bắt đầu bằng 03, 05, 07, 08, 09
    private static final Pattern PHONE_REGEX = Pattern.compile("^(0[3|5|7|8|9])+([0-9]{8})$");


    private Account getCurrentAccount(UserDetails userDetails) {
        if (userDetails == null) return null;
        return accountRepository.findByPhoneNumber(userDetails.getUsername()).orElse(null);
    }

    @PostMapping("/address/add")
    public ResponseEntity<?> addNewAddress(
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String address,
            @AuthenticationPrincipal UserDetails currentUserDetails) {

        Account account = getCurrentAccount(currentUserDetails);
        if (account == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Please login again."));
        }

        if (fullName == null || fullName.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name must not blank."));
        }
        if (phone == null || phone.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Phone number must not blank."));
        }
        if (address == null || address.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Address must not blank."));
        }

        // 2. Validate ký tự đặc biệt (Tên)
        if (!NAME_REGEX.matcher(fullName).matches()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name must contains character and space."));
        }

        // 3. Validate SĐT
        if (!PHONE_REGEX.matcher(phone).matches()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No qualified phone number (10 numbers and start with 03, 05, 07, 08, 09)."));
        }

        try {

            Address newAddress = addressService.addNewAddress(account, fullName, phone, address);

            return ResponseEntity.ok(newAddress);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(Map.of("error", "Error system."));
        }
    }

    //  Endpoint Cập nhật địa chỉ
    @PostMapping("/address/update")
    public ResponseEntity<?> updateAddress(
            @RequestParam("addressId") int addressId,
            @RequestParam("fullName") String fullName,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @AuthenticationPrincipal UserDetails currentUserDetails) {

        Account account = getCurrentAccount(currentUserDetails);
        if (account == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Please login again."));
        }

        if (fullName == null || fullName.isBlank() || phone == null || phone.isBlank() || address == null || address.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Please fulfill information."));
        }
        if (!NAME_REGEX.matcher(fullName).matches()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Name must contains character and space."));
        }
        if (!PHONE_REGEX.matcher(phone).matches()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No qualified phone number (10 numbers and start with 03, 05, 07, 08, 09)."));
        }

        try {
            // Gọi service update
            Address updatedAddress = addressService.updateAddress(account, addressId, fullName, phone, address);
            return ResponseEntity.ok(updatedAddress); // Trả về địa chỉ đã cập nhật

        } catch (IllegalArgumentException e) { // Lỗi trùng SĐT
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (EntityNotFoundException | SecurityException e) { // Lỗi không tìm thấy hoặc không có quyền
            return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error system."));
        }
    }

    // Endpoint Đặt làm mặc định
    @PostMapping("/address/set-default")
    public ResponseEntity<?> setDefaultAddress(
            @RequestParam("addressId") int addressId,
            @AuthenticationPrincipal UserDetails currentUserDetails) {

        Account account = getCurrentAccount(currentUserDetails);
        if (account == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Please login again."));
        }

        try {
            addressService.setDefaultAddress(account, addressId);
            // Thành công, chỉ cần trả về tin nhắn
            return ResponseEntity.ok(Map.of("message", "Đặt làm mặc định thành công."));

        } catch (EntityNotFoundException | SecurityException e) { // Lỗi không tìm thấy hoặc không có quyền
            return ResponseEntity.status(403).body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Map.of("error", "Error system."));
        }
    }
}