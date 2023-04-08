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
public class Genre {

	/**
	 * table name:id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double id;

	/**
	 * table name:name
	 * table type:varchar(200)
	 * table comment:null
	 */
	private String name;

	/**
	 * table name:deleted
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double deleted;

}
