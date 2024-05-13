package com.fdmgroup.Genesis.service;

import com.fdmgroup.Genesis.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    private UserService userService;

    public boolean topUp(User user, String value) {
        double walletValue = user.getWallet();

        double topUpValue = Integer.parseInt(value);
        if (topUpValue < 0) {
            return false;
        } else {
            walletValue += topUpValue;
            user.setWallet(walletValue);
            userService.saveUser(user);
            return true;
        }
    }

}
