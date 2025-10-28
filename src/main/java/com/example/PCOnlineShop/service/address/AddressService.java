package com.example.PCOnlineShop.service.address;

import com.example.PCOnlineShop.model.account.Account;
import com.example.PCOnlineShop.model.account.Address;
import com.example.PCOnlineShop.repository.account.AddressRepository;
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

    public Address addNewAddress(Account account, String fullName, String phone, String address) {
        Address newAddress = new Address();
        newAddress.setAccount(account);
        newAddress.setFullName(fullName);
        newAddress.setPhone(phone);
        newAddress.setAddress(address);

        // Logic: Nếu đây là địa chỉ đầu tiên, đặt làm mặc định
        List<Address> existing = addressRepository.findByAccount(account);
        if (existing == null || existing.isEmpty()) {
            newAddress.setDefault(true);
        }

        return addressRepository.save(newAddress);
    }

    // Bạn sẽ cần thêm các hàm (ví dụ: setDefault, deleteAddress)
}