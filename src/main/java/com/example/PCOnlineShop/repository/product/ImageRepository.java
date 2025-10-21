package com.example.PCOnlineShop.repository.product;

import com.example.PCOnlineShop.model.product.Image;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository <Image,Integer> {
    List<Image> findByProduct_ProductId(int productId);
}
