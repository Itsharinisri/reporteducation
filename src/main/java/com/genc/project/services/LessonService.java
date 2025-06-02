package com.genc.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.entities.Course;
import com.genc.project.entities.Lesson;
import com.genc.project.repositories.LessonRepository;

@Service
public class LessonService {
	@Autowired
	private LessonRepository lessonRepository;

	public void addLessonToCourse(Lesson lesson, int courseId) {
		lesson.setCourse(new Course()); 
        lesson.getCourse().setId(courseId);
        lessonRepository.save(lesson);
		
	}

	public void saveAll(List<Lesson> lessons) {
		lessonRepository.saveAll(lessons);
	}
	
}
