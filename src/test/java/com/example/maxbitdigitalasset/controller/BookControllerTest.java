package com.example.maxbitdigitalasset.controller;

import com.example.maxbitdigitalasset.model.request.V1PatchBookRequest;
import com.example.maxbitdigitalasset.model.request.V1PostBookRequest;
import com.example.maxbitdigitalasset.model.response.V1GetBookResponse;
import com.example.maxbitdigitalasset.service.BookService;
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
class BookControllerTest {
    @InjectMocks
    BookController bookController;

    @Mock
    BookService bookService;

    @Captor
    ArgumentCaptor<V1PostBookRequest> captorParamsV1PostBookRequest;
    @Captor
    ArgumentCaptor<V1PatchBookRequest> captorParamsV1PatchBookRequest;

    private final String id = "id";
    private final String title = "title";
    private final V1GetBookResponse mockV1GetBookResponse = new V1GetBookResponse()
            .setId(id)
            .setTitle(title);

    @BeforeEach
    void setup() {
        HttpHeaders httpHeaders = new HttpHeaders();
        ReflectionTestUtils.setField(bookController, "httpHeaders", httpHeaders);
    }

    @Test
    void test_getAllUser_success() {
        var expected = List.of(mockV1GetBookResponse);

        doReturn(expected)
                .when(bookService)
                .getAllBooks();

        var actual = bookController.getAll();
        assertEquals(expected, actual.getBody());
    }

    @Test
    void test_getById_success() {
        var expected = mockV1GetBookResponse;

        doReturn(expected)
                .when(bookService)
                .getBookById(id);

        var actual = bookController.getById(id);
        assertEquals(expected, actual.getBody());
    }

    @Test
    void test_post_success() {
        var request = new V1PostBookRequest()
                .setId(id)
                .setTitle(title);

        doNothing()
                .when(bookService)
                .insertBook(captorParamsV1PostBookRequest.capture());

        assertDoesNotThrow(() -> bookController.post(request));
        assertEquals(request, captorParamsV1PostBookRequest.getValue());
        verify(bookService, times(1))
                .insertBook(request);
    }

    @Test
    void test_patchById_success() {
        var request = new V1PatchBookRequest()
                .setTitle(title);

        doNothing()
                .when(bookService)
                .updateBook(eq(id), captorParamsV1PatchBookRequest.capture());

        assertDoesNotThrow(() -> bookController.patchById(id, request));
        assertEquals(request, captorParamsV1PatchBookRequest.getValue());
        verify(bookService, times(1))
                .updateBook(id, request);
    }

    @Test
    void test_deleteById_success() {
        doNothing()
                .when(bookService)
                .deleteBook(id);

        assertDoesNotThrow(() -> bookController.deleteById(id));
        verify(bookService, times(1))
                .deleteBook(id);
    }
}