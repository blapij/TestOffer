package com.test.offer.dto;

import com.test.offer.model.Gender;
import java.util.List;

public class UserDTO {
    private Long id;
    private String username;
    private String birthdate;
    private String country;
    private Integer number;
    private List<Gender> gender;

    public UserDTO() {
    }

    public UserDTO(String username, String birthdate, String country) {
        this.username = username;
        this.birthdate = birthdate;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<Gender> getGender() {
        return gender;
    }

    public void setGender(List<Gender> gender) {
        this.gender = gender;
    }

}
