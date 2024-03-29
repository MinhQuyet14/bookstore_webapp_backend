package com.example.scbook.services.Impl;

import com.example.scbook.dtos.BookDTO;
import com.example.scbook.exceptions.DataNotFoundException;
import com.example.scbook.models.Author;
import com.example.scbook.models.Book;
import com.example.scbook.models.Category;
import com.example.scbook.repositories.AuthorRepository;
import com.example.scbook.repositories.BookRepository;
import com.example.scbook.repositories.CategoryRepository;
import com.example.scbook.services.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    @Override
    public Book createBook(BookDTO bookDTO) throws DataNotFoundException {
        Category existingCategory = categoryRepository.findById(bookDTO.getCategoryId())
                .orElseThrow(
                        ()-> new DataNotFoundException(
                                "Cannot find category with id: "+bookDTO.getCategoryId()));
        Author existingAuthor = authorRepository.findById(bookDTO.getAuthorId())
                .orElseThrow(
                        ()-> new DataNotFoundException(
                                "Cannot find author with id: "+bookDTO.getAuthorId()));
        Book newBook = Book.builder()
                .name(bookDTO.getName())
                .price(bookDTO.getPrice())
                .url(bookDTO.getUrl())
                .category(existingCategory)
                .author(existingAuthor)
                .build();
        return bookRepository.save(newBook);
    }

    @Override
    public Book getBookById(Long id) {
        return null;
    }

    @Override
    public Page<Book> getAllBooks(PageRequest pageRequest) {
        return null;
    }

    @Override
    public Book updateBook(Long id, BookDTO bookDTO) {
        return null;
    }

    @Override
    public void deleteBook(Long id) {

    }

    @Override
    public boolean existsByName(String name) {
        return false;
    }
}
