package com.genc.project.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.entities.Course;
import com.genc.project.entities.Enrollment;
import com.genc.project.entities.User;
import com.genc.project.repositories.EnrollmentRepository;
import com.genc.project.repositories.QuizRepository;

@Service
public class EnrollmentService {
	@Autowired
    private EnrollmentRepository enrollmentRepository;
	
	@Autowired
	private QuizRepository quizRepository;

    public void enroll(User user, Course course) {
    	Enrollment e = enrollmentRepository.findByUserIdAndCourseId(user.getId(), course.getId());
    	if(e==null) {
    		Enrollment enrollment = new Enrollment(user, course);
    		enrollmentRepository.save(enrollment);
    	}
        
    }

	public List<Course> getEnrolledCoursesByStudent(int id) {
		return enrollmentRepository.findByUserId(id);
	}

	public boolean isStudentEnrolled(int id, int courseId) {
		Enrollment e = enrollmentRepository.findByUserIdAndCourseId(id, courseId);
		return e!=null;
		
	}

	
	public Map<Integer, Double> calculateProgressForUser(User user) {
        List<Enrollment> enrollments = enrollmentRepository.findByUser(user);
        Map<Integer, Double> progressMap = new HashMap<Integer, Double>();

        for (Enrollment enrollment : enrollments) {
            int courseId = enrollment.getCourse().getId();
            int totalQuizzes = quizRepository.countByCourseId(courseId);
            int quizzesPassed = enrollment.getQuizScores();
            System.out.println("totalllllllllllllllllllllllll quizess\n\n"+ totalQuizzes +"\n\n" );

            double progress = (totalQuizzes > 0) ? ((double) quizzesPassed / totalQuizzes) * 100 : 0.0;
            System.out.println("totalllllllllllllllllllllllll quizess\n\n"+ progress +"\n\n" );
            progressMap.put(courseId, progress);
        }

        return progressMap;
    }

	public Enrollment findByUserAndCourse(User user, Course course) {
		Enrollment e = enrollmentRepository.findByUserIdAndCourseId(user.getId(), course.getId());
		return e;
	}

	public void save(Enrollment enrollment) {
		
		enrollmentRepository.save(enrollment);
	}

	public double calculateProgressForUserPerCourse(User user, int courseId) {
		Enrollment enrollment = enrollmentRepository.findByUserIdAndCourseId(user.getId(), courseId);
		int totalQuizzes = quizRepository.countByCourseId(courseId);
		int quizzesPassed = 0;
		if(enrollment!=null)
			quizzesPassed = enrollment.getQuizScores();
		double progress = (totalQuizzes > 0) ? ((double) quizzesPassed / totalQuizzes) * 100 : 0.0;
		return progress;
	}

	public List<User> findAllUsers(int courseId) {
		List<User> users = enrollmentRepository.findAllUsers(courseId);
		return users;
	}
		
}
