package com.example.keyboardshops.service.product;

import com.example.keyboardshops.model.Product;
import com.example.keyboardshops.request.AddProductRequest;
import com.example.keyboardshops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest request);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByNameAndBrand(String name, String brand);
    Long countProductsByBrandAndName(String brand, String name);

}
