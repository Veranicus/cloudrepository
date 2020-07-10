package com.udacity.jwdnd.course1.cloudstorage.repositories.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Select("SELECT FROM CREDENTIALS WHERE credentialId=#{credentialId}")
    Credential selectCredentialById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> selectAllCredentials();

    @Insert("INSERT INTO CREDENTIALS (url,username,password,userId) VALUES (#{url}," +
            "#{username},#{password},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "credentialId")
    int insertCredential(Credential credential);

}
