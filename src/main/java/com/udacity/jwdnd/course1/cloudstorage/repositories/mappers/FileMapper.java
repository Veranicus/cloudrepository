package com.udacity.jwdnd.course1.cloudstorage.repositories.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT FROM FILES WHERE fileId=#{fileId}")
    File getFileById(Integer fileId);

    @Select("SELECT * FROM FILES")
    List<File> selectAllFiles();

    @Insert("INSERT INTO FILES (filename,contentType,fileSize,userId,fileData) VALUES" +
            " (#{filename},#{contentType},#{fileSize},#{userId},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(File file);

}
