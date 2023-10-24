package com.library.app.borrowed_book.repository;

import com.library.app.borrowed_book.repository.entity.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {
    @Query("SELECT bb FROM BorrowedBook bb WHERE bb.dueDate < :currentDate AND bb.returned = false")
    List<BorrowedBook> findOverdueBooks(@Param("currentDate") LocalDate currentDate);

    Optional<BorrowedBook> findByPatronIdAndBookId(Long patronId, Long bookId);
}
