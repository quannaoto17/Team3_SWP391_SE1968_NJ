package model.staff;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "AccountDetail")
@Getter @Setter
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
}
