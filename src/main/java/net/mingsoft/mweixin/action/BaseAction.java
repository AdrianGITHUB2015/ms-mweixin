package net.mingsoft.mweixin.action;

import java.util.MissingResourceException;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.mingsoft.base.constant.Const;
import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.entity.SessionEntity;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.util.OauthUtils;

import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.util.SpringUtil;
import net.mingsoft.weixin.service.WeixinService;

/**
 * mweixin基础控制层
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
public class BaseAction extends com.mingsoft.basic.action.BaseAction{

	@Override
	protected String getResString(String key) {
		// TODO Auto-generated method stub
		String str = "";
		try {
			str = super.getResString(key);
		} catch (MissingResourceException e) {
			str = net.mingsoft.mweixin.constant.Const.RESOURCES.getString(key);
		}

		return str;
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
	 * 构建微信的服务工具，
	 * @param weixinNo 微信号
	 * @return WeixinService
	 */
	protected WeixinService builderWeixinService(String weixinNo) {
		IWeixinBiz weixinBiz = SpringUtil.getBean(IWeixinBiz.class);
		WeixinEntity weixin = weixinBiz.getByWeixinNo(weixinNo);
		return new WeixinService(weixin);
	}
}
