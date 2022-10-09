package com.assignment.jitpay.jitpayassignment.repository;

import com.assignment.jitpay.jitpayassignment.entity.LocationEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity,String> {

    List<LocationEntity> findFirstLocationEntityByUserIdOrderByCreatedOnDesc( String userId);
    List<LocationEntity> findLocationEntitiesByUserIdAndCreatedOnBetween( String userId,  Date from, Date to);



}
