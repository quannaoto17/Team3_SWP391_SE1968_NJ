package com.example.PCOnlineShop.model.staff;

import jakarta.persistence.*;

@Entity
@Table(name = "AccountDetail")
public class AccountDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountDetailId;

    @ManyToOne
    @JoinColumn(name = "accountId", nullable = false)
    private Account account;

    private String email;
    private String firstName;
    private String lastName;
    private String address;

    public AccountDetail() {}

    public AccountDetail(int accountDetailId, Account account, String email,
                         String firstName, String lastName, String address) {
        this.accountDetailId = accountDetailId;
        this.account = account;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
    }

    public int getAccountDetailId() {
        return accountDetailId;
    }

    public void setAccountDetailId(int accountDetailId) {
        this.accountDetailId = accountDetailId;
    }

    public Account getAccount() {               // <- getter cần có
        return account;
    }

    public void setAccount(Account account) {   // <- setter cần có
        this.account = account;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
