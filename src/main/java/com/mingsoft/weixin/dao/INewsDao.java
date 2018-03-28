/**
The MIT License (MIT) * Copyright (c) 2017 铭飞科技

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */package com.mingsoft.weixin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.NewsEntity;

/**
 * 铭飞CMS-铭飞内容管理系统
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 刘继平
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 微信素材持久化接口，继承IBaseDao
 * Create Date:2014-9-25
 * Modification history:
 */
public interface INewsDao extends IBaseDao {

	/**
	 * 自定义字段查询素材集合</br>
	 * 分页查询</br>
	 * @param whereMap 查询条件</br>
	 * key:需要查询字段在数据库中的名称</br>
	 * value:值</br>
	 * @param orderMap 排序条件不需要时传入null即可</br>
	 * key：排序字段名称</br>
	 * value: true(desc) false(asc)</br>
	 * @param page 分页条件,传入null值时不进行分页
	 * @return 素材集合
	 */
	public List<NewsEntity> queryNewsListByCustom(@Param("whereMap")Map<String,Object> whereMap,@Param("orderMap")Map<String,Boolean> orderMap,@Param("page")PageUtil page);

	/**
	 * 自定义字段查询素材总数</br>
	 * 结合分页使用</br>
	 * @param whereMap 查询条件  key:需要查询字段在数据库中的名称 value:值</br>
	 * @return 素材总数
	 */
	public int queryCountNewsByCustom(@Param("whereMap")Map<String,Object> whereMap);	
	
	/**
	 * 根据微信素材编号id查找素材实体
	 * @param newsId 素材ID
	 * @return 素材实体
	 */
	public NewsEntity getNewsByNewsId(@Param("newsId") int newsId);	

	/**
	 * 通过应用编号和微信ID获取素材总数
	 * @param appId 应用编号
	 * @param weixinId 微信ID
	 * @return 素材数量
	 */
	public int queryCount(@Param("appId")int appId,@Param("weixinId") int weixinId);
	
	/**
	 * 查询图文素材列表
	 * @param appId 应用编号
	 * @param weixinId 微信ID
	 * @param singleNews 单图文
	 * @param news 多图文
	 * @param page 分页参数
	 * @return 图文素材列表
	 */
	public List<NewsEntity> queryListNewsImage(@Param("appId")int appId,@Param("weixinId")int weixinId,@Param("singleNews")Integer singleNews,@Param("news")Integer news,@Param("page")PageUtil page);
	
	/**
	 * 查询图文素材总数
	 * @param appId 应用编号
	 * @param weixinId 微信ID
	 * @param singleNews 单图文
	 * @param news 多图文
	 * @return 图文素材总数
	 */
	public int getCountNewsImage(@Param("appId")int appId,@Param("weixinId")int weixinId,@Param("singleNews")Integer singleNews,@Param("news")Integer news);
	
	/**
	 * 查询图文列表
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @param categoryId 栏目ID
	 * @param newsType 素材类型
	 * @param page 分页参数
	 * @return 图文素材列表
	 */
	public List queryNewsList(@Param("appId")int appId,@Param("weixinId")int weixinId,@Param("categoryId")Integer categoryId,@Param("newsType")Integer newsType,@Param("page")PageUtil page);
}
