/*
 * Created on 27 �.�. 2558 ( Time 16:44:19 )
 * Generated by Telosys Tools Generator ( version 2.1.0 )
 */
package com.nichesoft.business.service.mapping;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import com.nichesoft.bean.Project;
import com.nichesoft.bean.jpa.ProjectEntity;

/**
 * Mapping between entity beans and display beans.
 */
@Component
public class ProjectServiceMapper extends AbstractServiceMapper {

	/**
	 * ModelMapper : bean to bean mapping library.
	 */
	private ModelMapper modelMapper;
	
	/**
	 * Constructor.
	 */
	public ProjectServiceMapper() {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	/**
	 * Mapping from 'ProjectEntity' to 'Project'
	 * @param projectEntity
	 */
	public Project mapProjectEntityToProject(ProjectEntity projectEntity) {
		if(projectEntity == null) {
			return null;
		}

		//--- Generic mapping 
		Project project = map(projectEntity, Project.class);

		return project;
	}
	
	/**
	 * Mapping from 'Project' to 'ProjectEntity'
	 * @param project
	 * @param projectEntity
	 */
	public void mapProjectToProjectEntity(Project project, ProjectEntity projectEntity) {
		if(project == null) {
			return;
		}

		//--- Generic mapping 
		map(project, projectEntity);

	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected ModelMapper getModelMapper() {
		return modelMapper;
	}

	protected void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

}