package com.example.scbook.repositories;

import com.example.scbook.models.Book;
import org.hibernate.query.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.*;
import java.awt.print.Pageable;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByName(String name);

    //Page<Book> findAll(Pageable pageable); //paging
}
