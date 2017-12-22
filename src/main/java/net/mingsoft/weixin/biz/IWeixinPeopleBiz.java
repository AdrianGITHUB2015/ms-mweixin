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
 */package net.mingsoft.weixin.biz;

import java.util.List;
import com.mingsoft.people.biz.IPeopleUserBiz;
import com.mingsoft.util.PageUtil;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * 微信用户业务层接口
 * @author 铭飞开发团队
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-11-18 11:23:59<br/>
 * 历史修订：<br/>
 */
public interface IWeixinPeopleBiz  extends IPeopleUserBiz  {
		
	/**
	 * 查询用户信息
	 * @param weixinPeopleOpenId 微信用户的唯一标识
	 * @param appId 应用ID
	 * @param weixinId 关联的微信ID
	 * @return 微信用户实体
	 */
	WeixinPeopleEntity getEntityByOpenIdAndAppIdAndWeixinId(String weixinPeopleOpenId,int appId,int weixinId);
	/**
	 * 根据user保存或更新用户
	 * @param user 微信工具获取的实体
	 * @param weixinId 当前微信ID。
	 */
	void saveOrUpdate(WxMpUser user,int weixinId);
}
