/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.nichesoft.bean.RolePermission;
import com.nichesoft.bean.jpa.RolePermissionEntity;
import com.nichesoft.business.service.RolePermissionService;
import com.nichesoft.business.service.mapping.RolePermissionServiceMapper;
import com.nichesoft.data.repository.jpa.RolePermissionJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of RolePermissionService
 */
@Component
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

	@Resource
	private RolePermissionJpaRepository rolePermissionJpaRepository;

	@Resource
	private RolePermissionServiceMapper rolePermissionServiceMapper;
	
	@Override
	public RolePermission findById(Integer rolePermissionId) {
		RolePermissionEntity rolePermissionEntity = rolePermissionJpaRepository.findOne(rolePermissionId);
		return rolePermissionServiceMapper.mapRolePermissionEntityToRolePermission(rolePermissionEntity);
	}

	@Override
	public List<RolePermission> findAll() {
		Iterable<RolePermissionEntity> entities = rolePermissionJpaRepository.findAll();
		List<RolePermission> beans = new ArrayList<RolePermission>();
		for(RolePermissionEntity rolePermissionEntity : entities) {
			beans.add(rolePermissionServiceMapper.mapRolePermissionEntityToRolePermission(rolePermissionEntity));
		}
		return beans;
	}

	@Override
	public RolePermission save(RolePermission rolePermission) {
		return update(rolePermission) ;
	}

	@Override
	public RolePermission create(RolePermission rolePermission) {
		RolePermissionEntity rolePermissionEntity = rolePermissionJpaRepository.findOne(rolePermission.getRolePermissionId());
		if( rolePermissionEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		rolePermissionEntity = new RolePermissionEntity();
		rolePermissionServiceMapper.mapRolePermissionToRolePermissionEntity(rolePermission, rolePermissionEntity);
		RolePermissionEntity rolePermissionEntitySaved = rolePermissionJpaRepository.save(rolePermissionEntity);
		return rolePermissionServiceMapper.mapRolePermissionEntityToRolePermission(rolePermissionEntitySaved);
	}

	@Override
	public RolePermission update(RolePermission rolePermission) {
		RolePermissionEntity rolePermissionEntity = rolePermissionJpaRepository.findOne(rolePermission.getRolePermissionId());
		rolePermissionServiceMapper.mapRolePermissionToRolePermissionEntity(rolePermission, rolePermissionEntity);
		RolePermissionEntity rolePermissionEntitySaved = rolePermissionJpaRepository.save(rolePermissionEntity);
		return rolePermissionServiceMapper.mapRolePermissionEntityToRolePermission(rolePermissionEntitySaved);
	}

	@Override
	public void delete(Integer rolePermissionId) {
		rolePermissionJpaRepository.delete(rolePermissionId);
	}

	public RolePermissionJpaRepository getRolePermissionJpaRepository() {
		return rolePermissionJpaRepository;
	}

	public void setRolePermissionJpaRepository(RolePermissionJpaRepository rolePermissionJpaRepository) {
		this.rolePermissionJpaRepository = rolePermissionJpaRepository;
	}

	public RolePermissionServiceMapper getRolePermissionServiceMapper() {
		return rolePermissionServiceMapper;
	}

	public void setRolePermissionServiceMapper(RolePermissionServiceMapper rolePermissionServiceMapper) {
		this.rolePermissionServiceMapper = rolePermissionServiceMapper;
	}

}
