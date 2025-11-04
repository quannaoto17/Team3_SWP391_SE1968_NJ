package com.example.PCOnlineShop.service.address;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.repository.account.AddressRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    public List<Address> getAddressesForAccount(Account account) {
        return addressRepository.findByAccount(account);
    }

    public Optional<Address> getDefaultAddress(Account account) {
        return addressRepository.findDefaultByAccount(account);
    }

    public Address addNewAddress(Account account, String fullName, String phone, String address)
            throws IllegalArgumentException { // Báo lỗi cụ thể

        if (addressRepository.existsByAccountAndPhone(account, phone)) {
            // Ném ra lỗi để Controller bắt được
            throw new IllegalArgumentException("Số điện thoại này đã được sử dụng cho một địa chỉ khác.");
        }

        Address newAddress = new Address();
        newAddress.setAccount(account);
        newAddress.setFullName(fullName);
        newAddress.setPhone(phone);
        newAddress.setAddress(address);

        List<Address> existing = addressRepository.findByAccount(account);
        if (existing == null || existing.isEmpty()) {
            newAddress.setDefault(true);
        }

        return addressRepository.save(newAddress);
    }

    // Phương thức cập nhật địa chỉ
    @Transactional
    public Address updateAddress(Account account, int addressId, String fullName, String phone, String address)
            throws IllegalArgumentException {

        // 1. Tìm địa chỉ
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy địa chỉ với ID: " + addressId));

        // 2. Kiểm tra bảo mật: Địa chỉ này có thuộc tài khoản đang đăng nhập không?
        if (existingAddress.getAccount().getAccountId() != account.getAccountId()) {
            throw new SecurityException("Bạn không có quyền sửa địa chỉ này.");
        }

        // 3. Kiểm tra SĐT trùng (loại trừ chính nó)
        if (addressRepository.existsByAccountAndPhoneAndAddressIdNot(account, phone, addressId)) {
            throw new IllegalArgumentException("Số điện thoại này đã được sử dụng cho một địa chỉ khác.");
        }

        // 4. Cập nhật thông tin
        existingAddress.setFullName(fullName);
        existingAddress.setPhone(phone);
        existingAddress.setAddress(address);

        return addressRepository.save(existingAddress);
    }

    // Phương thức đặt làm mặc định
    @Transactional
    public void setDefaultAddress(Account account, int addressId) {
        // 1. Tìm địa chỉ muốn đặt làm mặc định
        Address newDefault = addressRepository.findById(addressId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy địa chỉ với ID: " + addressId));

        // 2. Kiểm tra bảo mật
        if (newDefault.getAccount().getAccountId() != account.getAccountId()) {
            throw new SecurityException("Bạn không có quyền thay đổi địa chỉ này.");
        }

        // 3. Bỏ tất cả mặc định cũ (dùng query mới trong Repository)
        addressRepository.clearDefaultByAccount(account);

        // 4. Đặt địa chỉ này làm mặc định và lưu
        newDefault.setDefault(true);
        addressRepository.save(newDefault);
    }
}