package com.genc.project.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.entities.Quiz;
import com.genc.project.entities.QuizTrack;
import com.genc.project.entities.User;
import com.genc.project.repositories.QuizRepository;
import com.genc.project.repositories.QuizTrackRepository;
@Service
public class QuizTrackService {

	@Autowired
	private QuizTrackRepository quizTrackRepository;
	
	@Autowired
	private QuizRepository quizRepository;
	
	public void save(QuizTrack quiz) {
		quizTrackRepository.save(quiz);
		
	}

	public List<Quiz> getNewQuizzes(User user, int courseId) {
		List<Quiz> allQuizzes = quizRepository.findByCourseId(courseId);
	    List<Quiz> completedQuizIds = quizTrackRepository.getAllQuizOfUser(user.getId());

	    return allQuizzes.stream()
	                     .filter(quiz -> !completedQuizIds.contains(quiz))
	                     .collect(Collectors.toList());
	
	}

}
