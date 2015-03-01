/*
 * Created on 27 �.�. 2558 ( Time 16:44:33 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service;

import java.util.List;

import com.nichesoft.bean.Project;

/**
 * Business Service Interface for entity Project.
 */
public interface ProjectService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param projectId
	 * @return entity
	 */
	Project findById( Integer projectId  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<Project> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	Project save(Project entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	Project update(Project entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	Project create(Project entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param projectId
	 */
	void delete( Integer projectId );


}