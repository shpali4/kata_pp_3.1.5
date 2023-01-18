package ru.kata.spring.boot_security.demo.init;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class Init {

    private final RoleRepository roleRepository;
    private final UserService userService;

    public Init(RoleRepository roleRepository, UserService userService) {
        this.roleRepository = roleRepository;
        this.userService = userService;
    }

    @PostConstruct
    public void init() {
        Role roleAdmin = new Role("ROLE_ADMIN");
        roleRepository.save(roleAdmin);
        Role roleUser = new Role("ROLE_USER");
        roleRepository.save(roleUser);

        User user = new User(25, "admin", "Ivan", "Ivanov", "ivan@gmail.com");
        user.getRoles().add(roleAdmin);

        User admin = new User(40, "user",  "Petr", "Petrov", "petr@mail.ru");
        admin.getRoles().add(roleUser);

        userService.saveNewUser(admin);
        userService.saveNewUser(user);
    }
}
