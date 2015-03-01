package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.ProjectEntity;

/**
 * Repository : Project.
 */
public interface ProjectJpaRepository extends PagingAndSortingRepository<ProjectEntity, Integer> {

}
