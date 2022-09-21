/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test.offer.controller;

import com.test.offer.dto.UserDTO;
import com.test.offer.dto.UserMapper;
import com.test.offer.model.User;
import com.test.offer.response.OfferResponseEntity;
import com.test.offer.service.UserService;
import io.swagger.annotations.ApiOperation;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Returns the list of all users", nickname = "Get all users", response = UserDTO.class)
    public ResponseEntity<List<UserDTO>> getAll() {

        List<User> users = userService.findAll();
        List<UserDTO> usersDto = new ArrayList<>();

        for (User user : users) {
            UserDTO userDto = UserMapper.buildUserDTO(user);
            usersDto.add(userDto);
        }

        return ResponseEntity.ok(usersDto);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return a user", nickname = "Get a user by Id", response = UserDTO.class)
    public ResponseEntity<Object> getById(@PathVariable("id") Long id) {
        try {
            User user = userService.findById(id);
            UserDTO userDTO = UserMapper.buildUserDTO(user);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
        } catch (NotFoundException e) {
            return OfferResponseEntity.build("User not found", 404, "/users/" + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/name/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Return a user", nickname = "Get a user by username", response = UserDTO.class)
    public ResponseEntity<Object> getByUsername(@PathVariable("username") String username) {
        User user = userService.loadUserByUsername(username);
        UserDTO userDTO = UserMapper.buildUserDTO(user);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @ApiOperation(value = "Create user", nickname = "Create user", code = 201)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> create(@RequestBody UserDTO userDTO) {
        
        User toCreate = UserMapper.buildUser(userDTO);
        User created = userService.create(toCreate);
        UserDTO createdDto = UserMapper.buildUserDTO(created);
        return ResponseEntity.created(URI.create("/users")).body(createdDto);

    }

    @ApiOperation(value = "Update user", nickname = "Update user", code = 204)
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody UserDTO userDto) {

        try {
            userDto.setId(id);
            userService.update(userDto);
            return ResponseEntity.status(HttpStatus.OK).body(userDto);
        } catch (NotFoundException e) {
            return OfferResponseEntity.build("User not found", 404, "/users/" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete user", nickname = "Delete user", code = 204)
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException e) {
            return OfferResponseEntity.build("User not found", 404, "/users/" + id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/name/{username}")
    @ApiOperation(value = "Delete user", nickname = "Delete user", code = 204)
    public ResponseEntity<Object> delete(@PathVariable("username") String username) {
        userService.delete(username);
        return ResponseEntity.noContent().build();
    }
}
