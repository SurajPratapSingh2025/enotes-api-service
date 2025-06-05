package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception{
		
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if(saveNotes) {
			return CommonUtil.createdBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}
		return CommonUtil.createdErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/download/{id}")
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	
	
	

	@GetMapping("/")
	public ResponseEntity<?> getAllNotes(@RequestBody NotesDto notesDto){
		
		List<NotesDto> notes = notesService.getAllNotes();
		if(CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	
	
	

}
