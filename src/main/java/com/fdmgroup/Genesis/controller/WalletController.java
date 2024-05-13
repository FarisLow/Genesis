package com.fdmgroup.Genesis.controller;

import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.service.CartService;
import com.fdmgroup.Genesis.service.UserService;
import com.fdmgroup.Genesis.service.WalletService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WalletController {

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/TopUp")
    public String topUp(Model model, HttpSession session) {
        String username = (String) session.getAttribute("activeUser");
        User user = userService.getUserByUsername(username);
        model.addAttribute("user", user);
        return "TopUp";
    }

    @PostMapping("/topup")
    public String gettopUp() {
        return "redirect:/TopUp";
    }

    @PostMapping("/topupvalue")
    public String topupValue(Model model, HttpSession session, @RequestParam String value) {

        String username = (String) session.getAttribute("activeUser");
        User user = userService.getUserByUsername(username);
        if(walletService.topUp(user, value)){
            return "redirect:/TopUp";
        } else {
            model.addAttribute("topUpError", "Invalid Value! Please Try Again!");
            model.addAttribute("user", user);
            return "redirect:/TopUp";
        }


    }
}
