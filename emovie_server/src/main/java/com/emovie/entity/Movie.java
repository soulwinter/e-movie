package com.emovie.entity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.annotations.MapKey;

/**
 * @author  huangzelin 
 * @create 2023-04-07 19:54 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

	/**
	 * table name:id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double id;

	/**
	 * table name:adult
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double adult;

	/**
	 * table name:budget
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double budget;

	/**
	 * table name:homepage
	 * table type:varchar(200)
	 * table comment:null
	 */
	private String homepage;

	/**
	 * table name:imgb_id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private String imgbId;

	/**
	 * table name:original_language
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String originalLanguage;

	/**
	 * table name:original_title
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String originalTitle;

	/**
	 * table name:overview
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String overview;

	/**
	 * table name:popularity
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Integer popularity;

	/**
	 * table name:poster_path
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String posterPath;

	/**
	 * table name:release_date
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String releaseDate;

	/**
	 * table name:revenue
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double revenue;

	/**
	 * table name:runtime
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double runtime;

	/**
	 * table name:status
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double status;

	/**
	 * table name:tagline
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String tagline;

	/**
	 * table name:title
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String title;

	/**
	 * table name:vote_average
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Integer voteAverage;

	/**
	 * table name:vote_count
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double voteCount;

	/**
	 * table name:allCrew
	 * table type:varchar(500)
	 * table comment:null
	 */
	private String allCrew;

	/**
	 * table name:deleted
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double deleted;

}
