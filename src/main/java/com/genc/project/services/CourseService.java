package com.genc.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.entities.Course;
import com.genc.project.entities.Quiz;
import com.genc.project.repositories.CourseRepository;

@Service
public class CourseService {
	@Autowired
	private CourseRepository courseRepository;
	
	public Course getCourse(int id) {
		return courseRepository.getCourseById(id);
	}

	public List<Course> getCourses() {
		return courseRepository.findAll();
	}
	

	public List<Course> findAllByInstructorId(int id) {
		return courseRepository.findByInstructorId(id);
	}

	public void saveCourse(Course course) {
		courseRepository.save(course);
	}

	public void updateCourse(int id, Course updatedCourse) {
		Course existingCourse = getCourse(id);
	    existingCourse.setTitle(updatedCourse.getTitle());
	    existingCourse.setDescription(updatedCourse.getDescription());
	    existingCourse.setCategory(updatedCourse.getCategory());
	    courseRepository.save(existingCourse);
	}

	public void deleteCourse(int id) {
		courseRepository.deleteById(id);
	}
	
}
