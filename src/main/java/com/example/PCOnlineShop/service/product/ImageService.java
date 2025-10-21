package com.example.PCOnlineShop.service.product;

import com.example.PCOnlineShop.model.product.Image;
import com.example.PCOnlineShop.model.product.Product;
import com.example.PCOnlineShop.repository.product.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ImageService {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

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
    public String storeImageFile(MultipartFile file) throws IOException {
        String uploadDir = "./uploads/images/";
        String originalFilename = file.getOriginalFilename();
        String timestamp = String.valueOf(LocalDateTime.now().toEpochSecond(java.time.ZoneOffset.UTC));
        String newFilename = timestamp + "_" + originalFilename;
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Path filePath = uploadPath.resolve(newFilename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return "/image/" + newFilename;
    }
}

