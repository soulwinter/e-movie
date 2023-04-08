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
public class MovieKeyword {

	/**
	 * table name:id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double id;

	/**
	 * table name:Key_id
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double keyId;

	/**
	 * table name:deleted
	 * table type:decimal(8)
	 * table comment:null
	 */
	private Double deleted;

}
