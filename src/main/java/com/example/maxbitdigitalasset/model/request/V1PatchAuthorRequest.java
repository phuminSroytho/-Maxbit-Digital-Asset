package com.example.maxbitdigitalasset.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class V1PatchAuthorRequest implements Serializable {
    @NotNull
    private String name;
}
