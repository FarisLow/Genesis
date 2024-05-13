package com.fdmgroup.Genesis;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.fdmgroup.Genesis.repository.ItemRepo;
import com.fdmgroup.Genesis.repository.UserRepo;
import com.fdmgroup.Genesis.repository.CartRepo;
import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.model.Item;
import com.fdmgroup.Genesis.model.ShoppingCart;


@Component
public class Runner implements ApplicationRunner {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ItemRepo itemRepo;
	
	@Autowired
	private CartRepo cartRepo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		//CREATION
//		User user97 = new User("usname", "password123");
//		userRepo.save(user97);
//		ShoppingCart cart = new ShoppingCart();
//		
//		user97.setShoppingCart(cart);
//		cart.setUser(user97);
//		cartRepo.save(cart);

		
		
		
	}

}
