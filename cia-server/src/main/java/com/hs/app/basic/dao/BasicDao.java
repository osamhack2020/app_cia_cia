package com.hs.app.basic.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class BasicDao extends SqlSessionDaoSupport {

	
	public List<Map<String,Object>> getFaqList() {
		return getSqlSession().selectList("basic.getFaqList");
	}
	
}
