package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.SStatus;

@Repository
@Transactional
public interface SStatusRepository extends JpaRepository<SStatus, Long> {
	@Query("SELECT s FROM SStatus s WHERE s.status=(:status)")
	SStatus findByStatus(@Param("status") String status);

}
