package com.emovie.entity;

import lombok.Data;

@Data
public class User {
    private Long id;

    private String username;

    private String password;

    private String telephone;

    private String deleted;

    private Integer type;

}
