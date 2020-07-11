package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FileMapper fileMapper;

    private UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public int saveFile(FileModel fileModel, Authentication authentication) {
        File file = null;
        try {
             file = new File(null, fileModel.getFilename(), fileModel.getContentType(), fileModel.getFileSize(),
                    userMapper.getUserByUsername(authentication.getName()).getUserId(),
                   fileModel.getFileData());
            logger.info("Inserting new file " + fileModel.getFilename());
        }catch (Exception j){
            j.printStackTrace();
        }
        return fileMapper.insertFile(file);
    }
    public List<File> getAllFiles(){
        return fileMapper.selectAllFiles();
    }

//    private static byte[] convertFileContentToBlob(byte[] fileContent) throws IOException {
//        byte[] blob = null;
//        try {
//
//             blob = new javax.sql.rowset.serial.SerialBlob(fileContent);
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//        return blob;
//    }
}