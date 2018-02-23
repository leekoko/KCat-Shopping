package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

public interface ContentCategoryService {
	List<EUTreeNode> getCategoryList(long parentId);
	

}
