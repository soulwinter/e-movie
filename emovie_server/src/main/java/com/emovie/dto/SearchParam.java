package com.emovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
