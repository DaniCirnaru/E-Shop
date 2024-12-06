package com.ace.pm.mapper;

import com.ace.pm.dto.CategoryDto;
import com.ace.pm.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toCategoryDto(Category category)
    {
        return new CategoryDto(category.getId(), category.getName(),category.getDescription());
    }

    public Category toCategory(CategoryDto categoryDto)
    {
        Category category =new Category();
        category.setId(categoryDto.id());
        category.setName(categoryDto.name());
        category.setDescription(categoryDto.description());
        return category;
    }
}
