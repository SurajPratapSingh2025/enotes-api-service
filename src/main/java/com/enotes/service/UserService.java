package com.enotes.service;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.PasswordResetRequest;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
	
	public void changePassword(PasswordChangeRequest passwordRequest);


	public void sendEmailPasswordReset(String email, HttpServletRequest request) throws Exception;


	public void verifyPswdResetLink(Integer uid, String code) throws Exception;


	public void resetPassword(PasswordResetRequest pswdResetRequest) throws Exception;

}
