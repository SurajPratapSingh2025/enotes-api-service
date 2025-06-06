package com.enotes.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {
	
private Integer id;
	
	private String title;
	
	private String description;
	
	private CategoryDto category;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updateBy;
	
	private Date updatedOn;
	
	private FilesDto fileDetails;
	
	private Boolean isDeleted;
	
	private LocalDateTime deletedOn;
	
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class FilesDto{
		private Integer id;
		
		private String originalFileName;
		
		private String displayFileName;
	}
	
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CategoryDto{
		private Integer id;
		private String name;
	}
}
