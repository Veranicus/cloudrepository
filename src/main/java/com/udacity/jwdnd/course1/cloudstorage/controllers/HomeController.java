package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Credential;
import com.udacity.jwdnd.course1.cloudstorage.entities.File;
import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.models.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.models.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.http.HttpHeaders;
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
    public String deleteNote(@ModelAttribute(value = "note") Note note, Model model) {
        System.out.println("deleting NOTE: " + note.getNoteId());
        int deletedNoteStatus = noteService.deleteNote(note.getNoteId());
        String editError = null;
        if (deletedNoteStatus < 0) {
            editError = "There was an error with deleting note with id " + note.getNoteId();
        }
        model.addAttribute("editError", editError);
        return "result";
    }

    @PostMapping("/editNote")
    public String editNote(@ModelAttribute(value = "note") Note note) {
        System.out.println("noteid " + note.getNoteId());
        System.out.println("notedesc" + note.getNoteDescription());
        noteService.editNote(note);
        return "result";
    }

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

        credentialService.updateCredential(credential, authentication);
        return "result";
    }

    @PostMapping("/deleteCredential")
    public String deleteCredential(@ModelAttribute(value = "credential") Credential credential, Model model) {
        System.out.println("deleting Credential: " + credential.getCredentialId());
        int deletedCredentialStatus = credentialService.deleteCredential(credential);
        String editError = null;
        if (deletedCredentialStatus < 0) {
            editError = "There was an error with deleting credential with id " + credential.getCredentialId();
        }
        model.addAttribute("editError", editError);
        return "result";
    }

    @PostMapping("/saveFile")
    public String saveFile(Model model, @RequestParam("fileUpload") MultipartFile fileUpload, Authentication authentication) {
        String editError = null;

        if (fileUpload.isEmpty()) {
            editError = "Please choose file you want to upload.";
        }
        if (!fileService.checkIfFileWithFilenameAlreadyExist(fileUpload.getOriginalFilename())) {
            editError = "File with filename " + fileUpload.getOriginalFilename() + " already exists.";
        }
        try {
            if (editError == null) {
                FileModel fileModel1 = new FileModel(fileUpload.getOriginalFilename(), fileUpload.getContentType(),
                        String.valueOf(fileUpload.getSize()), fileUpload.getBytes());
                fileService.saveFile(fileModel1, authentication);
            }
        } catch (IOException e) {
            editError = "There was an error while processing your file.";
            e.printStackTrace();
        }
        model.addAttribute("editError", editError);
        return "result";
    }

    @PostMapping("/deleteFile")
    public String deleteFile(@RequestParam(value = "fileId") Integer fileId, Model model) {
        System.out.println("deleting File " + fileId);
        int deletedFileStatus = fileService.deleteFile(fileId);
        String editError = null;
        if (deletedFileStatus < 0) {
            editError = "There was an error with deleting file with id " + fileId;
        }
        model.addAttribute("editError", editError);
        return "result";
    }

    @GetMapping("/download/{filename}/db")
    public ResponseEntity downloadFromDB(@PathVariable(value = "filename") String fileName) {
        System.out.println("Downloading filename " + fileName);
        File file = fileService.selectFileByName(fileName);
        System.out.println(file.getFilename() + file.getContentType());
        return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(file.getFileData());
    }

}
