package com.taotao.sso.service;

import com.taotao.common.pojo.TaotaoResult;

public interface UserService {
	
	TaotaoResult checkData(String content, Integer type);

}
