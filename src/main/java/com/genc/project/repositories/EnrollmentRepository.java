package com.genc.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.genc.project.entities.Course;
import com.genc.project.entities.Enrollment;
import com.genc.project.entities.User;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

	
	@Query("SELECT e FROM Enrollment e WHERE e.student.id = :id AND e.course.id = :courseId")
	public Enrollment findByUserIdAndCourseId(@Param("id") int id, @Param("courseId") int courseId);

	@Query("SELECT e.course FROM Enrollment e WHERE e.student.id = :id")
	public List<Course> findByUserId(@Param("id") int id);

	@Query("SELECT e FROM Enrollment e WHERE e.student = :user")
	public List<Enrollment> findByUser(@Param("user") User user);


    @Query("SELECT e.student FROM Enrollment e WHERE e.course.id = :courseId")
    public List<User> findAllUsers(@Param("courseId") int courseId);
	

}
