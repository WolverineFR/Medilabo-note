package com.openclassrooms.medilabo.note.controller;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.medilabo.note.model.Note;
import com.openclassrooms.medilabo.note.service.NoteService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/notes")
public class NoteController {

	@Autowired
	private NoteService noteService;

	private static final Logger logger = LogManager.getLogger(NoteController.class);

	@Autowired
	public NoteController(NoteService noteService) {
		this.noteService = noteService;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> notes = noteService.getAllNotes();
		logger.info("Récupération de toutes les notes, count={}", notes.size());
		return ResponseEntity.ok(notes);

	}

	@GetMapping("/{id}")
	public ResponseEntity<Note> getNoteById(@PathVariable String id) {
		return noteService.getNoteById(id).map(Note -> {
			logger.info("Note trouvé avec id={}", id);
			return ResponseEntity.ok(Note);
		}).orElseGet(() -> {
			logger.warn("Note non trouvé avec id={}", id);
			return ResponseEntity.notFound().build();
		});
	}

	@PostMapping
	public ResponseEntity<Note> saveNote(@Valid @RequestBody Note Note) {
		Note saved = noteService.saveNote(Note);
		logger.info("Note créé avec id={}", saved.getId());
		return ResponseEntity.status(201).body(saved);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Note> updateNote(@PathVariable String id, @Valid @RequestBody Note updateNote) {
	    Optional<Note> OptionalNote = noteService.getNoteById(id);
	    if (!OptionalNote.isPresent()) {
	        logger.warn("La note n'existe pas avec id={}", id);
	        return ResponseEntity.notFound().build();
	    }

	    Note note = OptionalNote.get();
	    note.setNote(updateNote.getNote());
	    note.setPatientName(updateNote.getPatientName());

	    Note updated = noteService.saveNote(note);
	    logger.info("Note mise à jour avec id={}", id);
	    return ResponseEntity.ok(updated);
	}


	@GetMapping("/patient/{patientId}")
	public ResponseEntity<List<Note>> getNotesByNoteId(@PathVariable String patientId) {
		return ResponseEntity.ok(noteService.getNotesByPatientId(patientId));
	}

}
