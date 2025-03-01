package com.example.maxbitdigitalasset.model.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class V1GetMemberResponse implements Serializable {
    private String id;
    private String name;
    private String email;
}
