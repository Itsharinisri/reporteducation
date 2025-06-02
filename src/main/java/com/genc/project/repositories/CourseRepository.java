package com.genc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.genc.project.entities.Course;
import com.genc.project.entities.Quiz;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
	public Course getCourseById(int id);

	public List<Course> findByInstructorId(int id);

}
