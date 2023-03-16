package com.emovie.dao;

import com.emovie.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {

    @Select("select * from tb_user where telephone=#{telephone} and password=#{password}")
    public User getUserByTelephoneAndPassword(String telephone, String password);

    @Update("update tb_user set username=#{username},telephone=#{telephone},password=#{password}")
    void updateUser(User user);

}
