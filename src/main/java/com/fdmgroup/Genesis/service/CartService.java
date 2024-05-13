package com.fdmgroup.Genesis.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdmgroup.Genesis.model.Item;
import com.fdmgroup.Genesis.model.ShoppingCart;
import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.repository.CartRepo;
import com.fdmgroup.Genesis.repository.ItemRepo;

@Service
public class CartService {
	@Autowired
	CartRepo cartRepo;
	
	
	public void saveNewCart(ShoppingCart cart) {
		cartRepo.save(cart);
	}
	
	public ShoppingCart getShoppingCart(User user) {
		return user.getShoppingCart();
	}
	
	public ShoppingCart getCartByCartId(int id) {
		return cartRepo.findById(id);
	}

	public void emptyCart(ShoppingCart cart) {
        cart.getItemQuantities().clear(); // Remove all items from the cart
        cartRepo.save(cart); // Save the updated cart to the database
    }
	
	public void deleteCartbyid(String id) {
		cartRepo.deleteById(id);
	}
	
}
