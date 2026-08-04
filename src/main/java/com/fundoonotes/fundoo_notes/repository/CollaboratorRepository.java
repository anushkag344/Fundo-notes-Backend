package com.fundoonotes.fundoo_notes.repository;

import com.fundoonotes.fundoo_notes.model.Collaborator;
import com.fundoonotes.fundoo_notes.model.Note;
import com.fundoonotes.fundoo_notes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CollaboratorRepository extends JpaRepository<Collaborator, Long> {

    List<Collaborator> findByNote(Note note);

    Optional<Collaborator> findByNoteAndUser(Note note, User user);

    Optional<Collaborator> findByNoteIdAndUserId(Long noteId, Long userId);

    boolean existsByNoteIdAndUserId(Long noteId, Long userId);

    List<Collaborator> findByUser(User user);

    void deleteByNoteAndUser(Note note, User user);
}
