package org.springtest.data.repository;

import lombok.extern.slf4j.Slf4j;
import org.springtest.data.entity.Note;
import org.springframework.stereotype.Component;
import org.springtest.service.exception.NoteNotFoundException;

import java.util.*;

@Slf4j
@Component
public class NoteRepository {

    private final List<Note> notes = new ArrayList<>();

    public List<Note> findAllNotes() {
        log.info("Find all notes");
        return notes;
    }
    public Note addNote(Note note) {
        log.info("Add note: {}", note);
        note.setId(UUID.randomUUID());
        notes.add(note);
        return note;
    }

    public void removeNoteById(UUID id) throws NoteNotFoundException {
        log.info("Remove note by id: {}", id);
        Note note = findNoteById(id);
        if (note != null) {
            notes.remove(note);
        } else {
            throw new NoteNotFoundException("Note not found");
        }
    }

    public void updateNote(Note note) throws NoteNotFoundException {
        log.info("Update note: {}", note);
        Note noteToUpdate = findNoteById(note.getId());
        if (noteToUpdate != null) {
            int noteId = notes.indexOf(noteToUpdate);
            notes.set(noteId, note);
        } else {
            throw new NoteNotFoundException("Note not found");
        }
    }

    public Note findNoteById(UUID id) {
        log.info("Find note by id: {}", id);
        return notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
