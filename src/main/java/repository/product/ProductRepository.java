package repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import model.product.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

}
