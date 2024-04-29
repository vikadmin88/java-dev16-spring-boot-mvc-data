package org.springtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springtest.controller.controller.NoteController;
import org.springtest.controller.controller.RootController;
import org.springtest.service.dto.NoteDto;
import org.springtest.service.exception.NoteNotFoundException;
import org.springtest.service.mapper.NoteMapper;
import org.springtest.service.service.NoteService;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.AUTO_CONFIGURED)
@Sql(statements = "delete from note;", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class JavaDev16SpringBootMvcDataApplicationTests {

    @Autowired
    private RootController rootController;
    @Autowired
    private NoteController noteController;
    @Autowired
    private NoteService noteService;
    @Autowired
    private NoteMapper noteMapper;
    private NoteDto note;

    @BeforeEach
    public void beforeEach() {
        note = new NoteDto();
        note.setTitle("Title");
        note.setContent("Content");
    }

    @Test
    void contextLoads() {
        assertThat(rootController).isNotNull();
        assertThat(noteController).isNotNull();
    }

    @Test
    void testAddNote() {
        //When
        NoteDto noteObj = noteService.add(note);

        //Then
        Assertions.assertNotNull(noteObj.getId());
    }

    @Test
    void testGetById() throws NoteNotFoundException {
        //When
        note.setTitle("TEST");
        NoteDto noteAdded = noteService.add(note);
        NoteDto noteObj = noteService.getById(noteMapper.toNoteEntity(noteAdded).getId());

        //Then
        String  expected = "TEST";
        Assertions.assertEquals(expected, noteObj.getTitle());
    }

    @Test
    void testUpdate() throws NoteNotFoundException {
        //When
        NoteDto noteAdded = noteService.add(note);
        noteAdded.setTitle("UPDATED");
        noteService.update(noteAdded);
        NoteDto noteObj = noteService.getById(noteAdded.getId());

        //Then
        String expected = "UPDATED";
        Assertions.assertEquals(expected, noteObj.getTitle());
    }

    @Test
    void testDelete() throws NoteNotFoundException {
        //When
        NoteDto noteAdded = noteService.add(note);
        List<NoteDto> listNotes = noteService.listAll();
        int expected = 1;
        Assertions.assertEquals(expected, listNotes.size());

        noteService.deleteById(noteAdded.getId());
        listNotes = noteService.listAll();

        //Then
        expected = 0;
        Assertions.assertEquals(expected, listNotes.size());
    }

    @Test
    void testListAll() {
        //When
        noteService.add(note);
        List<NoteDto> listNotes = noteService.listAll();

        //Then
        int expected = 1;
        Assertions.assertEquals(expected, listNotes.size());
    }

    @Test
    void testDeleteNoteNotFoundException() {
        //When-Then
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.deleteById(null));
    }

    @Test
    void testUpdateNoteNotFoundException() {
        //When-Then
        note.setId(UUID.randomUUID());
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.update(note));
    }

    @Test
    void testGetByIdNoteNotFoundException() {
        //When-Then
        UUID id = UUID.randomUUID();
        Assertions.assertThrows(NoteNotFoundException.class, () -> noteService.getById(id));
    }


}
