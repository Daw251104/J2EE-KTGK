package com.example.KiemTraGiuaKy.repository;

import com.example.KiemTraGiuaKy.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
