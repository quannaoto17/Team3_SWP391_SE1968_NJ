package com.example.PCOnlineShop.model;

import com.example.PCOnlineShop.constant.RoleName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private Integer id;
    private String phoneNumber;
    private String password;

    // all role has prefix "ROLE_", e.g., "ROLE_USER", "ROLE_ADMIN"
    private RoleName role;
    private boolean active;
}
