
package com.test.offer.service;

import com.test.offer.model.User;

public interface IUserService{
    
    User loadUserByUsername(String username);
    
}
