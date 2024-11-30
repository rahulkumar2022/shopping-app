package com.learn.ShopperStop.repository;

import com.learn.ShopperStop.model.CartItem;
import com.learn.ShopperStop.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
