package com.sas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Models;

@Repository
@Transactional
public interface ModelsRepository extends JpaRepository<Models, Long> {
	@Query("SELECT m FROM Models m WHERE m.ref=(:mkRef)")
	List<Models> findByMakeName(@Param("mkRef") String mkRef);
}
