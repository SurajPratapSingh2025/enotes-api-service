package com.enotes.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDto {
	
	private Integer id;
	
	private String title;
	
	private StatusDto status;
	
	private Integer createdBy;
	
	private Date createdOn;
	
	private Integer updatedBy;
	
	private Date updatedOn;
	
	
	@Setter
	@Getter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class StatusDto{
		private Integer id;
		private String name;
	}
	
	
	
}
