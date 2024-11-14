package com.learn.ShopperStop.repository;

import com.learn.ShopperStop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> findByCategoryName(String category);

    public List<Product> findByBrand(String brand);

    public List<Product> findByCategoryNameAndBrand(String category, String brand);

    public List<Product> findByName(String name) ;


    public List<Product> findByBrandAndName(String brand, String name);

    public Long countByBrandAndName(String brand, String name);

}
