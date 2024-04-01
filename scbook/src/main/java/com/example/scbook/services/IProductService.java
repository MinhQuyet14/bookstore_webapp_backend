package com.example.scbook.services;

import com.example.scbook.dtos.ProductDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Product;
import com.example.scbook.responses.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface IProductService {
    Product createProduct(ProductDTO productDTO) throws Exception;
    Product getProductById(Long id) throws Exception;
    Page<ProductResponse> getAllProducts(PageRequest pageRequest);
    Product updateProduct(Long id, ProductDTO productDTO) throws Exception;

    void deleteProduct(Long id);
    boolean existsByName(String name);
}
