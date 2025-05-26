package com.paccy.springbootcaching.services;

import com.paccy.springbootcaching.entities.Product;
import com.paccy.springbootcaching.requests.CreateProductRequest;
import com.paccy.springbootcaching.requests.EditProductRequest;
import org.springframework.data.domain.Page;

public interface ProductService {

    public Product createProduct(CreateProductRequest createProductRequest);
    public Product getProduct(Long id);
    public Page<Product> getAllProducts(int page,int size);
    public Product updateProduct(Long id, EditProductRequest editProductRequest);
    public void deleteProduct(Long id);
}
