package com.learn.ShopperStop.service.cart;

import com.learn.ShopperStop.model.Cart;

import java.math.BigDecimal;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);
}
