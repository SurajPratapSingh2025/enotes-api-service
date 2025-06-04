package com.enotes.service;

import java.util.List;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;

public interface CategoryService {
<<<<<<< Updated upstream
	public Boolean saveCategory(Category category);
	public List<Category> getAllCatory();
=======
	
	public Boolean saveCategory(CategoryDto categoryDto);
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();
	
	
>>>>>>> Stashed changes
}
