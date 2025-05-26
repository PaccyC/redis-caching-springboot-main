package com.paccy.springbootcaching.controllers;


import com.paccy.springbootcaching.entities.Product;
import com.paccy.springbootcaching.requests.CreateProductRequest;
import com.paccy.springbootcaching.requests.EditProductRequest;
import com.paccy.springbootcaching.response.ApiResponse;
import com.paccy.springbootcaching.services.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@CacheConfig(cacheNames = "product")
public class ProductController {
    private final ProductServiceImpl productService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<Product>> createProduct(
            @RequestBody CreateProductRequest createProductRequest
            ){
        Product response= productService.createProduct(createProductRequest);
        return new ApiResponse<>("Product Created Successfully", HttpStatus.CREATED,response).toResponseEntity();
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<Product>>> getAllProducts (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        Page<Product> response= productService.getAllProducts(page,size);

        return new ApiResponse<>("Product List Successfully", HttpStatus.OK,response).toResponseEntity();
    }

    @GetMapping("{id}")
//    @Cacheable(value = "product",key = "#id",unless = "#result.body.data.price > 100000")
    public ResponseEntity<ApiResponse<Product>> getProductById( @PathVariable("id") Long id){
        Product response = productService.getProduct(id);
        return new ApiResponse<>("Product details retrieved Successfully", HttpStatus.OK,response).toResponseEntity();
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse<Product>> editProduct(
            @PathVariable("id") Long id,
            @RequestBody EditProductRequest editProductRequest){
        Product response= productService.updateProduct(id,editProductRequest);
        return new ApiResponse<>("Product details updated successfully", HttpStatus.OK,response).toResponseEntity();
    }
    @DeleteMapping("/delete/{id}")
    @CacheEvict(cacheNames = "product",key = "#id",beforeInvocation = true)
    public void deleteProductById(@PathVariable("id") Long id){
         productService.deleteProduct(id);


    }


}
