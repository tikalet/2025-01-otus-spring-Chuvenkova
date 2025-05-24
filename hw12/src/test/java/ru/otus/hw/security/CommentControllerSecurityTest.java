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
import ru.otus.hw.controller.CommentController;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.CommentDto;
import ru.otus.hw.dto.CommentSaveDto;
import ru.otus.hw.mapper.CommentMapper;
import ru.otus.hw.services.CommentService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Безопасность. Контроллер для комментариев")
@WebMvcTest(CommentController.class)
@Import({SecurityConfiguration.class})
public class CommentControllerSecurityTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private CommentService commentService;

    @MockitoBean
    private CommentMapper commentMapper;

    @DisplayName("должен проверить корректность возвращаемого статуса")
    @ParameterizedTest(name = "{4} для метода {0} {1} c пользователем {2}")
    @MethodSource("createTestData")
    void shouldVerifyCorrectnessUrlAndUserAndReturnedStatus(String httpMethod, String url,
                                                            String user, List<SimpleGrantedAuthority> authorityList,
                                                            int status) throws Exception {

        when(commentMapper.toSaveDto(any())).thenReturn(new CommentSaveDto(1, "comment", 1));
        when(commentService.findById(1)).thenReturn(new CommentDto(1, "comment", new BookDto()));

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
                Arguments.of("get", "/comment/book/1", null, null, 302),
                Arguments.of("get", "/comment/book/1", libUser, libAuthorityList, 200),
                Arguments.of("get", "/comment/book/1", userUser, userAuthorityList, 200),
                Arguments.of("get", "/comment/1", null, null, 302),
                Arguments.of("get", "/comment/1", libUser, libAuthorityList, 403),
                Arguments.of("get", "/comment/1", userUser, userAuthorityList, 200),
                Arguments.of("get", "/comment/book/1/create", null, null, 302),
                Arguments.of("get", "/comment/book/1/create", libUser, libAuthorityList, 403),
                Arguments.of("get", "/comment/book/1/create", userUser, userAuthorityList, 200),
                Arguments.of("post", "/comment", null, null, 302),
                Arguments.of("post", "/comment", libUser, libAuthorityList, 403),
                Arguments.of("post", "/comment", userUser, userAuthorityList, 200),
                Arguments.of("post", "/comment/1/delete", null, null, 302),
                Arguments.of("post", "/comment/1/delete", libUser, libAuthorityList, 403),
                Arguments.of("post", "/comment/1/delete", userUser, userAuthorityList, 302)
        );
    }
}
