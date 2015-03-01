package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.CorporateEntity;

/**
 * Repository : Corporate.
 */
public interface CorporateJpaRepository extends PagingAndSortingRepository<CorporateEntity, Integer> {

}
