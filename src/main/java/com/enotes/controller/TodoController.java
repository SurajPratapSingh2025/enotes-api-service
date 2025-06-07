package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.TodoDto;
import com.enotes.service.TodoService;
import com.enotes.util.CommonUtil;

@RestController
@RequestMapping("/api/v1/todo")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@PostMapping("/")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception{
		Boolean saveTodo = todoService.saveTodo(todo);
		if(saveTodo) {
			return CommonUtil.createdBuildResponseMessage("Todo Saved Success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createdErrorResponseMessage("Todo not save", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTodoById(@RequestBody Integer id) throws Exception{
		
		TodoDto todo = todoService.getTodoById(id);
		return CommonUtil.createdBuildResponse(todo, HttpStatus.OK);
	}
	

	@GetMapping("/list")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllTodoByUser() throws Exception{
		
		List<TodoDto> todoList = todoService.getTodoByUser();
		if(CollectionUtils.isEmpty(todoList)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createdBuildResponse(todoList, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	

}
