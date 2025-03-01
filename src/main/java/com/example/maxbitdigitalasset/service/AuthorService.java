package com.example.maxbitdigitalasset.service;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import com.example.maxbitdigitalasset.model.request.V1PatchAuthorRequest;
import com.example.maxbitdigitalasset.model.request.V1PostAuthorRequest;
import com.example.maxbitdigitalasset.model.response.V1GetAuthorResponse;
import com.example.maxbitdigitalasset.repository.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.maxbitdigitalasset.util.JsonUtil.objectToJson;

@Service
@Slf4j
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public List<V1GetAuthorResponse> getAllAuthors() {
        List<V1GetAuthorResponse> response = new ArrayList<>();
        List<AuthorEntity> authors = authorRepository.findAll();

        for (AuthorEntity author : authors) {
            response.add(new V1GetAuthorResponse(String.valueOf(author.getId()), author.getName()));
        }
        log.info("response = : {}", objectToJson(response));

        return response;
    }

    public V1GetAuthorResponse getAuthorById(String id) {
        Optional<AuthorEntity> author = authorRepository.findById(id);
        V1GetAuthorResponse response = author.map(authorEntity -> new V1GetAuthorResponse()
                        .setId(String.valueOf(authorEntity.getId()))
                        .setName(authorEntity.getName()))
                .orElse(new V1GetAuthorResponse());
        log.info("response = : {}", objectToJson(response));

        return response;
    }

    public void insertAuthor(V1PostAuthorRequest request) {
        authorRepository.save(new AuthorEntity(Integer.parseInt(request.getId()), request.getName(), new ArrayList<>()));
    }

    public void updateAuthor(String id, V1PatchAuthorRequest request) {
        authorRepository.findById(id)
                .map(authorEntity -> {
                    authorEntity.setName(request.getName());
                    return authorRepository.save(authorEntity);
                })
                .orElseThrow(() -> new RuntimeException(String.format("Can not found id: %s in authorRepository", id)));
    }

    public void deleteAuthor(String id) {
        authorRepository.deleteById(id);
    }
}
