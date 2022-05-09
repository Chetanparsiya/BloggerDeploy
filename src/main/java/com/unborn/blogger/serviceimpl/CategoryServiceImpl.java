package com.unborn.blogger.serviceimpl;

import com.unborn.blogger.datatransferobject.CategoryDto;
import com.unborn.blogger.entities.Category;
import com.unborn.blogger.exceptions.ResourceNotFoundException;
import com.unborn.blogger.repositories.CategoryRepo;
import com.unborn.blogger.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    public CategoryRepo categoryRepo;

    @Autowired
    public ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = categoryDtoToCategory(categoryDto);
        Category createdCategory = categoryRepo.save(category);
        return categoryToCategoryDto(createdCategory);
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {;
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        Category updatedCategory = categoryRepo.save(category);
        return categoryToCategoryDto(updatedCategory);
    }

    @Override
    public List<CategoryDto> getCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoriesDto = categories.stream().map(category -> categoryToCategoryDto(category)).collect(Collectors.toList());
        return categoriesDto;
    }

    @Override
    public CategoryDto getCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        return categoryToCategoryDto(category);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
        categoryRepo.deleteById(categoryId);
    }

    public Category categoryDtoToCategory(CategoryDto categoryDto){
        return  modelMapper.map(categoryDto, Category.class);
    }

    public CategoryDto categoryToCategoryDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }
}
