package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> getListOfUsers();
    User getUserById(Long id);
    void updateUser(User user, Long id);
    void deleteUser(Long id);
    void saveNewUser(User user);
    User loadUserByUsername(String email);
}
