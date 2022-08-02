package com.sas.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.SStatus;
import com.sas.entity.ServiceOrder;
import com.sas.entity.Vehicle;

@Repository
@Transactional
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder, Long> {
	//
	@Query("SELECT so FROM ServiceOrder so WHERE so.sstatus=(:sstatus)")
	List<ServiceOrder> findByOrderStatus(@Param("sstatus") SStatus sstatus);

	@Query("SELECT so FROM ServiceOrder so WHERE so.sstatus<>(:sstatus)")
	List<ServiceOrder> findByNotOrderStatus(@Param("sstatus") SStatus sstatus);

	@Query("SELECT so FROM ServiceOrder so WHERE so.vehicle=(:vehicle)")
	List<ServiceOrder> findByVehicle(@Param("vehicle") Vehicle vehicle);

	@Query("SELECT so FROM ServiceOrder so WHERE so.sdate>(:date) AND  so.edate<(:date2)")
	List<ServiceOrder> findByDateRange(@Param("date") Date date, @Param("date2") Date date2);

	@Query("SELECT so FROM ServiceOrder so WHERE so.jobid=(:jobId)")
	ServiceOrder findByJobId(@Param("jobId") String jobId);

	@Query("SELECT so FROM ServiceOrder so WHERE so.jobid LIKE(:jobId)")
	List<ServiceOrder> findLikeJobId(String jobId);

}
