package model.build;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "memory")

public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private int capacity; // in GB
    private int speed; // in MHz
    private String type; // e.g., DDR4, DDR5
    private int modules; // number of modules (e.g., 2 for a 2x8GB kit)
    private double price; // in USD
}
