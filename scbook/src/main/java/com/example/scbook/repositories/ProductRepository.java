package com.example.scbook.repositories;

import com.example.scbook.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    //Page<Book> findAll(Pageable pageable); //paging
}
