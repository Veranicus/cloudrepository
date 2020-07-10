package com.udacity.jwdnd.course1.cloudstorage.services.security;

import com.udacity.jwdnd.course1.cloudstorage.entities.User;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;

    private final HashService hashService;

    @Autowired
    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public int createUser(User user){

        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(),encodedSalt);
        return userMapper.insertUser(new User(null,user.getUsername(),
                encodedSalt,hashedPassword, user.getFirstName(),user.getLastName()));

    }

    public User getUser(String username){
        return userMapper.getUserByUsername(username);
    }

    public boolean isUsernameAvailable(String username){
        return userMapper.getUserByUsername(username) == null;
    }
}
