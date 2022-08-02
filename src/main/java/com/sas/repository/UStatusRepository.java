package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.UStatus;

@Repository
@Transactional
public interface UStatusRepository extends JpaRepository<UStatus, Long> {

	// UStatus findByStatus(String str);
	@Query("SELECT us FROM UStatus us WHERE us.status=(:str)")
	UStatus findByStatus(@Param("str") String str);

}
