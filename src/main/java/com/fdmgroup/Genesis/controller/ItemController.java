package com.fdmgroup.Genesis.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fdmgroup.Genesis.model.Item;
import com.fdmgroup.Genesis.model.ShoppingCart;
import com.fdmgroup.Genesis.model.User;
import com.fdmgroup.Genesis.service.CartService;
import com.fdmgroup.Genesis.service.ItemService;
import com.fdmgroup.Genesis.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {
	
	private static final Logger logger = LogManager.getLogger(ItemController.class);

	
	@Autowired
	ItemService itemService;
	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/items")
	public String getAllItems(Model model, HttpSession session) {

	    // Get User From Username and session
	    String username = (String) session.getAttribute("activeUser");
	    Optional<User> optionalUser = Optional.ofNullable(userService.getUserByUsername(username));
	    
        double totalPrice = 0;
	    
	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();

	        // Get Cart from User
	        ShoppingCart cart = cartService.getCartByCartId((int) user.getUserId());
            

	        
	        if (cart != null) {
	        	logger.debug("Cart is null");
	        	Map<Item, Integer> itemQuantities = cart.getItemQuantities();
	            List<Map.Entry<Item, Integer>> sortedItemQuantities = new ArrayList<>(itemQuantities.entrySet());
	            sortedItemQuantities.sort(Comparator.comparing(entry -> entry.getKey().getName()));
	            model.addAttribute("itemQuantities", sortedItemQuantities);
	            
	    	    for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
	                Item item = entry.getKey();
	                Integer quantity = entry.getValue();
	                totalPrice += item.getPrice() * quantity;
	                
	                model.addAttribute("totalPrice", totalPrice);
	            }
	        } else {
	            System.out.println("Cart is null");
	            cart = new ShoppingCart();
	            cart.setUser(user);
	            cartService.saveNewCart(cart);
	        }
	    } else {
	        System.out.println("User not found: " + username);
	        // Handle the scenario where user is not found
	    }
	    
	    
        
	   

	    List<Item> items = itemService.retrieveAllItems();
	    model.addAttribute("totalPrice", totalPrice);
	    model.addAttribute("items", items);
	    return "item-list";
	}

	
	@PostMapping("/addItems")
	public String addItems(Item item) {
		itemService.saveNewItem(item);
		return "redirect:/items";
	}
	

	

}
