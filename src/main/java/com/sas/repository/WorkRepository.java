package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Work;

@Repository
@Transactional//(readOnly=true)
public interface WorkRepository extends JpaRepository<Work, Long> {

}
