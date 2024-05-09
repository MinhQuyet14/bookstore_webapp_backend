package com.example.scbook.services.Impl;

import com.example.scbook.dtos.ProductDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Product;
import com.example.scbook.models.Category;
import com.example.scbook.repositories.ProductRepository;
import com.example.scbook.repositories.CategoryRepository;
import com.example.scbook.responses.ProductResponse;
import com.example.scbook.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
//        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
//                .orElseThrow(
//                        ()-> new DataNotFoundException(
//                                "Cannot find category with id: "+ productDTO.getCategoryId()));

        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .url(productDTO.getUrl())
                //.category(existingCategory)
                .author(productDTO.getAuthor())
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find product with this id: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Long categoryId, PageRequest pageRequest) {
        Page<Product> productsPage;
        productsPage = productRepository.searchProducts(categoryId, keyword, pageRequest);
        return productsPage.map(ProductResponse::fromProduct);
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            existingProduct.setName(productDTO.getName());
//            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
//                    .orElseThrow(
//                            ()-> new DataNotFoundException(
//                                    "Cannot find category with id: "+ productDTO.getCategoryId()));
//
//            existingProduct.setCategory(existingCategory);
            existingProduct.setAuthor(productDTO.getAuthor());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setUrl(productDTO.getUrl());
            productRepository.save(existingProduct);
        }
        return existingProduct;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }

    @Override
    public List<Product> findProductsByIds(List<Long> productIds) {
        return productRepository.findProductsByIds(productIds);
    }

}
