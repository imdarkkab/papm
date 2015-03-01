package com.nichesoft.data.repository.jpa;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.nichesoft.bean.jpa.UserEntity;

/**
 * Repository : User.
 */
public interface UserJpaRepository extends PagingAndSortingRepository<UserEntity, Integer> {

	
	UserEntity findByUserNameAndPassword( String userName , String password  ) ;
	
	@Query("select u from UserEntity u where u.userName = :userName or u.password = :password")
	UserEntity findByUserNameOrPassword(@Param("userName") String userName,
	                                 @Param("password") String password);
	
	 @Query(value = "SELECT * FROM user WHERE employee_id in(select employee_id from corporate where corporate_id = ?1 )", nativeQuery = true)
	 UserEntity findByCorporateId(Integer corporateId);
}
