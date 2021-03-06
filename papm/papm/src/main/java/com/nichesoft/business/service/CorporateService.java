/*
 * Created on 27 �.�. 2558 ( Time 16:44:32 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service;

import java.util.List;

import com.nichesoft.bean.Corporate;

/**
 * Business Service Interface for entity Corporate.
 */
public interface CorporateService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param corporateId
	 * @return entity
	 */
	Corporate findById( Integer corporateId  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Corporate> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Corporate save(Corporate entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Corporate update(Corporate entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Corporate create(Corporate entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param corporateId
	 */
	void delete( Integer corporateId );


}
