package com.fdmgroup.Genesis.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;

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
public class CartController {
	
	private static final Logger logger = LogManager.getLogger(CartController.class);

	
	@Autowired
	CartService cartService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemService;
	
	@PostMapping("/showItems")
	public String showItems(@RequestParam("selectedItem") String selectedItem, Model model, HttpSession session) {
	   // Get User From Username and session
	    String username = (String) session.getAttribute("activeUser");
	    Optional<User> optionalUser = Optional.ofNullable(userService.getUserByUsername(username));

	    if (optionalUser.isPresent()) {
	        User user = optionalUser.get();

	        // Get Cart from User
	        ShoppingCart cart = cartService.getCartByCartId((int) user.getUserId());
			if (cart == null) {
				cart = new ShoppingCart();
				System.out.println(user);
				cart.setUser(user);
				user.setShoppingCart(cart);
			}
	        Map<Item, Integer> itemQuantities = cart.getItemQuantities();

	        // Find the item by name
	        Optional<Item> optionalItem = itemService.findItembyName(selectedItem);
	        if (optionalItem.isPresent()) {
	            Item item = optionalItem.get();
	            cart.addItemQuantity(item, 1);
	            List<Map.Entry<Item, Integer>> sortedItemQuantities = new ArrayList<>(itemQuantities.entrySet());
	            sortedItemQuantities.sort(Comparator.comparing(entry -> entry.getKey().getName()));
	            model.addAttribute("itemQuantities", sortedItemQuantities);
	            

	        } else {
	            System.out.println("Item not found: " + selectedItem);
	        }

	        cartService.saveNewCart(cart);
	        user.setShoppingCart(cart);
	        double totalPrice = 0;
	        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
	            Item item = entry.getKey();
	            Integer quantity = entry.getValue();
	            totalPrice += item.getPrice() * quantity;
	        }
	        
	        
	        model.addAttribute("totalPrice", totalPrice);
	        
	        

	        // Don't touch this
	        List<Item> items = itemService.retrieveAllItems();
	        model.addAttribute("items", items);
	    } else {
	        System.out.println("User not found: " + username);
	    }

	    return "item-list";
	}
	
	@PostMapping("/EmptyCart")
	public String emptyCart(HttpSession session) {
	    String username = (String) session.getAttribute("activeUser");
	    User user = userService.getUserByUsername(username);
	    logger.error("Cart will be emptied");
	    
	    if (user != null) {
	        ShoppingCart cart = user.getShoppingCart();
	        if (cart != null) {
	            cartService.emptyCart(cart);
	            // Optional: You can perform additional actions here if needed
	            
	            // Redirect to a success page or any other desired page
	            return "redirect:/items";
	        }
	    }
	    
	    // Redirect to an error page or any other desired page if the cart removal fails
	    return "redirect:/items";
	}
	
	@PostMapping("/payment")
	public String goToPayment(Model model, HttpSession session) {
		
		String username = (String) session.getAttribute("activeUser");
		User user = userService.getUserByUsername(username);

		ShoppingCart cart = cartService.getCartByCartId((int) user.getUserId());
        Map<Item, Integer> itemQuantities = cart.getItemQuantities();
        
		double totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            totalPrice += item.getPrice() * quantity;
        }
        
        session.setAttribute("totalPrice", totalPrice);
		model.addAttribute("totalPrice", totalPrice);
		
		
		model.addAttribute("user", user);
		
		
		return "payment";
	}
	
	@PostMapping("/pay")
	public String toPay(Model model, HttpSession session) {
		
		String username = (String) session.getAttribute("activeUser");
		User user = userService.getUserByUsername(username);

		ShoppingCart cart = cartService.getCartByCartId((int) user.getUserId());
        Map<Item, Integer> itemQuantities = cart.getItemQuantities();
        
		double totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            totalPrice += item.getPrice() * quantity;
        }
        
        session.setAttribute("totalPrice", totalPrice);
		model.addAttribute("totalPrice", totalPrice);
		
		
		model.addAttribute("user", user);
		
		return "redirect:/pay";

	}
	
	
	@GetMapping("/pay")
	public String gotoPayment(Model model, HttpSession session) {
		String username = (String) session.getAttribute("activeUser");
		User user = userService.getUserByUsername(username);
		
		ShoppingCart cart = cartService.getCartByCartId((int) user.getUserId());
        Map<Item, Integer> itemQuantities = cart.getItemQuantities();
        
		double totalPrice = 0;
        for (Map.Entry<Item, Integer> entry : itemQuantities.entrySet()) {
            Item item = entry.getKey();
            Integer quantity = entry.getValue();
            totalPrice += item.getPrice() * quantity;
        }
        
        double wallet = user.getWallet();
        
        //payment
        if (wallet < totalPrice) {
        	model.addAttribute("payError", "Payment Error!  Insufficient Funds!");
    		model.addAttribute("totalPrice", totalPrice);
    		model.addAttribute("user", user);
        	return "payment";
        } else {
            wallet -= totalPrice;
            user.setWallet(wallet);
            cartService.emptyCart(cart);
            userService.saveUser(user);
			if(totalPrice != 0) model.addAttribute("paySuccess", "Payment SUCCESS!");
    		model.addAttribute("totalPrice", 0);
    		model.addAttribute("user", user);
    		
    		return "payment";
        }

	}





}
