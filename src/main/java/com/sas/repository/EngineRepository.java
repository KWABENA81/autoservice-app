package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Engine;

@Repository
@Transactional
public interface EngineRepository extends JpaRepository<Engine, Long> {
	@Query("SELECT e FROM Engine e WHERE e.auxdesc=(:aDesc)")
	Engine findByAuxDesc(@Param("aDesc") String aDesc);
}
