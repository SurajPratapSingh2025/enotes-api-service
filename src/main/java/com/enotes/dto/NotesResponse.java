package com.enotes.dto;

import java.util.List;

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
public class NotesResponse {
	
	private List<NotesDto> notes;
	
	private Integer pageNo;
	private Integer pageSize;
	private long totalElements;
	private Integer totalPages;
	private Boolean isFirst;
	private Boolean isLast;
	
	
	
}
