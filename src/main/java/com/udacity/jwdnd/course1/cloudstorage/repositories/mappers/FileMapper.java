package com.udacity.jwdnd.course1.cloudstorage.repositories.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface FileMapper {

    @Select("SELECT FROM FILES WHERE fileId=#{fileId}")
    File selectFileById(Integer fileId);

    @Select("SELECT * FROM FILES WHERE filename=#{filename}")
    File selectFileByName(String fileName);

    @Select("SELECT * FROM FILES")
    List<File> selectAllFiles();

    @Insert("INSERT INTO FILES (filename,contentType,fileSize,userId,fileData) VALUES" +
            " (#{filename},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);

}
