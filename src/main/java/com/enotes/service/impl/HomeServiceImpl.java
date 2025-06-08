package com.enotes.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enotes.entity.AccountStatus;
import com.enotes.entity.User;
import com.enotes.exception.ResourceNotFoundException;
import com.enotes.exception.SuccessException;
import com.enotes.repository.UserRepository;
import com.enotes.service.HomeService;

@Service
public class HomeServiceImpl implements HomeService{
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public Boolean verifyAccount(Integer userId, String verificationCode) throws Exception {
		
		User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("invalid user"));
		
		if(user.getStatus().getVerifictionCode() == null) {
			throw new SuccessException("Account already verified");
		}
		
		if(user.getStatus().getVerifictionCode().equals(verificationCode)) {
			AccountStatus status = user.getStatus();
			status.setIsActive(true);
			status.setVerifictionCode(null);
			
			userRepo.save(user);
			return true;
		}
		
		return false;
	}

}
