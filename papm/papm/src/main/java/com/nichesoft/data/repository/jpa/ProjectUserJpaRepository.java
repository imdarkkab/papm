package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.ProjectUserEntity;

/**
 * Repository : ProjectUser.
 */
public interface ProjectUserJpaRepository extends PagingAndSortingRepository<ProjectUserEntity, Integer> {

}
