package com.learn.ShopperStop.service.category;

import com.learn.ShopperStop.exceptions.AlreadyExistException;
import com.learn.ShopperStop.exceptions.ResourceNotFoundException;
import com.learn.ShopperStop.model.Category;
import com.learn.ShopperStop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Category not found! "));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public Category addCategory(Category category) {
        return Optional.of(category).filter(c->!categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save).orElseThrow(()-> new AlreadyExistException("Category Already exist!! "+category.getName()));
    }

    @Override
    public Category updatedCategory(Category category,Long id) {
        return Optional.ofNullable(getCategoryById(id)).map(oldCategory->{
            oldCategory.setName(category.getName());
            return categoryRepository.save(oldCategory);
        }).orElseThrow(()-> new ResourceNotFoundException("Invalid category id provided ! "));
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(categoryRepository::delete,()->{
            throw new ResourceNotFoundException("Category Not found! ");
        });
    }
}
