package com.example.maxbitdigitalasset.model.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class V1PostMemberRequest implements Serializable {
    @Pattern(regexp = "^\\d*$",message = "Invalid id format")
    private String id;
    @NotNull
    private String name;
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @NotNull
    private String email;
}
