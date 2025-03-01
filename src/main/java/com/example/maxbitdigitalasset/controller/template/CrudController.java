package com.example.maxbitdigitalasset.controller.template;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CrudController<GetResponse, PostRequest, PatchRequest> {
    ResponseEntity<List<GetResponse>> getAllUser();

    ResponseEntity<GetResponse> getUserById(String id);

    ResponseEntity<Void> postUser(PostRequest request);

    ResponseEntity<Void> patchUserById(String id, PatchRequest request);

    ResponseEntity<Void> deleteUserById(String id);
}
