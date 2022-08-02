package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.DriveType;

@Repository
@Transactional
public interface DriveTypeRepository extends JpaRepository<DriveType, Long> {

}
