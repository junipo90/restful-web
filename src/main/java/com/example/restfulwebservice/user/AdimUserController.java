package com.example.restfulwebservice.user;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdimUserController {
    private UserDaoService userDaoService;

    public AdimUserController(UserDaoService userDaoService) {
        this.userDaoService = userDaoService;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers(){

        List<User> users = userDaoService.findAll();

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "ssn");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(users);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

//    @GetMapping("v1/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=1")
//    @GetMapping(value = "/users/{id}/", headers = "X-API-VERSION=1")
    @GetMapping(value = "/users/{id}/", produces = "application/vnd.company.appv1+json")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("userId", "userName", "password");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfo", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(user);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

//    @GetMapping("v2/users/{id}")
//    @GetMapping(value = "/users/{id}/", params = "version=2")
//    @GetMapping(value = "/users/{id}/", headers = "X-API-VERSION=2")
    @GetMapping(value = "/users/{id}/", produces = "application/vnd.company.appv2+json")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = userDaoService.findOne(id);
        if (user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        UserV2 userV2 = new UserV2();
        BeanUtils.copyProperties(user,userV2);
        userV2.setGrade("TestGrade");

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "password", "grade");

        FilterProvider filterProvider = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userV2);
        mappingJacksonValue.setFilters(filterProvider);

        return mappingJacksonValue;
    }

}
