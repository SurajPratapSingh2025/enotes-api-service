package com.enotes.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.enotes.handler.GenericResponse;

public class CommonUtil {
	
	public static ResponseEntity<?> createdBuildResponse(Object data,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.message("success")
				.data(data)
				.build();
		return response.create();
	}
	
	
	public static ResponseEntity<?> createdBuildResponseMessage(String messsage,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("success")
				.message("message")
				.build();
		return response.create();
	}
	
	
	public static ResponseEntity<?> createdErrorResponse(Object data,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message("failed")
				.data(data)
				.build();
		return response.create();
	}
	

	public static ResponseEntity<?> createdErrorResponseMessage(String message,HttpStatus status){
		
		GenericResponse response = GenericResponse.builder()
				.responseStatus(status)
				.status("failed")
				.message(message)
				.build();
		return response.create();
	}
	
	
	
	
	
	
	
	
	
	
	
}
