package com.example.KiemTraGiuaKy.repository;

import com.example.KiemTraGiuaKy.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
