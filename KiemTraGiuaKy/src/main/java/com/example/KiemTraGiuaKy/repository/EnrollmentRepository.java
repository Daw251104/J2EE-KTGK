package com.example.KiemTraGiuaKy.repository;

import com.example.KiemTraGiuaKy.entity.Enrollment;
import com.example.KiemTraGiuaKy.entity.Student;
import com.example.KiemTraGiuaKy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudent(Student student);
    boolean existsByStudentAndCourse(Student student, Course course);
}
