package com.sas.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.entity.Model;
import com.sas.entity.Owner;
import com.sas.entity.Vehicle;
import com.sas.repository.VehicleRepository;
import com.sas.service_interface.VehicleServiceInterface;


@Service
public class VehicleService implements VehicleServiceInterface {
	@Autowired
	VehicleRepository vehicleRepository;

	@Autowired
	ModelService modelService;

	@Autowired
	OwnerService ownerService;

	@Override
	public Collection<Vehicle> findAll() {
		return vehicleRepository.findAll();
	}

	@Override
	public Collection<Vehicle> findAll(int pageNumber, int pageSize) {
		return vehicleRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public Vehicle findOne(Long id) {
		return vehicleRepository.findOne(id);
	}

	@Override
	public Vehicle create(Vehicle v) {
		if (v.getID() != null) {
			return null;
		}
		Owner owner = v.getOwner();
		Model mdl = v.getModel();

		owner.addVehicle(v);
		mdl.addVehicle(v);

		Vehicle veh = vehicleRepository.save(v);
		modelService.update(mdl);
		ownerService.update(owner);
		return veh;
	}

	@Override
	public Vehicle update(Vehicle vehicle) {
		Vehicle storedVehicle = vehicleRepository.findOne(vehicle.getID());
		if (storedVehicle == null) {
			return null;
		}
		storedVehicle.setPlate(vehicle.getPlate());
		storedVehicle.setOwner(vehicle.getOwner());
		return vehicleRepository.save(storedVehicle);
	}

	@Override
	public void delete(Long id) {
		vehicleRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return vehicleRepository.count();
	}

	/**
	 * 
	 * @param vin
	 * @param plate
	 * @return
	 */
	public Vehicle findByVINorPlate(String vin, String plate) {
		return vehicleRepository.findByVINOrPlate(vin, plate);
	}

	/**
	 * ]
	 * 
	 * @param vin
	 * @return
	 */
	public Vehicle findByVIN(String vin) {
		return vehicleRepository.findByVIN(vin);
	}

	/**
	 * 
	 * @param plate
	 * @return
	 */
	public Vehicle findByPlate(String plate) {
		return vehicleRepository.findByPlate(plate);
	}

	/**
	 * 
	 * @param vin
	 * @param plate
	 * @return
	 */
	public Vehicle findByVINAndPlate(String vin, String plate) {
		return vehicleRepository.findByVINAndPlate(vin, plate);
	}

}
