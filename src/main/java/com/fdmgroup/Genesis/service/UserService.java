package com.fdmgroup.Genesis.service;

import java.util.List;
import java.util.Optional;

import com.fdmgroup.Genesis.model.ShoppingCart;
import com.fdmgroup.Genesis.repository.CartRepo;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CartRepo cartRepo;

	public boolean registerUser(User user){
		Boolean isRegistered = registerNewUser(user);
		//Create new Cart for User
		if (isRegistered) {
            ShoppingCart cart = new ShoppingCart();
            user.setShoppingCart(cart);
            cart.setUser(user);
            cartRepo.save(cart);
        }
        return isRegistered;
	}
	public boolean verifyUserCredentials(String username, String password) {
		Optional<User> userOptional = userRepo.findByUsername(username);
		
		if(userOptional.isPresent() && userOptional.get().getPassword().equals(password)) {
			return true;
		} else {
				return false;
		}
		
	}
	
	public boolean registerNewUser(User user) {
		Optional<User> optionalUser = userRepo.findByUsername(user.getUsername());
		
		if(optionalUser.isEmpty()) {
			userRepo.save(user);
			return true;
		} else {
			return false;
		}
	}
	
	public User getUserById(long id) {
		return userRepo.findById(id).get();
	}
	
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username).get();
	}

	public List<User> retrieveAllUsers() {
		return userRepo.findAll();
	}
	
	public void saveUser(User user) {
		userRepo.save(user);
	}
	
	public void removeUser(long id) {
		userRepo.deleteById(id);
	}
}
