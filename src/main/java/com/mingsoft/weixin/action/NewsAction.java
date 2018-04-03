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
 */package com.mingsoft.weixin.action;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.biz.IColumnBiz;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.basic.entity.ColumnEntity;
import com.mingsoft.cms.biz.IArticleBiz;
import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.constant.ModelCode;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.WeixinEntity;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * @ClassName:  NewsAction   
 * @Description:TODO(微信素材控制层)   
 * @author: 铭飞开发团队
 * @date:   2018年4月3日 上午9:29:10   
 *     
 * @Copyright: 2018 www.mingsoft.net Inc. All rights reserved.
 */
@Controller
@RequestMapping("/${managerPath}/weixin/news")
public class NewsAction extends BaseAction {

	
	/**
	 * 素材业务层注入
	 */
	@Autowired
	private INewsBiz newsBiz;
	
	
	/**
	 * 文章业务层注入
	 */
	@Autowired
	private IArticleBiz articleBiz;

	/**
	 * 文章分类业务层
	 */
	@Autowired
	private IColumnBiz columnBiz;	
	
	
	/**
	 * 图文素材列表
	 * @param request
	 * @param response
	 * @return 图文素材列表界面
	 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,HttpServletResponse response,ModelMap model) {
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		NewsEntity news = new NewsEntity();
		news.setNewsAppId(BasicUtil.getAppId());
		news.setNewsWeixinId(weixin.getWeixinId());
		//查询图文素材
		List<NewsEntity> newsList = newsBiz.query(news);
		//压入url地址
		this.setCookie(request, response, CookieConstEnum.BACK_COOKIE,"list.do");
		model.addAttribute("newsList", newsList);
		return Const.VIEW+"/weixin/news/news_list";
	}
	
	/**
	 * 图文素材列表
	 * @param request
	 * @param model 
	 * @return 图文素材列表界面
	 */
	@RequestMapping("/listAjax")
	public String listAjax(HttpServletRequest request,ModelMap model) {
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取应用Id
		int appId = this.getAppId(request);
		//当前页码
		int pageNo = this.getPageNo(request);
		//图文素材总数
		int recordCount = newsBiz.getCountNewsImage(appId,weixinId, NewsTypeEnum.SINGLE_NEWS.toInt(),NewsTypeEnum.NEWS.toInt());
		//分页通用
		PageUtil page = new PageUtil(pageNo,recordCount, null);
		//查询图文素材
		List<NewsEntity> newsList = newsBiz.queryListNewsImage(appId,weixinId,NewsTypeEnum.SINGLE_NEWS.toInt(),NewsTypeEnum.NEWS.toInt(), page);
		model.addAttribute("page",page);
		model.addAttribute("newsList", newsList);
		return Const.VIEW+"/weixin/news/news_list_ajax";
	}	
	
	/**
	 * 添加素材页面
	 * @param newsType 素材类型
	 * @param mode
	 * @param request 
	 * @return 返回news_form.ftl
	 */
	@RequestMapping("{newsType}/add")
	public String add(@PathVariable String newsType,HttpServletRequest request,ModelMap mode){
		//获取应用ID	
		//根据应用ID查询文章分类
		List<ColumnEntity> listColumn = columnBiz.queryColumnListByWebsiteId(BasicUtil.getAppId());		
		mode.addAttribute("newsType", Integer.parseInt(newsType));
		mode.addAttribute("listColumn",JSONObject.toJSONString(listColumn));		
		return view("/weixin/news/news_form");
	}

	/**
	 * 文章列表的ajax的请求</br>
	 * 当category为0或者不存在时默认查询所有</br>
	 * @param request
	 * @param response
	 */
	@RequestMapping("/articleJson")
	@ResponseBody
	@Deprecated
	public void articleJson(HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		//获取栏目ID
		String categoryIdStr = request.getParameter("categoryId");
		//获取appId
		int appId = this.getAppId(request);
		//当前页面 
		int pageNo = this.getPageNo(request);	
		//判断分类ID是否为空，若为空根据appId查询列表
		if(!StringUtil.isInteger(categoryIdStr)){
			//查询数据表中记录集合总数
			int count =articleBiz.getCountByWebsiteId(appId);			
			//分页通用
			PageUtil page=new PageUtil(pageNo,count);		
			//根据appId查询文章列表
			List<ArticleEntity> articleList = articleBiz.queryPageListByWebsiteId(appId,page,"article_basicid",false);	
			this.outJson(response, ModelCode.WEIXIN_NEWS,true,Integer.toString(count),JSONObject.toJSONString(articleList));
			return ;
		}
		//若栏目ID不为空，则根据栏目ID查询文章列表
		int categoryIdInt = Integer.valueOf(categoryIdStr); //栏目ID
		//该栏目下文章的总数
		int count = articleBiz.countByCategoryId(categoryIdInt);
		//分页通用
		PageUtil page=new PageUtil(pageNo,count);	
		//根据栏目ID appId 和page查询文章列表
		List<BaseEntity> articleList = articleBiz.queryPageByCategoryId(categoryIdInt,appId,page,false);
		this.outJson(response,ModelCode.WEIXIN_NEWS,true,Integer.toString(count),JSONObject.toJSONString(articleList));
		return ;
	}
	
	
	/**
	 * 保存素材
	 * @param news 素材实体
	 * @param request
	 * @param response
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(@ModelAttribute NewsEntity news,HttpServletRequest request,HttpServletResponse response){
		if(news == null){
			this.outJson(response, null, false);
			return;
		}
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或者微信相关数据不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_OAUTH, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取应用编号
		int appId = this.getAppId(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		news.setNewsAppId(appId);//应用ID
		news.setNewsWeixinId(weixinId);//微信ID
		news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//保存时间
		//持久化新增素材
		newsBiz.saveEntity(news);
		this.outJson(response, ModelCode.WEIXIN_NEWS, true,null);
	}	
	
	
	/**
	 * 编辑素材页面
	 * @param request
	 * @param mode
	 * @param newsId 素材id
	 * @return 素材编辑页面or素材列表页
	 */
	@RequestMapping("/{newsId}/edit")
	public String edit(@PathVariable int newsId,HttpServletRequest request,ModelMap mode){
		//获取相应素材实体
		NewsEntity news = (NewsEntity)newsBiz.getNewsByNewsId(newsId);
		//如果该素材不存在，则返回素材列表
		if(news == null){
			//非法
			return this.redirectBack(request,true);
		}
		//获取应用编号
		int appId = this.getAppId(request);
		//根据应用ID查询文章分类
		List<ColumnEntity> listColumn = columnBiz.queryColumnListByWebsiteId(appId);
		mode.addAttribute("listColumn",JSONObject.toJSONString(listColumn));
		mode.addAttribute("news",news);
		mode.addAttribute("newsType", news.getNewsType());
		return Const.VIEW+"/weixin/news/news_form";
	}
	
	/**
	 * 根据素材Id获取素材实体
	 * @param response
	 * @param mode
	 * @param newsId 素材id
	 */
	@RequestMapping("/{newsId}/get")
	@ResponseBody
	public void get(@PathVariable int newsId,HttpServletResponse response,ModelMap mode){
		if(newsId<=0){
			this.outJson(response, null, false);
			return;
		}
		//根据素材ID获取相应素材
		NewsEntity news = (NewsEntity)newsBiz.getNewsByNewsId(newsId);
		//如果该素材不存在
		if(news == null){
			this.outJson(response, null, false);
			return;
		}
		this.outJson(response, JSONObject.toJSONStringWithDateFormat(news,"YYYY-MM-dd"));
	}
	
	/**
	 * 更新素材
	 * @param news 素材实体
	 * @param request
	 * @param response
	 * @param newsId 素材ID
	 */
	@RequestMapping("/{newsId}/update")
	@ResponseBody
	public void update(@ModelAttribute NewsEntity news,@PathVariable int newsId,HttpServletRequest request,HttpServletResponse response){
		if(news == null || newsId<=0){
			this.outJson(response, null, false);
			return;
		}
		//根据素材ID查找相应素材实体
		NewsEntity newsEntity = this.newsBiz.getNewsByNewsId(newsId);
		if(newsEntity == null){
			this.outJson(response, null, false);
			return;
		}
		int appId = this.getAppId(request);
		news.setNewsAppId(appId);//应用ID
		news.setNewsWeixinId(newsEntity.getNewsWeixinId());//微信ID
		news.setNewsId(newsId);//素材ID
		news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//更新时间	
		//更新素材
		newsBiz.updateEntity(news);
		//读取更新后的跳转地址
		String url = this.getCookie(request, CookieConstEnum.BACK_COOKIE);
		this.outJson(response,ModelCode.WEIXIN_NEWS, true,null,url);
	}	
	
	/**
	 * 根据素材Id删除指定素材
	 * @param newsId 素材ID
	 * @param request
	 * @param response
	 * @param newsId 素材ID
	 */
	@RequestMapping("/{newsId}/delete")
	@ResponseBody
	public void delete(@PathVariable int newsId,HttpServletRequest request,HttpServletResponse response){
		if(newsId<=0){
			this.outJson(response, null, false);
			return;
		}
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或者微信相关数据不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_OAUTH, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信Id
		int weixinId = weixin.getWeixinId(); 
		//获取appId
		int appId = this.getAppId(request);
//		//由于被动回复要调用素材，且素材与被动回复属于多对一的关系，若该关系存在，则素材不可直接删除，需先删除被哦的那个回复
//		List<PassiveMessageEntity> passiveMessageList =  this.passiveMessageBiz.queryListByNewsIdAndWeixinId(newsId, weixin.getWeixinId());
//		//若该素材关联被动回复
//		if(passiveMessageList.size()!= 0){
//			this.outJson(response, ModelCode.WEIXIN_NEWS, false,null);
//			return;
//		}
//		//获取newsId所对应的菜单列表中是否有实体存在与该newsId相等的menuUul
//		List<MenuEntity> menuList = this.menuBiz.queryListByMenuUrl(String.valueOf(newsId),appId,weixinId);
//		//若该素材关联菜单
//		if(menuList.size() != 0){
//			this.outJson(response, ModelCode.WEIXIN_NEWS, false,null);
//			return;
//		}
		//删除指定素材
		newsBiz.deleteEntity(newsId);
		this.outJson(response, ModelCode.WEIXIN_NEWS, true,null);
	}	
	
	/**
	 * 文本素材列表
	 * @param request
	 * @param response
	 * @param mode
	 * @return 文本素材列表页面
	 */
	@RequestMapping("/textList")
	public String textList(HttpServletRequest request,HttpServletResponse response,ModelMap mode){
		//获取当前页码
		int pageNo = this.getPageNo(request);
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取应用编号
		int appId = this.getAppId(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//查询记录总数
		int recordCount=newsBiz.queryCountNewsByAppIdAndWeixinIdAndType(appId,weixinId,NewsTypeEnum.TEXT.toInt());
		//保存cookie值
		this.setCookie(request, response, CookieConstEnum.PAGENO_COOKIE, String.valueOf(pageNo));
		//查询所有的消息信息
		PageUtil page=new PageUtil(pageNo,recordCount,getUrl(request)+"/manager/weixin/news/textList.do");
		//根绝微信ID，分页对象，素材类型查询列表
		List<NewsEntity> listNews = newsBiz.queryListNewsByAppIdAndWeixinIdAndType(appId,weixinId, NewsTypeEnum.TEXT.toInt(),page);
		mode.addAttribute("listNews", listNews);
		mode.addAttribute("page",page);
		return view("/weixin/news/news_text_list");		
	}
	
	/**
	 * 新增文本素材
	 * @param news 素材实体
	 * @param response
	 * @param request
	 */
	@RequestMapping("/textSave")
	@ResponseBody
	public void textSave(@ModelAttribute NewsEntity news,HttpServletResponse response,HttpServletRequest request){
		if(news == null){
			this.outJson(response, null, false);
			return;
		}
		///取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信或者微信相关数据不存在
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, ModelCode.WEIXIN_OAUTH, false, this.getResString("weixin.not.found"));
			return;
		}
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取应用ID
		int appId = this.getAppId(request);	
		news.setNewsDateTime(new Timestamp(System.currentTimeMillis()));//设置消息发布时间
		news.setNewsAppId(appId);//设置appid
		news.setNewsWeixinId(weixinId);//设置微信ID
		//保存素材实体
		newsBiz.saveEntity(news);
		this.outJson(response, ModelCode.WEIXIN_NEWS_TEXT, true,JSONObject.toJSONString(news));		
	}	
	
	
	/**
	 * 获取单图文和多图文列表
	 * @param request
	 * @param model
	 * @return 图文素材选择页面
	 */
	@RequestMapping("/listSelect")
	public String listSelect(HttpServletRequest request,ModelMap model){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//获取微信ID
		int weixinId = weixin.getWeixinId();
		//获取应用ID 
		int appId = this.getAppId(request);
		//当前页码
		int pageNo = this.getPageNo(request);
		//图文素材总数
		int count = newsBiz.getCountNewsImage(appId,weixinId,NewsTypeEnum.SINGLE_NEWS.toInt(),NewsTypeEnum.NEWS.toInt());		
		//分页通用
		PageUtil page=new PageUtil(pageNo,count,getUrl(request)+"/manager/weixin/news/listSelect.do");
		//查询图文素材
		List<NewsEntity> newsList = newsBiz.queryListNewsImage(appId,weixinId,NewsTypeEnum.SINGLE_NEWS.toInt(),NewsTypeEnum.NEWS.toInt(), page);
		model.addAttribute("page",page);
		model.addAttribute("newsList", newsList);
		model.addAttribute("count", count);
		return view("/weixin/news/news_list_select");		
	}
}
