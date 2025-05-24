package ru.otus.hw.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.otus.hw.controller.BookController;
import ru.otus.hw.dto.BookSaveDto;
import ru.otus.hw.mapper.BookMapper;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Безопасность. Контроллер для книг")
@WebMvcTest(BookController.class)
@Import({SecurityConfiguration.class})
public class BookControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private AuthorService authorService;

    @MockitoBean
    private GenreService genreService;

    @MockitoBean
    private BookMapper bookMapper;

    @DisplayName("должен проверить корректность возвращаемого статуса")
    @ParameterizedTest(name = "{4} для метода {0} {1} c пользователем {2}")
    @MethodSource("createTestData")
    void shouldVerifyCorrectnessUrlAndUserAndReturnedStatus(String httpMethod, String url,
                                                            String user, List<SimpleGrantedAuthority> authorityList,
                                                            int status) throws Exception {
        when(bookMapper.toSaveDto(any())).thenReturn(new BookSaveDto(1, "title", 1, 1));

        var request = createRequest(httpMethod, url);
        assert request != null;

        if (user != null) {
            request = request.with(user(user).authorities(authorityList));
        }

        ResultActions resultActions = mvc.perform(request).andExpect(status().is(status));

        if (Objects.isNull(user)) {
            resultActions.andExpect(redirectedUrlPattern("**/login"));
        }
    }

    private MockHttpServletRequestBuilder createRequest(String httpMethod, String url) {
        if (httpMethod.equals("get")) {
            return MockMvcRequestBuilders.get(url);
        } else if (httpMethod.equals("post")) {
            return MockMvcRequestBuilders.post(url);
        }

        return null;
    }


    public static Stream<Arguments> createTestData() {
        var libUser = "lib";
        var libAuthorityList = List.of(new SimpleGrantedAuthority("BOOK_EDITOR"));

        var userUser = "user";
        var userAuthorityList = List.of(new SimpleGrantedAuthority("COMMENT_EDITOR"));

        return Stream.of(
                Arguments.of("get", "/", null, null, 302),
                Arguments.of("get", "/", libUser, libAuthorityList, 200),
                Arguments.of("get", "/", userUser, userAuthorityList, 200),
                Arguments.of("get", "/book/1", null, null, 302),
                Arguments.of("get", "/book/1", libUser, libAuthorityList, 200),
                Arguments.of("get", "/book/1", userUser, userAuthorityList, 403),
                Arguments.of("get", "/book", null, null, 302),
                Arguments.of("get", "/book", libUser, libAuthorityList, 200),
                Arguments.of("get", "/book", userUser, userAuthorityList, 403),
                Arguments.of("post", "/book", null, null, 302),
                Arguments.of("post", "/book", libUser, libAuthorityList, 200),
                Arguments.of("post", "/book", userUser, userAuthorityList, 403),
                Arguments.of("post", "/book/1/delete", null, null, 302),
                Arguments.of("post", "/book/1/delete", libUser, libAuthorityList, 302),
                Arguments.of("post", "/book/1/delete", userUser, userAuthorityList, 403)
        );
    }
}
