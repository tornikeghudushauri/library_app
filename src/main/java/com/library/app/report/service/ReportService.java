package com.library.app.report.service;

import com.library.app.book.repository.entity.Book;
import com.library.app.borrowed_book.repository.BorrowedBookRepository;
import com.library.app.borrowed_book.repository.entity.BorrowedBook;
import com.library.app.patron.repository.entity.Patron;
import com.library.app.report.repository.entity.TransactionHistory;
import com.library.app.report.repository.TransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * The `ReportService` class provides services related to generating and sending reports, as well as handling notifications.
 */
@Service
public class ReportService {
    private final BorrowedBookRepository borrowedBookRepository;


    private final TransactionHistoryRepository transactionHistoryRepository;

    private final JavaMailSender javaMailSender;


    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    public ReportService(BorrowedBookRepository borrowedBookRepository,
                         TransactionHistoryRepository transactionHistoryRepository,
                         JavaMailSender javaMailSender) {
        this.borrowedBookRepository = borrowedBookRepository;
        this.transactionHistoryRepository = transactionHistoryRepository;
        this.javaMailSender = javaMailSender;
    }


    public List<TransactionHistory> generateTransactionHistoryReport() {
        return transactionHistoryRepository.findAll();
    }

    public void scheduleOverdueNotifications(List<BorrowedBook> overdueBooks) {
        overdueBooks.forEach(borrowedBook -> sendOverdueNotification(borrowedBook.getPatron(), borrowedBook.getBook()));
    }

    /**
     * Sends an overdue book notification to a patron via email.
     *
     * @param patron The patron to whom the notification will be sent.
     * @param book   The overdue book.
     */
    public void sendOverdueNotification(Patron patron, Book book) {
        String recipientEmail = patron.getEmail();

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(senderEmail);
            helper.setTo(recipientEmail);
            helper.setSubject("Overdue Book Reminder");
            helper.setText("Dear " + patron.getName() + ",\n\n" + "You have an overdue book: " + book.getTitle() +
                    ". Please return it as soon as possible.\n\n" + "Thank you for using our library!");

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public List<BorrowedBook> findOverdueBooks() {
        return borrowedBookRepository.findOverdueBooks(LocalDate.now());
    }

    public void notifyPatronsAboutOverdueBooks() {
        scheduleOverdueNotifications(findOverdueBooks());
    }

    public void saveTransactionHistory(String action, Long patronId, Long bookId) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAction(action);
        transactionHistory.setPatronId(patronId);
        transactionHistory.setBookId(bookId);
        transactionHistory.setTransactionDate(LocalDateTime.now());

        transactionHistoryRepository.save(transactionHistory);
    }
}

