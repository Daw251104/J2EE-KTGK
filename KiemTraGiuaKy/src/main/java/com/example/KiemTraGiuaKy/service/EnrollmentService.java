package com.example.KiemTraGiuaKy.service;

import com.example.KiemTraGiuaKy.entity.Course;
import com.example.KiemTraGiuaKy.entity.Enrollment;
import com.example.KiemTraGiuaKy.entity.Student;
import com.example.KiemTraGiuaKy.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enrollStudent(Student student, Course course) {
        if (!enrollmentRepository.existsByStudentAndCourse(student, course)) {
            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(student);
            enrollment.setCourse(course);
            enrollment.setEnrollDate(LocalDate.now());
            enrollmentRepository.save(enrollment);
        } else {
            throw new RuntimeException("Bạn đã đăng ký học phần này rồi.");
        }
    }
}
