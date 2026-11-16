package com.example.shop.service;

import com.example.shop.dto.CategoryDto;
import com.example.shop.entity.Category;
import com.example.shop.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepo;

    @Transactional
    public CategoryDto create(CategoryDto dto) {
        Category cat = new Category();
        cat.setName(dto.name());
        Category saved = categoryRepo.save(cat);
        return new CategoryDto(saved.getId(), saved.getName());
    }

    @Transactional
    public CategoryDto getById(Long id) {
        Category cat = categoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return new CategoryDto(cat.getId(), cat.getName());
    }

    @Transactional
    public List<CategoryDto> findAll() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> result = new ArrayList<>();
        for (Category cat : categories) {
            result.add(new CategoryDto(cat.getId(), cat.getName()));
        }
        return result;
    }
}