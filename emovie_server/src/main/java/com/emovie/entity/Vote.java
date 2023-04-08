package com.emovie.entity;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author  huangzelin 
 * @create 2023-04-07 20:36 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

	/**
	 * table name:user_id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double userId;

	/**
	 * table name:movie_id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double movieId;

	/**
	 * table name:rating
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double rating;

	/**
	 * table name:timestamp
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double timestamp;

	/**
	 * table name:deleted
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double deleted;

}
