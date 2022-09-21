package com.test.offer.service;

import com.test.offer.dto.UserDTO;
import com.test.offer.dto.UserMapper;
import com.test.offer.exception.CustomException;
import com.test.offer.model.Gender;
import com.test.offer.model.User;
import com.test.offer.repository.UserRepository;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    //@Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        
        User user = new User();
        user.setUsername("Bonisseur de la Bath");
        user.setBirthdate("01/01/1990");
        user.setCountry("French");
        user.setNumber(612345678);
        user.setGender(new ArrayList<>(Arrays.asList(Gender.MALE)));
        userRepository.save(user);
    }

    @Override
    public User loadUserByUsername (String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }
    
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User create(User user) {
        //Permet de checker l'age du user
        Calendar bd = Calendar.getInstance();
        Calendar current = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Permet de vérifier s'il y a 4 chiffre à l'année (sinon toujours > 18 ans..)
        if (user.getBirthdate().length() != 10) {
            throw new CustomException("The date of birth have wrong format", HttpStatus.BAD_REQUEST);
        }
        //Check si le parsing marche correctement (01.01.1990 est un mauvais format)
        try {
            bd.setTime(sdf.parse(user.getBirthdate()));
        } catch (ParseException ex) {
            throw new CustomException("The date of birth have wrong format", HttpStatus.BAD_REQUEST);
        }
        //Calcul de l'âge du user
        int age = current.get(Calendar.YEAR) - bd.get(Calendar.YEAR);
        if (age < 18 ) {
            throw new CustomException("The user have under 18 years", HttpStatus.FORBIDDEN);
        } 
        //Check l'origine du user
        if (!"FRENCH".equals(user.getCountry().toUpperCase())) {
            throw new CustomException("The user is not french", HttpStatus.FORBIDDEN);
        } 
        //Check si l'username est unique (possibilité de get et delete par le username)
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new CustomException("Username already used", HttpStatus.FORBIDDEN);
        }
        return userRepository.save(user);
    }

    public User findById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public void update(UserDTO content) throws NotFoundException {
        //On peut modifier la date de naissance et l'origine
        // /!\ Il faut pouvoir modifier seulement l'username, le numéro ou le genre (pas encore réflechis à une solution)
        User toUpdate = findById(content.getId());
        toUpdate = UserMapper.copy(toUpdate, content); 
        userRepository.save(toUpdate);
    }

    public void delete(Long id) throws NotFoundException{
        userRepository.delete(findById(id));
    }
}
