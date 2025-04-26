package ru.otus.hw.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<BookDto> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping(value = "/book/{id}")
    public String bookEditPage(@PathVariable("id") long id, Model model) {
        BookDto book = bookService.findById(id);
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();

        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book_edit";
    }

    @PostMapping(value = "/book")
    public String saveBook(@ModelAttribute("book") BookDto book) {
        if (book.getId() == 0) {
            bookService.create(book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        } else {
            bookService.update(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getGenre().getId());
        }

        return "redirect:/";
    }

    @PostMapping(value = "/book/{id}/delete")
    public String deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping(value = "/book")
    public String bookNewPage(Model model) {
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();

        BookDto bookDto = new BookDto();
        bookDto.setGenre(new GenreDto());
        bookDto.setAuthor(new AuthorDto());

        model.addAttribute("book", bookDto);
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        return "book_edit";
    }

}
