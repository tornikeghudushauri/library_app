package com.library.app.book.repository.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Getter
@Setter
@SequenceGenerator(name = "ID_GEN", sequenceName = "ID_SEQ", initialValue = 1000, allocationSize = 1)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GEN")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    private String author;

    @Column(name = "publication_year")
    private LocalDate publicationYear;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "genre")
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "available_for_borrowing")
    private boolean availableForBorrowing;

}
