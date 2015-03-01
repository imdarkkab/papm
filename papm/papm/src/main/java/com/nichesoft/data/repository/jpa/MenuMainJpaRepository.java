package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.MenuMainEntity;

/**
 * Repository : MenuMain.
 */
public interface MenuMainJpaRepository extends PagingAndSortingRepository<MenuMainEntity, Integer> {

}
