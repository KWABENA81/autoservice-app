package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.BodyType;

@Repository
@Transactional
public interface BodyTypeRepository extends JpaRepository<BodyType, Long> {

}
