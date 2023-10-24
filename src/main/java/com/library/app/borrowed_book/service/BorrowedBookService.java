package com.library.app.borrowed_book.service;

import com.library.app.book.repository.BookRepository;
import com.library.app.book.repository.entity.Book;
import com.library.app.borrowed_book.repository.BorrowedBookRepository;
import com.library.app.borrowed_book.repository.entity.BorrowedBook;
import com.library.app.exception.*;
import com.library.app.patron.repository.PatronRepository;
import com.library.app.patron.repository.entity.Patron;
import com.library.app.report.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * The `BorrowedBookService` class provides services for managing borrowed books within the library.
 */
@Service
public class BorrowedBookService {

    private final BorrowedBookRepository borrowedBookRepository;
    private final ReportService reportService;
    private final PatronRepository patronRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BorrowedBookService(
            BorrowedBookRepository borrowedBookRepository,
            ReportService reportService, PatronRepository patronRepository,
            BookRepository bookRepository
    ) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.reportService = reportService;
        this.patronRepository = patronRepository;
        this.bookRepository = bookRepository;
    }


    /**
     * Allows a patron to borrow a book, provided they are a library member.
     *
     * @param patronId      The unique identifier of the patron borrowing the book.
     * @param bookId        The unique identifier of the book to be borrowed.
     * @param borrowingDays The number of days the book will be borrowed.
     * @throws PatronNotMemberException              If the patron is not a library member.
     * @throws BookNotAvailableForBorrowingException If the book is not available for borrowing.
     * @throws InvalidBorrowingPeriodException       If the specified borrowing period is invalid.
     */
    public void borrowBook(Long patronId, Long bookId, int borrowingDays) throws PatronNotMemberException {
        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new PatronNotFoundException("Couldn't find patron with id: " + patronId));
        if (!patron.isMember()) {
            throw new PatronNotMemberException("patron is not a member");
        }
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException("Couldn't find patron with id: " + bookId));

        if (!book.isAvailableForBorrowing()) {
            throw new BookNotAvailableForBorrowingException(String.format("book with id:%s is not available for borrowing", book.getId()));
        }

        if (borrowingDays < 1 || borrowingDays > 30) {
            throw new InvalidBorrowingPeriodException();
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setPatron(patron);
        borrowedBook.setBook(book);
        borrowedBook.setBorrowDate(LocalDate.now());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, borrowingDays);
        borrowedBook.setDueDate(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        borrowedBookRepository.save(borrowedBook);

        book.setAvailableForBorrowing(false);
        bookRepository.save(book);

        reportService.saveTransactionHistory("Borrowed", patronId, bookId);
    }


    /**
     * Allows a patron to return a borrowed book.
     *
     * @param patronId The unique identifier of the patron returning the book.
     * @param bookId   The unique identifier of the book to be returned.
     */
    public void returnBook(Long patronId, Long bookId) {
        BorrowedBook borrowedBook = borrowedBookRepository.findByPatronIdAndBookId(patronId, bookId)
                .orElseThrow(() -> new RuntimeException("Borrowed book not found"));
        Book book = borrowedBook.getBook();
        book.setAvailableForBorrowing(true);

        bookRepository.save(book);

        borrowedBookRepository.delete(borrowedBook);

        reportService.saveTransactionHistory("Returned", patronId, bookId);
    }
}


