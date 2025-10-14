package com.example.PCOnlineShop.model.account;

import com.example.PCOnlineShop.constant.RoleName;
import jakarta.persistence.*;
import jakarta.validation.constraints.*; // Giữ lại import này
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

    // Giữ phiên bản có validation
    @NotBlank(message = "Phone number is required")
    @Size(min = 9, max = 15, message = "Phone number must be between 9 and 15 digits")
    @Column(name = "phone_number")
    private String phoneNumber;

    // Giữ phiên bản có validation
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 50)
    private RoleName role;

    // Giữ phiên bản có validation
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(name = "email", length = 100)
    private String email;

    // Giữ phiên bản có validation
    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstname;

    // Giữ phiên bản có validation
    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastname;

    public String getFullName() {
        return firstname + " " + lastname;
    }

    // Nhánh main đã xóa trường này. Để đồng bộ, bạn cũng nên xóa nó.
    // Nếu vẫn cần, bạn phải trao đổi lại với team.
    // @Column(name = "gender")
    // private Boolean gender;

    // Giữ phiên bản có validation
    @NotBlank(message = "Address is required")
    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "enabled")
    private Boolean enabled = true;
}