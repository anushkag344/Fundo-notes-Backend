package com.fundoonotes.fundoo_notes.jms;

import com.fundoonotes.fundoo_notes.model.Note;
import com.fundoonotes.fundoo_notes.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReminderScheduler {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ReminderProducer reminderProducer;

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void checkReminders() {

        List<Note> dueNotes = noteRepository
                .findByReminderBeforeAndReminderSentFalseAndIsTrashedFalse(
                        LocalDateTime.now()
                );

        if (dueNotes.isEmpty()) {
            return;
        }

        System.out.println("Found " + dueNotes.size() + " reminder(s) due!");

        for (Note note : dueNotes) {
            note.setReminderSent(true);
            noteRepository.save(note);

            String title = (note.getTitle() != null && !note.getTitle().trim().isEmpty())
                    ? note.getTitle() : "Untitled Note";

            reminderProducer.sendReminder(
                    note.getUser().getEmail(),
                    title
            );

            System.out.println("Reminder processed for: " + title);
        }
    }
}