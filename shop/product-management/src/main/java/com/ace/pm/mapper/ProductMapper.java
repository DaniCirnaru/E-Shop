package com.ace.pm.mapper;

import com.ace.pm.dto.ProductDto;
import com.ace.pm.entity.Category;
import com.ace.pm.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toProductDto(Product product) {
        return new ProductDto(product.getName(), product.getDescription(), product.getPrice(),
                              product.getStockQuantity(), product.getCategory().getId());
    }

    public Product toProduct(ProductDto productDto, Category category)
    {
        Product product =new Product();
        product.setName(productDto.name());
        product.setDescription(productDto.description());
        product.setPrice(productDto.price());
        product.setStockQuantity(productDto.stockQuantity());
        product.setCategory(category);

        return product;
    }
}
