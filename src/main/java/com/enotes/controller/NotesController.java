package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouriteNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/notes")
public class NotesController {
	
	@Autowired
	private NotesService notesService;
	
	@PostMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception{
		
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if(saveNotes) {
			return CommonUtil.createdBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}
		return CommonUtil.createdErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@GetMapping("/download/{id}")
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
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
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllNotes(@RequestBody NotesDto notesDto){
		
		List<NotesDto> notes = notesService.getAllNotes();
		if(CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
		
	}
	

	@GetMapping("/user-notes")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo",defaultValue="0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue="10") Integer pageSize){
		
		Integer userId=2;
		NotesResponse notes = notesService.getAllNotesByUser(userId,pageNo,pageSize);
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		
		notesService.softDeleteNotes(id);
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	
	@GetMapping("/restore/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
		
		notesService.restoreNotes(id);
		
		return CommonUtil.createdBuildResponseMessage("Notes restore Success", HttpStatus.OK);
	}
	
	

	@GetMapping("/recycle-bin")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
		
		Integer userId=2;
		List<NotesDto> notes = notesService.getUserRecycleBinNotes(userId);
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createdBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
		}
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
		
		notesService.hardDeleteNotes(id);
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	

	@DeleteMapping("/delete")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> emptyRecycleBin() throws Exception{
		
		int userId=2;
		notesService.emptyRecycleBin(userId);
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	

	@GetMapping("/fav/{noteId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception{
		
		
		notesService.favouriteNotes(noteId);
		
		return CommonUtil.createdBuildResponseMessage("Notes added Favourite", HttpStatus.CREATED);
	}
	

	@DeleteMapping("/un-fav/{favNotId}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> unFavoriteNote(@PathVariable Integer favNotId) throws Exception{
		
		
		notesService.unFavoriteNotes(favNotId);
		
		return CommonUtil.createdBuildResponseMessage("Remove Favourite", HttpStatus.OK);
	}
	

	@GetMapping("/fav-note")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getUserFavoriteNote() throws Exception{
		
		List<FavouriteNoteDto> userFavoriteNotes = notesService.getUserFavouriteNotes();
		if(CollectionUtils.isEmpty(userFavoriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	

	@GetMapping("/copy/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
		
		Boolean copyNotes = notesService.copyNotes(id);
		if(copyNotes) {
			return CommonUtil.createdBuildResponseMessage("Copied Success", HttpStatus.CREATED);
		}
		return CommonUtil.createdErrorResponseMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	
	
}
