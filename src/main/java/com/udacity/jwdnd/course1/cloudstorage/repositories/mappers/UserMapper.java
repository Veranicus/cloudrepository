package com.udacity.jwdnd.course1.cloudstorage.repositories.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USERS WHERE username = #{username}")
    User getUserByUsername(String username);

    @Insert("INSERT INTO USERS (username,salt,password,firstName,lastName) VALUES (#{username}," +
            "#{salt},#{password},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true,keyProperty = "userId")
    int insertUser(User user);
}
