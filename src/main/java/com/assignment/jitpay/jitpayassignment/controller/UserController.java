package com.assignment.jitpay.jitpayassignment.controller;


import com.assignment.jitpay.jitpayassignment.dto.User;
import com.assignment.jitpay.jitpayassignment.dto.UserLocationDto;
import com.assignment.jitpay.jitpayassignment.service.UserService;
import com.assignment.jitpay.jitpayassignment.utils.Constants;
import com.assignment.jitpay.jitpayassignment.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Date;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUpdateUserData")
    public ResponseEntity<String> postBody(@RequestBody User user) {


        if (StringUtils.isNull(user.getUserId())) {
            return new ResponseEntity<>(
                    Constants.USER_LOCATION_ADDED_ERROR,
                    HttpStatus.BAD_REQUEST);
        }
        userService.saveOrUpdate(user);
        return new ResponseEntity<>(
                Constants.USER_LOCATION_ADDED_SUCCESS,
                HttpStatus.OK);
    }

    @GetMapping("/userDetail")
    public ResponseEntity<MappingJacksonValue> getUserDetailWithLatestLocation(User user){

        if( StringUtils.isNull(user) || StringUtils.isNullOrEmptyString(user.getUserId()) ){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);  // return Bad request response if the mandatory user id is not provided in request.
        }

        user = userService.getUserDetailWithLatestLocation(user);

        SimpleBeanPropertyFilter simpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept(Constants.CREATE_ON_FIELD);

        SimpleBeanPropertyFilter locationSimpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept( Constants.CREATE_ON_FIELD,Constants.SR_NO_FIELD,Constants.USER_ID_FIELD);

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(Constants.USER_FILTER, simpleBeanPropertyFilter)
                .addFilter(Constants.LOCATION_FILTER,locationSimpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);
        return new ResponseEntity<>(
                mappingJacksonValue ,
                HttpStatus.OK);
    }


    @GetMapping("/userLocationsByDateRange")
    public ResponseEntity<MappingJacksonValue> getUserLocationsByDateRange
            (@RequestParam("userId") String userId,
             @RequestParam("fromDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
             @RequestParam("toDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate){

        if(StringUtils.isNullOrEmptyString(userId) || StringUtils.isNull(fromDate) || StringUtils.isNull(toDate)){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);  // return Bad request response if the mandatory input parameter are not provided completly.
        }

        UserLocationDto userLocationDto = userService.getUserLocationsByDateRange(userId,fromDate,toDate);

        SimpleBeanPropertyFilter locationSimpleBeanPropertyFilter =
                SimpleBeanPropertyFilter.serializeAllExcept( Constants.SR_NO_FIELD,Constants.USER_ID_FIELD,Constants.CREATE_ON_FIELD);  // to exclude fields from response

        FilterProvider filterProvider = new SimpleFilterProvider()
                .addFilter(Constants.LOCATION_FILTER,locationSimpleBeanPropertyFilter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userLocationDto);
        mappingJacksonValue.setFilters(filterProvider);  // convert json based on filterprovider

        return new ResponseEntity<>(mappingJacksonValue,HttpStatus.OK);
    }

}


