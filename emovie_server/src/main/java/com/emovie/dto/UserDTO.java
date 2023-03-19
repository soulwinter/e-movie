package com.emovie.dto;

import lombok.Data;

/**
 * 这个类是User类的一个缩小版 排除了一些敏感信息
 * 用于反馈给前端
 */
@Data
public class UserDTO {
    private Long id;
    private String username;
    private String telephone;
}
