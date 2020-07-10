package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
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

    public HomeController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public String getHomePage(@ModelAttribute("noteModel")NoteModel noteModel, Model model) {
        model.addAttribute("allNotes",noteService.getAllNotes());
        return "home";
    }

    @PostMapping("/saveNote")
    public String saveNote(@ModelAttribute("noteModel") NoteModel noteModel, Model model,
                           Authentication authentication) {
//        ModelAndView mav = new ModelAndView("home");
//        model.addAttribute("allNotes",noteService.getAllNotes());
        noteService.saveNote(noteModel, authentication);
//        mav.addObject("allNotes", noteService.getAllNotes());
        return "result";
    }

}
