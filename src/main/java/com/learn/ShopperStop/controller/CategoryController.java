package com.learn.ShopperStop.controller;


import com.learn.ShopperStop.exceptions.AlreadyExistException;
import com.learn.ShopperStop.exceptions.ResourceNotFoundException;
import com.learn.ShopperStop.model.Category;
import com.learn.ShopperStop.reponse.ApiResponse;
import com.learn.ShopperStop.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService iCategoryService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories(){
        try {
            List<Category> categories = iCategoryService.getAllCategory();
            return ResponseEntity.ok(new ApiResponse("Found! ",categories));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:", INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name){
        try {
            Category category = iCategoryService.addCategory(name);
            return ResponseEntity.ok(new ApiResponse("Success",category));
        }
        catch (AlreadyExistException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/category/{id}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long id){
        try{
            Category category = iCategoryService.getCategoryById(id);
            return ResponseEntity.ok(new ApiResponse("Found",category));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }

    @GetMapping("/{name}/category")
    public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){
        try{
            Category category = iCategoryService.getCategoryByName(name);
            return ResponseEntity.ok(new ApiResponse("Found",category));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }

    @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long id){
        try{
            iCategoryService.deleteCategory(id);
            return ResponseEntity.ok(new ApiResponse("Deleted!!",null));
        }catch(ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));

        }
    }

    @PutMapping("/category/{id}/update")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long id,@RequestBody Category category){
        try {
            Category updatedCategory = iCategoryService.updatedCategory(category,id);
            return ResponseEntity.ok(new ApiResponse("Updated Success! ",updatedCategory));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }


}
