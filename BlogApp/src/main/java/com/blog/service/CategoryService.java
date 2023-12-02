package com.blog.service;

import java.util.List;

import com.blog.payload.CategoryDto;

public interface CategoryService {
	
 CategoryDto createCategory(CategoryDto categoryDto);
 CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
 void deleteCategory(Integer categoryId);
 List<CategoryDto>getall();
 CategoryDto getCategoryById(Integer categoryId);
}
