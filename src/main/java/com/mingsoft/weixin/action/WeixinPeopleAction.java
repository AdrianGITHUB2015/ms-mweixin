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
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;

/**
 * 微信基用户控制层
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:微信基用户控制层
 * Create Date:2014-10-5
 * Modification history:
 */
@Controller
@RequestMapping("/${managerPath}/weixin/weixinPeople")
public class WeixinPeopleAction extends BaseAction{
	
	/**
	 * 注入微信用户业务层
	 */
	@Autowired
	private IWeixinPeopleBiz weixinPeopleBiz;
	
	private static final int PEOPLE_NUM = 200;
	
	/**
	 * 微信用户管理主界面
	 * @param request
	 * @param response 
	 * @return 页面
	 */
	@RequestMapping("/index")
	public String index(HttpServletRequest request, HttpServletResponse response){
		return view("/weixin/people/index");
	}
	
	/**
	 * 分页查询所有的微信用户信息
	 * @param request
	 * @param mode
	 * @param response
	 * @return 微信用户列表
	 */
	@SuppressWarnings("static-access")
	@RequestMapping("/list")
	@ResponseBody
	public void list(WeixinPeopleEntity weixinPeople, HttpServletRequest request,ModelMap mode, HttpServletResponse response){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		weixinPeople.setWeixinPeopleAppId(BasicUtil.getAppId());
		weixinPeople.setWeixinPeopleWeixinId(weixin.getWeixinId());
		//分页开始
		BasicUtil.startPage();
		//分页查询
		List listPeople = weixinPeopleBiz.query(weixinPeople);
		EUListBean _list = new EUListBean(listPeople,(int) BasicUtil.endPage(listPeople).getTotal());
		this.outJson(response, JSONArray.toJSONString(_list));
		
	}
	/**
	 * 导入微信所有用户
	 * @param request
	 */
	@RequestMapping("/importPeople")
	@ResponseBody
	public void importPeople(HttpServletRequest request,HttpServletResponse response){
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		//调用递归方法,openId为""表示从第一个用户开始
		Boolean bool = weixinPeopleBiz.recursionImportPeople(weixin,"",PEOPLE_NUM);
		this.outJson(response, null, bool);
	}
	
	/**
	 * 根据用户ID获取用户实体
	 * @param peopleId 用户编号
	 * @param response
	 */
	@RequestMapping("/{peopleId}/getPeopleById")
	@ResponseBody
	public void getPeopleById(@PathVariable int peopleId,HttpServletResponse response){
		if(peopleId<=0){
			this.outJson(response, null, false);
			return;
		}
		//根据用户编号查询用户实体
		WeixinPeopleEntity people = weixinPeopleBiz.getPeopleById(peopleId);
		if(people == null){
			this.outJson(response, null, false);
			return;
		}
		//返回json的格式用户实体信息
		this.outJson(response,null, true ,JSONObject.toJSONString(people));
	}
	
	
}
