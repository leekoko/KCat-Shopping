package com.taotao.portal.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

/**
 * 购物车Service
 * @author liyb
 *
 */
public interface CartService {

	public TaotaoResult aadCartItem(long itemId, int num, 
			HttpServletRequest request, HttpServletResponse response);
	public List<CartItem> getCartItem(HttpServletRequest request, HttpServletResponse response);
	public TaotaoResult deleteCartItem(long itemId,HttpServletRequest request, HttpServletResponse response);
}
