package model.build;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Other {
    private String name;
    private String brand;
    private String type; // e.g., Monitor, Keyboard, Mouse, Speaker, Headset
    private String description;
    private double price; // in USD
}
