package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getListOfUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    @Transactional
    public void updateUser(User updatedUser, Long id) {
        User userForUpdate = userRepository.findById(id).orElse(null);
        updatedUser.setId(id);
        assert userForUpdate != null;
        if (updatedUser.getRoles().isEmpty()) {
            updatedUser.setRoles(userForUpdate.getRoles());
        }
        if (updatedUser.getPassword().isEmpty()) {
            updatedUser.setPassword(userForUpdate.getPassword());
        } else {
            updatedUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }
        userRepository.save(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void saveNewUser(User user) {
        if (user.getRoles() == null) {
            Set<Role> roleSet = new HashSet<>();
            roleSet.add(new Role(1L, "USER"));
            user.setRoles(roleSet);
        }
       if (userRepository.findUserByEmail(user.getEmail()) == null) {
           user.setPassword(passwordEncoder.encode(user.getPassword()));
           userRepository.save(user);
       }
    }

    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Incorrect username");
        }
        return user;
    }
}
