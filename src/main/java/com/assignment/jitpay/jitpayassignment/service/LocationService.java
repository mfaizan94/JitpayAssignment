package com.assignment.jitpay.jitpayassignment.service;

import com.assignment.jitpay.jitpayassignment.entity.LocationEntity;
import com.assignment.jitpay.jitpayassignment.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    public LocationRepository locationRepo;




    public LocationEntity saveOrUpdate(LocationEntity location){
        return locationRepo.save(location);
    }

    public List<LocationEntity> findAll(){
        return (List<LocationEntity>) locationRepo.findAll();
    }


}
