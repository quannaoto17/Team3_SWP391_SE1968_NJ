package com.example.PCOnlineShop.model.staff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int accountId;

    private String phonenumber;

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Column(name = "role", length = 50)
    private String role = "Staff";

    @Column(name = "email", length = 100)
    private String email;

    @Column (name = "first_name")
    private String firstname;

    @Column (name = "last_name")
    private String lastname;

    @Column(name = "gender")
    private Boolean gender;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "enabled")
    private Boolean enabled = true;
}
