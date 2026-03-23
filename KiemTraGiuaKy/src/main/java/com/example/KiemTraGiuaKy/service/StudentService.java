package com.example.KiemTraGiuaKy.service;

import com.example.KiemTraGiuaKy.entity.Role;
import com.example.KiemTraGiuaKy.entity.Student;
import com.example.KiemTraGiuaKy.repository.RoleRepository;
import com.example.KiemTraGiuaKy.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveStudent(Student student) {
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        Role role = roleRepository.findByName("STUDENT")
                .orElseGet(() -> {
                    Role newRole = new Role(null, "STUDENT");
                    return roleRepository.save(newRole);
                });
        
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        student.setRoles(roles);

        studentRepository.save(student);
    }

    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username).orElse(null);
    }
}
