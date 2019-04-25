package com.biz.rent.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.rent.model.UserVO;

public interface UserDao {

	@Select("SELECT * FROM tbl_user")
	public List<UserVO> selectAll();
	
	@Select("SELECT * FROM tbl_user WHERE user_seq = #{user_seq}")
	public UserVO findById(long user_seq);
	
	@InsertProvider(type=UserSQL.class,method="user_insert_sql")
	public int insert(UserVO userVO);
	
	@UpdateProvider(type=UserSQL.class,method="user_update_sql")
	public int update(UserVO userVO);
	
	//아이디를 아예 seq으로 바꾸어서 쿼리에게 전달해달라고 하는말.
	@Delete("DELETE FROM tbl_user WHERE user_seq =#{user_seq}")
	public void delete(@Param("user_seq")long id);

	// 이름이나 전화번호가 일치하는 항목들이 있으면 그 데이터를 보여주기 위한 코드
	@Select("SELECT * FROM tbl_user "
			 + "WHERE user_name like '%' || #{s_string} || '%' "
			 + " OR user_phone like  '%' || #{s_string} || '%' ")
	public List<UserVO> getSearchList(@Param("s_string") String s_string); // mybatis에서 이름을 넘길때 명시적으로 넘겨라...
	
	
	
}
