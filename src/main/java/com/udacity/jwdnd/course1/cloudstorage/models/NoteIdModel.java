package com.udacity.jwdnd.course1.cloudstorage.models;

public class NoteIdModel {

    private Integer noteIdHidden;

    public NoteIdModel() {
    }

    public NoteIdModel(Integer noteIdHidden) {
        this.noteIdHidden = noteIdHidden;
    }

    public Integer getNoteIdHidden() {
        return noteIdHidden;
    }
}
