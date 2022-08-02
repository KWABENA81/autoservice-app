package com.sas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Owner;

@Repository
@Transactional
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	@Query("SELECT o FROM Owner o WHERE (o.firstname=(:fname) OR o.lastname=(:lname)) AND o.phone=(:phone)")
	Owner findByNameAndPhone(@Param("fname") String fname, @Param("lname") String lname, @Param("phone") String phone);

	@Query("SELECT o FROM Owner o WHERE o.firstname=(:fname) AND o.lastname=(:lname)")
	Owner findByNames(@Param("fname") String fname, @Param("lname") String lname);

	@Query("SELECT o FROM Owner o WHERE o.firstname=(:fname) OR o.lastname=(:lname)")
	List<Owner> findByName(@Param("fname") String fname, @Param("lname") String lname);

	@Query("SELECT o FROM Owner o WHERE o.phone=(:phone) OR o.phoneOther=(:phoneOther) ")//
	List<Owner> findByPhone(@Param("phone") String phone, @Param("phoneOther") String phoneOther);

}
