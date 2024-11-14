package com.learn.ShopperStop.controller;


import com.learn.ShopperStop.exceptions.ResourceNotFoundException;
import com.learn.ShopperStop.model.Product;
import com.learn.ShopperStop.reponse.ApiResponse;
import com.learn.ShopperStop.request.AddProductRequest;
import com.learn.ShopperStop.request.ProductUpdateRequest;
import com.learn.ShopperStop.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {
    private final IProductService iProductService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllProducts(){
        List<Product> products = iProductService.getAllProducts();
        return ResponseEntity.ok(new ApiResponse("success",products));
    }
    @GetMapping("/product/{productId}/product")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId){
        try{
            Product product = iProductService.getProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Success",product));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }

    }


    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){
        try {
            Product product1 = iProductService.addProduct(product);
            return ResponseEntity.ok(new ApiResponse("Product Addition Success!!", product1));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PutMapping("/product/{productId}/update")
    public ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product,@PathVariable Long productId){
        try{
            Product product1 = iProductService.updateProductById(product,productId);
            return ResponseEntity.ok(new ApiResponse("Update Product Success",product1));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @DeleteMapping("/product/{productId}/delete")
    public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){
        try{
            iProductService.deleteProductById(productId);
            return ResponseEntity.ok(new ApiResponse("Delete product success",null));
        }
        catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }

    @GetMapping("/productName/brandName")
    public ResponseEntity<ApiResponse> getProuctByBrandName(@RequestParam String brandName,@RequestParam String productName){
        try {
            List<Product> products = iProductService.getProductByBrandAndName(brandName,productName);
            if(products.isEmpty())
                return ResponseEntity.ok(new ApiResponse("No Product found!!",null));
             return ResponseEntity.ok(new ApiResponse("Success",products));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }

    @GetMapping("/product/{name}")
    public ResponseEntity<ApiResponse> getProuctByBrandName(@PathVariable String name){
        try {
            List<Product> products = iProductService.getProductByName(name);
            if(products.isEmpty())
                return ResponseEntity.ok(new ApiResponse("No Product found!!",null));
            return ResponseEntity.ok(new ApiResponse("Success",products));
        }
        catch (Exception e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(),null));
        }

    }
}
