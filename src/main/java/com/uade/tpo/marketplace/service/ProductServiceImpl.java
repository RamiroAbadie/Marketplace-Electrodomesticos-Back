package com.uade.tpo.marketplace.service;

import com.uade.tpo.marketplace.entity.Product;
import com.uade.tpo.marketplace.entity.Category;
import com.uade.tpo.marketplace.entity.dto.CategoryResponse;
import com.uade.tpo.marketplace.entity.dto.ProductResponse;
import com.uade.tpo.marketplace.repository.OrderItemRepository;
import com.uade.tpo.marketplace.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Se usa para bloquear el borrado de un producto si se encuentra en un Orden
    @Autowired
    private OrderItemRepository orderItemRepository;


    @Override
    public List<Product> getAllProducts() {
        // Estamos usando un metodo que ya viene implementado por Spring Data JPA
        return productRepository.findAll();
    }

    @Override
    public List<Product> getAvailableProducts() {
        return productRepository.findByStockGreaterThan(0);
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }


    /// /////////////////////////////
    ///
    /// Se agrega esta funcion para obtener el dto de Product (ProductResponse)
    /// Es necesario extender esto al resto de los GET tanto en esta como en el resto de las clases
    /// Se devuelve List<ProductResponse> en Controller y Service; Repository traabaja con Product directamente
    private ProductResponse mapToDto(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory().getDescription()
        );
    }


    public List<ProductResponse> getProductsByPriceRange(BigDecimal min, BigDecimal max) {
        return productRepository.findByPriceBetween(min, max)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
/// ///////////////////////////////////

    public List<Product> getProductsByPriceLess(BigDecimal min) {
        return productRepository.findByPriceLessThan(min);
    }

    @Override
    public Product createProduct(String description, BigDecimal price, Integer stock, Category category) {
        Product product = new Product();
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        return productRepository.save(product);
    }
    
    @Override
    //Optional<T>	Evita errores por null. Te obliga a manejar el caso “no existe”
    public Optional<Product> getProductById(Long id) {
        // Estamos usando un metodo que ya viene implementado por Spring Data JPA
        return productRepository.findById(id);
    }

    @Override
    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    
        if (orderItemRepository.existsByProduct(product)) {
            throw new RuntimeException("No se puede eliminar el producto porque ya fue comprado en una orden.");
        }
    
        productRepository.deleteById(id);
    }    

    @Override
    public Product save(Product product) {
        // Estamos usando un metodo que ya viene implementado por Spring Data JPA
        return productRepository.save(product);
    }
}
