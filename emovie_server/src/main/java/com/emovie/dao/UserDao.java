package com.emovie.dao;

import com.emovie.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Bean;

@Mapper
public interface UserDao {


    @Select("select * from tb_user where telephone=#{telephone}")
    public User getUserByTelephone(String telephone);

    @Select("select * from tb_user where telephone=#{telephone} and password=#{password}")
    public User getUserByTelephoneAndPassword(String telephone, String password);

    @Update("update tb_user set username=#{username},telephone=#{telephone},password=#{password},token=#{token}")
    void updateUser(User user);

    
}
