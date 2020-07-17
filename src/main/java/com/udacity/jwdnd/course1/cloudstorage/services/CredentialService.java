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

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.math.BigInteger;
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
        String generatedSecretKey = generateSecretKey();
        System.out.println("GeneratedSecretKey is " + generatedSecretKey);
        Credential credential = new Credential(null, credentialModel.getUrl(), credentialModel.getUsername(),
                generatedSecretKey, encryptionService.encryptValue(credentialModel.getPassword(),
                generatedSecretKey),
                userMapper.getUserByUsername(authentication.getName()).getUserId());
        return credentialMapper.insertCredential(credential);
    }

    public int updateCredential(Credential credential, Authentication authentication) {
        return credentialMapper.updateCredential(credential.getUrl(), credential.getUsername(),
                encryptionService.encryptValue(credential.getPassword(), getKeyOfCredential(credential.getCredentialId())),
                credential.getCredentialId());
    }

    public int deleteCredential(Credential credential) {
        return credentialMapper.deleteCredential(credential.getCredentialId());
    }

    private String getKeyOfCredential(Integer credentialId) {
        return credentialMapper.selectCredentialKeyById(credentialId);
    }

    public List<Credential> getAllCredentials() {
        return credentialMapper.selectAllCredentials();
    }

    public String getDecryptedCredentialPassword(Integer credentialId) {
        System.out.println("CredentialId to decrypt change is " + credentialId);
        Credential credentialToDecrypt = getCredentialById(credentialId);
        return encryptionService.decryptValue(credentialToDecrypt.getPassword(),
                credentialToDecrypt.getKey());
    }

    private String generateSecretKey() {
        try {
            KeyGenerator gen = KeyGenerator.getInstance("AES");
            gen.init(128); /* 128-bit AES */
            SecretKey secret = gen.generateKey();
            byte[] binary = secret.getEncoded();
            String key = String.format("%032X", new BigInteger(+1, binary));
            return key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    Credential getCredentialById(Integer credentialId) {
        System.out.println(credentialMapper.selectCredentialById(credentialId).getPassword() + " password of credential");
        System.out.println(credentialMapper.selectCredentialById(credentialId).getKey() + " key of credential");
        return credentialMapper.selectCredentialById(credentialId);
    }

}
