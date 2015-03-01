package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.PermissionEntity;

/**
 * Repository : Permission.
 */
public interface PermissionJpaRepository extends PagingAndSortingRepository<PermissionEntity, Integer> {

}
