package com.paccy.springbootcaching.services.impl;

import com.paccy.springbootcaching.entities.Product;
import com.paccy.springbootcaching.exceptions.BadRequestException;
import com.paccy.springbootcaching.repository.ProductRepository;
import com.paccy.springbootcaching.requests.CreateProductRequest;
import com.paccy.springbootcaching.requests.EditProductRequest;
import com.paccy.springbootcaching.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;;
    @Override
    public Product createProduct(CreateProductRequest createProductRequest) {

        try {
            Optional<Product> _existingProduct= productRepository.findByCode(createProductRequest.getCode());
            if (_existingProduct.isPresent()){
                throw  new BadRequestException("Product already exists");
            }
            Product product = Product
                    .builder()
                    .code(createProductRequest.getCode())
                    .name(createProductRequest.getName())
                    .quantity(createProductRequest.getQuantity())
                    .price(createProductRequest.getPrice())
                    .build();
            return productRepository.save(product);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    @Cacheable(value = "product")
    public Product getProduct(Long id) {
        Optional<Product> product= productRepository.findById(id);
        if (product.isEmpty()){
            throw  new BadRequestException("Product with the provided ID doesn't  exist");
        }
        return product.get();
    }

    @Override
    @Cacheable("product")
    public Page<Product> getAllProducts(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);

        return productRepository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "product", key = "#id")
    public Product updateProduct(Long id, EditProductRequest editProductRequest) {
      Product product = productRepository.findById(id).orElseThrow(
              ()-> new BadRequestException("Product with the provided ID doesn't exist")
      );
      product.setName(editProductRequest.getName());
      product.setQuantity(editProductRequest.getQuantity());
      product.setPrice(editProductRequest.getPrice());
     return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                ()-> new BadRequestException("Cannot delete the product which doesn't exist")
        );

        productRepository.delete(product);
    }
}
