package com.emovie.dao;

import com.emovie.entity.Genre;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface GenreDao {

    @Select("select name from genre")
    public List<String> getAllName();
}
