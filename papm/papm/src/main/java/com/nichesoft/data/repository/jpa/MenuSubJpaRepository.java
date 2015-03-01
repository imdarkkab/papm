package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.MenuSubEntity;

/**
 * Repository : MenuSub.
 */
public interface MenuSubJpaRepository extends PagingAndSortingRepository<MenuSubEntity, Integer> {

}
