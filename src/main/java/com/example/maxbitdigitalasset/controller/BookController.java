package com.example.maxbitdigitalasset.controller;

import com.example.maxbitdigitalasset.controller.template.CrudController;
import com.example.maxbitdigitalasset.model.request.V1PatchBookRequest;
import com.example.maxbitdigitalasset.model.request.V1PostBookRequest;
import com.example.maxbitdigitalasset.model.response.V1GetBookResponse;
import com.example.maxbitdigitalasset.service.BookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestControllerAdvice
@RequestMapping(name = "/v1/book")
@Validated
public class BookController implements CrudController<V1GetBookResponse, V1PostBookRequest, V1PatchBookRequest> {
    @Autowired
    private BookService bookService;
    @Autowired
    private HttpHeaders httpHeaders;


    @Override
    @GetMapping("/all")
    public ResponseEntity<List<V1GetBookResponse>> getAll() {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        return new ResponseEntity<>(
                bookService.getAllBooks(),
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<V1GetBookResponse> getById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        return new ResponseEntity<>(
                bookService.getBookById(id),
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> post(
            @RequestBody @Valid V1PostBookRequest request
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        bookService.insertBook(request);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @PatchMapping
    public ResponseEntity<Void> patchById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id,
            @RequestBody @Valid V1PatchBookRequest request
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        bookService.updateBook(id, request);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    public ResponseEntity<Void> deleteById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        bookService.deleteBook(id);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }
}
