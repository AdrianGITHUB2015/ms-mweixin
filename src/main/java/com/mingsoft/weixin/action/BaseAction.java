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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.util.MD5Util;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.util.OauthUtils;

/**
 * 铭飞科技 Copyright: Copyright (c) 2014 - 2015
 * 
 * @author 王天培 QQ:78750478 Comments:微信模块基础控制 Create Date:2015-1-21 Modification
 *         history:
 */
public abstract class BaseAction extends com.mingsoft.people.action.BaseAction {

	/**
	 * 读取国际化资源文件 
	 * 
	 * @param key
	 *            ，键值
	 * @return字符串
	 */
	protected String getResString(String key) {
		return com.mingsoft.weixin.constant.Const.RESOURCES.getString(key);
	}

	/**
	 * 设置微信session
	 * 
	 * @param request
	 *            HttpServletRequest 对象
	 * @param weixinSession
	 *            键SessionConst里面定义(session名称)
	 * @param obj
	 *            对象
	 */
	protected void setWeixinSession(HttpServletRequest request, com.mingsoft.weixin.constant.SessionConst weixinSession,
			Object obj) {
		if (StringUtil.isBlank(obj.toString())) {
			return;
		}
		request.getSession().setAttribute(weixinSession.toString(), obj);
	}

	/**
	 * 读取微信session
	 * 
	 * @param request
	 * @param 微信实体信息
	 */
	protected WeixinEntity getWeixinSession(HttpServletRequest request) {
		return (WeixinEntity) request.getSession()
				.getAttribute(com.mingsoft.weixin.constant.SessionConst.WEIXIN_SESSION.toString());
	}

	/**
	 * 组装授权地址
	 * 
	 * @param link授权后跳转地址
	 * @param state扩展参数最长128字节
	 * @param isUserInfo
	 *            true用户详细信息，false:openid详细见微信接口
	 * @param request当前请求对象
	 * @return 返回组织后的请求地址
	 */
	public String builderOauthUrl(String link, String state, boolean isUserInfo, HttpServletRequest request) {
		AppEntity app = this.getApp(request);
		if (link.toLowerCase().indexOf("http://") < 0) {
			link = app.getAppHostUrl() + "/" + link;
		}
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin==null) {
			LOG.error("非微信环境下调用");
			return "";
		}
		String url = OauthUtils.builderOauthUrl(link, state, isUserInfo, weixin.getWeixinAppId());
		LOG.debug("oauthUrl:" + url);
		return url;
	}

	/**
	 * 枚举转list
	 * 
	 * @param <T>
	 * @param cls
	 *            实现了BaseEnum的子类
	 * @return 转换失败返回null
	 */
	protected <T> List<Map<String, Object>> weixinEnumToList(Class<T> cls) {
		List<Map<String, Object>> list = null;
		if (cls != null) {
			list = new ArrayList<Map<String, Object>>();
			try {
				Method method = cls.getDeclaredMethod("values");
				BaseEnum[] be = (BaseEnum[]) method.invoke(cls);
				for (BaseEnum e : be) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", e.toInt());
					map.put("value", e.toString());
					list.add(map);
				}
			} catch (Exception e) {
				return null;
			}

		}
		return list;
	}

}
