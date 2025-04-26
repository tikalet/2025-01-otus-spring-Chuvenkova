package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.NotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Контроллер для книг")
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private GenreService genreService;

    @DisplayName("должен отдать главную страницу с моделью")
    @Test
    public void shouldRenderMainPageWithModel() throws Exception {
        List<BookDto> bookDtoList = createBookList();

        when(bookService.findAll()).thenReturn(bookDtoList);

        mvc.perform(get("/"))
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", bookDtoList));
    }

    @DisplayName("должен отдать страницу для редактирования книги с моделью")
    @Test
    public void shouldRenderBookEditPageWithModel() throws Exception {
        List<BookDto> bookDtoList = createBookList();
        BookDto bookDto = bookDtoList.get(0);

        List<AuthorDto> authorDtoList = createAuthorList();
        List<GenreDto> genreDtoList = createGenreList();

        when(bookService.findById(1)).thenReturn(bookDto);
        when(authorService.findAll()).thenReturn(authorDtoList);
        when(genreService.findAll()).thenReturn(genreDtoList);

        mvc.perform(get("/book/1"))
                .andExpect(view().name("book_edit"))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(model().attribute("authors", authorDtoList))
                .andExpect(model().attribute("genres", genreDtoList));
    }

    @DisplayName("должен обновить книгу и сделать redirect на главную страницу")
    @Test
    public void shouldSaveBookAndRedirectMainPage() throws Exception {
        List<BookDto> bookDtoList = createBookList();
        BookDto bookDto = bookDtoList.get(0);
        bookDto.setTitle("Title_new");

        mvc.perform(post("/book")
                        .param("id", "1")
                        .param("title", "Title_new")
                        .param("author.id", "1")
                        .param("genre.id", "1"))
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1))
                .update(bookDto.getId(), bookDto.getTitle(), bookDto.getAuthor().getId(), bookDto.getGenre().getId());
    }

    @DisplayName("должен удалить книгу и сделать redirect на главную страницу")
    @Test
    public void shouldDeleteBookAndRedirectMainPage() throws Exception {
        mvc.perform(post("/book/1/delete"))
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1)).deleteById(1);
    }

    @DisplayName("должен отдать страницу для создания новой книги с моделью")
    @Test
    public void shouldRenderNewBookPageWithModel() throws Exception {
        List<AuthorDto> authorDtoList = createAuthorList();
        List<GenreDto> genreDtoList = createGenreList();
        BookDto bookDto = new BookDto();
        bookDto.setGenre(new GenreDto());
        bookDto.setAuthor(new AuthorDto());

        when(authorService.findAll()).thenReturn(authorDtoList);
        when(genreService.findAll()).thenReturn(genreDtoList);

        mvc.perform(get("/book"))
                .andExpect(view().name("book_edit"))
                .andExpect(model().attribute("book", bookDto))
                .andExpect(model().attribute("authors", authorDtoList))
                .andExpect(model().attribute("genres", genreDtoList));
    }

    @DisplayName("должен создать книгу и сделать redirect на главную страницу")
    @Test
    public void shouldSaveNewBookAndRedirectMainPage() throws Exception {
        BookDto bookDto = new BookDto();
        bookDto.setId(0);
        bookDto.setTitle("new book");
        bookDto.setGenre(new GenreDto());
        bookDto.getGenre().setId(2);
        bookDto.setAuthor(new AuthorDto());
        bookDto.getAuthor().setId(1);

        mvc.perform(post("/book")
                        .param("id", "0")
                        .param("title", "new book")
                        .param("author.id", "1")
                        .param("genre.id", "2"))
                .andExpect(view().name("redirect:/"));

        verify(bookService, times(1))
                .create(bookDto.getTitle(), bookDto.getAuthor().getId(), bookDto.getGenre().getId());
    }

    @DisplayName("Должен отдать страницу с ошибкой если будет NotFoundException")
    @Test
    public void shouldRenderErrorPage() throws Exception {
        when(bookService.findById(1)).thenThrow(new NotFoundException(""));
        mvc.perform(get("/book/1"))
                .andExpect(view().name("error"));
    }

    private List<BookDto> createBookList() {
        List<BookDto> bookDtoList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            BookDto bookDto = new BookDto();
            bookDto.setId(i);
            bookDto.setTitle("Title_" + i);
            bookDto.setAuthor(new AuthorDto());
            bookDto.getAuthor().setId(i);
            bookDto.getAuthor().setFullName("Author_" + i);
            bookDto.setGenre(new GenreDto());
            bookDto.getGenre().setId(i);
            bookDto.getGenre().setName("Genre_" + i);
            bookDtoList.add(bookDto);
        }

        return bookDtoList;
    }

    private List<AuthorDto> createAuthorList() {
        List<AuthorDto> authorDtoList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            AuthorDto authorDto = new AuthorDto();
            authorDto.setId(i);
            authorDto.setFullName("Author_" + i);
            authorDtoList.add(authorDto);
        }

        return authorDtoList;
    }

    private List<GenreDto> createGenreList() {
        List<GenreDto> genreDtoList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            GenreDto genreDto = new GenreDto();
            genreDto.setId(i);
            genreDto.setName("Genre_" + i);
            genreDtoList.add(genreDto);
        }

        return genreDtoList;
    }
}
