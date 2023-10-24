package com.library.app.report.repository.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "action")
    private String action;

    @Column(name = "patron_id")
    private Long patronId;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "borrowed_book_id")
    private Long borrowedBookId;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

}