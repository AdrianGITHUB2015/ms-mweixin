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
 */package net.mingsoft.weixin.action;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.basic.constant.e.CookieConstEnum;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.action.BaseAction;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.weixin.service.WeixinService;

/**
 * 微信基用户控制层
 * Copyright: Copyright (c) 2014 - 2015
 * @author 成卫雄   QQ:330216230
 * Comments:微信基用户控制层
 * Create Date:2014-10-5
 * Modification history:
 */
@Controller("netWeixinPeopleAction")
@RequestMapping("/${managerPath}/net/weixin/weixinPeople")
public class WeixinPeopleAction extends BaseAction{
	
	/**
	 * 注入微信用户业务层
	 */
	@Autowired
	private IWeixinPeopleBiz weixinPeopleBiz;
	@Autowired
	private IWeixinBiz weixinBiz;
	
	private WeixinService wxService;
	
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
		//进行同步
		this.outJson(response, getWeixinPeople(weixin,null));
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
	/**
	 * 同步用户数据，如果已经存在，那么进行更新，否则进行保存
	 * @param weixin 微信实体
	 * @param nextOpenId 下一个用户的微信唯一编号（表示从下一个用户再次获取数据）
	 * @return
	 */
	private boolean getWeixinPeople(WeixinEntity weixin,String nextOpenId){
		//拿到微信工具类服务
		wxService = new WeixinService(weixin);
		//获取用户数据
		try {
			WxMpUserList wxUsers = wxService.getUserService().userList("");
			//储蓄转化后的用户信息
			List<String> openIds = wxUsers.getOpenids();
			for(String openid : openIds){
				//通过openId拿到用户信息
				WxMpUser user = wxService.getUserService().userInfo(openid,"zh_CN");
				//保存用户
				WeixinPeopleEntity weixinPeople = new WeixinPeopleEntity();		
				weixinPeople.setWeixinPeopleAppId(weixin.getAppId());//微信用户应用ID
				weixinPeople.setWeixinPeopleWeixinId(weixin.getWeixinId());//微信用户微信ID
				weixinPeople.setWeixinPeopleOpenId(user.getOpenId());//微信用户OpenId，用户在微信的唯一识别字段
				weixinPeople.setPuSex(user.getSexId());//用户性别
				weixinPeople.setWeixinPeopleCity(user.getCity());//微信用户所在城市
				weixinPeople.setWeixinPeopleHeadimgUrl(user.getHeadImgUrl());//微信用户头像
				 if (user.getNickname() != null && user.getNickname().length() > 0) {
					 weixinPeople.setPuNickname(user.getNickname().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));//用户昵称;
				}
				weixinPeople.setWeixinPeopleProvince(user.getProvince());//微信用户所在省份
				weixinPeople.setWeixinPeopleState(WeixinPeopleEntity.WEIXIN_PEOPLE_WATCH);//微信用户所在市
				weixinPeople.setPeopleAppId(weixin.getAppId());//people表单用户应用ID
				weixinPeople.setPeopleDateTime(new Date(user.getSubscribeTime()));	//用户注册时间
				
				//查询数据库中是否已经存在该用户数据
				WeixinPeopleEntity _weixin = weixinPeopleBiz.getEntityByOpenIdAndAppIdAndWeixinId(user.getOpenId(),weixin.getAppId(),weixin.getWeixinId());
				//当不存在该用户信息时则执行新增
				if(_weixin == null){
					weixinPeopleBiz.savePeopleUser(weixinPeople);
				}else{
					//若存在，则执行更新
					weixinPeople.setPeopleId(_weixin.getPeopleId());
					weixinPeopleBiz.updatePeopleUser(weixinPeople);
				}
			}
			/*if(wxUsers.getNextOpenid() != null){
				getWeixinPeople(weixin, wxUsers.getNextOpenid());
			}*/
			
		} catch (WxErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
}
