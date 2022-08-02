package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Trim;

@Repository
@Transactional
public interface TrimRepository extends JpaRepository<Trim, Long> {
	@Query("SELECT t FROM Trim t WHERE  t.level=(:level)")
	Trim findByDescription(@Param("level") String level);

}
