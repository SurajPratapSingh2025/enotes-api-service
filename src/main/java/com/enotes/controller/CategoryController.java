package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.CategoryDto;
import com.enotes.dto.CategoryResponse;
import com.enotes.endpoint.CategoryEndpoint;
import com.enotes.service.CategoryService;
import com.enotes.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CategoryController implements CategoryEndpoint{
	
	@Autowired
	private CategoryService categoryService;
	
	@PostMapping("/save")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> saveCategory(@RequestBody CategoryDto categoryDto){
		
		Boolean saveCategory = categoryService.saveCategory(categoryDto);
		if(saveCategory) {
			return CommonUtil.createdBuildResponseMessage("saved success", HttpStatus.CREATED);
//			return new ResponseEntity<>("saved success",HttpStatus.CREATED);
		}else {
			return CommonUtil.createdErrorResponseMessage("Category not saved", HttpStatus.INTERNAL_SERVER_ERROR);
//			return new ResponseEntity<>("not savaed",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Override
	public ResponseEntity<?> getAllCategory(){
		
		List<CategoryDto> allCategory=categoryService.getAllCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
			return CommonUtil.createdBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	
	@Override
	public ResponseEntity<?> getActiveCategory(){
		List<CategoryResponse> allCategory=categoryService.getActiveCategory();
		
		if(CollectionUtils.isEmpty(allCategory)) {
			return ResponseEntity.noContent().build();
		}else {
			return CommonUtil.createdBuildResponse(allCategory, HttpStatus.OK);
//			return new ResponseEntity<>(allCategory,HttpStatus.OK);
		}
	}
	
	
	@Override
	public ResponseEntity<?> getCategoryDetailsById(@PathVariable Integer id) throws Exception{
		
		
		CategoryDto categoryDto = categoryService.getCategoryById(id);
		if(ObjectUtils.isEmpty(categoryDto)) {
			return CommonUtil.createdErrorResponseMessage("Internal Server Error", HttpStatus.NOT_FOUND);
//			return new ResponseEntity<> ("Internal Server Error", HttpStatus.NOT_FOUND);
		}
		return CommonUtil.createdBuildResponse(categoryDto, HttpStatus.OK);
//		return new ResponseEntity<> (categoryDto, HttpStatus.OK);
		
		
	}
	
	
	@Override
	public ResponseEntity<?> deleteCategoryById(@PathVariable Integer id){
		
		Boolean deleted = categoryService.deleteCategory(id);
		if(deleted) {
			return CommonUtil.createdBuildResponse("Category deleted success", HttpStatus.OK);
//			return new ResponseEntity<> ("Category deleted success", HttpStatus.OK);
		}
		return CommonUtil.createdErrorResponseMessage("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
//		return new ResponseEntity<> ("Category not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
