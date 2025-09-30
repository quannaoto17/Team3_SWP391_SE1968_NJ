package com.example.PCOnlineShop.model.staff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AccountDetail")
@Getter @Setter
@Data
@NoArgsConstructor @AllArgsConstructor
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

//    public Account getAccount() {
//        return account;
//    }
//
//    public void setAccount(Account account) {
//        this.account = account;
//    }
}
