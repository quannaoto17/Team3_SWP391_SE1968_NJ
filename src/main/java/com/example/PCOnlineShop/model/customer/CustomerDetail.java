package com.example.PCOnlineShop.model.customer;

import com.example.PCOnlineShop.model.staff.Account;
import jakarta.persistence.*;

public class CustomerDetail {
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
}
