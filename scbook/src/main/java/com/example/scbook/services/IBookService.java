package com.example.scbook.services;

import com.example.scbook.dtos.BookDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

public interface IBookService {
    Book createBook(BookDTO bookDTO) throws DataNotFoundException;
    Book getBookById(Long id);
    Page<Book> getAllBooks(PageRequest pageRequest);
    Book updateBook(Long id, BookDTO bookDTO);

    void deleteBook(Long id);
    boolean existsByName(String name);
}
