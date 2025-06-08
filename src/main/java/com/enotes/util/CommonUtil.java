package com.enotes.util;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;

import com.enotes.config.security.CustomUserDetails;
import com.enotes.entity.User;
import com.enotes.handler.GenericResponse;

import jakarta.servlet.http.HttpServletRequest;


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


	public static String getContentType(String originalFileName) {
		
		String extension = FilenameUtils.getExtension(originalFileName);
		
		switch(extension){
			case "pdf":
				return "application/pdf";
			case "xlsx":
				return "application/vnd.openxmlformats-officedocument.spreadsheettml.sheet";
			case "txt":
				return "text/plain";
			case "png":
				return "image/jpeg";
			case "jpeg":
				return "image/jpeg";
			default:
				return "application/octet-stream";
			
		}
		
	}


	public static String getUrl(HttpServletRequest request) {
		
		String apiUrl=request.getRequestURL().toString();		//http:localhost:9090/api/v1/auth
		apiUrl=apiUrl.replace(request.getServletPath(), "");	//http:localhost:9090
		return apiUrl;
	}
	
	public static User getLoggedInUser() {
		try {
			CustomUserDetails logUser=(CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return logUser.getUser();
		} catch (Exception e) {
			throw e;
		}
		
	}
	
	
	
	
}
