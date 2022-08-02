package com.sas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Address;
import com.sas.entity.Region;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	@Query("SELECT a FROM Address a WHERE a.region=(:region)")
	List<Address> findAddressByRegion(@Param("region") Region region);

}
