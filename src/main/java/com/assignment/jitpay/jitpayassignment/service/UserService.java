package com.assignment.jitpay.jitpayassignment.service;

import com.assignment.jitpay.jitpayassignment.dto.Location;
import com.assignment.jitpay.jitpayassignment.dto.LocationDto;
import com.assignment.jitpay.jitpayassignment.dto.User;
import com.assignment.jitpay.jitpayassignment.dto.UserLocationDto;
import com.assignment.jitpay.jitpayassignment.entity.LocationEntity;
import com.assignment.jitpay.jitpayassignment.entity.UserEntity;
import com.assignment.jitpay.jitpayassignment.repository.LocationRepository;
import com.assignment.jitpay.jitpayassignment.repository.UserRepository;
import com.assignment.jitpay.jitpayassignment.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    LocationRepository locationRepository;

    public void saveOrUpdate(User user){
        UserEntity userEntity = new UserEntity();
        LocationEntity locationEntity = new LocationEntity();
        Optional<UserEntity> userSearch =  userRepository.findById(user.getUserId());
        if(userSearch.isPresent()){
            userEntity = userSearch.get();
            this.populateUserEntity(user,userEntity);
        }else{
            this.populateUserEntity(user,userEntity);
        }
        this.populateLocationEntity(user,locationEntity);
        userRepository.save(userEntity);
        if(!StringUtils.isNullOrEmptyString(locationEntity.getLatitude()) && !StringUtils.isNullOrEmptyString(locationEntity.getLongitude())) {
            locationRepository.save(locationEntity);
        }
    }


    public void populateUserEntity(User user, UserEntity userEntity){
        if(!StringUtils.isNullOrEmptyString(user.getUserId())){
            userEntity.setUserId(user.getUserId());
        }
        if(!StringUtils.isNullOrEmptyString(user.getFirstName())){
            userEntity.setFirstName(user.getFirstName());
        }
        if(!StringUtils.isNullOrEmptyString(user.getSecondName())){
            userEntity.setSecondName(user.getSecondName());
        }
        if(!StringUtils.isNullOrEmptyString(user.getEmail())){
            userEntity.setEmail(user.getEmail());
        }
        if(!StringUtils.isNull(user.getCreatedOn())){
            userEntity.setCreatedOn(user.getCreatedOn());
        }
    }

    public void populateLocationEntity(User user, LocationEntity locationEntity){
        if(!StringUtils.isNullOrEmptyString(user.getUserId())){
            locationEntity.setUserId(user.getUserId());
        }
        if(!StringUtils.isNull(user.getLocation())){
            if(!StringUtils.isNullOrEmptyString(user.getLocation().getLatitude())){
                locationEntity.setLatitude(user.getLocation().getLatitude());
            }
            if(!StringUtils.isNullOrEmptyString(user.getLocation().getLongitude())){
                locationEntity.setLongitude(user.getLocation().getLongitude());
            }
            if(!StringUtils.isNull(user.getLocation().getCreatedOn())){
                locationEntity.setCreatedOn(user.getLocation().getCreatedOn());
            }else if(!StringUtils.isNull(user.getCreatedOn())){
                locationEntity.setCreatedOn(user.getCreatedOn());
            }
        }
    }

    public User getUserDetailWithLatestLocation(User user){
        User userDto = new User();
        Location locationDto = new Location();
        if(!StringUtils.isNullOrEmptyString(user.getUserId())) {
            Optional<UserEntity> userSearch = userRepository.findById(user.getUserId());
            if (userSearch.isPresent()) {
                this.populateUserDtoFromUserEntity(userDto, userSearch.get());
                List<LocationEntity> locations = locationRepository.findFirstLocationEntityByUserIdOrderByCreatedOnDesc(user.getUserId());
                if (!StringUtils.isNullOrEmptyList(locations)) {
                    this.populateLocationDtoFromLocationEntity(locationDto, (LocationEntity) locations.get(0));
                    userDto.setLocation(locationDto);
                }

            }
        }

        return userDto;
    };

    public UserLocationDto getUserLocationsByDateRange(String userId, Date fromDate,Date toDate){
        UserLocationDto userLocationDto = new UserLocationDto();
        LocationDto locationDto;
        userLocationDto.setUserId(userId);
        List<LocationEntity> locationsList = locationRepository.findLocationEntitiesByUserIdAndCreatedOnBetween(userId,fromDate,toDate);
        if (!StringUtils.isNullOrEmptyList(locationsList)) {
            for (LocationEntity locationEntity: locationsList) {
                locationDto = new LocationDto();
                if(!StringUtils.isNull(locationEntity.getCreatedOn())) {
                    locationDto.setCreatedOn(locationEntity.getCreatedOn().toString());
                }
                locationDto.getLocation().setLatitude(locationEntity.getLatitude());
                locationDto.getLocation().setLongitude(locationEntity.getLongitude());
                userLocationDto.getLocations().add(locationDto);
            }
        }

        return userLocationDto;
    };



    public void populateUserDtoFromUserEntity(User user, UserEntity userEntity){
        BeanUtils.copyProperties(userEntity,user);
    }

    private void populateLocationDtoFromLocationEntity(Location location, LocationEntity locationEntity){
        BeanUtils.copyProperties(locationEntity,location);
    }



}
