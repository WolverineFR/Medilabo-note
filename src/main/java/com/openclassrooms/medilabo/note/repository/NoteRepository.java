package com.openclassrooms.medilabo.note.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.medilabo.note.model.Note;

/**
 * Repository Spring Data MongoDB pour la gestion des entités Note.
 * 
 * Cette interface hérite de MongoRepository, ce qui permet de bénéficier
 * automatiquement de toutes les opérations CRUD standard (save, findAll,
 * findById, delete, etc.).
 * 
 * Elle définit également une méthode de recherche personnalisée pour récupérer
 * les notes associées à un patient donné.
 */
@Repository
public interface NoteRepository extends MongoRepository<Note, String> {

	/**
	 * Récupère toutes les notes associées à un patient à partir de son identifiant.
	 * 
	 * @param patientId identifiant du patient dont on souhaite récupérer les notes
	 * @return liste des notes associées à ce patient
	 */
	List<Note> getNotesByPatientId(String patientId);
}
