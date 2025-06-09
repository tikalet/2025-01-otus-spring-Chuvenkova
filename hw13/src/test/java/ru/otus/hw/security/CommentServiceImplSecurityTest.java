package ru.otus.hw.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.services.AclServiceWrapperServiceImpl;
import ru.otus.hw.services.CommentServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Безопасность. Сервис комментариев ")
@DataJpaTest
@Import({CommentServiceImpl.class, CommentMapper.class,
        AclServiceWrapperServiceImpl.class, AclConfig.class})
public class CommentServiceImplSecurityTest {

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private AclServiceWrapperServiceImpl aclServiceWrapperService;

    @DisplayName("для user и книги [1] отдаст список комментариев")
    @WithMockUser(username = "user", authorities = {})
    @Test()
    void shouldSelectCommentByBookTest1() {
        assertThat(commentService.findByBookId(1)).isNotNull().isNotEmpty().hasSize(3);
    }

    @DisplayName("для user и книги [2] отдаст пустой список комментариев")
    @WithMockUser(username = "user", authorities = {})
    @Test()
    void shouldSelectCommentByBookTest2() {
        assertThat(commentService.findByBookId(2)).isNotNull().isEmpty();
    }

    @DisplayName("для lib и книги [1] отдаст список комментариев")
    @WithMockUser(username = "lib", authorities = {"BOOK_EDITOR", "COMMENT_EDITOR"})
    @Test()
    void shouldSelectCommentByBookTest3() {
        assertThat(commentService.findByBookId(1)).isNotNull().isNotEmpty().hasSize(3);
    }

    @DisplayName("для lib и книги [2] отдаст список комментариев")
    @WithMockUser(username = "lib", authorities = {"BOOK_EDITOR", "COMMENT_EDITOR"})
    @Test()
    void shouldSelectCommentByBookTest4() {
        assertThat(commentService.findByBookId(2)).isNotNull().isNotEmpty().hasSize(2);
    }

}
