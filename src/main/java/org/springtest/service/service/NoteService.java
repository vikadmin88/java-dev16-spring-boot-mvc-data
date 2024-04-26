package org.springtest.service.service;

import org.springtest.data.entity.Note;
import org.springtest.service.exception.NoteNotFoundException;

import java.util.List;
import java.util.UUID;

public interface NoteService {
    List<Note> listAll();
    Note add(Note note);
    void deleteById(UUID id) throws NoteNotFoundException;
    void update(Note note) throws NoteNotFoundException;
    Note getById(UUID id) throws NoteNotFoundException;
}
