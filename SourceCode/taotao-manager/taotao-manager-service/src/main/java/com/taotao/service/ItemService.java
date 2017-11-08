package com.taotao.service;

import org.springframework.stereotype.Service;

import com.taotao.pojo.TbItem;
public interface ItemService {
	TbItem getItemById(long itemId);  
	
	
}
