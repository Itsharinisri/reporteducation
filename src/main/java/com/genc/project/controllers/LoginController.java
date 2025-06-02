package com.genc.project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.genc.project.dto.AuthRequest;
import com.genc.project.entities.Course;
import com.genc.project.entities.User;
import com.genc.project.services.CourseService;
import com.genc.project.services.EnrollmentService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;
import com.genc.project.util.JwtUtil;

@Controller
public class LoginController {
	@Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired 
    UserService userService;
    
    @Autowired
    CourseService courseService;
    
    @Autowired
	private EnrollmentService enrollmentService;
	
	@GetMapping("/login")
	public String login(Model model){
		model.addAttribute("user", new User());
		return "login";
	}
	

	@PostMapping(value = "/do_register")
    public String registerUser(@ModelAttribute User user, Model model) {
        boolean isDuplicateUser = userService.isUserAlreadyExists(user.getEmail());
        if(isDuplicateUser){
            model.addAttribute("error", "User already exists in database.");
            return "login";
        }
        
        User dbUser = userService.save(user);
        model.addAttribute("user", dbUser);
        return "login";
    }
	
	
	@GetMapping("/home")
	public String authenticatedHome(Model model, Authentication authentication){
        String role = authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .findFirst()
                        .orElse("");
        
        if (role.equals("student")) {
          	int id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        	List<Course> allCourses = courseService.getCourses(); 
        	List<Course> enrolledCourses = enrollmentService.getEnrolledCoursesByStudent(id); // Get enrolled courses
        	List<Course> availableCourses = allCourses.stream()
        	    .filter(course -> !enrolledCourses.contains(course))
        	    .collect(Collectors.toList());
        	model.addAttribute("user", userService.findById(id));
            model.addAttribute("courses", availableCourses);
            return "studentHome";
        } 
        else {
        	int id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        	List<Course> courses = courseService.findAllByInstructorId(id);
        	model.addAttribute("user", userService.findById(id));
        	model.addAttribute("courses", courses);
        	System.out.println("Instructor");
            return "instructorHome";
        }
	}
	
}
