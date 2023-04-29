package com.example.backend.domain;

import java.io.Serial;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Article implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String title;
    private String authorName;
    private String description;
}
