package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enotes.dto.PasswordChangeRequest;
import com.enotes.entity.User;
import com.enotes.repository.UserRepository;
import com.enotes.service.UserService;
import com.enotes.util.CommonUtil;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepo;
	
	
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
	
	
	
	
	
	
	

}
