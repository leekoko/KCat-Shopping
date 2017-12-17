package com.taotao.service;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUDataDridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
public interface ItemService {
	TbItem getItemById(long itemId); 
	EUDataDridResult getItemList(int page,int rows);
	TaotaoResult createItem(TbItem item);
}
