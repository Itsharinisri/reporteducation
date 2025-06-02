package com.genc.project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.dto.StudentReport;
import com.genc.project.entities.Course;
import com.genc.project.entities.User;

@Service
public class ReportService {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	public List<Course> getCoursesByInstructor(int uid){
		return courseService.findAllByInstructorId(uid);
	}

	public List<StudentReport> getStudentReportsForCourse(int courseId, User user) {
		
		List<StudentReport> result  = new ArrayList<>();
		for(User student : enrollmentService.findAllUsers(courseId)) {
			int progress = (int) enrollmentService.calculateProgressForUserPerCourse(student, courseId);
			int id = student.getId();
			String name = student.getName();
			String grade = "A";
			if(progress>=90 && progress<=100)grade="A";
			else if(progress>=80 && progress<90) grade = "B";
			else if(progress>=50 && progress<80) grade = "C";
			else grade="U";
			int gradePercent = progress;
			StudentReport studentReport = new StudentReport(id, name, progress, grade, gradePercent);
			result.add(studentReport);
		}
		return result;
	}
}
