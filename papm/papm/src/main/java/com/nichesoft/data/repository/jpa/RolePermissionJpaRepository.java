package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.RolePermissionEntity;

/**
 * Repository : RolePermission.
 */
public interface RolePermissionJpaRepository extends PagingAndSortingRepository<RolePermissionEntity, Integer> {

}
