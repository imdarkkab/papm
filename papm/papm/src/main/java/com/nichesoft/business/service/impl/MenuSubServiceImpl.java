/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.nichesoft.bean.MenuSub;
import com.nichesoft.bean.jpa.MenuSubEntity;
import java.util.List;
import com.nichesoft.business.service.MenuSubService;
import com.nichesoft.business.service.mapping.MenuSubServiceMapper;
import com.nichesoft.data.repository.jpa.MenuSubJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of MenuSubService
 */
@Component
@Transactional
public class MenuSubServiceImpl implements MenuSubService {

	@Resource
	private MenuSubJpaRepository menuSubJpaRepository;

	@Resource
	private MenuSubServiceMapper menuSubServiceMapper;
	
	@Override
	public MenuSub findById(Integer menuSubIs) {
		MenuSubEntity menuSubEntity = menuSubJpaRepository.findOne(menuSubIs);
		return menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntity);
	}

	@Override
	public List<MenuSub> findAll() {
		Iterable<MenuSubEntity> entities = menuSubJpaRepository.findAll();
		List<MenuSub> beans = new ArrayList<MenuSub>();
		for(MenuSubEntity menuSubEntity : entities) {
			beans.add(menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntity));
		}
		return beans;
	}

	@Override
	public MenuSub save(MenuSub menuSub) {
		return update(menuSub) ;
	}

	@Override
	public MenuSub create(MenuSub menuSub) {
		MenuSubEntity menuSubEntity = menuSubJpaRepository.findOne(menuSub.getMenuSubIs());
		if( menuSubEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		menuSubEntity = new MenuSubEntity();
		menuSubServiceMapper.mapMenuSubToMenuSubEntity(menuSub, menuSubEntity);
		MenuSubEntity menuSubEntitySaved = menuSubJpaRepository.save(menuSubEntity);
		return menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntitySaved);
	}

	@Override
	public MenuSub update(MenuSub menuSub) {
		MenuSubEntity menuSubEntity = menuSubJpaRepository.findOne(menuSub.getMenuSubIs());
		menuSubServiceMapper.mapMenuSubToMenuSubEntity(menuSub, menuSubEntity);
		MenuSubEntity menuSubEntitySaved = menuSubJpaRepository.save(menuSubEntity);
		return menuSubServiceMapper.mapMenuSubEntityToMenuSub(menuSubEntitySaved);
	}

	@Override
	public void delete(Integer menuSubIs) {
		menuSubJpaRepository.delete(menuSubIs);
	}

	public MenuSubJpaRepository getMenuSubJpaRepository() {
		return menuSubJpaRepository;
	}

	public void setMenuSubJpaRepository(MenuSubJpaRepository menuSubJpaRepository) {
		this.menuSubJpaRepository = menuSubJpaRepository;
	}

	public MenuSubServiceMapper getMenuSubServiceMapper() {
		return menuSubServiceMapper;
	}

	public void setMenuSubServiceMapper(MenuSubServiceMapper menuSubServiceMapper) {
		this.menuSubServiceMapper = menuSubServiceMapper;
	}

}
