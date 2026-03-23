package com.example.KiemTraGiuaKy.controller;

import com.example.KiemTraGiuaKy.entity.Course;
import com.example.KiemTraGiuaKy.entity.Student;
import com.example.KiemTraGiuaKy.security.CustomUserDetails;
import com.example.KiemTraGiuaKy.service.CourseService;
import com.example.KiemTraGiuaKy.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/my-courses")
    public String viewMyCourses(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        Student student = userDetails.getStudent();
        model.addAttribute("enrollments", enrollmentService.getEnrollmentsByStudent(student));
        return "my-courses";
    }

    @PostMapping("/enroll/{courseId}")
    public String enrollInCourse(@PathVariable Long courseId,
                                 @AuthenticationPrincipal CustomUserDetails userDetails,
                                 RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        try {
            Student student = userDetails.getStudent();
            Course course = courseService.getCourseById(courseId);
            enrollmentService.enrollStudent(student, course);
            redirectAttributes.addFlashAttribute("successMessage", "Đăng ký thành công học phần: " + course.getName());
        } catch (RuntimeException ex) {
            redirectAttributes.addFlashAttribute("errorMessage", ex.getMessage());
        }
        return "redirect:/home";
    }
}
