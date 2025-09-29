package model.build;
import lombok.*;
import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "cpu")

public class CPU {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String brand;
    private String model;
    private int cores;
    private int threads;
    private double baseClock; // in GHz
    private double boostClock; // in GHz
    private String socketType;
    private int tdp; // in Watts
    private double price; // in USD
}
