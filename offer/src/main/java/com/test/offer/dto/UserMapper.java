package com.test.offer.dto;

import com.test.offer.model.User;


public class UserMapper {

    public static UserDTO buildUserDTO(User user) {

        UserDTO userDTO = new UserDTO();
        
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setBirthdate(user.getBirthdate());
        userDTO.setCountry(user.getCountry());
        userDTO.setNumber(user.getNumber());
        userDTO.setGender(user.getGender());
        return userDTO;
    }

    public static User buildUser(UserDTO userDTO){
        
        User user = new User();
        
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        user.setCountry(userDTO.getCountry());
        user.setNumber(userDTO.getNumber());
        user.setGender(userDTO.getGender());

        return user;
    }

    public static User copy(User user, UserDTO content){

        if (content.getUsername()!= null) {
            user.setUsername(content.getUsername());
        }
        
        if (content.getBirthdate()!= null) {
            user.setBirthdate(content.getBirthdate());
        }
        
        if (content.getCountry()!= null) {
            user.setCountry(content.getCountry());
        }
        
        if (content.getNumber()!= null) {
            user.setNumber(content.getNumber());
        }
        
        if (content.getGender()!= null) {
            user.setGender(content.getGender());
        }

        return user;
    }
}
