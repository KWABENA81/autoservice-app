package com.sas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Country;
import com.sas.entity.Region;

@Repository
@Transactional//(readOnly=true)
public interface RegionRepository extends JpaRepository<Region, Long> {

	@Query("SELECT r FROM Region r WHERE r.country=(:country)")
	List<Region> findByCountry(@Param("country") Country country);

	@Query("SELECT r FROM Region r WHERE r.name=(:name)")
	Region findByName(@Param("name") String name);
}
