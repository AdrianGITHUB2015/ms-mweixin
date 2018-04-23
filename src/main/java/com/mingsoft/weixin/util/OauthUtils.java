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
 *//**
 *
 */
package com.mingsoft.weixin.util;

import java.net.URLEncoder;
import java.util.Map;

import com.mingsoft.util.StringUtil;


/**
 * mswx-铭飞微信酒店预订平台
 * Copyright: Copyright (c) 2014 - 2015
 * Company:景德镇铭飞科技有限公司
 * @author 成卫雄
 * @version 300-001-001
 * 版权所有 铭飞科技
 * Comments:网页授权获取用户基本信息
 * 基本信息：
 * 		用户的唯一标识：openid<br/>
 * 		用户昵称：nickname<br/>
 *		用户的性别：sex值为1时是男性，值为2时是女性，值为0时是未知<br/>
 *		用户个人资料填写的省份：province<br/>
 * 		普通用户个人资料填写的城市：city<br/>
 *		国家:country如中国为CN<br/>
 *		用户头像:headimgurl最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空<br/>
 *		用户特权信息:headimgurl，json 数组，如微信沃卡用户为（chinaunicom）<br/>
 * Create Date:2014-3-10
 * Modification history:
 */

public class OauthUtils extends BaseUtils {
	
	
	
	/*
	 *  网页授权说明:
	 *  1.用户同意授权，获取code,让用户访问授权地址，调用静态方法getCodeUrl可生成该地址
	 *  2.通过code换取网页授权access_token,通过调用授权地址可获取返回值code,再通过code可获取access_token
	 *  3.刷新access_token（如果需要）
	 *  4.拉取用户信息(需scope为 snsapi_userinfo)
	 */
	
	/**
	 * 应用授权作用域类型,不弹出授权页面</br>
	 * 此授权只能获取到用的openId</br>
	 */
	public final static String SCOPE_BASE = "snsapi_base";
	
	/**
	 * 应用授权作用域类型,弹出授权页面</br>
	 * 此授权可获取到用户的详细信息</br>
	 */
	public final static String SCOPE_USERINFO = "snsapi_userinfo";
	
	/**
	 * 授权方式的key值
	 */
	public final static String SCOPE = "scope";
	
	/**
	 * 调用微信接口必须要传递微信公众号的开发者凭据信息
	 * @param appid 具体见微信平台的开发模式描述
	 * @param secret 具体见微信平台的开发模式描述
	 */
	public OauthUtils(String appid, String secret) {
		super(appid, secret); 
	}
	
	/**
	 * 微信APP拼装授权地址</br>
	 * 应用授权作用域:1.不弹出授权页面，只能拿到用户的openId</br>
	 * 						 2.弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息</br>
	 * @param appId 公众号的唯一标识
	 * @param redirectUrl 授权后重定向的回调链接地址
	 * @param scope 应用授权作用域 true:不弹出授权页面，只能拿到用户的openId</br>
	 * 											   false:弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息</br>
	 * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值,没有直接传入null
	 * @return 拼装完成的授权地址
	 */
	public static String getCodeUrl(String appId,String redirectUrl,Boolean scope,String state){
		//用urlencode对地址进行处理
		redirectUrl = StringUtil.encodeStringByUTF8(redirectUrl);
		
		//获取code请求地址
		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+redirectUrl+"&response_type=code";
		
		//判断是否弹出授权界面
		if(scope || scope==null){
			url+="&scope="+SCOPE_BASE;
		}else{
			url+="&scope="+SCOPE_USERINFO;
		}
		
		//判断是否有参数传入
		if(StringUtil.isBlank(state)){
			url+="&state=#wechat_redirect";
		}else{
			url+="&state="+state+"#wechat_redirect";
		}
		
		return url;
	}
	
	/**
	 * 获取用户信息</br>
	 * 当为不弹出页面授权时只返回openId</br>
	 * 参考json数据:</br>
	 * 			{</br>
	 *			   "openid":"用户的唯一标识",</br>
 	 *			   "nickname":用户昵称,</br>
	 *			   "sex":"用户的性别，值为1时是男性，值为2时是女性，值为0时是未知",</br>
	 *			   "province":"用户个人资料填写的省份"</br>
	 *			   "city":"普通用户个人资料填写的城市",</br>
	 *			   "country":"国家，如中国为CN",</br>
	 *			    "headimgurl":"户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空", </br>
	 *				"privilege":[用户特权信息，json 数组，如微信沃卡用户为(chinaunicom)]</br>
	 *			}</br>
	 * @return Map: key:openid 		value:用户的唯一标识</br>
	 * 						 key:nickname    value:用户昵称</br>
	 * 						 key:sex 			value:用户的性别，值为1时是男性，值为2时是女性，值为0时是未知</br>
	 * 						 key:province		value:用户个人资料填写的省份</br>
	 * 						 key:city				value:普通用户个人资料填写的城市</br>
	 * 						 key:country		value:国家，如中国为CN</br>
	 * 						 key:headimgurl	value:户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空</br>
	 * 						 key:privilege		value:[用户特权信息，json 数组，如微信沃卡用户为(chinaunicom)]</br>
	 * 						 key:scope			value:授权方式 
	 * 				当获取用户信息失败时:</br>
	 * 						key:errcode value:错误编号</br>
	 * 						key:errmsg  value:错误原因</br>
	 */
	public Map<String,Object> getUser(String code){
		//获取accessToken
		Map<String,Object> accessTokenMap = getAccessToken(code);
		
		if(accessTokenMap == null){
			logger.debug("accessToken获取失败");
			return null;
		}
		
		if(StringUtil.isBlank(accessTokenMap.get(SCOPE))){
			logger.debug("授权方式为空");
			return null;
		}
		
		//判断用户授权类型,如果为snsapi_base则直接返回openId如果为snsapi_userinfo则抓取用户信息
		if(accessTokenMap.get(SCOPE).equals(SCOPE_BASE)){//不弹出页面授权
			accessTokenMap.put(SCOPE, SCOPE_BASE);
			return accessTokenMap;
		}else if(accessTokenMap.get(SCOPE).equals(SCOPE_USERINFO)){//弹出授权界面
			//刷新access_token,由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新
			getRefresh(accessTokenMap.get("refresh_token").toString());
			
			//拉取用户信息(需scope为 snsapi_userinfo)
			Map<String,Object> userInfoMap = getUserInfo(accessTokenMap.get("access_token").toString(),accessTokenMap.get("openid").toString());
			
			userInfoMap.put(SCOPE, SCOPE_USERINFO);
			return userInfoMap;
		}
		
		return null;
	}
	
	

	/**
	 * 通过code换取用户access_token</br>
	 * 正确时返回的JSON数据包如下：</br>
	 * {
     * 		"access_token":"ACCESS_TOKEN",</br>
     * 		"expires_in":7200,</br>
     * 		"refresh_token":"REFRESH_TOKEN",</br>
     * 		"openid":"OPENID",</br>
   	 * 		"scope":"SCOPE"</br>
   	 * }</br>
   	 * 错误时微信会返回JSON数据包如下:
   	 * 				{"errcode":40029,"errmsg":"invalid code"}
   	 * 
	 * @param code 请求授权页面返回的code值
	 * 
 	 * @return map		key:access_token   value:网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同</br>
 	 * 							key:expires_in	    value:access_token接口调用凭证超时时间，单位（秒）</br>
 	 * 							key:refresh_token  value:用户刷新access_token</br>
 	 * 							key:openid				value:用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID</br>
 	 * 							key:scope				value:用户授权的作用域，使用逗号（,）分隔
	 */
	private Map<String,Object> getAccessToken(String code) {
		String urlAccess =  AUTHORIZATION_ACCESS_TOKEN_URL+"?appid="+this.appid+"&secret="+this.secret+"&code="+code+"&grant_type=authorization_code";
		logger.debug("网页授权2.0，获取access_token:"+urlAccess);
		Map<String,Object> map = jsonStrAuthor(urlAccess);
		return map;
	}	
	
	/***
	 * 刷新access_token</br>
	 * 由于access_token拥有较短的有效期,当access_token超时后,可以使用refresh_token进行刷新</br>
	 * refresh_token拥有较长的有效期（7天、30天、60天、90天）, 当refresh_token失效的后，需要用户重新授权。</br>
	 * 
	 * @param refreshToken 填写通过access_token获取到的refresh_token参数
	 * 
 	 * @return map		key:access_token   value:网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同</br>
 	 * 							key:expires_in	    value:access_token接口调用凭证超时时间，单位（秒）</br>
 	 * 							key:refresh_token  value:用户刷新access_token</br>
 	 * 							key:openid				value:用户唯一标识，请注意，在未关注公众号时，用户访问公众号的网页，也会产生一个用户和公众号唯一的OpenID</br>
 	 * 							key:scope				value:用户授权的作用域，使用逗号（,）分隔
	 */
	private Map<String,Object> getRefresh(String refreshToken) {
		if(StringUtil.isBlank(refreshToken)){
			return null;
		}
		String urlRefresh = AUTHORIZATION_REFRESH_TOKEN_URL+"?appid="+this.appid+"&grant_type=refresh_token&refresh_token="+refreshToken;
		return jsonStrAuthor(urlRefresh);
	}	

	
	/**
	 * 通过获取到的accesstoken与openid获取用户的基本信息
	 * @param json 如果是临时获取用户信息，则传入:带有AUTHOR_ACCESS_TOKEN的json数据<br/>
	 *   如果有过期时间的用户数据，则传入 ： 带有RefreshJson对象
	 * @return
	 */
	private Map<String,Object> getUserInfo(String accessToken,String openId) {
		//最终返回用户信息		
		String urlUserInfor = AUTHORIZATION_SCOPE_URL+"?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		return jsonStrAuthor(urlUserInfor);
	}
	
	/**
	 * 组织授权地址
	 * @param link授权后跳转地址
	 * @param state扩展参数最长128字节
	 * @param isUserInfo true用户详细信息，false:openid详细见微信接口
	 * @param appId 微信应用编号
	 * @return　返回组织后的请求地址
	 */
	public static String builderOauthUrl(String link,String state,boolean isUserInfo,String appId) {
		return "https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri="+URLEncoder.encode(link)+"&response_type=code&scope="+(isUserInfo?SCOPE_USERINFO:SCOPE_BASE)+(state==null?"":"&state="+state)+"#wechat_redirect";
	}
	
	
}
