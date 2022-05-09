package com.unborn.blogger.services;

import com.unborn.blogger.datatransferobject.CategoryDto;
import com.unborn.blogger.entities.Category;

import java.util.List;

public interface CategoryService {

    public CategoryDto createCategory(CategoryDto categoryDto);

    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId);

    public List<CategoryDto> getCategories();

    public CategoryDto getCategory(Long categoryId);

    public void deleteCategory(Long categoryId);


}
