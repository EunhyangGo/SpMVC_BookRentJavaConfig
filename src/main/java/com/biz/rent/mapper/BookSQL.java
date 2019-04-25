package com.biz.rent.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BookSQL {

	public String book_insert_sql() {
		
		SQL sql = new SQL()
		.INSERT_INTO("tbl_book")
		.INTO_COLUMNS("BOOK_SEQ").INTO_VALUES("SEQ_BOOK.NEXTVAL")
		.INTO_COLUMNS("BOOK_ISBN").INTO_VALUES("#{book_isbn,jdbcType=VARCHAR}")
		.INTO_COLUMNS("BOOK_TITLE").INTO_VALUES("#{book_title,jdbcType=VARCHAR}")
		.INTO_COLUMNS("BOOK_AUTHOR").INTO_VALUES("#{book_author,jdbcType=VARCHAR}")
		.INTO_COLUMNS("BOOK_PRICE").INTO_VALUES("#{book_price,jdbcType=INTEGER}")
		.INTO_COLUMNS("BOOK_DESCRIPTION").INTO_VALUES("#{book_description,jdbcType=VARCHAR}")
		.INTO_COLUMNS("BOOK_IMAGE").INTO_VALUES("#{book_image,jdbcType=VARCHAR}")
		.INTO_COLUMNS("BOOK_LINK").INTO_VALUES("#{book_link,jdbcType=VARCHAR}")
		.INTO_COLUMNS("BOOK_RENT_YN").INTO_VALUES("#{book_rent_yn,jdbcType=VARCHAR}");

		return sql.toString();
		
	}
	
	public String book_update_sql() {
		
		SQL sql = new SQL()
		.UPDATE("tbl_book")
		.SET("BOOK_ISBN = #{book_isbn,jdbcType=VARCHAR}")
		.SET("BOOK_TITLE = #{book_title,jdbcType=VARCHAR}")
		.SET("BOOK_AUTHOR = #{book_author,jdbcType=VARCHAR}")
		.SET("BOOK_PRICE = #{book_price,jdbcType=INTEGER}")
		.SET("BOOK_DESCRIPTION = #{book_description,jdbcType=VARCHAR}")
		.SET("BOOK_IMAGE = #{book_image,jdbcType=VARCHAR}")
		.SET("BOOK_LINK = #{book_link,jdbcType=VARCHAR}")
		.SET("BOOK_RENT_YN = #{book_rent_yn,jdbcType=VARCHAR}")
		.WHERE("BOOK_SEQ = #{book_seq}");
		return sql.toString();
		
	}
	

}
