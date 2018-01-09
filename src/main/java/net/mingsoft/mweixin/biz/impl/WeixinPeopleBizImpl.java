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
 */package net.mingsoft.mweixin.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.people.biz.impl.PeopleUserBizImpl;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.dao.IWeixinPeopleDao;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.UserUtils;

import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.biz.IWeixinPeopleBiz;

/**
 * 微信用户业务层
 * @author 铭飞开发团队
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-11-18 11:23:59<br/>
 * 历史修订：<br/>
 */
@Service("netWeixinPeopleBiz")
public class WeixinPeopleBizImpl  extends PeopleUserBizImpl implements IWeixinPeopleBiz{
	/**
	 * 注入微信用户持久化层
	 */
	@Autowired
	private IWeixinPeopleDao weixinPeopleDao;
	
	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return this.weixinPeopleDao;
	}
	
	/**
	 * 查询用户信息
	 * @param weixinPeopleOpenId 微信中对用户的唯一标识
	 * @param appId 应用Id
	 * @param weixinId 关联微信的ID
	 * @return 微信用户实体
	 */
	@Override
	public WeixinPeopleEntity getEntityByOpenIdAndAppIdAndWeixinId(String weixinPeopleOpenId,int appId,int weixinId){
		return weixinPeopleDao.getWeixinPeopleEntity(null,appId,weixinId, weixinPeopleOpenId);
	}

	@Override
	public void saveOrUpdate(WxMpUser user,int weixinId) {
		//保存用户
		WeixinPeopleEntity weixinPeople = new WeixinPeopleEntity();		
		weixinPeople.setWeixinPeopleAppId(BasicUtil.getAppId());//微信用户应用ID
		weixinPeople.setWeixinPeopleWeixinId(weixinId);//微信用户微信ID
		weixinPeople.setWeixinPeopleOpenId(user.getOpenId());//微信用户OpenId，用户在微信的唯一识别字段
		weixinPeople.setPuSex(user.getSexId());//用户性别
		weixinPeople.setWeixinPeopleCity(user.getCity());//微信用户所在城市
		weixinPeople.setWeixinPeopleHeadimgUrl(user.getHeadImgUrl());//微信用户头像
		 if (user.getNickname() != null && user.getNickname().length() > 0) {
			 weixinPeople.setPuNickname(user.getNickname().replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", ""));//用户昵称;
		}
		weixinPeople.setWeixinPeopleProvince(user.getProvince());//微信用户所在省份
		weixinPeople.setWeixinPeopleState(WeixinPeopleEntity.WEIXIN_PEOPLE_WATCH);//微信用户所在市
		weixinPeople.setPeopleAppId(BasicUtil.getAppId());//people表单用户应用ID
		weixinPeople.setPuIcon(user.getHeadImgUrl());
		weixinPeople.setPeopleDateTime(new Date());	//用户注册时间
		//查询数据库中是否已经存在该用户数据
		WeixinPeopleEntity _weixin = weixinPeopleDao.getWeixinPeopleEntity(null,BasicUtil.getAppId(),weixinId, user.getOpenId());
		//当不存在该用户信息时则执行新增
		if(_weixin == null){
			this.savePeopleUser(weixinPeople);
		}else{
			//若存在，则执行更新
			weixinPeople.setPeopleId(_weixin.getPeopleId());
			this.updatePeopleUser(weixinPeople);
		}
	}

}
