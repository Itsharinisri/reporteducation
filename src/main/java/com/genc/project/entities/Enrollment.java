package com.genc.project.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User student; 
    
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course; 

    @Column(name = "quizscores")
    private int quizScores; 

    public Enrollment() {
    	
    }
	public Enrollment(User user, Course course) {
		this.student = user;
	    this.course = course;
	    this.quizScores = 0;
	}

	public int getEnrollmentId() {
		return id;
	}

	public void setEnrollmentId(int enrollmentId) {
		this.id = enrollmentId;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getQuizScores() {
		return quizScores;
	}
	
	public void setQuizScores(int quizScores) {
		this.quizScores = quizScores;
	} 

    
}
