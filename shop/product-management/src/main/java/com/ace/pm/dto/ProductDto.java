package com.ace.pm.dto;

public record ProductDto(
        String name,
        String description,
        float price,
        int stockQuantity,
        Long categoryId
) {
}
