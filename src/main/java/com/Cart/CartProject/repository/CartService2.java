package com.Cart.CartProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.Cart.CartProject.model.CartItem;
import com.Cart.CartProject.model.User;

@Repository
public interface CartService2 extends JpaRepository<CartItem, Integer> {

    // Option 1 - Spring Data naming convention
    int countBy(); 

    // OR Option 2 - Custom query
    @Query("SELECT COUNT(c) FROM CartItem c")
    int getCartItemCount();

    //   List<CartItem> getCartItems();
}