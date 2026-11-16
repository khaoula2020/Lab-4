package com.example.shop.service;

import com.example.shop.dto.ProductDto;
import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;

    @Transactional
    public ProductDto create(ProductDto dto) {
        Category cat = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        Product p = new Product(null, dto.name(), dto.price(), cat);
        Product saved = productRepo.save(p);
        return new ProductDto(saved.getId(), saved.getName(), saved.getPrice(), cat.getId());
    }

    @Transactional
    public List<ProductDto> findAll() {
        List<Product> products = productRepo.findAll();
        List<ProductDto> result = new ArrayList<>();
        for (Product p : products) {
            Long categoryId = null;
            if (p.getCategory() != null) {
                categoryId = p.getCategory().getId();
            }
            result.add(new ProductDto(p.getId(), p.getName(), p.getPrice(), categoryId));
        }
        return result;
    }

    @Transactional
    public Page<ProductDto> findAll(Pageable pageable) {
        Page<Product> page = productRepo.findAll(pageable);
        List<ProductDto> result = new ArrayList<>();
        for (Product p : page.getContent()) {
            Long categoryId = null;
            if (p.getCategory() != null) {
                categoryId = p.getCategory().getId();
            }
            result.add(new ProductDto(p.getId(), p.getName(), p.getPrice(), categoryId));
        }
        return new PageImpl<>(result, pageable, page.getTotalElements());
    }

    @Transactional
    public ProductDto getById(Long id) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Long categoryId = null;
        if (p.getCategory() != null) {
            categoryId = p.getCategory().getId();
        }
        return new ProductDto(p.getId(), p.getName(), p.getPrice(), categoryId);
    }

    @Transactional
    public ProductDto update(Long id, ProductDto dto) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        Category cat = categoryRepo.findById(dto.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        p.setName(dto.name());
        p.setPrice(dto.price());
        p.setCategory(cat);
        Product saved = productRepo.save(p);
        return new ProductDto(saved.getId(), saved.getName(), saved.getPrice(), cat.getId());
    }

    @Transactional
    public void delete(Long id) {
        Product p = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        productRepo.delete(p);
    }
}