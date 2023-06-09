package com.emovie.dao;

import com.emovie.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Bean;

@Mapper
public interface UserDao {


    @Select("select * from user where telephone=#{telephone}")
    public User getUserByTelephone(String telephone);

    @Select("select * from user where telephone=#{telephone} and password=#{password}")
    public User getUserByTelephoneAndPassword(String telephone, String password);

    @Update("update user set username=#{username},telephone=#{telephone},password=#{password}")
    void updateUser(User user);

    @Insert("insert into user (username,password,telephone) values (#{username},#{password},#{telephone})" )
    int register(User user);

    
}
