package com.library.app.book.boundary;

import com.library.app.book.repository.entity.Book;
import com.library.app.book.repository.entity.Genre;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
public class BookDTO {
    private Long id;
    private String title;
    private String author;
    @NotEmpty
    private String isbn;
    @NotEmpty
    private Genre genre;
    private boolean availableForBorrowing;

    public static BookDTO fromEntity(Book book) {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(book.getId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setIsbn(book.getIsbn());
        bookDTO.setGenre(book.getGenre());
        bookDTO.setAvailableForBorrowing(book.isAvailableForBorrowing());
        return bookDTO;
    }

    public Book toEntity() {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setIsbn(isbn);
        book.setGenre(genre);
        book.setAvailableForBorrowing(availableForBorrowing);
        return book;
    }
}
