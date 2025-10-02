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
    @Column(name = "AccountID")
    private int accountId;

    private String phonenumber;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Role", length = 50)
    private String role = "Staff";

    @Column(name = "Email", length = 100)
    private String email;


    private String firstname;


    private String lastname;

    @Column(name = "Gender")
    private Boolean gender;

    @Column(name = "Address", length = 255)
    private String address;

    @Column(name = "Avatar", length = 255)
    private String avatar;

    @Column(name = "Enabled")
    private Boolean enabled = true;
}
