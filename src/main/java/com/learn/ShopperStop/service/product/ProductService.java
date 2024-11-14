package com.learn.ShopperStop.service.product;

import com.learn.ShopperStop.exceptions.ProductNotFoundException;
import com.learn.ShopperStop.exceptions.ResourceNotFoundException;
import com.learn.ShopperStop.model.Category;
import com.learn.ShopperStop.model.Product;
import com.learn.ShopperStop.repository.CategoryRepository;
import com.learn.ShopperStop.repository.ProductRepository;
import com.learn.ShopperStop.request.AddProductRequest;
import com.learn.ShopperStop.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService{

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    @Override
    public Product addProduct(AddProductRequest request) {
        // check if the category is found in ths DB
        // If Yes, set it as the new Product category
        // If No, then save it as new category
        //  Set as new product category.
        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName())).orElseGet(
                ()->{
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                }
        );
        request.setCategory(category);
        return productRepository.save(createProduct(request,category));
    }

    private Product createProduct(AddProductRequest request, Category category){
        return new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getInventory(),
                request.getBrand(),
                category

        );
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete,
                ()-> {
            throw new ResourceNotFoundException("Product Not found!");
        });

    }

    @Override
    public Product updateProductById(ProductUpdateRequest request, Long productId) {
        return productRepository.findById(productId)
                .map(existingProduct->updatexistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product not Found! "));

    }

    private Product updatexistingProduct(Product existingProduct, ProductUpdateRequest request){
        existingProduct.setCategory(request.getCategory());
        existingProduct.setName(request.getName());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setBrand(request.getBrand());
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand,name);
    }
}
