package com.library.app.book.repository;

import com.library.app.book.repository.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitleContainingIgnoreCaseAndAvailableForBorrowingIsTrue(String searchText);

    default List<Book> filterBooks(String searchText, Boolean available) {
        List<Book> books = findAll();

        if (searchText != null && !searchText.isEmpty()) {
            books = books.stream()
                    .filter(book -> book.getTitle().toLowerCase().contains(searchText.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (available != null && available) {
            books = books.stream()
                    .filter(Book::isAvailableForBorrowing)
                    .collect(Collectors.toList());
        }

        return books;
    }
}
