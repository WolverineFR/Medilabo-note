package com.openclassrooms.medilabo.note.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.medilabo.note.model.Note;
import com.openclassrooms.medilabo.note.repository.NoteRepository;

@Service
public class NoteService {

	@Autowired
	private NoteRepository noteRepository;

	public List<Note> getAllNotes() {
		return noteRepository.findAll();
	}

	public Optional<Note> getNoteById(String id) {
		return noteRepository.findById(id);
	}
	
	public List<Note> getNotesByPatientId(String patientId) {
		return noteRepository.getNotesByPatientId(patientId);
	}

	public Note saveNote(Note note) {
		return noteRepository.save(note);
	}

}
