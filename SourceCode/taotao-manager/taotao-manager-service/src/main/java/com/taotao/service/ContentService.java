package com.taotao.service;

import com.taotao.common.pojo.EUDataDridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	TaotaoResult insertContent(TbContent content);
	EUDataDridResult getContentList(int page, int rows, long categoryId);
}
