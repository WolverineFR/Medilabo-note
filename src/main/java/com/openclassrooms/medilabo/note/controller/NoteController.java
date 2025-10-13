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

/**
 * Contrôleur REST responsable de la gestion des notes médicales.
 * 
 * Fournit des endpoints pour CRUD.
 * 
 * Respecte le principe REST et renvoie des ResponseEntity avec les codes HTTP
 * appropriés.
 */
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

	/**
	 * Récupère toutes les notes enregistrées dans la base de données.
	 *
	 * @return ResponseEntity contenant la liste des notes (HTTP 200)
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> notes = noteService.getAllNotes();
		logger.info("Récupération de toutes les notes, count={}", notes.size());
		return ResponseEntity.ok(notes);

	}

	/**
	 * Récupère une note à partir de son identifiant.
	 *
	 * @param id identifiant de la note recherchée
	 * @return ResponseEntity contenant la note (HTTP 200) ou un code 404 si
	 *         introuvable
	 */
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

	/**
	 * Crée une nouvelle note.
	 *
	 * @param note l’objet Note à créer (validé via {@link Valid})
	 * @return ResponseEntity contenant la note créée (HTTP 201)
	 */
	@PostMapping
	public ResponseEntity<Note> saveNote(@Valid @RequestBody Note Note) {
		Note saved = noteService.saveNote(Note);
		logger.info("Note créé avec id={}", saved.getId());
		return ResponseEntity.status(201).body(saved);
	}

	/*
	 * Met à jour une note existante.
	 *
	 * @param id identifiant de la note à mettre à jour
	 * 
	 * @param updateNote données de mise à jour (texte de la note, nom du patient,
	 * etc.)
	 * 
	 * @return ResponseEntity contenant la note mise à jour (HTTP 200) ou un code
	 * 404 si introuvable
	 */
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

	/**
	 * Récupère toutes les notes associées à un patient spécifique.
	 *
	 * @param patientId identifiant du patient
	 * @return ResponseEntity contenant la liste des notes de ce patient (HTTP 200)
	 */
	@GetMapping("/patient/{patientId}")
	public ResponseEntity<List<Note>> getNotesByNoteId(@PathVariable String patientId) {
		return ResponseEntity.ok(noteService.getNotesByPatientId(patientId));
	}

}
