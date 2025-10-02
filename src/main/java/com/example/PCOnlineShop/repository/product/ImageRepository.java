package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Image;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;

public interface ImageRepository extends JpaAttributeConverter<Image, Integer> {
}
