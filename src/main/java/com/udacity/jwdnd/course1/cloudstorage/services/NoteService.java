package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.repositories.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.security.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.security.EncryptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    private final AuthenticationService authenticationService;

    private final UserMapper userMapper;

    private Logger logger = LoggerFactory.getLogger(EncryptionService.class);


    @Autowired
    public NoteService(NoteMapper noteMapper, AuthenticationService authenticationService,
                       UserMapper userMapper) {
        this.noteMapper = noteMapper;
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    public void saveNote(NoteModel noteModel, Authentication authentication) {
        Note note = new Note(null, noteModel.getNoteTitle(), noteModel.getNoteDescription(),
                userMapper.getUserByUsername(authentication.getName()).getUserId());
        logger.info("Saving object " + note.getNoteTitle());
        noteMapper.insertNote(note);
    }

    public List<Note> getAllNotes(){
        for (Note note: noteMapper.getAllNotes()){
            logger.info(note.getNoteTitle());
        }
        return noteMapper.getAllNotes();
    }
}
