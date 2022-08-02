package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Category;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Long> {

}

