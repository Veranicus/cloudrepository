package com.udacity.jwdnd.course1.cloudstorage.repositories.mappers;

import com.udacity.jwdnd.course1.cloudstorage.entities.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {


    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNoteById(@Param("noteId") Integer noteId);

    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();

    @Insert("INSERT INTO NOTES (noteTitle,noteDescription,userId) VALUES " +
            "(#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty = "noteId")
    int insertNote(Note note);

    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription}" +
            "WHERE noteId = #{noteId}")
    int updateNote(@Param("noteTitle") String noteTitle, @Param("noteDescription") String noteDescription,
    @Param("noteId")Integer noteId);



}
