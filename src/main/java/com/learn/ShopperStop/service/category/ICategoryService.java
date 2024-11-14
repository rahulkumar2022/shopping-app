package com.learn.ShopperStop.service.category;

import com.learn.ShopperStop.model.Category;

import java.util.List;

public interface ICategoryService {
    Category getCategoryById(Long id);
    Category getCategoryByName(String name);
    List<Category> getAllCategory();
    Category addCategory(Category category);
    Category updatedCategory(Category category,Long id);
    void deleteCategory(Long id);
}
