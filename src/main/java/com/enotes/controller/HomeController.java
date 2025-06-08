package com.enotes.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.enotes.dto.PasswordResetRequest;
import com.enotes.endpoint.HomeEndpoint;
import com.enotes.service.HomeService;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class HomeController implements HomeEndpoint{
	
	Logger log=LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private UserService userService;
	
	@Override
	public ResponseEntity<?> verifyUserAccount(@RequestParam Integer uid,@RequestParam String code) throws Exception{
		log.info("HomeController : verifyUserAccount() : Exceution Start");
		Boolean verifyAccount = homeService.verifyAccount(uid, code);
		if(verifyAccount) {
			return CommonUtil.createdBuildResponseMessage("Account verification success", HttpStatus.OK);
		}
		log.info("HomeController : verifyUserAccount() : Exceution End");
		return CommonUtil.createdErrorResponseMessage("Invalid Verification Link", HttpStatus.BAD_REQUEST);
		
	}
	
	
	@Override
	public  ResponseEntity<?> sendEmailForPasswordReset(@RequestParam String email,HttpServletRequest request) throws Exception{
		
		userService.sendEmailPasswordReset(email,request);
		return CommonUtil.createdBuildResponseMessage("Email Send Success !! Check Email Reset Password", HttpStatus.OK);
		
	}

	
	@Override
	public  ResponseEntity<?> verifyPasswordResetLink(@RequestParam Integer uid, @RequestParam String code) throws Exception{
		
		userService.verifyPswdResetLink(uid,code);
		return CommonUtil.createdBuildResponseMessage("verification success", HttpStatus.OK);
	}

	
	@Override
	public  ResponseEntity<?> resetPassword(@RequestBody PasswordResetRequest pswdResetRequest) throws Exception{
		userService.resetPassword(pswdResetRequest);
		return CommonUtil.createdBuildResponseMessage("Password reset Success", HttpStatus.OK);
	}
	
	
	
	
	
	
	

}
