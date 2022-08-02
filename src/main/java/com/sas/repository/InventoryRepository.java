package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Inventory;

@Repository
@Transactional
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

	@Query("SELECT inv FROM Inventory inv WHERE inv.partNr=(:partNr)")
	Inventory findByPartNr(@Param("partNr") String partNr);

}
