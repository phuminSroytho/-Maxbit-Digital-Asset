package com.example.maxbitdigitalasset.model.request;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class V1PatchBookRequest implements Serializable {
    @NotNull
    private String title;
    private AuthorEntity author;
}
