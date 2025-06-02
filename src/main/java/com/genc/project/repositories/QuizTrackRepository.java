package com.genc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.genc.project.entities.Quiz;
import com.genc.project.entities.QuizTrack;
import com.genc.project.entities.User;

@Repository
public interface QuizTrackRepository extends JpaRepository<QuizTrack, Integer> {

	@Query("SELECT q FROM Quiz q JOIN QuizTrack qs ON q.id = qs.quiz.id WHERE qs.user.id = :userId")
	List<Quiz> getAllQuizOfUser(@Param("userId") int user);


}
