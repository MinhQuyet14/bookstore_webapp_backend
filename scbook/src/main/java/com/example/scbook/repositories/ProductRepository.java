package com.example.scbook.repositories;

import com.example.scbook.models.Product;
import com.example.scbook.responses.ProductsSoldResponse;
import com.example.scbook.responses.SoldProductListResponse;
import com.example.scbook.responses.SoldProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    //Page<Book> findAll(Pageable pageable); //paging
    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    Page<Product> searchProducts
            (@Param("categoryId") Long categoryId,
            @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductsByIds(@Param("productIds") List<Long> productIds);

  @Query("SELECT p FROM Product p ORDER BY p.sold DESC LIMIT 3")
  List<Product> getTopSoldProducts();

    @Query("SELECT p FROM Product p WHERE p.sold > 0 AND "  +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE %:keyword% OR p.description LIKE %:keyword%)")
    Page<Product> getAllSoldProducts(@Param("categoryId") Long categoryId,
                                     @Param("keyword") String keyword, Pageable pageable);
}
