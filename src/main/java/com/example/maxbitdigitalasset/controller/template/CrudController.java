package com.example.maxbitdigitalasset.controller.template;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CrudController<GetResponse, PostRequest, PatchRequest> {
    ResponseEntity<List<GetResponse>> getAll();

    ResponseEntity<GetResponse> getById(@RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id);

    ResponseEntity<Void> post(@RequestBody @Valid PostRequest request);

    ResponseEntity<Void> patchById(@RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id, @RequestBody @Valid PatchRequest request);

    ResponseEntity<Void> deleteById(@RequestParam @NotNull @Pattern(regexp = "^\\d*$", message = "Invalid id format") String id);
}
