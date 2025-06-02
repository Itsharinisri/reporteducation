package com.genc.project.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.genc.project.entities.Course;
import com.genc.project.entities.Lesson;
import com.genc.project.entities.Quiz;
import com.genc.project.entities.User;
import com.genc.project.services.CourseService;
import com.genc.project.services.LessonService;
import com.genc.project.services.QuizService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;

@Controller
public class InstructorController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private LessonService lessonService;
	
	@Autowired
	private QuizService quizService;

	
	@GetMapping("/addCourse")
	public String showAddCourseForm(Model model, Authentication auth) {
		int id = ((UserDetailsImpl) auth.getPrincipal()).getId();
	    User instructor = userService.findById(id);
	    model.addAttribute("user", instructor);
	    return "createCourse"; 
	}
	
	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute Course course,  
	        @RequestParam("lessonsTitle[]") List<String> titles,
	        @RequestParam("lessonsContent[]") List<String> contents,
	        @RequestParam("quizQuestion[]") List<String> questions,
	        @RequestParam("quizOptions[]") List<List<String>> options,
	        @RequestParam("quizCorrectAnswer[]") List<String> answer,
	        Authentication auth) throws JsonProcessingException {

	    ObjectMapper objectMapper = new ObjectMapper();
	    List<Lesson> lessonList = new ArrayList<>();
	    for (int i = 0; i < titles.size(); i++) {
	        Lesson lesson = new Lesson();
	        lesson.setTitle(titles.get(i));
	        lesson.setContent(contents.get(i));
	        lesson.setCourse(course);
	        lessonList.add(lesson);
	    }
	    course.setLessons(lessonList);
	    List<Quiz> quizList = new ArrayList<>();
        for (int i = 0; i < questions.size(); i++) {
            Quiz quiz = new Quiz();
            
            quiz.setQuestion(questions.get(i));
            List<String> ops = options.get(i);
            String jsonOptions = String.join(", ", ops); 
            String correctOptionsFormatted = jsonOptions.replaceAll("[\\[\\]\"]", "").trim(); 
            quiz.setAnswerOptions(objectMapper.writeValueAsString(correctOptionsFormatted));
            String correctAnswerFormatted = answer.get(i).replaceAll("[\\[\\]\"]", "").trim();
            quiz.setCorrectAnswers(objectMapper.writeValueAsString(correctAnswerFormatted));
            quiz.setCourse(course);
            quizList.add(quiz);
	        quiz.setCourse(course);
	        quizList.add(quiz);
	    }
	    course.setQuizzes(quizList);

	    int id = ((UserDetailsImpl) auth.getPrincipal()).getId();
	    User instructor = userService.findById(id);
	    
	    course.setInstructor(instructor);
	    courseService.saveCourse(course);
	    
	    return "redirect:/home"; 
	}

	
	@GetMapping("/updateCourse/{id}")
    public String showEditForm(@PathVariable int id, Model model, Authentication auth) {
		int uid = ((UserDetailsImpl) auth.getPrincipal()).getId();
	    User instructor = userService.findById(uid);
	    model.addAttribute("user", instructor);
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
        model.addAttribute("user",instructor);
        return "updateCourse"; 
    }
	



	
	@PostMapping("/updateCourse/{id}")
	public String updateCourse(@PathVariable int id, @ModelAttribute Course course, 
	                           @RequestParam(value = "lessonsTitle[]", required = false) List<String> titles,
	                           @RequestParam(value = "lessonsContent[]", required = false) List<String> contents,
	                           @RequestParam(value = "quizQuestion[]", required = false) List<String> questions,
	                           @RequestParam(value = "quizOptions[]", required = false) List<List<String>> options, 
	                           @RequestParam(value = "quizCorrectAnswer[]", required = false) List<String> answer) throws JsonProcessingException 
	{
	    ObjectMapper objectMapper = new ObjectMapper();
	    List<Lesson> lessons = new ArrayList<>();
	    
	    if (titles != null) {
	        for (int i = 0; i < titles.size(); i++) {
	            Lesson lesson = new Lesson();
	            lesson.setTitle(titles.get(i));
	            lesson.setContent(contents.get(i));
	            lesson.setCourse(course);
	            lessons.add(lesson);
	        }
	        lessonService.saveAll(lessons);
	        course.setLessons(lessons);
	    }

	    if (questions != null) {
	        List<Quiz> quizList = new ArrayList<>();
	        for (int i = 0; i < questions.size(); i++) {
	            Quiz quiz = new Quiz();
	            
	            quiz.setQuestion(questions.get(i));
	            List<String> ops = options.get(i);
	            String jsonOptions = String.join(", ", ops); 
	            String correctOptionsFormatted = jsonOptions.replaceAll("[\\[\\]\"]", "").trim(); 
	            quiz.setAnswerOptions(objectMapper.writeValueAsString(correctOptionsFormatted));
	            String correctAnswerFormatted = answer.get(i).replaceAll("[\\[\\]\"]", "").trim();
	            quiz.setCorrectAnswers(objectMapper.writeValueAsString(correctAnswerFormatted));
	            quiz.setCourse(course);
	            quizList.add(quiz);
	        }
	        quizService.saveAll(quizList);
	        course.setQuizzes(quizList);
	    }

	    courseService.updateCourse(course.getId(), course);
	    return "redirect:/home";
	}


    
    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return "redirect:/home"; 
    }
    
}

