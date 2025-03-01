package com.example.maxbitdigitalasset.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class V1GetAuthorResponse implements Serializable {
    private String id;
    private String name;
}
