package com.genc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.genc.project.entities.Quiz;
import com.genc.project.entities.QuizTrack;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {

	@Query("SELECT COUNT(q) FROM Quiz q WHERE q.course.id = :courseId")
	public int countByCourseId(@Param("courseId") int courseId);
	

	@Query("SELECT q FROM Quiz q WHERE q.course.id = :courseId")
	public List<Quiz> findByCourseId(@Param("courseId") int courseId);


	


}
