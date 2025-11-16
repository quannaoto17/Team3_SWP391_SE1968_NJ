package com.example.PCOnlineShop.model.account;

import com.example.PCOnlineShop.constant.RoleName;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import java.util.List;


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

    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 15, message = "Phone number must be between 9 and 15 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50)
    private RoleName role;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", length = 100)
    private String email;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstname;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastname;

    @Column(name = "gender")
    private Boolean gender;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Address> addresses;

    @Transient
    private String address;

    @Column(name = "enabled")
    private Boolean enabled = true;

    public String getFullName() {
        String first = (this.firstname != null) ? this.firstname : "";
        String last = (this.lastname != null) ? this.lastname : "";
        return (first + " " + last).trim();
    }

    public String getDefaultAddress() {
        if (addresses == null || addresses.isEmpty()) {
            return "N/A";
        }

        return addresses.stream()
                .filter(Address::isDefault)
                .findFirst()
                .map(Address::getAddress)
                .orElse(addresses.get(0).getAddress());
    }

}
