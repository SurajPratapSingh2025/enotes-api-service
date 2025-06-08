package com.enotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.TodoDto;
import com.enotes.endpoint.TodoEndpoint;
import com.enotes.service.TodoService;
import com.enotes.util.CommonUtil;

@RestController
public class TodoController implements TodoEndpoint{
	
	@Autowired
	private TodoService todoService;
	
	@Override
	public ResponseEntity<?> saveTodo(@RequestBody TodoDto todo) throws Exception{
		Boolean saveTodo = todoService.saveTodo(todo);
		if(saveTodo) {
			return CommonUtil.createdBuildResponseMessage("Todo Saved Success", HttpStatus.CREATED);
		}else {
			return CommonUtil.createdErrorResponseMessage("Todo not save", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
	}
	
	
	@Override
	public ResponseEntity<?> getTodoById(@RequestBody Integer id) throws Exception{
		
		TodoDto todo = todoService.getTodoById(id);
		return CommonUtil.createdBuildResponse(todo, HttpStatus.OK);
	}
	

	@Override
	public ResponseEntity<?> getAllTodoByUser() throws Exception{
		
		List<TodoDto> todoList = todoService.getTodoByUser();
		if(CollectionUtils.isEmpty(todoList)) {
			return ResponseEntity.noContent().build();
		}
		return CommonUtil.createdBuildResponse(todoList, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
	

}
