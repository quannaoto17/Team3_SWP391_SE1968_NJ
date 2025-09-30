package com.example.PCOnlineShop.service.staff;

import com.example.PCOnlineShop.model.staff.Account;
import com.example.PCOnlineShop.model.staff.AccountDetail;
import com.example.PCOnlineShop.repository.build.staff.AccountRepository;
import com.example.PCOnlineShop.repository.build.staff.AccountDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    private final AccountRepository accountRepo;
    private final AccountDetailRepository detailRepo;

    public StaffService(AccountRepository accountRepo, AccountDetailRepository detailRepo) {
        this.accountRepo = accountRepo;
        this.detailRepo = detailRepo;
    }

    public List<AccountDetail> getAllStaff() {
        return detailRepo.findAll();
    }

    public AccountDetail addStaff(Account account, AccountDetail detail) {
        Account savedAcc = accountRepo.save(account);
        detail.setAccount(savedAcc);
        return detailRepo.save(detail);
    }

    public AccountDetail updateStaff(AccountDetail detail) {
        return detailRepo.save(detail);
    }

    public AccountDetail getStaffById(int id) {
        return detailRepo.findById(id).orElse(null);
    }
}

