package com.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payload.ApiResponse;
import com.blog.payload.CategoryDto;
import com.blog.service.CategoryService;
import com.blog.serviceImpl.CategoryServiceImpl;

@RestController
@RequestMapping("api/v2/")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
@PostMapping("/add")
	public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
		CategoryDto cateDto=this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(cateDto,HttpStatus.CREATED);
}


@PutMapping("/update/{categoryId}")
public ResponseEntity<CategoryDto>updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
	CategoryDto updatedCategory=this.categoryService.updateCategory(categoryDto, categoryId);
	return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
}
@GetMapping("/get-all")
public ResponseEntity<List<CategoryDto>>getall(){
	List<CategoryDto>catdto=this.categoryService.getall();
	return ResponseEntity.ok(catdto);
	}
@GetMapping("/{categoryId}")
public ResponseEntity<CategoryDto>getById(@PathVariable Integer categoryId){
	CategoryDto cat=this.categoryService.getCategoryById(categoryId);
	return ResponseEntity.ok(cat);
}
@DeleteMapping("/{categoryId}")
public ResponseEntity<ApiResponse>deleteById(@PathVariable Integer categoryId){
	this.categoryService.deleteCategory(categoryId);
	return new ResponseEntity<ApiResponse>(new ApiResponse("category deleted with categoryId",true),HttpStatus.OK);
}
}
