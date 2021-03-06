/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.nichesoft.bean.MenuMain;
import com.nichesoft.bean.jpa.MenuMainEntity;
import com.nichesoft.business.service.MenuMainService;
import com.nichesoft.business.service.mapping.MenuMainServiceMapper;
import com.nichesoft.data.repository.jpa.MenuMainJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of MenuMainService
 */
@Component
@Transactional
public class MenuMainServiceImpl implements MenuMainService {

	@Resource
	private MenuMainJpaRepository menuMainJpaRepository;

	@Resource
	private MenuMainServiceMapper menuMainServiceMapper;
	
	@Override
	public MenuMain findById(Integer menuMainId) {
		MenuMainEntity menuMainEntity = menuMainJpaRepository.findOne(menuMainId);
		return menuMainServiceMapper.mapMenuMainEntityToMenuMain(menuMainEntity);
	}

	@Override
	public List<MenuMain> findAll() {
		Iterable<MenuMainEntity> entities = menuMainJpaRepository.findAll();
		List<MenuMain> beans = new ArrayList<MenuMain>();
		for(MenuMainEntity menuMainEntity : entities) {
			beans.add(menuMainServiceMapper.mapMenuMainEntityToMenuMain(menuMainEntity));
		}
		return beans;
	}

	@Override
	public MenuMain save(MenuMain menuMain) {
		return update(menuMain) ;
	}

	@Override
	public MenuMain create(MenuMain menuMain) {
		MenuMainEntity menuMainEntity = menuMainJpaRepository.findOne(menuMain.getMenuMainId());
		if( menuMainEntity != null ) {
			throw new IllegalStateException("already.exists");
		}
		menuMainEntity = new MenuMainEntity();
		menuMainServiceMapper.mapMenuMainToMenuMainEntity(menuMain, menuMainEntity);
		MenuMainEntity menuMainEntitySaved = menuMainJpaRepository.save(menuMainEntity);
		return menuMainServiceMapper.mapMenuMainEntityToMenuMain(menuMainEntitySaved);
	}

	@Override
	public MenuMain update(MenuMain menuMain) {
		MenuMainEntity menuMainEntity = menuMainJpaRepository.findOne(menuMain.getMenuMainId());
		menuMainServiceMapper.mapMenuMainToMenuMainEntity(menuMain, menuMainEntity);
		MenuMainEntity menuMainEntitySaved = menuMainJpaRepository.save(menuMainEntity);
		return menuMainServiceMapper.mapMenuMainEntityToMenuMain(menuMainEntitySaved);
	}

	@Override
	public void delete(Integer menuMainId) {
		menuMainJpaRepository.delete(menuMainId);
	}

	public MenuMainJpaRepository getMenuMainJpaRepository() {
		return menuMainJpaRepository;
	}

	public void setMenuMainJpaRepository(MenuMainJpaRepository menuMainJpaRepository) {
		this.menuMainJpaRepository = menuMainJpaRepository;
	}

	public MenuMainServiceMapper getMenuMainServiceMapper() {
		return menuMainServiceMapper;
	}

	public void setMenuMainServiceMapper(MenuMainServiceMapper menuMainServiceMapper) {
		this.menuMainServiceMapper = menuMainServiceMapper;
	}

}
