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
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

/**
 * 微信用户业务层接口
 * @author 成卫雄(qq:330216230)
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2015年9月22日 上午12:39:13<br/>
 * 历史修订：<br/>
 */
public interface IWeixinPeopleBiz  extends IPeopleUserBiz  {
	
	/**
	 * 根据应用Id和微信Id查找微信用户列表（带分页）
	 * @param appId 应用ID
	 * @param weixinId :微信用户对应的微信ID
	 * @param page  :分页参数
	 * @param orderBy :排序依据字段
	 * @param order :排序方式   false:从大到小  true:从小到大
	 * @return 微信用户集合
	 */
	@Deprecated
	List<WeixinPeopleEntity> queryList(int appId,int weixinId,PageUtil page, String orderBy,boolean order);
	
	/**
	 * 根据应用编号和微信Id查询微信用户列表(不带分页)
	 * @param appId 应用ID
	 * @param weixinId 微信ID 
	 * @return 微信用户列表
	 */
	@Deprecated
	List<WeixinPeopleEntity> queryListByAppIdAndWeixinId(int appId,int weixinId);
	
	
	/**
	 * 根据应用Id和微信id查找用户的总数
	 * @param appId 应用ID
	 * @param weixinId 微信ID
	 * @return 微信用户总数
	 */
	@Deprecated
	int queryCount(int appId,int weixinId);
	
	/**
	 * 根据自定义字段查找用户实体
	 * @param weixinPeopleOpenId 用户在微信中的唯一标识
	 * @param weixinPeopleWeixinId 微信编号
	 * @return 用户实体
	 */
	WeixinPeopleEntity getEntityByOpenIdAndWeixinId(String weixinPeopleOpenId,int weixinPeopleWeixinId);
	
	/**
	 * 使用递归抓取持久化用户信息
	 * @param weixin 微信实体
	 * @param openId 用户在微信的唯一标识
	 * @param userNum 一次抓取的数量
	 */
	Boolean recursionImportPeople(WeixinEntity weixin,String openId,int userNum);
	
	/**
	 * 查询用户信息
	 * @param weixinPeopleOpenId 微信用户的唯一标识
	 * @param appId 应用ID
	 * @param weixinId 关联的微信ID
	 * @return 微信用户实体
	 */
	WeixinPeopleEntity getEntityByOpenIdAndAppIdAndWeixinId(String weixinPeopleOpenId,int appId,int weixinId);

	
	/**
	 * 检测微信用户的唯一性</br>
	 * 当该用户存在时返回该用户信息</br>
	 * 当该用户不存在时先持久化该用户信息然后再返回</br>
	 * @param weixinpeople 微信用户
	 * @return 微信用户实体
	 */
	WeixinPeopleEntity checkSoleWeixinPeople(WeixinPeopleEntity weixinPeople);
	
	/**
	 * 通过自增长ID获取用户实体
	 * @param peopleId 微信用户自增长ID
	 * @return 微信用户实体
	 */
	WeixinPeopleEntity getPeopleById(int peopleId);
	
	/**
	 * 根据微信openId查询用户实体
	 * @param openId
	 * @return
	 */
	WeixinPeopleEntity getByOpenId(String openId);
}
