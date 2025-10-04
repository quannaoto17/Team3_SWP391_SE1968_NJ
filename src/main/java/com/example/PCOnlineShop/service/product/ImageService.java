package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Image;
import com.example.PCOnlineShop.repository.product.ImageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    public void addImage(Image image) {
        imageRepository.save(image);
    }

    public void updateImage(Image image) {
        imageRepository.save(image);
    }

    public void saveImage(Image image) {
        imageRepository.save(image);
    }
}

