package com.example.scbook.services;


import com.example.scbook.dtos.CategoryDTO;
import com.example.scbook.models.Category;

import java.util.List;

public interface ICategoryService {
    Category createCategory(CategoryDTO categoryDTO);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    Category updateCategory(Long categoryId, CategoryDTO categoryDTO);
    void deleteCategoryById(Long categoryId);
}
