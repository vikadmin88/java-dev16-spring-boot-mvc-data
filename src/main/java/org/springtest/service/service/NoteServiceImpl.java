package org.springtest.service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springtest.data.entity.Note;
import org.springtest.data.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springtest.service.exception.NoteNotFoundException;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public List<Note> listAll() {
        log.info("Getting all notes from repository");
        return noteRepository.findAllNotes();
    }

    @Override
    public Note add(Note note) {
        log.info("Adding note: {}", note);
        return noteRepository.addNote(note);
    }

    @Override
    public void deleteById(UUID id) throws NoteNotFoundException {
        log.info("Deleting note with id: {}", id);
        noteRepository.removeNoteById(id);
    }

    @Override
    public void update(Note note) throws NoteNotFoundException {
        log.info("Updating note: {}", note);
        noteRepository.updateNote(note);
    }

    @Override
    public Note getById(UUID id) throws NoteNotFoundException {
        log.info("Getting note with id: {}", id);
        Note note = noteRepository.findNoteById(id);
        if (note != null) return note;
        throw new NoteNotFoundException("Note not found, id: " + id);
    }
}
