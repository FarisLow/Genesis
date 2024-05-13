package com.fdmgroup.Genesis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.Genesis.model.Item;
import com.fdmgroup.Genesis.model.ShoppingCart;
import com.fdmgroup.Genesis.model.User;


@Repository
public interface CartRepo extends JpaRepository<ShoppingCart, String>{

	ShoppingCart findById(int id);

	

	
	
	
}
