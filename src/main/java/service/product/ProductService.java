package service.product;

import model.product.Product;
import org.springframework.stereotype.Service;
import repository.product.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public int createProduct(Product product) {
        Product saveProduct = productRepository.save(product);
        return saveProduct.getProductId();
    }
}
