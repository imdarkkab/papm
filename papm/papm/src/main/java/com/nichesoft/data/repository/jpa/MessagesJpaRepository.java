package com.nichesoft.data.repository.jpa;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.nichesoft.bean.jpa.MessagesEntity;

/**
 * Repository : Messages.
 */
public interface MessagesJpaRepository extends PagingAndSortingRepository<MessagesEntity, Integer> {

}
