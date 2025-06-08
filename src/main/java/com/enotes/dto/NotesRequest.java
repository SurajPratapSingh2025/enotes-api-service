package com.enotes.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class NotesRequest {
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
	
	
	
	
	

}
