package com.library.app.book.service;

import com.library.app.book.boundary.BookDTO;
import com.library.app.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The `BookService` class provides services for managing books within the library.
 */
@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<BookDTO> getBookById(Long id) {
        return bookRepository.findById(id).map(BookDTO::fromEntity);
    }

    public void saveBook(BookDTO bookDTO) {
        BookDTO.fromEntity(bookRepository.save(bookDTO.toEntity()));
    }

    public void updateBook(BookDTO bookDTO) {
        bookRepository.save(bookDTO.toEntity());
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    /**
     * Filters and retrieves a list of books based on a search text and availability status.
     *
     * @param searchText   The search text to filter books by title or author.
     * @param availability The availability status (true for available, false for not available).
     * @return A list of `BookDTO` representing filtered books.
     */
    public List<BookDTO> filterBooks(String searchText, boolean availability) {
        return bookRepository.filterBooks(searchText, availability).stream()
                .map(BookDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
