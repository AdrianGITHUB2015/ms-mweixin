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
 */package com.mingsoft.weixin.action.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mingsoft.base.constant.Const;
import com.mingsoft.people.biz.IPeopleBiz;
import com.mingsoft.people.entity.PeopleEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.action.BaseAction;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.constant.SessionConst;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.WeixinUtil;
import com.mingsoft.weixin.util.bean.WeixinPeopleEntityUtils;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;

/**
 * 微信对外接口
 * @author Administrator
 *
 */
@Controller("webWeixinAction")
@RequestMapping("/mweixin/weixin")
public class WeixinAction extends BaseAction{
	
	/**
	 * 微信业务层
	 */
	@Autowired
	private IWeixinBiz weixinBiz;
	
	/**
	 * 微信js config获取方式
	 * @param appId 微信 appid
	 * @param ticketType 微信 tickettype 参考微信对应接口，默认jsapi
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/config",method=RequestMethod.GET)
	@ResponseBody
	public void config(HttpServletRequest request,HttpServletResponse response,RedirectAttributes model){
		//获取微信ID
		String appId = request.getParameter("appId");
		String url = request.getParameter("url");
		
		String ticketType = BasicUtil.getString("ticketType", "jsapi");
		
		
		if(StringUtil.isBlank(appId)) {
			model.addAttribute(Const.ERROR, "没有指定微信编号(weixinId)");
			this.outJson(response, false,"没有指定微信应用编号(appId)");
			return;
		}
		
		
		//查询微信详细信息
		WeixinEntity temp = new WeixinEntity();
		temp.setWeixinAppId(appId);
		WeixinEntity weixinEntity = (WeixinEntity)weixinBiz.getEntity(temp);
		if(weixinEntity == null){
			//返回错误地址
			this.outJson(response, false,"没有对应appid的微信记录");
			return;
		} 
		this.outJson(response, WeixinUtil.getSignatureConfig(weixinEntity.getWeixinAppId(), weixinEntity.getWeixinAppSecret(),ticketType,url));
	}
	
}
