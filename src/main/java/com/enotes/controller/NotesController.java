package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.FavouriteNoteDto;
import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesResponse;
import com.enotes.endpoint.NotesEndpoint;
import com.enotes.entity.FileDetails;
import com.enotes.service.NotesService;
import com.enotes.util.CommonUtil;

@RestController
public class NotesController implements NotesEndpoint{
	
	@Autowired
	private NotesService notesService;
	
	@Override
	public ResponseEntity<?> saveNotes(@RequestParam String notes, @RequestParam(required=false) MultipartFile file) throws Exception{
		
		Boolean saveNotes = notesService.saveNotes(notes, file);
		if(saveNotes) {
			return CommonUtil.createdBuildResponseMessage("Notes saved success", HttpStatus.CREATED);
		}
		return CommonUtil.createdErrorResponseMessage("Notes not saved", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@Override
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception{
		
		FileDetails fileDetails = notesService.getFileDetails(id);
		byte[] data = notesService.downloadFile(fileDetails);
		
		HttpHeaders headers = new HttpHeaders();
		String contentType = CommonUtil.getContentType(fileDetails.getOriginalFileName());
		headers.setContentType(MediaType.parseMediaType(contentType));
		headers.setContentDispositionFormData("attachment",fileDetails.getOriginalFileName());
		return ResponseEntity.ok().headers(headers).body(data);
	}
	
	
	
	
	
	@Override
	public ResponseEntity<?> getAllNotes(@RequestBody NotesDto notesDto){
		
		List<NotesDto> notes = notesService.getAllNotes();
		if(CollectionUtils.isEmpty(notes)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
		
	}
	
	
	@Override
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo",defaultValue="0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue="10") Integer pageSize){
		
		
		NotesResponse notes = notesService.getAllNotesByUser(pageNo,pageSize);
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
		
	}
	

	@Override
	public ResponseEntity<?> searchNotes(
			@RequestParam(name="key",defaultValue="") String key,
			@RequestParam(name="pageNo",defaultValue="0") Integer pageNo,
			@RequestParam(name="pageSize",defaultValue="10") Integer pageSize){
		
		
		NotesResponse notes = notesService.getNotesByUserSearch(pageNo,pageSize,key);
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
		
	}
	
	
	
	
	
	
	
	@Override
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception{
		
		notesService.softDeleteNotes(id);
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception{
		
		notesService.restoreNotes(id);
		
		return CommonUtil.createdBuildResponseMessage("Notes restore Success", HttpStatus.OK);
	}
	
	

	@Override
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception{
		
		
		List<NotesDto> notes = notesService.getUserRecycleBinNotes();
		if(CollectionUtils.isEmpty(notes)) {
			return CommonUtil.createdBuildResponseMessage("Notes not available in Recycle Bin", HttpStatus.OK);
		}
		return CommonUtil.createdBuildResponse(notes, HttpStatus.OK);
	}
	
	
	@Override
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception{
		
		notesService.hardDeleteNotes(id);
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception{
		
		
		notesService.emptyRecycleBin();
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	
	

	@Override
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception{
		
		
		notesService.favouriteNotes(noteId);
		
		return CommonUtil.createdBuildResponseMessage("Notes added Favourite", HttpStatus.CREATED);
	}
	

	@Override
	public ResponseEntity<?> unFavoriteNote(@PathVariable Integer favNotId) throws Exception{
		
		
		notesService.unFavoriteNotes(favNotId);
		
		return CommonUtil.createdBuildResponseMessage("Remove Favourite", HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> getUserFavoriteNote() throws Exception{
		
		List<FavouriteNoteDto> userFavoriteNotes = notesService.getUserFavouriteNotes();
		if(CollectionUtils.isEmpty(userFavoriteNotes)) {
			return ResponseEntity.noContent().build();
		}
		
		return CommonUtil.createdBuildResponseMessage("Delete Success", HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception{
		
		Boolean copyNotes = notesService.copyNotes(id);
		if(copyNotes) {
			return CommonUtil.createdBuildResponseMessage("Copied Success", HttpStatus.CREATED);
		}
		return CommonUtil.createdErrorResponseMessage("Copy failed ! Try Again", HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	
	
	
}
