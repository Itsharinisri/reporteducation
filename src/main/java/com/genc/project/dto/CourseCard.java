package com.genc.project.dto;

import com.genc.project.entities.User;

public class CourseCard {
	private int id; 
    private String title;
    private User instructor;
    private String description;
    public CourseCard(int id, String title, User instructor, String description) {
        this.id = id;
        this.title = title;
        this.instructor = instructor;
        this.description = description;
    }
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public User getInstructor() {
		return instructor;
	}
	public void setInstructor(User instructor) {
		this.instructor = instructor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
