package ru.otus.hw.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.services.CommentService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@DisplayName("Контроллер для комментарив")
@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CommentService commentService;

    @DisplayName("должен отдать страницу с комментариями для книги с моделью")
    @Test
    public void shouldRenderCommentForBookPageWithModel() throws Exception {
        List<CommentDto> commentDtoList = createCommentDtoList();

        when(commentService.findByBookId(1)).thenReturn(commentDtoList);

        mvc.perform(get("/comments").param("bookId", "1"))
                .andExpect(view().name("comments"))
                .andExpect(model().attribute("comments", commentDtoList))
                .andExpect(model().attribute("bookId", 1L));
    }

    @DisplayName("должен отдать страницу для редактирования комментария с моделью")
    @Test
    public void shouldRenderEditCommentPageWithModel() throws Exception {
        List<CommentDto> commentDtoList = createCommentDtoList();
        CommentDto commentDto = commentDtoList.get(0);

        when(commentService.findById(1)).thenReturn(commentDto);

        mvc.perform(get("/comment").param("id", "1"))
                .andExpect(view().name("comment_edit"))
                .andExpect(model().attribute("comment", commentDto));
    }

    @DisplayName("должен обновить комментарий и перенаправить на страницу с комментариями для книги")
    @Test
    public void shouldSaveEditCommentAndRedirectCommentForBookPage() throws Exception {
        List<CommentDto> commentDtoList = createCommentDtoList();
        CommentDto commentDto = commentDtoList.get(0);
        commentDto.setCommentText("updated_comment_text");

        when(commentService.update(commentDto.getId(), commentDto.getCommentText())).thenReturn(commentDto);

        mvc.perform(post("/comment_edit")
                        .param("id", "1")
                        .param("commentText", "updated_comment_text"))
                .andExpect(view().name("redirect:/comments?bookId=1"));
    }

    @DisplayName("должен удалить комментарий и перенаправить на страницу с комментариями для книги")
    @Test
    public void shouldDeleteCommentAndRedirectCommentForBookPage() throws Exception {
        List<CommentDto> commentDtoList = createCommentDtoList();
        CommentDto commentDto = commentDtoList.get(0);

        when(commentService.findById(1)).thenReturn(commentDto);

        mvc.perform(post("/comment_delete")
                        .param("id", "1"))
                .andExpect(view().name("redirect:/comments?bookId=1"));

        verify(commentService, times(1)).deleteById(1);
    }

    @DisplayName("должен отдать страницу для создания комментария с моделью")
    @Test
    public void shouldRenderNewCommentPageWithModel() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setBook(new BookDto());
        commentDto.getBook().setId(1L);

        mvc.perform(get("/comment_new").param("bookId", "1"))
                .andExpect(view().name("comment_new"))
                .andExpect(model().attribute("comment", commentDto))
                .andExpect(model().attribute("bookId", 1L));
    }

    @DisplayName("должен сохранить комментарий и перенаправить на страницу с комментариями для книги")
    @Test
    public void shouldSaveNewCommentAndRedirectCommentForBookPage() throws Exception {
        mvc.perform(post("/comment_save")
                        .param("book.id", "1")
                        .param("commentText", "new_comment"))
                .andExpect(view().name("redirect:/comments?bookId=1"));

        verify(commentService, times(1)).insert(1L, "new_comment");
    }

    private List<CommentDto> createCommentDtoList() {
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (int i = 1; i < 5; i++) {
            CommentDto commentDto = new CommentDto();
            commentDto.setCommentText("comment_text_" + i);
            commentDto.setId(i);
            commentDto.setBook(new BookDto());
            commentDto.getBook().setId(1);
            commentDtoList.add(commentDto);
        }

        return commentDtoList;
    }
}
