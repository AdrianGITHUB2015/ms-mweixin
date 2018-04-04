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
 */package com.mingsoft.weixin.biz.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.basic.dao.IAppDao;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.cms.dao.IArticleDao;
import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.dao.INewsDao;
import com.mingsoft.weixin.entity.NewsEntity;

import cn.hutool.core.util.ObjectUtil;

/**
 * 
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 刘继平
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 素材业务层实现类，继承BaseBiz，实现INewsBiz接口
 * Create Date:2014-9-25
 * Modification history:
 */
@Service("newsBiz")
public class NewsBizImpl extends BaseBizImpl implements INewsBiz {

	/**
	 * 素材持久化层注入
	 */
	@Autowired
	private INewsDao newsDao;

	/**
	 * 注入文章持久层
	 */
	@Autowired
	private IArticleDao articleDao;
	
	/**
	 * 注入应用持久层
	 */
	@Autowired
	private IAppDao appDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return newsDao;
	}	

	/**
	 * 根据微信素材id得到微信发送被动响应消息bean列表
	 * @param newsId　素材ID
	 * @return 素材实体
	 */
	@Override
	public NewsEntity getNewsByNewsId(int newsId) {
		// 获取微信素材实体
		NewsEntity newsEntity = newsDao.getNewsByNewsId(newsId);
		//获取应用实体
		if(ObjectUtil.isNull(newsEntity)){
			return null;
		}
		AppEntity app = (AppEntity)appDao.getEntity(newsEntity.getNewsAppId());
		//保存应用
		newsEntity.setApp(app);
		//若微信素材主图文ID大于0
		return this.setNewsAction(newsEntity);
	}
	
	/**
	 * 通过素材应用Id和类型获取素材列表</br>
	 * 根据自增长ID排序</br>
	 * 分页查询</br>
	 * @param newsAppId 素材对应应用编号
	 * @param newsWeixinId　微信编号
	 * @param page　分页
	 * @param newsType 素材类型
	 * @return 素材集合
	 */
	@Override
	public List<NewsEntity> queryListNewsByAppIdAndWeixinIdAndType(int newsAppId,int newsWeixinId,int newsType,PageUtil page){
		//查询条件
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("news_app_id", newsAppId);
		whereMap.put("news_weixin_id", newsWeixinId);
		whereMap.put("news_type", newsType);
		//排序条件
		Map<String,Boolean> orderMap = new HashMap<String,Boolean>();
		orderMap.put("news_id", true);
		return newsDao.queryNewsListByCustom(whereMap, orderMap, page);
	}
	
	/**
	 * 通过微信ID和素材类型获取素材数量
	 * @param newsWeixinId　素材对应微信ID
	 * @param newsType 素材类型
	 * @return 素材数量
	 */
	@Override
	public int queryCountNewsByAppIdAndWeixinIdAndType(int newsAppId,int newsWeixinId,int newsType){
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//查询条件
		whereMap.put("news_app_id", newsAppId);
		whereMap.put("news_weixin_id", newsWeixinId);
		whereMap.put("news_type", newsType);			
		return newsDao.queryCountNewsByCustom(whereMap);
	}

	/**
	 * 通过应用编号获取图文素材总数
	 * @param newsWeixinId 微信编号
	 * @return 素材数量
	 */
	@Override
	public int queryCount(int appId,int weixinId){
		return newsDao.queryCount(appId,weixinId);
	}	

	/**
	 * 根据素材应用ID查询素材</br>
	 * 根据自增长ID排序</br>
	 * @param newsWeixinId 素材微信ID
	 * @return 素材列表
	 */
	@Override
	public List<NewsEntity> query(NewsEntity news){
		List<NewsEntity> newsList = newsDao.query(news);
		//重新组装图文
		if(newsList != null){
			return setNewsAction(newsList);
		}
		return null;
	}
	
	
	/**
	 * 图文素材列表
	 * @param appId 应用编号
	 * @param newsWeixinId 素材对应的微信ID
	 * @param singleNews 单图文
	 * @param news 多图文
	 * @param page 分页参数
	 * @return 图文素材列表
	 */
	@Override
	public List<NewsEntity> queryListNewsImage(int appId,int weixinId,Integer singleNews, Integer news, PageUtil page) {
		//查询素材列表
		List<NewsEntity> newsList = newsDao.queryListNewsImage(appId,weixinId,singleNews, news, page);
		if(newsList != null){
			return setNewsAction(newsList);
		}
		return null;
	}
	

	/**
	 * 图文素材总数
	 * @param newsWeixinId 素材对应的微信编号
	 * @param singleNews 单图文
	 * @param news 多图文
	 * @return 图文素材总数
	 */
	@Override
	public int getCountNewsImage(int appId,int weixinId,Integer singleNews,Integer news) {
		// TODO Auto-generated method stub
		return this.newsDao.getCountNewsImage(appId,weixinId,singleNews,news);
	}
	
	/**
	 * 查询素材集合
	 * @param appId 应用编号
	 * @param newsWeixinId 素材对应微信Id 
	 * @param newsType 素材类型
	 * @param categoryId 栏目ID
	 * @param page 分页对象
	 * @return 素材集合
	 */
	@Override
	public List<NewsEntity> queryNewsList(int appId,int newsWeixinId, Integer newsType, Integer categoryId, PageUtil page) {
		// TODO Auto-generated method stub
		List<NewsEntity> newsList = newsDao.queryNewsList(appId,newsWeixinId, categoryId, newsType, page);
		if(newsList != null){
			return setNewsAction(newsList);
		}
		return null;
	}
	
	
	/**
	 * 依据传入的素材信息</br>
	 * 填充素材中文章列表,一般为微信发送图文消息时的主图文和主图文</br>
	 * @param newsEntity 需要转换的素材信息
	 * @return 转换后的素材信息
	 */
	private NewsEntity setNewsAction(NewsEntity newsEntity){
		//重新组装图文
		if(newsEntity != null){
			//根据当前素材的主图文ID查询该文章
			ArticleEntity masterArticle = (ArticleEntity) articleDao.getEntity(newsEntity.getNewsMasterArticleId());
			//保存当前素材实体主图文
			newsEntity.setNewsMasterArticle(masterArticle);
			//判断当前素材是否存在子图文
			if(StringUtil.isBlank(newsEntity.getNewsChildArticleIds())){
				return newsEntity;
			}				
			//声明子图文变量，文章编号集合，以逗号隔开
			String articleIds = newsEntity.getNewsChildArticleIds();
			//将子图文用","分割得到文章编号Integer数组
			Integer[] articleIdsList = StringUtil.stringsToIntegers(articleIds.split(","));
			//得到该数组的集合
			List<Integer> list = Arrays.asList(articleIdsList);
			if(list != null && list.size()>0){
				//根据字图文ID查询子图文列表
				List<ArticleEntity> newsChildArticleIdsList = articleDao.queryListByArticleIds(list);
				//保存素材子图文
				newsEntity.setChilds(newsChildArticleIdsList);
			}
			return newsEntity;
		}else{
			return null;
		}
	}
	
	/**
	 * 依据传入的素材信息</br>
	 * 填充素材中文章列表,一般为微信发送图文消息时的主图文和主图文</br>
	 * @param newsList 需要转换的素材信息集合
	 * @return 转换后的素材信息
	 */
	private List<NewsEntity> setNewsAction(List<NewsEntity> newsList){
		//重新组装图文
		if(newsList != null){
			List<NewsEntity> list = new ArrayList<NewsEntity>();
			for(int i=0;i<newsList.size();i++){
				NewsEntity newsEntity = newsList.get(i);
				list.add(this.setNewsAction(newsEntity));
			}
			return list;
		}else{
			return null;
		}
	}

	@Override
	public List<NewsEntity> organizationList(int id) {
		// TODO Auto-generated method stub
		NewsEntity news = this.getNewsByNewsId(id);
		if(ObjectUtil.isNull(news)){
			return null;
		}else{
			List<NewsEntity> newsList = new ArrayList<NewsEntity>();
			newsList.add(news);
			return setNewsAction(newsList);
		}
	}	
	
}
