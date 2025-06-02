package com.genc.project.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genc.project.entities.Course;
import com.genc.project.entities.Enrollment;
import com.genc.project.entities.Lesson;
import com.genc.project.entities.Quiz;
import com.genc.project.entities.QuizTrack;
import com.genc.project.entities.User;
import com.genc.project.repositories.CourseRepository;
import com.genc.project.repositories.QuizTrackRepository;
import com.genc.project.services.CourseService;
import com.genc.project.services.EnrollmentService;
import com.genc.project.services.LessonService;
import com.genc.project.services.QuizService;
import com.genc.project.services.QuizTrackService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private QuizService quizService;
	
	@Autowired
	private QuizTrackService quizTrackService;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping("/course/{id}")
	public String viewCourse(@PathVariable("id") int courseId, Model model, Authentication auth) {
		
		String email = ((UserDetailsImpl) auth.getPrincipal()).getEmail(); 
	    User user = userService.getUserByEmail(email);
	    Course course = courseService.getCourse(courseId);
	    course.setQuizzes(quizTrackService.getNewQuizzes(user, course.getId()));
	    model.addAttribute("user", user);
	    model.addAttribute("course", course);
	    boolean isEnrolled = enrollmentService.isStudentEnrolled(user.getId(), courseId);
	    if (isEnrolled) {
	        return "coursePage"; 
	    }
		return "courseDetail";
	}
	
	
	
	@PostMapping("/enroll")
	public String enrollStudent(@RequestParam int id, Authentication authentication, Model model) {
	    String email = ((UserDetailsImpl) authentication.getPrincipal()).getEmail(); 
	    User user = userService.getUserByEmail(email);
	    enrollmentService.enroll(user, courseService.getCourse(id));
	    Course course = courseService.getCourse(id);
	    course.setQuizzes(quizTrackService.getNewQuizzes(user, course.getId()));
	    model.addAttribute("user", user);
	    model.addAttribute("course", course);
	    return "coursePage"; 
	}
	
	@GetMapping("/enrolledCourses")
    public String getEnrolledCourses(Authentication authentication, Model model) {
		String email = ((UserDetailsImpl) authentication.getPrincipal()).getEmail(); 
	    User user = userService.getUserByEmail(email);
        List<Course> enrolledCourses = enrollmentService.getEnrolledCoursesByStudent(user.getId());
        model.addAttribute("courses", enrolledCourses);
        int id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
    	model.addAttribute("user", userService.findById(id));
        return "myLearning";
    }

	
	
	@GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication auth) {
		int id =  ((UserDetailsImpl)auth.getPrincipal()).getId();
		User user = userService.findById(id);
    	model.addAttribute("user", user);
    	List<Course> courses = enrollmentService.getEnrolledCoursesByStudent(id);
        Map<Integer, Double> progressMap = enrollmentService.calculateProgressForUser(user);

        model.addAttribute("user", user);
        model.addAttribute("courses", courses);
        model.addAttribute("progressMap", progressMap);

        return "dashboard";
    }
	
	
	@PostMapping("/submitQuiz")
	public String submitQuiz(@RequestParam int quizId, @RequestParam("answer") String userAnswer, 
	                         Authentication auth) throws JsonMappingException, JsonProcessingException {
	    System.out.println("Quiz ID: " + quizId);
	    System.out.println("User's Answer: " + userAnswer);
	    userAnswer = userAnswer.replaceAll("[\\[\\]\"]", "").trim();

	    int uid = ((UserDetailsImpl) auth.getPrincipal()).getId();
	    User user = userService.findById(uid);
	    Quiz quiz = quizService.findById(quizId);
	    Enrollment enrollment = enrollmentService.findByUserAndCourse(user, quiz.getCourse());

	    String correctAnswer = quiz.getCorrectAnswers().replaceAll("[\\[\\]\"]", "").trim();
	    System.out.println("Correct Answer: " + correctAnswer);

	    int correctCount = correctAnswer.equalsIgnoreCase(userAnswer.trim()) ? 1 : 0;
	    enrollment.setQuizScores(enrollment.getQuizScores() + correctCount);
	    enrollmentService.save(enrollment);
	    if (correctCount == 1) {
	        QuizTrack quizSubmission = new QuizTrack(user, quiz);
	        quizTrackService.save(quizSubmission);
	        System.out.println("Quiz submission recorded for user ID: " + uid);
	    }

	    return "redirect:/course/"+quiz.getCourse().getId();
	}

}

