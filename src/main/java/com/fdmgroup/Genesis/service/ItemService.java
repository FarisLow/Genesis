package com.fdmgroup.Genesis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.fdmgroup.Genesis.model.Item;
import com.fdmgroup.Genesis.repository.ItemRepo;

@Service
public class ItemService {

	@Autowired
	ItemRepo itemRepo;

	public List<Item> retrieveAllItems() {
		return itemRepo.findAll();
	}
	
	public void saveNewItem(Item item) {
		itemRepo.save(item);
	}
	
	public Optional<Item> findItembyName(String name) {
		return itemRepo.findByName(name);
	}
	
	
	
}
