package com.sas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Make;
import com.sas.entity.Model;

@Repository
@Transactional
public interface ModelRepository extends JpaRepository<Model, Long> {

	@Query("SELECT m FROM Model m WHERE  m.name=(:name) AND m.make=(:make) AND m.trim=(:trim)  AND m.engdesc=(:engdesc)")
	List<Model> findByNameMake(@Param("name") String name, @Param("make") Make make, @Param("trim") String trim,
			@Param("engdesc") String engdesc);

	@Query("SELECT m FROM Model m WHERE  m.name=(:name) AND m.make=(:make) AND m.trim=(:trim)  AND m.engdesc=(:engdesc)")
	Model findByNameMakeTrimEngine(@Param("name") String name, @Param("make") Make make, @Param("trim") String trim,
			@Param("engdesc") String engdesc);

	@Query("SELECT m FROM Model m WHERE  m.name=(:name)")
	List<Model> findByName(@Param("name") String name);

}
