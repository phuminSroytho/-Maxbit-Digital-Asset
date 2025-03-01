package com.example.maxbitdigitalasset.controller;

import com.example.maxbitdigitalasset.controller.template.CrudController;
import com.example.maxbitdigitalasset.model.request.V1PatchAuthorRequest;
import com.example.maxbitdigitalasset.model.request.V1PostAuthorRequest;
import com.example.maxbitdigitalasset.model.response.V1GetAuthorResponse;
import com.example.maxbitdigitalasset.service.AuthorService;
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

@RestController
@RequestMapping("/v1/author")
@Validated
public class AuthorController implements CrudController<V1GetAuthorResponse, V1PostAuthorRequest, V1PatchAuthorRequest> {

    @Autowired
    private AuthorService authorService;
    @Autowired
    private HttpHeaders httpHeaders;

    @Override
    @GetMapping("/all")
    public ResponseEntity<List<V1GetAuthorResponse>> getAll() {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        return new ResponseEntity<>(
                authorService.getAllAuthors(),
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @GetMapping
    public ResponseEntity<V1GetAuthorResponse> getById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        return new ResponseEntity<>(
                authorService.getAuthorById(id),
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @PostMapping
    public ResponseEntity<Void> post(
            @RequestBody @Valid V1PostAuthorRequest request
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        authorService.insertAuthor(request);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }

    @Override
    @PatchMapping
    public ResponseEntity<Void> patchById(
            @RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id,
            @RequestBody @Valid V1PatchAuthorRequest request
    ) {
        HttpHeaders newHttpHerder = new HttpHeaders();
        newHttpHerder.addAll(httpHeaders);

        authorService.updateAuthor(id, request);

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

        authorService.deleteAuthor(id);

        return new ResponseEntity<>(
                newHttpHerder,
                HttpStatus.OK
        );
    }
}
