package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.AppUser;

@Repository
@Transactional // (readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

//	@Query("SELECT a FROM AppUser a WHERE a.username=(:uname)")
//	AppUser findUserByUsername(@Param("uname") String uname);
}
