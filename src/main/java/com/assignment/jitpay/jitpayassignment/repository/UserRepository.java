package com.assignment.jitpay.jitpayassignment.repository;

import com.assignment.jitpay.jitpayassignment.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity,String> {

   // @Query("select u from UserEntity u join Location l on l.user_id=u.user_id order by  ")


}
