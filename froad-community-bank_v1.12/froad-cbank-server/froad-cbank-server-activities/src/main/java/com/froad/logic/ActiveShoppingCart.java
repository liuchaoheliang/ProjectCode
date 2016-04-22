package com.froad.logic;

import com.froad.po.ShoppingCartReq;
import com.froad.po.ShoppingCartRes;

public interface ActiveShoppingCart {
	/**
     * 进入购物车
     */
	ShoppingCartRes goShoppingCart(ShoppingCartReq shoppingCartReq);
}
