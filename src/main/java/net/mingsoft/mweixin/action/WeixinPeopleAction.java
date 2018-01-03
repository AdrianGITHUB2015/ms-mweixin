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
 */package net.mingsoft.mweixin.action;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
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
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.biz.IWeixinPeopleBiz;
import net.mingsoft.weixin.service.WeixinService;

/**
 * 微信基用户控制层
 * @author 铭飞开发团队
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-11-18 11:23:59<br/>
 * 历史修订：<br/>
 */
@Controller("netWeixinPeopleAction")
@RequestMapping("/${managerPath}/mweixin/weixinPeople")
public class WeixinPeopleAction extends BaseAction{
	
	/**
	 * 注入微信用户业务层
	 */
	@Resource(name="netWeixinPeopleBiz")
	private IWeixinPeopleBiz weixinPeopleBiz;
	
	@Resource(name="weixinService")
	private WeixinService weixinService;
	
	
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
		//进行同步
		this.outJson(response, getWeixinPeople(weixin,null));
	}
	
	/**
	 * 同步用户数据，如果已经存在，那么进行更新，否则进行保存
	 * @param weixin 微信实体
	 * @param nextOpenId 下一个用户的微信唯一编号（表示从下一个用户再次获取数据）
	 * @return
	 */
	private boolean getWeixinPeople(WeixinEntity weixin,String nextOpenId){
		//拿到微信工具类服务
		weixinService = weixinService.build(weixin);
		//获取用户数据
		try {
			WxMpUserList wxUsers = weixinService.getUserService().userList(nextOpenId);
			//储蓄转化后的用户信息
			List<String> openIds = wxUsers.getOpenids();
			for(String openid : openIds){
				//通过openId拿到用户信息
				WxMpUser user = weixinService.getUserService().userInfo(openid,"zh_CN");
				weixinPeopleBiz.saveOrUpdate(user,weixin.getWeixinId());
			}
			//如果没有下一个，那么返回成功信息
			if(wxUsers.getNextOpenid().length()>0){
				getWeixinPeople(weixin, wxUsers.getNextOpenid());
				return true;
			}
			
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
