package com.huohuo.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.huohuo.common.pojo.TaotaoResult;
import com.huohuo.common.utils.HttpClientUtil;
import com.huohuo.mapper.TbContentMapper;
import com.huohuo.pojo.TbContent;
import com.huohuo.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper  contentMapper;
	
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;
	
	@Override
	public TaotaoResult insertContent(TbContent content) {
		
		//补全pojo内容,其他都是表单中的内容
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_SYNC_URL+content.getCategoryId());
		} catch (Exception e) {
			e.printStackTrace();//通知处理异常
		}
		return TaotaoResult.ok();
	}
}
