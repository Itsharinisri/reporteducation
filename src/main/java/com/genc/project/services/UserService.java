package com.genc.project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.genc.project.entities.User;
import com.genc.project.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	public boolean isUserAlreadyExists(String email) {
        User user = getUserByEmail(email);
        if(user!=null){
            return true;
        }
        return false;
    }
	
	public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

	public User save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		User newUser = userRepository.save(user);
		return newUser;
	}

	public boolean isValidUser(User user) {
		User existingUser = userRepository.getUserByEmail(user.getEmail());
		return existingUser!=null;
	}
	

	public User findById(int id) {
		User user = userRepository.findById(id).get();
		return user;
	}

	@Transactional
	public void updateUserPassword(User user) {
		int id = user.getId();
		userRepository.updatePassword(id, user.getPassword());
	}
}
