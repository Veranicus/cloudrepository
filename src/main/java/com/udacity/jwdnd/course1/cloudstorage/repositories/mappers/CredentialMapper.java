package com.udacity.jwdnd.course1.cloudstorage.repositories.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential selectCredentialById(Integer credentialId);

    @Select("SELECT CREDENTIALS.key FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    String selectCredentialKeyById(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> selectAllCredentials();

    @Insert("INSERT INTO CREDENTIALS (url,username,key,password,userId) VALUES (#{url}," +
            "#{username},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, password=#{password} " +
            "WHERE credentialId = #{credentialId}")
    int updateCredential(@Param("url") String url, @Param("username") String username, @Param("password") String password,
                         @Param("credentialId") Integer credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredential(Integer credentialId);

}
