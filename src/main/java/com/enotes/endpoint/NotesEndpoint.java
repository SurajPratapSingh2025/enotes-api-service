package com.enotes.endpoint;

import static com.enotes.util.Constants.DEFAULT_PAGE_NO;
import static com.enotes.util.Constants.DEFAULT_PAGE_SIZE;
import static com.enotes.util.Constants.ROLE_ADMIN;
import static com.enotes.util.Constants.ROLE_ADMIN_USER;
import static com.enotes.util.Constants.ROLE_USER;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.enotes.dto.NotesDto;
import com.enotes.dto.NotesRequest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name="Notes",description="All the Notes Operation APIs")
@RequestMapping("/api/v1/notes")
public interface NotesEndpoint {
	
	@Operation(summary="Save Notes",tags= {"Notes","User"},description="User save notes")
	@PostMapping(value="/",consumes="multipart/form-data")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> saveNotes(@RequestParam 
			@Parameter(description = "Json String Notes",required = true,content = 
			@Content(schema = @Schema(implementation = NotesRequest.class)))
	String notes, @RequestParam(required=false) MultipartFile file) throws Exception;
	
	@Operation(summary="Download Upload File",tags= {"Notes","User"},description="Download file")
	@GetMapping("/download/{id}")
	@PreAuthorize(ROLE_ADMIN_USER)
	public ResponseEntity<?> downloadFile(@PathVariable Integer id) throws Exception;
	
	@Operation(summary="Get All Notes",tags= {"Notes"},description="Get All Notes Admin")
	@GetMapping("/")
	@PreAuthorize(ROLE_ADMIN)
	public ResponseEntity<?> getAllNotes(@RequestBody NotesDto notesDto);
	
	@Operation(summary="Search Notes",tags= {"Notes","User"},description="User Search Notes")
	@GetMapping("/user-notes")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getAllNotesByUser(
			@RequestParam(name="pageNo",defaultValue=DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name="pageSize",defaultValue=DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@Operation(summary="Get All Notes For User",tags= {"Notes","User"},description="Get All Notes For User")
	@GetMapping("/search")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> searchNotes(
			@RequestParam(name="key",defaultValue="") String key,
			@RequestParam(name="pageNo",defaultValue=DEFAULT_PAGE_NO) Integer pageNo,
			@RequestParam(name="pageSize",defaultValue=DEFAULT_PAGE_SIZE) Integer pageSize);
	
	@Operation(summary="Delete Notes",tags= {"Notes","User"},description="Delete Notes By User")
	@GetMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> deleteNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary="Restore Delete Notes",tags= {"Notes","User"},description="Restore Delete Notes from Recycle Bin")
	@GetMapping("/restore/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> restoreNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary="Get Notes from Recycle Bin",tags= {"Notes","User"},description="Get Notes from Recycle Bin")
	@GetMapping("/recycle-bin")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserRecycleBinNotes() throws Exception;
	
	@Operation(summary="Hard Delete Notes",tags= {"Notes","User"},description="Hard Delete Notes")
	@DeleteMapping("/delete/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> hardDeleteNotes(@PathVariable Integer id) throws Exception;
	
	@Operation(summary="Empty User Recycle Bin",tags= {"Notes","User"},description="Empty User Recycle Bin")
	@DeleteMapping("/delete")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> emptyUserRecycleBin() throws Exception;
	
	@Operation(summary="Favorite Notes",tags= {"Notes","User"},description="User Favorite Notes")
	@GetMapping("/fav/{noteId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> favouriteNote(@PathVariable Integer noteId) throws Exception;
	
	@Operation(summary="UnFavorite Notes",tags= {"Notes","User"},description="User UnFavorite Notes")
	@DeleteMapping("/un-fav/{favNotId}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> unFavoriteNote(@PathVariable Integer favNotId) throws Exception;
	
	@Operation(summary="Get User Favorite Notes",tags= {"Notes","User"},description="User Favorite Notes")
	@GetMapping("/fav-note")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> getUserFavoriteNote() throws Exception;
	
	@Operation(summary="Copy Notes",tags= {"Notes","User"},description="Copy Notes")
	@GetMapping("/copy/{id}")
	@PreAuthorize(ROLE_USER)
	public ResponseEntity<?> copyNotes(@PathVariable Integer id) throws Exception;
	
	
	
	
	
	
}
