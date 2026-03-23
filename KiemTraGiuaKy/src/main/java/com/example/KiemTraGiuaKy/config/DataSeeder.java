package com.example.KiemTraGiuaKy.config;

import com.example.KiemTraGiuaKy.entity.Category;
import com.example.KiemTraGiuaKy.entity.Course;
import com.example.KiemTraGiuaKy.entity.Role;
import com.example.KiemTraGiuaKy.entity.Student;
import com.example.KiemTraGiuaKy.repository.CategoryRepository;
import com.example.KiemTraGiuaKy.repository.CourseRepository;
import com.example.KiemTraGiuaKy.repository.RoleRepository;
import com.example.KiemTraGiuaKy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Seed Roles
        if (roleRepository.count() == 0) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);

            Role studentRole = new Role();
            studentRole.setName("STUDENT");
            roleRepository.save(studentRole);
        }

        // Seed Admin Account
        if (studentRepository.findByUsername("admin").isEmpty()) {
            Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
            Student admin = new Student();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setEmail("admin@eduportal.com");

            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            admin.setRoles(roles);

            studentRepository.save(admin);
        }
        
        // Seed Student Account
        if (studentRepository.findByUsername("student").isEmpty()) {
            Role studentRole = roleRepository.findByName("STUDENT").orElse(null);
            Student student = new Student();
            student.setUsername("student");
            student.setPassword(passwordEncoder.encode("student123"));
            student.setEmail("student@eduportal.com");

            Set<Role> roles = new HashSet<>();
            roles.add(studentRole);
            student.setRoles(roles);

            studentRepository.save(student);
        }

        // Seed Categories and Courses
        if (categoryRepository.count() == 0) {
            Category itCategory = new Category();
            itCategory.setName("Công nghệ thông tin");
            itCategory = categoryRepository.save(itCategory);

            Category businessCategory = new Category();
            businessCategory.setName("Kinh doanh");
            businessCategory = categoryRepository.save(businessCategory);

            Category designCategory = new Category();
            designCategory.setName("Thiết kế đồ họa");
            designCategory = categoryRepository.save(designCategory);

            // Seed Courses
            if (courseRepository.count() == 0) {
                Course course1 = new Course();
                course1.setName("Lập trình Java Cơ bản");
                course1.setCredits(3);
                course1.setLecturer("Nguyễn Văn A");
                course1.setCategory(itCategory);
                course1.setImage("");
                courseRepository.save(course1);

                Course course2 = new Course();
                course2.setName("Lập trình Spring Boot Application");
                course2.setCredits(4);
                course2.setLecturer("Trần Trọng B");
                course2.setCategory(itCategory);
                course2.setImage("");
                courseRepository.save(course2);

                Course course3 = new Course();
                course3.setName("Quản trị Kinh doanh Đại cương");
                course3.setCredits(3);
                course3.setLecturer("Lê Thị C");
                course3.setCategory(businessCategory);
                course3.setImage("");
                courseRepository.save(course3);
                
                Course course4 = new Course();
                course4.setName("Thiết kế UX/UI");
                course4.setCredits(3);
                course4.setLecturer("Phạm Văn D");
                course4.setCategory(designCategory);
                course4.setImage("");
                courseRepository.save(course4);
                
                Course course5 = new Course();
                course5.setName("Cơ sở dữ liệu nâng cao");
                course5.setCredits(3);
                course5.setLecturer("Hoàng Minh E");
                course5.setCategory(itCategory);
                course5.setImage("");
                courseRepository.save(course5);
                
                Course course6 = new Course();
                course6.setName("Marketing số");
                course6.setCredits(3);
                course6.setLecturer("Đinh Quang F");
                course6.setCategory(businessCategory);
                course6.setImage("");
                courseRepository.save(course6);
            }
        }
    }
}
