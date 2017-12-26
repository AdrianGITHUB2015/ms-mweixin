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
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.dao.IWeixinPeopleDao;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.UserUtils;

/**
 * 微信用户业务层
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2014-10-8<br/>
 * 历史修订：<br/>
 */
@Service("weixinPeopleBiz")
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
	 * 根据应用Id微信Id查找微信用户分页列表
	 * @param appId 应用ID
	 * @param weixinId :微信ID
	 * @param page :页面
	 * @param orderBy :排序依据字段
	 * @param order :排序方式   false:从大到小  true:从小到大
	 * @return 微信用户列表
	 */	
	@Override
	public List<WeixinPeopleEntity> queryList(int appId,int weixinId,PageUtil page,String orderBy, boolean order) {
		// TODO Auto-generated method stub
		return weixinPeopleDao.queryList(appId,weixinId, page.getPageNo(), page.getPageSize(), orderBy, order);
	}

	/**
	 * 根据应用ID和微信id查找用户的总数
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @return 微信用户总数
	 */
	@Override
	public int queryCount(int appId,int weixinId) {
		// TODO Auto-generated method stub
		return weixinPeopleDao.queryCount(appId,weixinId);
	}

	/**
	 * 根据自定义字段查找用户实体
	 * @param weixinPeopleOpenId 微信中的唯一标识
	 * @param weixinPeopleWeixinId 微信用户对应的微信ID
	 * @return 用户实体
	 */
	@Override
	public WeixinPeopleEntity getEntityByOpenIdAndWeixinId(String weixinPeopleOpenId,int weixinPeopleWeixinId) {
		// TODO Auto-generated method stub
		Map<String,Object> whereMap = new HashMap<String,Object> ();
		whereMap.put("PW_OPEN_ID", weixinPeopleOpenId);
		whereMap.put("PW_WEIXIN_ID", weixinPeopleWeixinId);
		return weixinPeopleDao.getEntity(whereMap);
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
		return this.weixinPeopleDao.getWeixinPeopleEntity(null,appId,weixinId, weixinPeopleOpenId);
	}

	/**
	 * 根据用户编号获取用户信息
	 * @param peopleId 用户编号
	 * @return 微信用户实体
	 */
	@Override
	public WeixinPeopleEntity getPeopleById(int peopleId) {
		// TODO Auto-generated method stub
		Map<String,Object> whereMap = new HashMap<String,Object>();
		//查询条件
		whereMap.put("PW_PEOPLE_ID",peopleId);
		Object obj = weixinPeopleDao.getEntity(whereMap);
		if (obj!=null) {
			return (WeixinPeopleEntity)weixinPeopleDao.getEntity(whereMap);
		}
		return null;
	}
	
	/**
	 * 检测微信用户的唯一性</br>
	 * 当该用户存在时返回该用户信息</br>
	 * 当该用户不存在时先持久化该用户信息然后再返回</br>
	 * @param weixinpeople 微信用户
	 * @return 微信用户实体
	 */
	@Override
	public WeixinPeopleEntity checkSoleWeixinPeople(WeixinPeopleEntity weixinPeople){
		//根据用户openId和weixinId查询该用户是否存在
		WeixinPeopleEntity weixinPeopleEntity = this.getEntityByOpenIdAndWeixinId(weixinPeople.getWeixinPeopleOpenId(),weixinPeople.getWeixinPeopleWeixinId());
		//当查询到用户不存在时则执行新增
		if(weixinPeopleEntity == null){
			//新增用户
			this.savePeopleUser(weixinPeople);
			//获取新增用户的用户ＩＤ
			int peopleId = weixinPeople.getPeopleId();
			weixinPeople.setPeopleId(peopleId);//保存用户ID
			weixinPeople.setPuPeopleId(peopleId);//保存people_user表的用户ID
			return weixinPeople;
		}else{
			return weixinPeopleEntity;
		}
	}

	/**
	 * 根据应用ID和微信ID查询用户列表
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @return 微信用户列表
	 */
	@Override
	public List<WeixinPeopleEntity> queryListByAppIdAndWeixinId(int appId,int weixinId) {
		// TODO Auto-generated method stub
		return this.weixinPeopleDao.queryListByAppIdAndWeixinId(appId,weixinId);
	}
	
	/**
	 * 使用递归抓取持久化用户信息
	 * @param weixin 微信实体
	 * @param openId 开始抓取的用户信息的位置
	 * @param userNum 一次抓取的数量
	 */
	@Override
	public Boolean recursionImportPeople(WeixinEntity weixin,String openId,int userNum){
		//若微信不存在
		if(weixin == null || weixin.getWeixinId()<=0 || weixin.getAppId()<=0 || StringUtil.isBlank(weixin.getWeixinAppSecret()) || StringUtil.isBlank(weixin.getWeixinAppId())){
			return false;
		}
		UserUtils userUtils = new UserUtils(weixin.getWeixinAppId(),weixin.getWeixinAppSecret());
		List<Map<String,Object>> listMap = userUtils.queryAllUserInfo(openId,userNum);
		if(listMap == null || listMap.size() == 0){
			return false;
		}		
		//储蓄转化后的用户信息
		List<WeixinPeopleEntity> list = new ArrayList<WeixinPeopleEntity>();	
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> userInfoMap = listMap.get(i);
			LOG.debug(userInfoMap.get("openid").toString()+"||"+userInfoMap.get("nickname").toString()+"||"+userInfoMap.get("sex").toString()+"||"+userInfoMap.get("city").toString()+"||"+userInfoMap.get("province").toString()+"||"+userInfoMap.get("headimgurl").toString());
			WeixinPeopleEntity weixinPeople = new WeixinPeopleEntity();			
			weixinPeople.setWeixinPeopleAppId(weixin.getAppId());//微信用户应用ID
			weixinPeople.setWeixinPeopleWeixinId(weixin.getWeixinId());//微信用户微信ID
			weixinPeople.setWeixinPeopleOpenId(userInfoMap.get("openid").toString());//微信用户OpenId，用户在微信的唯一识别字段
			weixinPeople.setPuSex(Integer.parseInt(userInfoMap.get("sex").toString()));//用户性别
			weixinPeople.setWeixinPeopleCity(userInfoMap.get("city").toString());//微信用户所在城市
			weixinPeople.setWeixinPeopleHeadimgUrl(userInfoMap.get("headimgurl").toString());//微信用户头像
			weixinPeople.setPuNickname(StringUtil.checkStr(userInfoMap.get("nickname").toString()));//用户昵称
			weixinPeople.setWeixinPeopleProvince(userInfoMap.get("province").toString());//微信用户所在省份
			weixinPeople.setWeixinPeopleState(WeixinPeopleEntity.WEIXIN_PEOPLE_WATCH);//微信用户所在市
			weixinPeople.setPeopleAppId(weixin.getAppId());//people表单用户应用ID
			weixinPeople.setPeopleDateTime(new Date());	//用户注册时间
			list.add(weixinPeople);
		}		
		//持久化用户信息
		this.saveOrUpdateBatchWeixinPeople(list);		
		String _openId = listMap.get((listMap.size()-1)).get("openid").toString();//最后一个用户的openId
		recursionImportPeople(weixin,_openId,userNum);	
		return true;
	}
	
	
	/**
	 * 批量持久化用户信息，若数据库中已经存在该用户数据则执行更新操作，若不存在，则执行保存操作
	 * @param list 用户信息集合
	 */
	private void saveOrUpdateBatchWeixinPeople(List<WeixinPeopleEntity> list){
		// TODO Auto-generated method stub
		if(list != null && list.size()>0){
			for(int i=0;i<list.size();i++){
				WeixinPeopleEntity weixinPeople = list.get(i);
				//查询数据库中是否已经存在该用户数据
				WeixinPeopleEntity _weixin = this.getEntityByOpenIdAndAppIdAndWeixinId(weixinPeople.getWeixinPeopleOpenId(),weixinPeople.getWeixinPeopleAppId(),weixinPeople.getWeixinPeopleWeixinId());
				//当不存在该用户信息时则执行新增持久化
				if(_weixin == null){
					this.savePeopleUser(weixinPeople);
				}else{
					//若存在，则执行更新
					weixinPeople.setPeopleId(_weixin.getPeopleId());
					this.updatePeopleUser(weixinPeople);
				}
			}
		}
	}
	
	/**
	 * 根据微信openId查询用户实体
	 */
	@Override
	public WeixinPeopleEntity getByOpenId(String openId) {
		// TODO Auto-generated method stub
		return weixinPeopleDao.getByOpenId(openId);
	}
	

}
