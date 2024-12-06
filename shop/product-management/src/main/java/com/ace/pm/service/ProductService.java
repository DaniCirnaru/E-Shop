package com.ace.pm.service;

import com.ace.pm.dto.ProductDto;
import com.ace.pm.entity.Category;
import com.ace.pm.entity.Product;
import com.ace.pm.mapper.ProductMapper;
import com.ace.pm.repository.CategoryRepository;
import com.ace.pm.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public ProductDto createProduct(ProductDto productDto) {
        Category category = findCategoryById(productDto);
        Product product = productMapper.toProduct(productDto, category);
        System.out.println("Mapped Product Entity: " + product); // Log the Product entity
        Product savedProduct = productRepository.save(product);
        return productMapper.toProductDto(savedProduct);
    }

    public ProductDto getProductById(Long id)
    {
        Product product = findProductById(id);
        return productMapper.toProductDto(product);
    }

    public void deleteProduct(Long id)
    {
        Product product =findProductById(id);
        productRepository.delete(product);
    }

    private Category findCategoryById(ProductDto productDto) {
        return categoryRepository.findById(productDto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category with id" + productDto.categoryId() + " not found"));
    }
    private Product findProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with id" + id + " not found"));
    }

    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productMapper::toProductDto)
                .collect(Collectors.toList());
    }

}
