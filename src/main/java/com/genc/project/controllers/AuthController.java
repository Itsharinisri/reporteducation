package com.genc.project.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.genc.project.dto.AuthRequest;
import com.genc.project.entities.Course;
import com.genc.project.dto.CourseCard;
import com.genc.project.dto.PasswordChangeRequest;
import com.genc.project.dto.StudentReport;
import com.genc.project.entities.User;
import com.genc.project.repositories.CourseRepository;
import com.genc.project.services.EnrollmentService;
import com.genc.project.services.ReportService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;
import com.genc.project.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;


@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RestController
public class AuthController {

	@Autowired
    AuthenticationManager authenticationManager;
	
    @Autowired
    JwtUtil jwtUtil;
    
    @Autowired
	private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
	private ReportService reportService;
    
    @Autowired
    private CourseRepository courseRepository;
    
    @PostMapping("/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody AuthRequest user, HttpServletResponse response){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
            String token = jwtUtil.generateToken(user.getEmail());
            ResponseCookie cookie = ResponseCookie.from("jwt", token)
            		.httpOnly(true).path("/").build(); 
            response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
            return ResponseEntity.ok(Map.of("token", token));

        }catch (Exception e){
            throw e;
        }
    }
    
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request, Authentication auth) {
        System.out.println("Haiiiiiiiiiiiiiiiiiiiiiiiiiii");
    	User user = userService.findById(((UserDetailsImpl) auth.getPrincipal()).getId());

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ResponseEntity.badRequest().body("Current password is incorrect.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.updateUserPassword(user);

        return ResponseEntity.ok("Password changed successfully.");
    }
    
    @GetMapping("/courses")
    public List<CourseCard> courses() { 
        List<Course> allCourses = courseRepository.findAll();

     
        List<CourseCard> courseCards = allCourses.stream().map(course -> new CourseCard(course.getId(),course.getTitle(),course.getInstructor(),course.getDescription()))
            .limit(9) 
            .collect(Collectors.toList());

        return courseCards;
    }
    
    @GetMapping("/api/instructor/reports")
    public ResponseEntity<List<StudentReport>> getStudentReportsForCourse(@RequestParam int courseId,
    		Authentication auth) {
    	System.out.println("))))))))))))))))))))))))))");
    	int uid = ((UserDetailsImpl) auth.getPrincipal()).getId();
        List<StudentReport> reports = reportService.getStudentReportsForCourse(courseId, userService.findById(uid));
        return ResponseEntity.ok(reports);
    }
}
