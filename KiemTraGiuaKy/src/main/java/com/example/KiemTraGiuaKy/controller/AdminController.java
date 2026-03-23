package com.example.KiemTraGiuaKy.controller;

import com.example.KiemTraGiuaKy.entity.Course;
import com.example.KiemTraGiuaKy.service.CategoryService;
import com.example.KiemTraGiuaKy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/admin/courses")
public class AdminController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CategoryService categoryService;

    public static String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/images/";

    @GetMapping
    public String listCourses(Model model, @RequestParam(value = "page", defaultValue = "1") int pageNo) {
        int pageSize = 10;
        var page = courseService.findPaginated(pageNo, pageSize, null);
        model.addAttribute("listCourses", page.getContent());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        return "admin/course-list";
    }

    @GetMapping("/new")
    public String showNewCourseForm(Model model) {
        Course course = new Course();
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/course-form";
    }

    @PostMapping("/save")
    public String saveCourse(@ModelAttribute("course") Course course,
                             @RequestParam("imageFile") MultipartFile file) throws IOException {
        
        if (!file.isEmpty()) {
            String uuid = UUID.randomUUID().toString();
            String originalFileName = file.getOriginalFilename();
            String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            String fileName = uuid + extension;
            Path fileNameAndPath = Paths.get(UPLOAD_DIR, fileName);
            Files.createDirectories(fileNameAndPath.getParent());
            Files.write(fileNameAndPath, file.getBytes());
            course.setImage(fileName);
        } else if (course.getId() != null) {
            Course existingCourse = courseService.getCourseById(course.getId());
            course.setImage(existingCourse.getImage());
        }

        courseService.saveCourse(course);
        return "redirect:/admin/courses";
    }

    @GetMapping("/edit/{id}")
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("course", course);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "admin/course-form";
    }

    @GetMapping("/delete/{id}")
    public String deleteCourse(@PathVariable(value = "id") Long id) {
        courseService.deleteCourseById(id);
        return "redirect:/admin/courses";
    }
}
