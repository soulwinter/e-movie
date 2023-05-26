package com.emovie.dto;

import com.emovie.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDocument {
    Double id;
    Boolean adult;
    String title;
    Integer popularity;
    String releaseDate;
    Integer voteAverage;
    List<String> genreList;
}
