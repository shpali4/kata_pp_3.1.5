package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAdminPage(Model model) {
        model.addAttribute("userList", userService.listOfUsers());
        model.addAttribute("roleUser", roleService.getRoleByName("ROLE_USER"));
        model.addAttribute("roleAdmin", roleService.getRoleByName("ROLE_ADMIN"));
        return "adminPage";
    }

    @GetMapping("/{id}")
    public String showUserInfoPage(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "userPage";
    }

    @GetMapping("/{id}/edit")
    public String showEditUserPage(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("roleUser", roleService.getRoleByName("ROLE_USER"));
        model.addAttribute("roleAdmin", roleService.getRoleByName("ROLE_ADMIN"));
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/createUser")
    public String getCreateUserPage(Model model) {
        model.addAttribute("user", new User());
        return "createUser";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveNewUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
