package ru.kata.spring.boot_security.demo.services;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> listOfUsers();
    User showUserById(Long id);
    void updateUser(User user);
    void deleteUser(Long id);
    void saveNewUser(User user);
    User getUserByName(String username);
}
