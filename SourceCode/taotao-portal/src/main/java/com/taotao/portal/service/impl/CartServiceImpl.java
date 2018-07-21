package com.taotao.portal.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;

@Service
public class CartServiceImpl implements CartService {
	
	@Value("REST_BASE_URL")
	private String REST_BASE_URL;
	@Value("ITEM_INFO_URL")
	private String ITEM_INFO_URL;
	
	@Override
	public TaotaoResult aadCartItem(long itemId, int num, 
			HttpServletRequest request, HttpServletResponse response) {
		CartItem cartItem = null;
		//获取Cookie中的信息
		List<CartItem> list = getCartList(request);
		for (CartItem cItem : list) {
			if(cItem.getId() == itemId){
				cItem.setNum(cItem.getNum() + num);
				cartItem = cItem;
				break;
			}
		}
		if(cartItem == null){
			//根据id查询商品信息
			String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
			//把json转化为java对象
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
			if(result.getStatus() == 200){
				TbItem item = (TbItem) result.getData();
				//将不需要的信息进行精简,创建一个新的pojo
				cartItem.setId(itemId);
				cartItem.setImage(item.getImage());
				cartItem.setNum(1);
				cartItem.setPrice(item.getPrice());
				cartItem.setTitle(item.getTitle());
			}
			
		}
		list.add(cartItem);
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(list),true);
		return TaotaoResult.ok();
	}

	private List<CartItem> getCartList(HttpServletRequest request) {
		
		String json = CookieUtils.getCookieValue(request, "TT_CART", true);
		if(StringUtils.isEmpty(json)){
			return new ArrayList<>();
		}
		try {
			List<CartItem> list = JsonUtils.jsonToList(json, CartItem.class);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	@Override
	public List<CartItem> getCartItem(HttpServletRequest request, HttpServletResponse response) {
		return getCartList(request);
	}

	@Override
	public TaotaoResult deleteCartItem(long itemId,HttpServletRequest request, HttpServletResponse response) {
		//从cookie去购物车商品列表
		List<CartItem> itemList = getCartList(request);
		//从列表找到商品
		for (CartItem cartItem : itemList) {
			if(cartItem.getId() == itemId){
				itemList.remove(cartItem);
				break;
			}
		}
		//重新写入Cookie
		CookieUtils.setCookie(request, response, "TT_CART", JsonUtils.objectToJson(itemList),true);
		return TaotaoResult.ok();
	}


}
