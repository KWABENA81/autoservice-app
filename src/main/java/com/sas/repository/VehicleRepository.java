package com.sas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sas.entity.Vehicle;

@Repository
@Transactional
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
	/**
	 * 
	 * @param vin
	 *            - String Vehicle VIN
	 * @return - Vehicle
	 */
	@Query("SELECT v FROM Vehicle v WHERE v.VIN=(:vin)")
	Vehicle findByVIN(@Param("vin") String vin);

	/**
	 * 
	 * @param vin
	 *            - String Vehicle VIN
	 * @param plate
	 *            - Vehicle plate
	 * @return - Vehicle
	 */
	@Query("SELECT v FROM Vehicle v WHERE v.VIN=(:vin) OR v.plate=(:plate)")
	Vehicle findByVINOrPlate(@Param("vin") String vin, @Param("plate") String plate);

	/**
	 * 
	 * @param plate
	 *            - String Vehicle plate
	 * @return - Vehicle
	 */
	@Query("SELECT v FROM Vehicle v WHERE v.plate=(:plate)")
	Vehicle findByPlate(@Param("plate") String plate);

	/**
	 * 
	 * @param vin
	 *            - String Vehicle VIN
	 * @param plate
	 *            - Vehicle plate
	 * @return - Vehicle
	 */
	@Query("SELECT v FROM Vehicle v WHERE v.VIN=(:vin) AND v.plate=(:plate)")
	Vehicle findByVINAndPlate(@Param("vin") String vin, @Param("plate") String plate);

}
