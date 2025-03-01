package com.example.maxbitdigitalasset.controller;

import com.example.maxbitdigitalasset.model.request.V1PatchAuthorRequest;
import com.example.maxbitdigitalasset.model.request.V1PostAuthorRequest;
import com.example.maxbitdigitalasset.model.response.V1GetAuthorResponse;
import com.example.maxbitdigitalasset.service.AuthorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorControllerTest {

    @InjectMocks
    AuthorController authorController;

    @Mock
    AuthorService authorService;
    @Captor
    ArgumentCaptor<V1PostAuthorRequest> captorParamsV1PostAuthorRequest;
    @Captor
    ArgumentCaptor<V1PatchAuthorRequest> captorParamsV1PatchAuthorMemberRequest;

    private final String name = "name";
    private final String id = "id";
    private final V1GetAuthorResponse mockV1GetAuthorResponse = new V1GetAuthorResponse()
            .setName(name)
            .setId(id);

    @BeforeEach
    void setup() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ReflectionTestUtils.setField(authorController, "httpHeaders", httpHeaders);
    }

    @Test
    void test_getAll_success() {
        var expected = List.of(mockV1GetAuthorResponse);

        doReturn(expected)
                .when(authorService)
                .getAllAuthors();

        var actual = authorController.getAll();
        assertEquals(expected, actual.getBody());
    }

    @Test
    void test_getById_success() {
        var expected = mockV1GetAuthorResponse;

        doReturn(expected)
                .when(authorService)
                .getAuthorById(id);

        var actual = authorController.getById(id);
        assertEquals(expected, actual.getBody());
    }

    @Test
    void test_post_success() {
        var request = new V1PostAuthorRequest()
                .setId(id)
                .setName(name);

        doNothing()
                .when(authorService)
                .insertAuthor(captorParamsV1PostAuthorRequest.capture());

        assertDoesNotThrow(() -> authorController.post(request));
        assertEquals(request, captorParamsV1PostAuthorRequest.getValue());
        verify(authorService, times(1))
                .insertAuthor(request);
    }

    @Test
    void test_patchById_success() {
        var request = new V1PatchAuthorRequest()
                .setName(name);

        doNothing()
                .when(authorService)
                .updateAuthor(eq(id), captorParamsV1PatchAuthorMemberRequest.capture());

        assertDoesNotThrow(() -> authorController.patchById(id, request));
        assertEquals(request, captorParamsV1PatchAuthorMemberRequest.getValue());
        verify(authorService, times(1))
                .updateAuthor(id, request);
    }

    @Test
    void test_deleteById_success() {
        doNothing()
                .when(authorService)
                .deleteAuthor(id);

        assertDoesNotThrow(() -> authorController.deleteById(id));
        verify(authorService, times(1))
                .deleteAuthor(id);
    }
}