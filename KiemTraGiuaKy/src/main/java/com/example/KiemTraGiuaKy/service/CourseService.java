package com.example.KiemTraGiuaKy.service;

import com.example.KiemTraGiuaKy.entity.Course;
import com.example.KiemTraGiuaKy.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Page<Course> findPaginated(int pageNo, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        if (keyword != null && !keyword.isEmpty()) {
            return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }
        return courseRepository.findAll(pageable);
    }

    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    public Course getCourseById(Long id) {
        Optional<Course> optional = courseRepository.findById(id);
        Course course = null;
        if (optional.isPresent()) {
            course = optional.get();
        } else {
            throw new RuntimeException("Course not found for id :: " + id);
        }
        return course;
    }

    public void deleteCourseById(Long id) {
        this.courseRepository.deleteById(id);
    }
}
