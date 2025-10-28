package com.example.PCOnlineShop.model.build;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Other {
    @NotBlank(message = "Name is required")
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Name must not contain special characters")
    private String name;

    @NotBlank(message = "Brand is required")
    @Size(min = 1, max = 100, message = "Brand must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Brand must not contain special characters")
    private String brand;

    @NotBlank(message = "Type is required")
    @Size(min = 1, max = 100, message = "Type must be between 1 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-]+$", message = "Type must not contain special characters")
    private String type;

    @NotBlank(message = "Description is required")
    @Size(min = 1, max = 500, message = "Description must be between 1 and 500 characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s-.,!()]+$", message = "Description must not contain special characters")
    private String description;

   @NotNull(message = "Price is required")
   @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
   @DecimalMax(value = "10000.00", message = "Price must be at most 10000.00")
    private double price;
}
