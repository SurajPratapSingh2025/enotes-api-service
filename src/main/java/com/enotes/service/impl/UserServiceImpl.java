package com.enotes.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.enotes.dto.EmailRequest;
import com.enotes.dto.PasswordChangeRequest;
import com.enotes.dto.PasswordResetRequest;
import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.repository.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailService;
	
	
	@Override
	public void changePassword(PasswordChangeRequest passwordRequest) {
		
		User logedInUser=CommonUtil.getLoggedInUser();
		if(!passwordEncoder.matches(passwordRequest.getOldPassword(),logedInUser.getPassword())) {
			throw new IllegalArgumentException("Old Password is incorrect");
		}
		String encodePassword=passwordEncoder.encode(passwordRequest.getNewPassword());
		logedInUser.setPassword(encodePassword);
		userRepo.save(logedInUser);
		
	}


	@Override
	public void sendEmailPasswordReset(String email,HttpServletRequest request) throws Exception {
		
		User user = userRepo.findByEmail(email);
		if(ObjectUtils.isEmpty(user)) {
			throw new ResourceNotFoundException("invalid Email");
		}
		
		//Generate unique password reset token
		String passwordResetToken=UUID.randomUUID().toString();
		user.getStatus().setPasswordResetToken(passwordResetToken);
		User updateUser=userRepo.save(user);
		
		
		String url=CommonUtil.getUrl(request);
		sendEmailRequest(updateUser,url);
		
		
	}


	private void sendEmailRequest(User user,String url) throws Exception {
		
		
		String message="Hi,<br>[[username]]</br> "
				+ "<br><p>You have requested to reset your password.</p><br>"
				+"<p>Click the link below to change your password:</p>"
				+"<p><a href=[[url]]>Change my password</a></p>"
				+"<p>Ignore this email if you do remember your password,"
				+"or you have not made the request.</p><br><br>"
				+"Thanks,<br>Enotes.com"
				;
		
		
		message=message.replace("[[username]]", user.getFirstName());
		message=message.replace("[[url]]", url+"/api/v1/home/verify-pswd-link?uid="+user.getId()+"&&code="
				+user.getStatus().getPasswordResetToken());
		
		
		
		EmailRequest emailRequest = EmailRequest.builder()
				.to(user.getEmail())
				.title("Password Reset")
				.subject("Password Reset Link")
				.message(message)
				.build();
		
		//send password reset to user
		emailService.sendEmail(emailRequest);
		
	}


	@Override
	public void verifyPswdResetLink(Integer uid, String code) throws Exception {
		
		User user=userRepo.findById(uid).orElseThrow(()->new ResourceNotFoundException("Invalid user"));
		verifyPasswordResetCode(user.getStatus().getPasswordResetToken(),code);
		
		
		
		
	}


	private void verifyPasswordResetCode(String existToken, String reqToken) {
		//request token not null
		if(StringUtils.hasLength(reqToken)) {
			//password already reset
			if(!StringUtils.hasText(existToken)) {
				throw new IllegalArgumentException("Already Password reset");
			}
			//user req token change
			if(!existToken.equals(reqToken)) {
				throw new IllegalArgumentException("invalid url");
			}
		}else {
			throw new IllegalArgumentException("invalid token");
		}
		
	}


	@Override
	public void resetPassword(PasswordResetRequest pswdResetRequest) throws Exception {
		
		User user=userRepo.findById(pswdResetRequest.getUid()).orElseThrow(()->new ResourceNotFoundException("Invalid user"));
		String encodePassword=passwordEncoder.encode(pswdResetRequest.getNewPassword());
		user.setPassword(encodePassword);
		user.getStatus().setPasswordResetToken(null);
		userRepo.save(user);
		
		
	}
	
	
	
	
	
	
	

}
