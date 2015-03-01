package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.LanguageEntity;

/**
 * Repository : Language.
 */
public interface LanguageJpaRepository extends PagingAndSortingRepository<LanguageEntity, Integer> {

}
