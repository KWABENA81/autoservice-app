package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Colours;

@Repository
@Transactional
public interface ColourRepository extends JpaRepository<Colours, Long> {

}
