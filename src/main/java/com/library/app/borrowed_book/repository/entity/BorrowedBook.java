package com.library.app.borrowed_book.repository.entity;

import com.library.app.book.repository.entity.Book;
import com.library.app.patron.repository.entity.Patron;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "borrowed_books")
@Getter
@Setter
@SequenceGenerator(name = "ID_GEN", sequenceName = "ID_SEQ", initialValue = 1000, allocationSize = 1)
public class BorrowedBook {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_GEN")
    private Long id;

    @ManyToOne
    private Patron patron;

    @ManyToOne
    private Book book;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private boolean returned;
}

