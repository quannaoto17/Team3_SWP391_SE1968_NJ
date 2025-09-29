package service.staff;

import model.staff.Account;
import model.staff.AccountDetail;
import repository.build.staff.AccountRepository;
import repository.build.staff.AccountDetailRepository;
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

