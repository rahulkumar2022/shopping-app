package com.learn.ShopperStop.service.product;

import com.learn.ShopperStop.model.Product;
import com.learn.ShopperStop.request.AddProductRequest;
import com.learn.ShopperStop.request.ProductUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface IProductService {

    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProductById(ProductUpdateRequest product, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(String category);
    List<Product> getProductByBrand(String brand);
    List<Product> getProductByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductByBrandAndName(String category, String name);
    Long countProductsByBrandAndName(String brand,String name);



}
