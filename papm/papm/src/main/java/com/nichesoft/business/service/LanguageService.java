/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service;

import java.util.List;

import com.nichesoft.bean.Language;

/**
 * Business Service Interface for entity Language.
 */
public interface LanguageService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param languageId
	 * @return entity
	 */
	Language findById( Integer languageId  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Language> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Language save(Language entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Language update(Language entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Language create(Language entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param languageId
	 */
	void delete( Integer languageId );


}
