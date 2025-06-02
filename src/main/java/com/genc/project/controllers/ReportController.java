package com.genc.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.genc.project.dto.StudentReport;
import com.genc.project.entities.User;
import com.genc.project.services.CourseService;
import com.genc.project.services.ReportService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;

@Controller
public class ReportController {
	 @Autowired
	    private ReportService reportService; 
	 
	 @Autowired
	    private CourseService courseService; 
	 
	 @Autowired
	 	private UserService userService;
	 
	 
//	    private String getLoggedInUsername(Principal principal) {
//	        return (principal != null) ? principal.getName() : "InstructorUser";
//	    }


	    @GetMapping("/instructor/reports")
	    public String showInstructorReports(Model model, Authentication auth) {
	    	int id = ((UserDetailsImpl) auth.getPrincipal()).getId();
	    	User user = userService.findById(id);
	    	model.addAttribute("user", user);
	        model.addAttribute("courses", reportService.getCoursesByInstructor(id)); 
	        return "reportPage"; 
	    }

	    
	    

}
