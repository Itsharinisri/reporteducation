package com.genc.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.entities.Quiz;
import com.genc.project.entities.User;
import com.genc.project.repositories.QuizRepository;

@Service
public class QuizService {
	
	@Autowired
	private QuizRepository quizRepository;

	public void saveAll(List<Quiz> quizList) {
		quizRepository.saveAll(quizList);
		
	}

	public Quiz findById(int id) {
		return quizRepository.findById(id).get();
	}

	
	
}
