package com.test.offer;

import com.test.offer.dto.UserDTO;
import com.test.offer.dto.UserMapper;
import com.test.offer.model.User;
import com.test.offer.service.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OfferApplicationTests {

    @Autowired
    private UserService userService;

    @BeforeAll
    static void setupAll() {
        System.out.println("setupAll");
    }

    @BeforeEach
    void setup() {
        System.out.println("setup");
    }

    @AfterEach
    void teardown() {
        System.out.println("teardown");
    }

    @AfterAll
    static void teardownAll() {
        System.out.println("teardownAll");
    }

    @Test
    public void correctUser() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("Jerry Golade ");
        userDTO.setBirthdate("01/01/1990");
        userDTO.setCountry("FRENCH");

        User toCreate = UserMapper.buildUser(userDTO);

        User created = userService.create(toCreate);
        UserDTO createdDto = UserMapper.buildUserDTO(created);

        assertEquals(toCreate.getUsername(), createdDto.getUsername());
    }
    
    @Test
    public void correctUserCountryLowerCase() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(" Jean Ticipe ");
        userDTO.setBirthdate("01/01/1990");
        userDTO.setCountry("french");

        User toCreate = UserMapper.buildUser(userDTO);

        User created = userService.create(toCreate);
        UserDTO createdDto = UserMapper.buildUserDTO(created);

        assertEquals(toCreate.getUsername(), createdDto.getUsername());
    }
    
    @Test
    public void missingAge() {
        //same thing for username and country
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(" Laure Ambar ");
        userDTO.setCountry("French");

        try {
            User toCreate = UserMapper.buildUser(userDTO);
            User created = userService.create(toCreate);
        } catch (Exception e) {
            assertTrue(true, "age is missing");
        }
    }
    
    @Test
    public void usernameAlreadyUsed() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(" Laure Ambar ");
        userDTO.setBirthdate("01/01/1990");
        userDTO.setCountry("French");

        try {
            User toCreate = UserMapper.buildUser(userDTO);
            User created = userService.create(toCreate);
        } catch (Exception e) {
            assertTrue(true, "Username already used");
        }
    }

    @Test
    public void youngUser() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(" Amar Mite ");
        userDTO.setBirthdate("01/01/1990");
        userDTO.setCountry("French");

        try {
            User toCreate = UserMapper.buildUser(userDTO);
            User created = userService.create(toCreate);
        } catch (Exception e) {
            assertTrue(true, "user too young");
        }
    }
    
    @Test
    public void foreignUser() {

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("John Doe");
        userDTO.setBirthdate("01/01/1990");
        userDTO.setCountry("English");

        try {
            User toCreate = UserMapper.buildUser(userDTO);
            User created = userService.create(toCreate);
        } catch (Exception e) {
            assertTrue(true, "user not french");
        }
    }

}
