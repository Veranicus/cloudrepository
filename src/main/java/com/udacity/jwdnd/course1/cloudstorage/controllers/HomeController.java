package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;

    private CredentialService credentialService;

    private FileService fileService;


    public HomeController(NoteService noteService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("noteModel") NoteModel noteModel,
                              @ModelAttribute("credentialModel") CredentialModel credentialModel,
                              Model model) {
        model.addAttribute("allNotes", noteService.getAllNotes());
        model.addAttribute("allCredentials", credentialService.getAllCredentials());
        model.addAttribute("allFiles", fileService.getAllFiles());
        return "home";
    }

    @PostMapping("/saveNote")
    public String saveNote(@ModelAttribute("noteModel") NoteModel noteModel, Model model,
                           Authentication authentication) {

        System.out.println(noteModel.getNoteId());
        noteService.saveNote(noteModel, authentication);
        System.out.println("SAVING NOTE: " + noteModel.getNoteDescription());
        return "result";
    }

    @PostMapping("/deleteNote")
    public String deleteNote(@ModelAttribute(value = "note") Note note) {
        System.out.println("deleting NOTE: " + note.getNoteId());
        noteService.deleteNote(note.getNoteId());
        return "result";
    }

    @PostMapping("/editNote")
    public String editNote(@ModelAttribute(value = "note") Note note, Model model,
                           Authentication authentication) {
        System.out.println("noteid " + note.getNoteId());
        System.out.println("notedesc" + note.getNoteDescription());
        noteService.editNote(note, authentication);
        return "result";
    }
//
//    @PostMapping(path = "/editNote/{editNoteId}")
//    public String editNote(@RequestBody Note note, @PathVariable(value = "editNoteId") Integer noteId) {
//        System.out.println("noteid " + noteId);
//        System.out.println("notedesc" + note.getNoteDescription());
//        return "result";
//    }

    @PostMapping(value = "/saveCredential")
    public String saveCredential(@ModelAttribute("credentialModel") CredentialModel credentialModel, Model model,
                                 Authentication authentication) {
        credentialService.saveCredential(credentialModel, authentication);
        return "result";
    }

    @GetMapping(value = "/getDecryptedPassword/{credentialId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getDecryptedPassword(@PathVariable(value = "credentialId") String credentialId) {
        System.out.println("credentialId" + credentialId);
        String result = credentialService.getDecryptedCredentialPassword(Integer.valueOf(credentialId));
        return ResponseEntity.ok(result);
    }


    @PostMapping(value = "/editCredential")
    public String editCredential(@ModelAttribute(value = "credential") Credential credential, Model model,
                                 Authentication authentication) {
        System.out.println("credentialId " + credential.getCredentialId());
        System.out.println("credential url" + credential.getUrl());
//        model.addAttribute("decryptedCredentialPassword",

        credentialService.updateCredential(credential, authentication);
        return "result";
    }

    @PostMapping("/deleteCredential")
    public String deleteCredential(@ModelAttribute(value = "credential") Credential credential) {
        System.out.println("deleting Credential: " + credential.getCredentialId());
        credentialService.deleteCredential(credential);
        return "result";
    }

    @PostMapping("/saveFile")
    public String saveFile(Model model, @RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication) {
        try {
            FileModel fileModel1 = new FileModel(fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                    String.valueOf(fileUpload.getSize()), fileUpload.getBytes());
            fileService.saveFile(fileModel1, authentication);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "result";
    }

}
