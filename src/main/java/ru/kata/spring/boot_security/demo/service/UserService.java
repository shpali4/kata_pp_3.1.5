package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> listOfUsers();
    User getUserById(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
    void saveNewUser(User user);
    //User getUserByName(String username);
    UserDetails loadUserByUsername(String username);
}
