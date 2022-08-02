package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Source;

@Repository
@Transactional
public interface SourceRepository extends JpaRepository<Source, Long> {

}
