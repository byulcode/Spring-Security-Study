package study.springsecurity.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import study.springsecurity.entity.Product;
import study.springsecurity.repository.ProductRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
