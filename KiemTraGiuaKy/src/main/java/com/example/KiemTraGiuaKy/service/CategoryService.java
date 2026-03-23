package com.example.KiemTraGiuaKy.service;

import com.example.KiemTraGiuaKy.entity.Category;
import com.example.KiemTraGiuaKy.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
