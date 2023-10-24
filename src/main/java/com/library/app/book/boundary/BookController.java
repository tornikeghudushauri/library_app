package com.library.app.book.boundary;

import com.library.app.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/add")
    public String getAddBookForm(Model model) {
        model.addAttribute("book", new BookDTO());
        return "books-add";
    }

    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") BookDTO bookDTO) {
        bookService.saveBook(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String getEditBookForm(@PathVariable Long id, Model model) {
        Optional<BookDTO> bookDTO = bookService.getBookById(id);
        model.addAttribute("book", bookDTO.orElseGet(BookDTO::new));
        return "books-edit";
    }

    @PostMapping("/edit")
    public String editBook(@ModelAttribute("book") BookDTO bookDTO) {
        bookService.updateBook(bookDTO);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping
    public String getAllBooks(Model model) {
        List<BookDTO> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books-list";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        Optional<BookDTO> bookDTO = bookService.getBookById(id);
        model.addAttribute("book", bookDTO.orElseGet(BookDTO::new));
        return "book-details";
    }

    @GetMapping("/search-books")
    public String searchBooks(@RequestParam(name = "searchText", required = false) String searchText,
                              @RequestParam(name = "availability", required = false) boolean availability,
                              Model model) {
        List<BookDTO> books = bookService.filterBooks(searchText, availability);
        model.addAttribute("books", books);
        return "books-list";
    }
}
