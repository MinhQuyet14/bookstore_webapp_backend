package com.example.scbook.services.Impl;

import com.example.scbook.dtos.ProductDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Author;
import com.example.scbook.models.Product;
import com.example.scbook.models.Category;
import com.example.scbook.repositories.AuthorRepository;
import com.example.scbook.repositories.ProductRepository;
import com.example.scbook.repositories.CategoryRepository;
import com.example.scbook.responses.ProductResponse;
import com.example.scbook.services.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Product createProduct(ProductDTO productDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(
                        ()-> new DataNotFoundException(
                                "Cannot find category with id: "+ productDTO.getCategoryId()));
        Author existingAuthor = authorRepository.findById(productDTO.getAuthorId())
                .orElseThrow(
                        ()-> new DataNotFoundException(
                                "Cannot find author with id: "+ productDTO.getAuthorId()));
        Product newProduct = Product.builder()
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .url(productDTO.getUrl())
                .category(existingCategory)
                .author(existingAuthor)
                .build();
        return productRepository.save(newProduct);
    }

    @Override
    public Product getProductById(Long id) throws Exception {
        return productRepository.findById(id)
                .orElseThrow(()-> new DataNotFoundException("Cannot find product with this id: " + id));
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest).map(product -> ProductResponse.fromProduct(product));
    }

    @Override
    public Product updateProduct(Long id, ProductDTO productDTO) throws Exception {
        Product existingProduct = getProductById(id);
        if(existingProduct != null){
            existingProduct.setName(productDTO.getName());
            Category existingCategory = categoryRepository.findById(productDTO.getCategoryId())
                    .orElseThrow(
                            ()-> new DataNotFoundException(
                                    "Cannot find category with id: "+ productDTO.getCategoryId()));
            Author existingAuthor = authorRepository.findById(productDTO.getAuthorId())
                    .orElseThrow(
                            ()-> new DataNotFoundException(
                                    "Cannot find author with id: "+ productDTO.getAuthorId()));
            existingProduct.setCategory(existingCategory);
            existingProduct.setAuthor(existingAuthor);
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setUrl(productDTO.getUrl());
            productRepository.save(existingProduct);
        }
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsByName(String name) {
        return productRepository.existsByName(name);
    }
}
