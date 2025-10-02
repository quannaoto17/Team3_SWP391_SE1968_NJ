package com.example.PCOnlineShop.model.customer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID")
    private int accountId;

    private String phonenumber;

    @Column(name = "Password", nullable = false, length = 255)
    private String password;

    @Column(name = "Role", length = 50)
    private String role = "Customer";

    @Column(name = "Email", length = 100)
    private String email;

    @Column(name = "FirstName", length = 50)
    private String firstname;

    @Column(name = "LastName", length = 50)
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
