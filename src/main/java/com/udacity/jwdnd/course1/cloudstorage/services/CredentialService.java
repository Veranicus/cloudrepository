package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private UserMapper userMapper;

    private EncryptionService encryptionService;

    private Logger logger = LoggerFactory.getLogger(EncryptionService.class);

    public CredentialService(CredentialMapper credentialMapper, UserMapper userMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }

    public int saveCredential(CredentialModel credentialModel, Authentication authentication) {
        Credential credential = new Credential(null, credentialModel.getUrl(), credentialModel.getUsername(),
                "1234567891234567", encryptionService.encryptValue(credentialModel.getPassword(),
                "1234567891234567"),
                userMapper.getUserByUsername(authentication.getName()).getUserId());
        return credentialMapper.insertCredential(credential);
    }

    public int updateCredential(Credential credential, Authentication authentication) {
        return credentialMapper.updateCredential(credential.getUrl(), credential.getUsername(), credential.getPassword(),
                credential.getCredentialId());
    }

    public int deleteCredential(Credential credential) {
        return credentialMapper.deleteCredential(credential.getCredentialId());
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.selectAllCredentials();
    }

    public String getDecryptedCredentialPassword(Integer credentialId) {
        return encryptionService.decryptValue(getCredentialById(credentialId).getPassword(), "1234567891234567");
    }

    Credential getCredentialById(Integer credentialId) {
        return credentialMapper.selectCredentialById(credentialId);
    }

}
