package com.library.app.borrowed_book.boundary;

import com.library.app.borrowed_book.repository.entity.BorrowedBook;
import com.library.app.borrowed_book.service.BorrowedBookService;
import com.library.app.exception.PatronNotMemberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/borrowed-books")
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    @Autowired
    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }
    @GetMapping("/borrow")
    public String showBorrowBookForm(
            @RequestParam Long bookId,
            Model model
    ) {
        model.addAttribute("bookId", bookId);

        return "book/borrow-book-form";
    }

    @PostMapping("/borrow")
    public String borrowBook(
            @RequestParam Long patronId,
            @RequestParam Long bookId,
            @RequestParam int borrowingDays,
            RedirectAttributes redirectAttributes
    ) {
        try {
            borrowedBookService.borrowBook(patronId, bookId, borrowingDays);
            redirectAttributes.addFlashAttribute("successMessage", "Book successfully borrowed!");
        } catch (PatronNotMemberException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }

    @GetMapping("/return")
    public String showReturnBookForm(Model model) {
        model.addAttribute("returnForm", new BorrowedBook());

        return "return-book-form";
    }

    @PostMapping("/return")
    public String returnBook(
            @RequestParam Long patronId,
            @RequestParam Long bookId,
            RedirectAttributes redirectAttributes
    ) {
        try {
            borrowedBookService.returnBook(patronId, bookId);
            redirectAttributes.addFlashAttribute("successMessage", "Book successfully returned!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/books";
    }

}
