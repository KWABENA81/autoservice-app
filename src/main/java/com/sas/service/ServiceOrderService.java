package com.sas.service;

import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sas.app.XMLUtils;
import com.sas.entity.SStatus;
import com.sas.entity.ServiceOrder;
import com.sas.entity.Vehicle;
import com.sas.repository.ServiceOrderRepository;
import com.sas.service_interface.ServiceOrderServiceInterface;

@Service
public class ServiceOrderService implements ServiceOrderServiceInterface {

	@Autowired
	ServiceOrderRepository serviceOrderRepository;

	@Autowired
	VehicleService vehicleService;
	@Autowired
	SStatusService sstatusService;

	@Override
	public Collection<ServiceOrder> findAll() {
		return serviceOrderRepository.findAll();
	}

	@Override
	public Collection<ServiceOrder> findAll(int pageNumber, int pageSize) {
		return serviceOrderRepository.findAll(new PageRequest(pageNumber, pageSize)).getContent();
	}

	@Override
	public ServiceOrder findById(Long id) {
		return serviceOrderRepository.findOne(id);
	}

	@Override
	public ServiceOrder create(ServiceOrder sorder) {
		if (sorder.getID() != null) {
			return null;
		}
		Vehicle vehicle = sorder.getVehicle();
		vehicle.addServiceOrder(sorder);
		ServiceOrder dbRecord = serviceOrderRepository.save(sorder);
		if (dbRecord != null)
			vehicleService.update(vehicle);
		//
		return dbRecord;
	}

	@Override
	public ServiceOrder update(ServiceOrder sorder) {
		ServiceOrder storedSOrder = serviceOrderRepository.findOne(sorder.getID());
		if (storedSOrder == null) {
			return null;
		}
		storedSOrder.setComment(sorder.getComment());
		storedSOrder.setDescription(sorder.getDescription());
		storedSOrder.setLabourCost(sorder.getLabourCost());
		storedSOrder.setPartCost(sorder.getPartCost());
		// storedSOrder.setSDate(sorder.getSDate());
		storedSOrder.setSStatus(sorder.getSStatus());
		storedSOrder.setOdometer(sorder.getOdometer());

		// List<Work> workList = storedSOrder.getWorkList();
		// workList.addAll(sorder.getWorkList());
		storedSOrder.setWorkList(sorder.getWorkList());
		return serviceOrderRepository.save(storedSOrder);
	}

	@Override
	public void delete(Long id) {
		serviceOrderRepository.delete(id);
	}

	@Override
	public Long getCount() {
		return serviceOrderRepository.count();
	}

	/**
	 * 
	 * @param status
	 * @param bool
	 * @return
	 */
	public List<ServiceOrder> findByOrderStatus(String status, boolean bool) {
		SStatus sstatus = sstatusService.findByStatus(status);
		if (bool)
			return serviceOrderRepository.findByOrderStatus(sstatus);
		else
			return serviceOrderRepository.findByNotOrderStatus(sstatus);
	}

	/**
	 * 
	 * @return
	 */
	public List<ServiceOrder> findByNotCompletedStatus() {
		String status = "COMPLETED";
		SStatus sstatus = sstatusService.findByStatus(status);
		List<ServiceOrder> soList = serviceOrderRepository.findByNotOrderStatus(sstatus);
		List<ServiceOrder> serviceableOrders = soList.stream()
				.filter(s -> !s.getVehicle().getPlate().contains(XMLUtils.DEF_PLATEPREFIX))
				.collect(Collectors.toList());
		return serviceableOrders;
	}

	/**
	 * 
	 * @return
	 */
	public String findNextJobID() {
		List<ServiceOrder> sorders = (List<ServiceOrder>) findAll();
		List<ServiceOrder> jobIDs = sorders.stream().sorted(Comparator.comparing(ServiceOrder::getJobID).reversed())
				.collect(Collectors.toList());
		int value = 1;
		if (!jobIDs.isEmpty()) {
			value = Integer.parseInt(jobIDs.get(0).getJobID());
			value += 1;
		}
		return String.format("%0" + XMLUtils.DEF_JOBIDLENGTH + "d", value);
	}

	/**
	 * 
	 * @param vehicle
	 * @return
	 */
	public List<ServiceOrder> findByVehicle(Vehicle vehicle) {
		return serviceOrderRepository.findByVehicle(vehicle);
	}

	/**
	 * 
	 * @param vehicle
	 * @return
	 */
	public long findVehicleMaxOdometerReading(Vehicle vehicle) {
		long maxValue = 0l;
		List<ServiceOrder> soList = serviceOrderRepository.findByVehicle(vehicle);
		if (soList.isEmpty())
			maxValue = 1000l;
		else
			maxValue = soList.stream().map(ServiceOrder::getOdometer).reduce(Long.MIN_VALUE, Long::max);
		return maxValue;
	}

	/**
	 * 
	 * @param vehicle
	 * @return
	 */
	public ServiceOrder findLastSOrder(Vehicle vehicle) {
		List<ServiceOrder> soList = findByVehicle(vehicle);
		long maxOdometer = 1110l;
		ServiceOrder sorder = null;
		for (ServiceOrder so : soList) {
			if (so.getOdometer() > maxOdometer) {
				maxOdometer = so.getOdometer();
				sorder = so;
			}
		}
		return sorder;
	}

	/**
	 * Service Orders between a certain period
	 * 
	 * @param jobId
	 * @return
	 */
	public ServiceOrder findByJobId(String jobId) {
		return serviceOrderRepository.findByJobId(jobId);
	}

	/**
	 * Service Orders between a certain period
	 * 
	 * @param afterDate
	 * @param beforeDate
	 * @return - List Collections of ServiceOrder objects
	 */
	public List<ServiceOrder> findByDateRange(Date afterDate, Date beforeDate) {
		List<ServiceOrder> sorders0 = serviceOrderRepository.findAll();
		//
		List<ServiceOrder> sorders3 = sorders0.stream()
				.filter(s -> (s.getSDate().after(afterDate) && s.getSDate().before(beforeDate))).distinct()
				.collect(Collectors.toList());
		return sorders3;
	}

	/**
	 * 
	 * @param jobId
	 * @return
	 */
	public List<ServiceOrder> findLikeJobId(String jobId) {
		return serviceOrderRepository.findLikeJobId(jobId);
	}

}
