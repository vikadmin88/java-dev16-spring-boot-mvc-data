package org.springtest.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springtest.data.entity.Note;

import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {

}
