package com.ppojin.regboard.controller;

import com.ppojin.regboard.advice.exception.CUserNotFoundException;
import com.ppojin.regboard.entity.User;
import com.ppojin.regboard.entity.UserDto;
import com.ppojin.regboard.model.response.ListResult;
import com.ppojin.regboard.model.response.SingleResult;
import com.ppojin.regboard.repo.UserJpaRepo;
import com.ppojin.regboard.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.*;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "v1")
public class UserController {
    @Autowired private UserJpaRepo userJpaRepo;
    @Autowired private ResponseService responseService;

    @ApiOperation(value = "모든 회원 조회")
    @GetMapping(value = "/users")
    public ListResult<User> findAllUser(){
        return responseService.getListResult(userJpaRepo.findAll());
    }

//    @ApiOperation(value = "회원정보 조회")
//    @GetMapping(value = "/user")
//    public List<User> UserInfo(){
//        return userJpaRepo.findAll();
//    }

    @ApiOperation(value = "회원 등록")
    @PostMapping(value = "/user")
    public EntityModel<SingleResult> save(
            @ApiParam(value = "회원 정보", required = true)
            @RequestBody UserDto.Create createUser
    ){
        User user = userJpaRepo.save(
            User.builder()
                .uid(createUser.getUid())
                .name(createUser.getName())
                .build()
        );

        EntityModel<SingleResult> model = new EntityModel<>(responseService.getSingleResult(user));
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAllUser());
        model.add(linkTo.withRel("all-Users"));

        return model;
    }


    @ApiOperation(value = "회원 수정")
    @PutMapping(value = "/user/{userPk}")
    public EntityModel<User> update(
            @ApiParam(value = "회원 정보", required = true) @RequestBody UserDto.Create updateUser,
            @ApiParam(value = "회원 PrimaryKey", required = true) @PathVariable Long userPk
    ){
        userJpaRepo
            .findById(userPk)
            .orElseThrow(CUserNotFoundException::new);

        User user = userJpaRepo.save(
                User.builder()
                        .userFk(userPk)
                        .uid(updateUser.getUid())
                        .name(updateUser.getName())
                        .build()
        );
        EntityModel<User> model = new EntityModel<>(user);
        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAllUser());
        model.add(linkTo.withRel("all-Users"));

        return model;
    }
}