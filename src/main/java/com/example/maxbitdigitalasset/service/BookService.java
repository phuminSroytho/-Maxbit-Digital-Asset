package com.example.maxbitdigitalasset.service;

import com.example.maxbitdigitalasset.model.entity.BookEntity;
import com.example.maxbitdigitalasset.model.request.V1PatchBookRequest;
import com.example.maxbitdigitalasset.model.request.V1PostBookRequest;
import com.example.maxbitdigitalasset.model.response.V1GetBookResponse;
import com.example.maxbitdigitalasset.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.example.maxbitdigitalasset.util.JsonUtil.objectToJson;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<V1GetBookResponse> getAllBooks() {
        List<V1GetBookResponse> response = new ArrayList<>();
        List<BookEntity> books = bookRepository.findAll();

        for (BookEntity book : books) {
            response.add(new V1GetBookResponse()
                    .setId(String.valueOf(book.getId()))
                    .setTitle(book.getTitle())
                    .setAuthor(book.getAuthor()));
        }
        log.debug("response = :{}", objectToJson(response));

        return response;
    }

    public V1GetBookResponse getBookById(String id) {
        V1GetBookResponse response;
        Optional<BookEntity> book = bookRepository.findById(id);

        response = book.map(bookEntity -> new V1GetBookResponse()
                        .setId(String.valueOf(bookEntity.getId()))
                        .setAuthor(bookEntity.getAuthor())
                        .setTitle(bookEntity.getTitle()))
                .orElse(new V1GetBookResponse());
        log.debug("response = :{}", objectToJson(response));

        return response;
    }

    public void insertBook(V1PostBookRequest request) {
        bookRepository.save(new BookEntity(Integer.parseInt(request.getId()), request.getTitle(), request.getAuthor(), new HashSet<>()));
    }

    public void updateBook(String id, V1PatchBookRequest request) {
        bookRepository.findById(id)
                .map(bookEntity -> {
                    bookEntity.setTitle(request.getTitle())
                            .setAuthor(request.getAuthor());
                    return bookRepository.save(bookEntity);
                })
                .orElseThrow(() -> new RuntimeException(String.format("Can not found id: %s in bookRepository", id)));
    }

    public void deleteBook(String id) {
        bookRepository.deleteById(id);
    }
}
