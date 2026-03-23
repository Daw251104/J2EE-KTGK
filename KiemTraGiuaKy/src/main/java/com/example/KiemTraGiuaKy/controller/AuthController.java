package com.example.KiemTraGiuaKy.controller;

import com.example.KiemTraGiuaKy.entity.Student;
import com.example.KiemTraGiuaKy.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUserAccount(@ModelAttribute("student") Student student, Model model) {
        if (studentService.findByUsername(student.getUsername()) != null) {
            model.addAttribute("error", "Username already exists");
            return "auth/register";
        }
        try {
            studentService.saveStudent(student);
        } catch (Exception e) {
            model.addAttribute("error", "Email may already be used or validation failed.");
            return "auth/register";
        }
        return "redirect:/login?success";
    }
}
