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
 */package com.mingsoft.weixin.biz;

import java.util.List;
import com.mingsoft.base.biz.IBaseBiz;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.NewsEntity;

/**
 * 铭飞CMS-铭飞内容管理系统</b>
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 刘继平
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 素材业务层接口，继承IBaseBiz接口
 * Create Date:2014-9-25
 * Modification history:
 */
public interface INewsBiz extends IBaseBiz {
	
	/**
	 * 根据微信素材id得到素材实体
	 * @param newsId 素材编号
	 * @return　素材实体
	 */
	public NewsEntity  getNewsByNewsId(int newsId);
	
	/**
	 * 通过应用编号，微信编号和类型进行自定义查询指定素材列表
	 * @param newsAppId 应用编号
	 * @param newsWeixinId　微信编号
	 * @param page　分页
	 * @param newsType 素材类型
	 * @return 素材列表
	 */
	public List<NewsEntity> queryListNewsByAppIdAndWeixinIdAndType(int newsAppId,int newsWeixinId,int newsType,PageUtil page);
	
	/**
	 * 通过应用ID,微信编号和类型获取指定素材数量
	 * @param newsAppId 应用编号
	 * @param newsWeixinId　微信编号
	 * @param newsType 素材类型
	 * @return 素材数量
	 */
	public int queryCountNewsByAppIdAndWeixinIdAndType(int newsAppId,int newsWeixinId,int newsType);	
	
	/**
	 * 根据应用编号和微信编号查询所有素材列表
	 * @param appId 应用编号
	 * @param weixinId 微信编号
	 * @return 素材列表
	 */
	public List<NewsEntity> query(NewsEntity news);

	/**
	 * 通过应用编号和微信Id获取所有素材总数
	 * @param appId 应用编号
	 * @param weixinId 应用编号
	 * @return 素材数量
	 */
	public int queryCount(int appId,int weixinId);		
	
	/**
	 * 查询图文素材列表
	 * @param appId 应用编号
	 * @param weixinId   微信编号
	 * @param singleNews ：单图文
	 * @param news ：多图文
	 * @param page 分页
	 * @return 图文素材列表
	 */
	public List<NewsEntity> queryListNewsImage(int appId,int weixinId,Integer singleNews,Integer news,PageUtil page);

	/**
	 * 查询图文素材总数
	 * @param appId 应用编号
	 * @param weixinId 素材对应微信ID
	 * @param singleNews 单图文
	 * @param news 多图文
	 * @return 图文素材总数
	 */
	public int getCountNewsImage(int appId,int weixinId,Integer singleNews,Integer news);
	
	/**
	 * 查询素材列表
	 * @param appId 应用ID
	 * @param newsWeixinId 微信ID
	 * @param newsType 素材类型
	 * @param categoryId 栏目ID
	 * @param page 分页
	 * @return 素材列表
	 */
	public List<NewsEntity> queryNewsList(int appId,int newsWeixinId,Integer newsType,Integer categoryId,PageUtil page);
	
	/**
	 * 组织页面数据
	 * @param newsEntity
	 * @return
	 */
	public List<NewsEntity> organizationList(int id);
}
