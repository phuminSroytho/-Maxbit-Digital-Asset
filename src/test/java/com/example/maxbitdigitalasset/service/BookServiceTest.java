package com.example.maxbitdigitalasset.service;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import com.example.maxbitdigitalasset.model.entity.BookEntity;
import com.example.maxbitdigitalasset.model.request.V1PatchBookRequest;
import com.example.maxbitdigitalasset.model.request.V1PostBookRequest;
import com.example.maxbitdigitalasset.model.response.V1GetBookResponse;
import com.example.maxbitdigitalasset.repository.BookRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @InjectMocks
    BookService bookService;

    @Mock
    BookRepository bookRepository;

    private final String id = "1";
    private final String title = "title";
    private final String name = "name";
    private final AuthorEntity mockAuthorEntity = new AuthorEntity(Integer.parseInt(id), name, new ArrayList<>());
    private final BookEntity mockBookEntity = new BookEntity(Integer.parseInt(id), title, mockAuthorEntity, new HashSet<>());

    @Nested
    class Test_getBook {
        private final V1GetBookResponse mockV1GetBookResponse = new V1GetBookResponse()
                .setId(id)
                .setTitle(title)
                .setAuthor(mockAuthorEntity);

        @Test
        void test_getAllBooks_success() {
            var expected = List.of(mockV1GetBookResponse);

            doReturn(List.of(mockBookEntity))
                    .when(bookRepository)
                    .findAll();

            var actual = bookService.getAllBooks();
            assertEquals(expected, actual);
        }

        @Test
        void test_getBookById_success() {
            doReturn(Optional.of(mockBookEntity))
                    .when(bookRepository)
                    .findById(id);

            var actual = bookService.getBookById(id);
            assertEquals(mockV1GetBookResponse, actual);
        }

        @Test
        void test_getBookById_notFound() {
            var expected = new V1GetBookResponse();

            doReturn(Optional.empty())
                    .when(bookRepository)
                    .findById(id);

            var actual = bookService.getBookById(id);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class Test_insertBook {
        private final V1PostBookRequest mockV1PostBookRequest = new V1PostBookRequest()
                .setId(id)
                .setTitle(title)
                .setAuthor(mockAuthorEntity);

        @Test
        void test_insertBook_success() {
            var expected = mockBookEntity;
            doReturn(expected)
                    .when(bookRepository)
                    .save(expected);

            assertDoesNotThrow(() -> bookService.insertBook(mockV1PostBookRequest));
            verify(bookRepository, times(1))
                    .save(expected);
        }
    }

    @Nested
    class Test_updateBook {
        private final V1PatchBookRequest mockV1PatchBookRequest = new V1PatchBookRequest()
                .setTitle(title);

        @Test
        void test_updateBook_success() {
            var expected = mockBookEntity;
            doReturn(Optional.of(expected))
                    .when(bookRepository)
                    .findById(id);

            doReturn(expected)
                    .when(bookRepository)
                    .save(expected);

            assertDoesNotThrow(() -> bookService.updateBook(id, mockV1PatchBookRequest));
        }

        @Test
        void test_updateBook_failed() {
            doReturn(Optional.empty())
                    .when(bookRepository)
                    .findById(id);

            assertThrows(RuntimeException.class, () -> bookService.updateBook(id, mockV1PatchBookRequest));
        }
    }

    @Nested
    class Test_deleteBook {
        @Test
        void test_deleteBook_success() {
            doNothing()
                    .when(bookRepository)
                    .deleteById(id);

            assertDoesNotThrow(() -> bookService.deleteBook(id));
        }
    }
}