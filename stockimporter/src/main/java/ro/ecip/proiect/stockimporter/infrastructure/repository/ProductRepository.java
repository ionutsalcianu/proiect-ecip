package ro.ecip.proiect.stockimporter.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.ecip.proiect.stockimporter.infrastructure.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);
}
