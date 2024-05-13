package com.fdmgroup.Genesis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fdmgroup.Genesis.model.Item;


@Repository
public interface ItemRepo extends JpaRepository<Item, Double>{
	
	Optional<Item> findByName(String name);
	
}
