package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private NoteService noteService;

    private CredentialService credentialService;

    public HomeController(NoteService noteService, CredentialService credentialService) {
        this.noteService = noteService;
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("noteModel") NoteModel noteModel,
                              @ModelAttribute("credentialModel") CredentialModel credentialModel,
                              Model model) {
        model.addAttribute("allNotes", noteService.getAllNotes());
        model.addAttribute("allCredentials", credentialService.getAllCredentials());
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

}
