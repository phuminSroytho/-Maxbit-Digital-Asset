package com.example.maxbitdigitalasset.model.response;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class V1GetBookResponse implements Serializable {
    private String id;
    private String title;
    private AuthorEntity author;
}
