package org.springtest.controller.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springtest.data.entity.Note;
import org.springtest.service.exception.NoteNotFoundException;
import org.springtest.service.service.NoteService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ModelAndView listNotes() {
        ModelAndView result = new ModelAndView("note/list");
        List<Note> notes = noteService.listAll();
        result.addObject("notes", notes);
        return result;
    }


    @RequestMapping(value = "/edit", method = {RequestMethod.GET})
    public ModelAndView getNoteForUpdate(@NotNull @RequestParam(value="id") UUID id) {
        Note note = new Note();
        ModelAndView result = new ModelAndView("note/edit");
        try {
            note = noteService.getById(id);
        } catch (NoteNotFoundException ex) {
            result.addObject("errorMessage", ex.getMessage());
        }
        result.addObject("note", note);
        return result;
    }

    @RequestMapping(value = "/create", method = {RequestMethod.POST})
    public ModelAndView createNote(
        @RequestParam(value="title") @Size(min = 1, max = 250) String title,
        @RequestParam(value="content") @NotBlank String content) {
        Note note = new Note();
        note.setTitle(title);
        note.setContent(content);
        noteService.add(note);
        return new ModelAndView("redirect:list");
    }

    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public ModelAndView updateNote(
        @NotNull @RequestParam(value="id") UUID id,
        @Size(min = 1, max = 250) @RequestParam(value="title") String title,
        @NotEmpty @RequestParam(value="content") String content) throws NoteNotFoundException {
        Note note = new Note();
        note.setId(id);
        note.setTitle(title);
        note.setContent(content);
        noteService.update(note);
        return new ModelAndView("redirect:list");
    }

    @DeleteMapping("/delete")
    @RequestMapping(value = "/delete", method = {RequestMethod.POST})
    public ModelAndView deleteNoteById(@Valid @NotNull @RequestParam(value="id") UUID id) throws NoteNotFoundException {
        noteService.deleteById(id);
        return new ModelAndView("redirect:list");
    }
}
