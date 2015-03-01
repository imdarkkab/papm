/*
 * Created on 27 �.�. 2558 ( Time 16:44:20 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.nichesoft.bean.UserPermission;
import com.nichesoft.bean.jpa.UserPermissionEntity;
import com.nichesoft.business.service.UserPermissionService;
import com.nichesoft.business.service.mapping.UserPermissionServiceMapper;
import com.nichesoft.data.repository.jpa.UserPermissionJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of UserPermissionService
 */
@Component
@Transactional
public class UserPermissionServiceImpl implements UserPermissionService {

	@Resource
	private UserPermissionJpaRepository userPermissionJpaRepository;

	@Resource
	private UserPermissionServiceMapper userPermissionServiceMapper;
	
	@Override
	public UserPermission findById(Integer userPermissionId) {
		UserPermissionEntity userPermissionEntity = userPermissionJpaRepository.findOne(userPermissionId);
		return userPermissionServiceMapper.mapUserPermissionEntityToUserPermission(userPermissionEntity);
	}

	@Override
	public List<UserPermission> findAll() {
		Iterable<UserPermissionEntity> entities = userPermissionJpaRepository.findAll();
		List<UserPermission> beans = new ArrayList<UserPermission>();
		for(UserPermissionEntity userPermissionEntity : entities) {
			beans.add(userPermissionServiceMapper.mapUserPermissionEntityToUserPermission(userPermissionEntity));
		}
		return beans;
	}

	@Override
	public UserPermission save(UserPermission userPermission) {
		return update(userPermission) ;
	}

	@Override
	public UserPermission create(UserPermission userPermission) {
		UserPermissionEntity userPermissionEntity = userPermissionJpaRepository.findOne(userPermission.getUserPermissionId());
		if( userPermissionEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		userPermissionEntity = new UserPermissionEntity();
		userPermissionServiceMapper.mapUserPermissionToUserPermissionEntity(userPermission, userPermissionEntity);
		UserPermissionEntity userPermissionEntitySaved = userPermissionJpaRepository.save(userPermissionEntity);
		return userPermissionServiceMapper.mapUserPermissionEntityToUserPermission(userPermissionEntitySaved);
	}

	@Override
	public UserPermission update(UserPermission userPermission) {
		UserPermissionEntity userPermissionEntity = userPermissionJpaRepository.findOne(userPermission.getUserPermissionId());
		userPermissionServiceMapper.mapUserPermissionToUserPermissionEntity(userPermission, userPermissionEntity);
		UserPermissionEntity userPermissionEntitySaved = userPermissionJpaRepository.save(userPermissionEntity);
		return userPermissionServiceMapper.mapUserPermissionEntityToUserPermission(userPermissionEntitySaved);
	}

	@Override
	public void delete(Integer userPermissionId) {
		userPermissionJpaRepository.delete(userPermissionId);
	}

	public UserPermissionJpaRepository getUserPermissionJpaRepository() {
		return userPermissionJpaRepository;
	}

	public void setUserPermissionJpaRepository(UserPermissionJpaRepository userPermissionJpaRepository) {
		this.userPermissionJpaRepository = userPermissionJpaRepository;
	}

	public UserPermissionServiceMapper getUserPermissionServiceMapper() {
		return userPermissionServiceMapper;
	}

	public void setUserPermissionServiceMapper(UserPermissionServiceMapper userPermissionServiceMapper) {
		this.userPermissionServiceMapper = userPermissionServiceMapper;
	}

}