package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.RoleEntity;

/**
 * Repository : Role.
 */
public interface RoleJpaRepository extends PagingAndSortingRepository<RoleEntity, Integer> {

}
