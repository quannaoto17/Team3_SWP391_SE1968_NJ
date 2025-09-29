package model.staff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Account")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;

    @Column(nullable = false, unique = true, length = 20)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    private String role = "Staff"; // mặc định Staff
    private boolean enabled = true;
}
