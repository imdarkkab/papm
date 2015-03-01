/*
 * Created on 27 �.�. 2558 ( Time 16:44:33 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service;

import java.util.List;

import com.nichesoft.bean.User;
import com.nichesoft.bean.jpa.UserEntity;

/**
 * Business Service Interface for entity User.
 */
public interface UserService { 

	/**
	 * Loads an entity from the database using its Primary Key
	 * @param userId
	 * @return entity
	 */
	User findById( Integer userId  ) ;

	/**
	 * Loads all entities.
	 * @return all entities
	 */
	List<User> findAll();

	/**
	 * Saves the given entity in the database (create or update)
	 * @param entity
	 * @return entity
	 */
	User save(User entity);

	/**
	 * Updates the given entity in the database
	 * @param entity
	 * @return
	 */
	User update(User entity);

	/**
	 * Creates the given entity in the database
	 * @param entity
	 * @return
	 */
	User create(User entity);

	/**
	 * Deletes an entity using its Primary Key
	 * @param userId
	 */
	void delete( Integer userId );
	
	User findByUserNameAndPassword( String userName , String password  ) ;
	User findByUserNameOrPassword( String userName , String password  ) ;
	User findByCorporateId(Integer corporateId);
 


}
