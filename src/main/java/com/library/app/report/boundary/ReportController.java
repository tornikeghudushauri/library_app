package com.library.app.report.boundary;

import com.library.app.borrowed_book.repository.entity.BorrowedBook;
import com.library.app.report.service.ReportService;
import com.library.app.report.repository.entity.TransactionHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/history")
    public String generateReports(Model model) {
        List<TransactionHistory> transactionHistory = reportService.generateTransactionHistoryReport();

        model.addAttribute("transactionHistory", transactionHistory);

        return "transaction_history";
    }
    @GetMapping("/notify")
    public String notifyPatrons() {
        reportService.notifyPatronsAboutOverdueBooks();

        return "notify";
    }

    @GetMapping("/overdue")
    public String viewOverdueBooks(Model model) {
        List<BorrowedBook> overdueBooks = reportService.findOverdueBooks();
        model.addAttribute("overdueBooks", overdueBooks);
        return "overdue-books";
    }
}