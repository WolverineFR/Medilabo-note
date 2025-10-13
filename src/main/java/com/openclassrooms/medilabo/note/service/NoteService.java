package com.openclassrooms.medilabo.note.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.medilabo.note.model.Note;
import com.openclassrooms.medilabo.note.repository.NoteRepository;

/**
 * Service de gestion des notes.
 */
@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	/**
	 * Récupère toutes les notes de la base de données.
	 * 
	 * @return la liste des notes
	 */
	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	/**
	 * Récupère une note de la base de données par son id.
	 * 
	 * @param id L'identifiant de la note
	 * @return la note retrouvée avec l'id
	 */
	public Optional<Note> getNoteById(String id) {
		return noteRepository.findById(id);
	}

	/**
	 * Récupère une liste de notes de la base de données par l'identifiant d'un
	 * patient.
	 * 
	 * @param patientId L'identifiant du patient
	 * @return une liste de notes retrouvée avec l'identifiant du patient
	 */
	public List<Note> getNotesByPatientId(String patientId) {
		return noteRepository.getNotesByPatientId(patientId);
	}

	/**
	 * Enregistre une note dans la base de données.
	 * 
	 * @param note La note à sauvegarder
	 * @return la note enregistrée
	 */
	public Note saveNote(Note note) {
		return noteRepository.save(note);
	}

}
