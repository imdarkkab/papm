package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.UserPermissionEntity;

/**
 * Repository : UserPermission.
 */
public interface UserPermissionJpaRepository extends PagingAndSortingRepository<UserPermissionEntity, Integer> {

}
