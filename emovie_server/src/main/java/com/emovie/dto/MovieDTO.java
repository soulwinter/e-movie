package com.emovie.dto;
import com.emovie.entity.Movie;
import lombok.Data;
import com.emovie.entity.Vote;
import java.util.List;

/**
 * 这个类用于包装后台查询某一电影详情的DTO
 * 用于反馈给前端
 */
@Data
public class MovieDTO{
    private Long id;
    private Movie basic;
    private List<String> genre;
    private List<String> company;
    private List<String> country;
    private List<Vote> vote;
    private List<String> keyword;


}
