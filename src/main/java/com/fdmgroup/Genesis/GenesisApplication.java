package com.fdmgroup.Genesis;

import com.fdmgroup.Genesis.model.Item;
import com.fdmgroup.Genesis.repository.ItemRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GenesisApplication {

	public static void main(String[] args) {
		SpringApplication.run(GenesisApplication.class, args);
	}
	@Bean
	CommandLineRunner run(ItemRepo itemRepo){
		return args -> {
			itemRepo.save(new Item("Pillows", 15.00));
			itemRepo.save(new Item("Bedsheet", 20.00));
			itemRepo.save(new Item("Floor Mat", 5.00));
		};

	}
	
	
}
