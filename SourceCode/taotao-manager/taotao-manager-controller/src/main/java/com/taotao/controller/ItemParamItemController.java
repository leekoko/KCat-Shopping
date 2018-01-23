package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.taotao.service.ItemParamItemService;

@Controller
public class ItemParamItemController {

	@Autowired
	private ItemParamItemService itemParamItemService;
	
	@RequestMapping("/items/param/item/{itemId}")
	public String getItemParamItemById(@PathVariable Long itemId, Model model) {
		String itemParamItem = itemParamItemService.getParamItemByItemId(itemId);
		model.addAttribute("param1", itemParamItem);
		return "item-param-show";
		//return "item";
	}
}
