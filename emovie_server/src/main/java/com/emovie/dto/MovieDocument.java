package com.emovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

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
}
