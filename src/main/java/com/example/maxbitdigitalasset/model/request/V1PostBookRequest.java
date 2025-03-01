package com.example.maxbitdigitalasset.model.request;

import com.example.maxbitdigitalasset.model.entity.AuthorEntity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class V1PostBookRequest implements Serializable {
    @NotNull
    @Pattern(regexp = "^\\d*$",message = "Invalid id format")
    private String id;
    @NotNull
    private String title;
    private AuthorEntity author;
}
