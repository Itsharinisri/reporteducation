package com.genc.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.genc.project.entities.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
	
}
