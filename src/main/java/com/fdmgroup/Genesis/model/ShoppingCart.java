package com.fdmgroup.Genesis.model;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapKeyJoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ShoppingCart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ElementCollection
    @CollectionTable(name = "ShoppingCart_Items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyJoinColumn(name = "item_id")
    @Column(name = "quantity")
    private Map<Item, Integer> itemQuantities;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String status = "pending";

    public ShoppingCart() {
        itemQuantities = new HashMap<>();
    }

    public void addItem(Item item) {
        addItemQuantity(item, 1);
        System.out.println("buy item function");
    }

    public void addItemQuantity(Item item, int quantity) {
        int currentQuantity = itemQuantities.getOrDefault(item, 0);
        itemQuantities.put(item, currentQuantity + quantity);
    }

    public void removeItem(Item item) {
        itemQuantities.remove(item);
    }

    public void removeItemQuantity(Item item, int quantity) {
        int currentQuantity = itemQuantities.getOrDefault(item, 0);
        if (currentQuantity <= quantity) {
            itemQuantities.remove(item);
        } else {
            itemQuantities.put(item, currentQuantity - quantity);
        }
    }

    public Map<Item, Integer> getItemQuantities() {
        return itemQuantities;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ShoppingCart [cart_id=" + id + ", itemQuantities=" + itemQuantities + ", user=" + user + ", status=" + status + "]";
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
