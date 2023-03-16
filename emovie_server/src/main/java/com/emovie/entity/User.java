package com.emovie.entity;

import lombok.Data;

@Data
public class User {
    Long id;

    String username;

    String password;

    String telephone;

    String deleted;

    String token;
}
