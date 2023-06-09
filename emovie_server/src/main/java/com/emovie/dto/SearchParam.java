package com.emovie.dto;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchParam {
//    Double id;
    String movieInfoString;
    Integer requestPage;
    Integer movieNumberPerPage;
    Boolean adult;
    String originalLanguage;
    String releaseDate;
    Integer voteAverageFrom;
    Integer voteAverageTo;
    List<String> genreList;

    public boolean isNull() {
        return movieInfoString==null
                &&adult==null
                &&originalLanguage==null
                &&releaseDate==null
                &&voteAverageFrom==null
                &&voteAverageTo==null
                &&genreList.isEmpty();
    }
}
