package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
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
        noteService.saveNote(noteModel, authentication);
        return "result";
    }

    @PostMapping("/saveCredential")
    public String saveCredential(@ModelAttribute("credentialModel") CredentialModel credentialModel, Model model,
                                 Authentication authentication) {
        credentialService.saveCredential(credentialModel, authentication);
        return "result";
    }

    @PostMapping("/saveFile")
    public String saveFile( Model model,@RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication) {
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
