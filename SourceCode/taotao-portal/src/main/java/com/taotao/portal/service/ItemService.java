package com.taotao.portal.service;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.Item;
import com.taotao.portal.pojo.ItemInfo;

public interface ItemService {
	
	ItemInfo getItemById(Long itemId);
}
