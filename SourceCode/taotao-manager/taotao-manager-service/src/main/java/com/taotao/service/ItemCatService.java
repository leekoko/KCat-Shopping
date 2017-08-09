package com.taotao.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;

public interface ItemCatService {
	List<EUTreeNode> getCatList(long parentId);
}
