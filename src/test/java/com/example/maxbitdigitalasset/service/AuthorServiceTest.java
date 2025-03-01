package com.example.maxbitdigitalasset.service;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import com.example.maxbitdigitalasset.model.request.V1PatchAuthorRequest;
import com.example.maxbitdigitalasset.model.request.V1PostAuthorRequest;
import com.example.maxbitdigitalasset.model.response.V1GetAuthorResponse;
import com.example.maxbitdigitalasset.repository.AuthorRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {
    @InjectMocks
    AuthorService authorService;

    @Mock
    AuthorRepository authorRepository;

    private final String id = "1";
    private final String name = "name";
    private final AuthorEntity mockAuthorEntity = new AuthorEntity(Integer.parseInt(id), name, new ArrayList<>());

    @Nested
    class Test_getAuthors {
        private final V1GetAuthorResponse mockV1GetAuthorResponse = new V1GetAuthorResponse()
                .setName(name)
                .setId(id);

        @Test
        void test_getAllAuthors_success() {
            var expected = List.of(mockV1GetAuthorResponse);

            doReturn(List.of(mockAuthorEntity))
                    .when(authorRepository)
                    .findAll();

            var actual = authorService.getAllAuthors();
            assertEquals(expected, actual);
        }

        @Test
        void test_getAuthorById_success() {
            doReturn(Optional.of(mockAuthorEntity))
                    .when(authorRepository)
                    .findById(id);

            var actual = authorService.getAuthorById(id);
            assertEquals(mockV1GetAuthorResponse, actual);
        }

        @Test
        void test_getAuthorById_notFound() {
            var expected = new V1GetAuthorResponse();

            doReturn(Optional.empty())
                    .when(authorRepository)
                    .findById(id);

            var actual = authorService.getAuthorById(id);
            assertEquals(expected, actual);
        }
    }

    @Nested
    class Test_insertAuthor {
        private final V1PostAuthorRequest mockV1PostAuthorRequest = new V1PostAuthorRequest()
                .setId(id)
                .setName(name);


        @Test
        void test_insertAuthor_success() {
            var expected = mockAuthorEntity;
            doReturn(expected)
                    .when(authorRepository)
                    .save(expected);

            assertDoesNotThrow(() -> authorService.insertAuthor(mockV1PostAuthorRequest));
            verify(authorRepository, times(1))
                    .save(expected);
        }
    }

    @Nested
    class Test_updateAuthor {
        private final V1PatchAuthorRequest mockV1PatchAuthorRequest = new V1PatchAuthorRequest()
                .setName(name);

        @Test
        void test_updateAuthor_success() {
            var expected = mockAuthorEntity;
            doReturn(Optional.of(expected))
                    .when(authorRepository)
                    .findById(id);

            doReturn(expected)
                    .when(authorRepository)
                    .save(expected);

            assertDoesNotThrow(() -> authorService.updateAuthor(id, mockV1PatchAuthorRequest));
        }

        @Test
        void test_updateAuthor_failed() {
            doReturn(Optional.empty())
                    .when(authorRepository)
                    .findById(id);

            assertThrows(RuntimeException.class, () -> authorService.updateAuthor(id, mockV1PatchAuthorRequest));
        }
    }

    @Nested
    class Test_deleteAuthor {
        @Test
        void test_deleteAuthor_success() {
            doNothing()
                    .when(authorRepository)
                    .deleteById(id);

            assertDoesNotThrow(() -> authorService.deleteAuthor(id));
        }
    }
}