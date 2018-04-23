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
 */package com.mingsoft.weixin.entity;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.weixin.constant.e.WeixinTypeEnum;

/**
 * mswx-铭飞微信酒店预订平台
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 石超
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments: 微信公众帐号基础信息实体类
 * Create Date:2013-12-23
 * Modification history:
 */
public class WeixinEntity extends BaseEntity {
	
	/**
	 * 自增长ID
	 */
	private int weixinId;
	
	/**
	 * 微信公众号所属用户编号
	 */
	private int appId;

	/**
	 * 微信号
	 */
	private String weixinNo;

	/**
	 * 微信原始ID
	 */
	private String weixinOriginId;

	/**
	 * 公众号名称
	 */
	private String weixinName;

	/**
	 * 微信号类型 0:服务号 1:订阅号 3:微信开发平台 4:微信商户平台
	 */
	private int weixinType;

	/**
	 * 应用编号
	 */
	private String weixinAppId;
	
	/**
	 * 应用授权码
	 */
	private String weixinAppSecret;
	
	/**
	 * 微信token
	 */
	private String weixinToken;

	/**
	 * 微信帐号的头像
	 */
	private String weixinHeadImg;

	/**
	 * 微信二维码图片
	 */
	private String weixinImage;
	
	/**
	 * 微信支付key,申请认证是邮件里面有
	 */
	private String weixinPayKey;
	/**
	 * 消息加解密密钥
	 */
	private String weixinAesKey;
	
	/**
	 * 微信支付mchid,申请认证是邮件里面有
	 */
	private String weixinPayMchId;
	
	/**
	 * 映射内网测试网地址，需要将微信的接口地址配置为代理地址才生效
	 */
	private String weixinProxyUrl;
	
	/**
	 * 网页2.0授权跳转地址,需要http
	 */
	private String weixinOauthUrl;


	/**
	 * @return 微信token
	 */
	public String getWeixinToken() {
		return weixinToken;
	}

	/**
	 * @return 微信号类型 0:服务号 1:公众号
	 */
	public int getWeixinType() {
		return weixinType;
	}
	

	public String getWeixinProxyUrl() {
		return weixinProxyUrl;
	}

	public void setWeixinProxyUrl(String weixinProxyUrl) {
		this.weixinProxyUrl = weixinProxyUrl;
	}

	public String getWeixinPayMchId() {
		return weixinPayMchId;
	}

	public void setWeixinPayMchId(String weixinPayMchId) {
		this.weixinPayMchId = weixinPayMchId;
	}

	public String getWeixinPayKey() {
		return weixinPayKey;
	}

	public void setWeixinPayKey(String weixinPayKey) {
		this.weixinPayKey = weixinPayKey;
	}

	public String getWeixinHeadImg() {
		return weixinHeadImg;
	}

	public void setWeixinHeadImg(String weixinHeadImg) {
		this.weixinHeadImg = weixinHeadImg;
	}


	public String getWeixinNo() {
		return weixinNo;
	}

	public void setWeixinNo(String weixinNo) {
		this.weixinNo = weixinNo;
	}

	/**
	 * @param 微信token
	 */
	public void setWeixinToken(String weixinToken) {
		this.weixinToken = weixinToken;
	}
	
	/**
	 * 声明方法过期
	 * @param weixinType
	 */
	@Deprecated
	public void setWeixinType(int weixinType){
		this.weixinType = weixinType;
	}
	
	/**
	 * @param 微信号类型
	 * 0:服务号 1:公众号2:微信开发平台3:微信用户
	 */
	public void setWeixinType(WeixinTypeEnum weixinType) {
		this.weixinType = weixinType.toInt();
	}
	
	
	/**
	 * @return the weixinName
	 */
	public String getWeixinName() {
		return weixinName;
	}

	/**
	 * @param weixinName
	 *            the weixinName to set
	 */
	public void setWeixinName(String weixinName) {
		this.weixinName = weixinName;
	}

	public String getWeixinImage() {
		return weixinImage;
	}

	public void setWeixinImage(String weixinImage) {
		this.weixinImage = weixinImage;
	}

	/**
	 * @return the winxinAppID
	 */
	public String getWeixinAppId() {
		return weixinAppId;
	}

	/**
	 * @param weixinAppID the winxinAppID to set
	 */
	public void setWeixinAppId(String weixinAppId) {
		this.weixinAppId = weixinAppId;
	}

	/**
	 * @return the winxinAppSecret
	 */
	public String getWeixinAppSecret() {
		return weixinAppSecret;
	}

	/**
	 * @param weixinAppSecret the winxinAppSecret to set
	 */
	public void setWeixinAppSecret(String weixinAppSecret) {
		this.weixinAppSecret = weixinAppSecret;
	}

	/**
	 * 获取weixinPeopleId
	 * @return  weixinPeopleId
	 */
	public int getAppId() {
		return appId;
	}

	/**
	 * 设置weixinPeopleId
	 * @param appId
	 */
	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getWeixinId() {
		return weixinId;
	}

	public void setWeixinId(int weixinId) {
		this.weixinId = weixinId;
	}

	public String getWeixinOriginId() {
		return weixinOriginId;
	}

	public void setWeixinOriginId(String weixinOriginId) {
		this.weixinOriginId = weixinOriginId;
	}

	public String getWeixinOauthUrl() {
		return weixinOauthUrl;
	}

	public void setWeixinOauthUrl(String weixinOauthUrl) {
		this.weixinOauthUrl = weixinOauthUrl;
	}

	public String getWeixinAesKey() {
		return weixinAesKey;
	}

	public void setWeixinAesKey(String weixinAesKey) {
		this.weixinAesKey = weixinAesKey;
	}
	
	
}
