package org.springtest;

import org.springtest.data.entity.Note;
import org.springtest.data.repository.NoteRepository;
import org.springtest.service.exception.NoteNotFoundException;
import org.springtest.service.service.NoteService;
import org.springtest.service.service.NoteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class NoteServiceImplTests {

    private Note note;
    private NoteService noteService;

    @BeforeEach
    public void beforeEach() {
        NoteRepository noteRepository = new NoteRepository();
        noteService = new NoteServiceImpl(noteRepository);
        note = new Note();
    }

    @Test
    void testAddNote() {
        //When
        Note noteObj = noteService.add(note);

        //Then
        Assertions.assertNotNull(noteObj.getId());
    }

    @Test
    void testGetById() throws NoteNotFoundException {
        //When
        note.setTitle("TEST");
        Note noteAdded = noteService.add(note);
        Note noteObj = noteService.getById(noteAdded.getId());

        //Then
        String  expected = "TEST";
        Assertions.assertEquals(expected, noteObj.getTitle());
    }

    @Test
    void testUpdate() throws NoteNotFoundException {
        //When
        Note noteAdded = noteService.add(note);
        noteAdded.setTitle("UPDATED");
        noteService.update(noteAdded);
        Note noteObj = noteService.getById(noteAdded.getId());

        //Then
        String expected = "UPDATED";
        Assertions.assertEquals(expected, noteObj.getTitle());
    }

    @Test
    void testDelete() throws NoteNotFoundException {
        //When
        Note noteAdded = noteService.add(note);
        List<Note> listNotes = noteService.listAll();
        int expected = 1;
        Assertions.assertEquals(expected, listNotes.size());

        noteService.deleteById(noteAdded.getId());

        //Then
        int expected2 = 0;
        Assertions.assertEquals(expected2, listNotes.size());
    }

    @Test
    void testListAll() {
        //When
        noteService.add(note);
        List<Note> listNotes = noteService.listAll();

        //Then
        int expected = 1;
        Assertions.assertEquals(expected, listNotes.size());
    }

    @Test
    void testUpdateNoteNotFoundException() {
        //When-Then
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.update(note));
    }

    @Test
    void testDeleteNoteNotFoundException() {
        //When-Then
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.deleteById(note.getId()));
    }

    @Test
    void testGetByIdNoteNotFoundException() {
        //When-Then
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.getById(id));
    }

}
