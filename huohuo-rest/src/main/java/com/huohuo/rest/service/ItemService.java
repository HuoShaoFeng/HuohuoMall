package com.huohuo.rest.service;

import com.huohuo.common.pojo.TaotaoResult;

public interface ItemService {
	TaotaoResult getItemBaseInfo(long itemId);
	TaotaoResult getItemDesc(long itemId);
	TaotaoResult getItemParam(long itemId);
}
