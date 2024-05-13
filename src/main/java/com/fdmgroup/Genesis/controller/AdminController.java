package com.fdmgroup.Genesis.controller;

import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.service.CartService;
import com.fdmgroup.Genesis.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    @PostMapping("/registerAdmin")
    public String registerAsAdmin(User user) {

        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getUserId());

        if (userService.registerUser(user)) {
            return "redirect:/users";
        }
        return "redirect:/users";
    }

    @GetMapping("/admin")
    public String Admin() {
        return "admin";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> users = userService.retrieveAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/users/{id}")
    public String getUser(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute(user);
        return "userInfo";
    }

    @PostMapping("/editUser/remove")
    public String removeUser(@RequestParam String remove) {

        //delete the cart
        cartService.deleteCartbyid(remove);

        //delete user
        long userId = Integer.parseInt(remove);
        userService.removeUser(userId);
        return "redirect:/users";

    }

    @PostMapping("/editUser/edit")
    public String editUser(@RequestParam String edit, Model model) {
        long userId = Integer.parseInt(edit);
        User user = userService.getUserById(userId);
        model.addAttribute("users", user);
        return "editUser";
    }

    @PostMapping("/editUser/editdetails")
    public String editUserDetails(@RequestParam String edit, Model model, @RequestParam String username, @RequestParam String password) {
        long userId = Integer.parseInt(edit);
        User user = userService.getUserById(userId);
        user.setUsername(username);
        user.setPassword(password);
        userService.saveUser(user);
//		model.addAttribute("users", user);
        return "redirect:/users";
    }
}
