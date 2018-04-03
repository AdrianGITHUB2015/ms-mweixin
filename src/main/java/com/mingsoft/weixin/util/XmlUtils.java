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
 */package com.mingsoft.weixin.util;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSONArray;
import com.mingsoft.basic.entity.AppEntity;
import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.parser.IParserRegexConstant;
import com.mingsoft.util.MD5Util;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.NewsBean;
import com.mingsoft.weixin.bean.UploadNewsBean;
import com.mingsoft.weixin.entity.NewsEntity;



/**
 * 
 * @ClassName:  XmlUtils   
 * @Description:TODO(微信接口消息(XML)通过处理方法，只是对一些字符串的处理，str2xml,xml2str)   
 * @author: 铭飞开发团队
 * @date:   2018年4月3日 上午9:34:35   
 *     
 * @Copyright: 2018 www.mingsoft.net Inc. All rights reserved.
 */
public class XmlUtils {
	
	/**
	 * list数组长度不正确
	 */
	private static String NEWS_ERR = "items size err";
	
	/**
	 * 多客服
	 */
	public static final String DUO_KE_FU = "transfer_customer_service";
	
	/**
	 * log4j
	 */
	protected final static Logger LOG = Logger.getLogger(XmlUtils.class);

	/**
	 * 初始化回复图文消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param fromUser
	 *            开发者微信号
	 * @param items
	 *            多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 * @return String 对应微信-发送被动响应消息(图文消息)接口
	 * @see 参考地址
	 *      :http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%
	 *      E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B
	 *      .9E.E5.A4.8D.E5.9B.BE.E6.96.87.E6.B6.88.E6.81.AF 例子：
	 *      XmlUtils.buildText(XmlUtils.getString(postStr, "FromUserName"),
	 *      XmlUtils.getString(postStr, "ToUserName"),list对象);
	 */
	public static String buildXmlNews(String toUser, String fromUser, NewsEntity news) {
		//组织访问地址
		AppEntity app = news.getApp();
		//组装缩略图地址需要host
		String host = app.getAppHostUrl();
		//组织移动端访问地址需要hostUrl;
		String hostUrl = "";
		LOG.debug("host访问地址的初始值为======="+host);
		if (!StringUtil.isBlank(app.getAppMobileStyle())) {
			hostUrl = host + File.separator +IParserRegexConstant.HTML_SAVE_PATH+File.separator +app.getAppId()+File.separator+ IParserRegexConstant.MOBILE+File.separator;
		}
		int size = 1;
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>");
		sb.append("<CreateTime><![CDATA[").append(new Date().getTime()).append("]]></CreateTime>");
		sb.append("<MsgType><![CDATA[").append("news").append("]]></MsgType>");
		if (news != null && news.getNewsMasterArticle() != null) {
			// 计算素材的长度
			if (news.getChilds() != null && news.getChilds().size() > 0) {
				size += news.getChilds().size();
			}
			sb.append("<ArticleCount>").append(size).append("</ArticleCount>");
			sb.append("<Articles>");
			// 设置主消息
			sb.append("<item>");
			sb.append("<Title><![CDATA[").append(news.getNewsMasterArticle() .getBasicTitle()).append("]]></Title>");// 标题经过ISO转码处理
			sb.append("<Description><![CDATA[").append(news.getNewsMasterArticle() .getBasicDescription()).append("]]></Description>");// 描述经过ISO转码处理
			sb.append("<PicUrl><![CDATA[").append(host+news.getNewsMasterArticle() .getBasicThumbnails()).append("]]></PicUrl>");
			if (!StringUtil.isBlank(news.getNewsLink())) { //如果素材的连接被重置过。优先重置的连接
				sb.append("<Url><![CDATA[").append(news.getNewsLink()).append("]]></Url>");
			} else {
				sb.append("<Url><![CDATA[").append(hostUrl+news.getNewsMasterArticle() .getArticleUrl()).append("]]></Url>");//对应素材的原始连接
			}
			
			sb.append("</item>");

			if (size>1) {
				for (ArticleEntity article:news.getChilds()) {
					sb.append("<item>");
					sb.append("<Title><![CDATA[").append(article.getBasicTitle()).append("]]></Title>");// 标题经过ISO转码处理
					sb.append("<Description><![CDATA[").append(article.getBasicDescription()).append("]]></Description>");// 描述经过ISO转码处理
					sb.append("<PicUrl><![CDATA[").append(host+article.getBasicThumbnails()).append("]]></PicUrl>");
					sb.append("<Url><![CDATA[").append(hostUrl+article.getArticleUrl()).append("]]></Url>");
					sb.append("</item>");					
				}
			}
			sb.append("</Articles>");
		} else {
			if (size > 10) {
				return NEWS_ERR;
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/**
	 * 初始化回复图文消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param items
	 *            多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 * @return String 对应微信-发送被动响应消息(图文消息)接口
	 * @see 参考地址
	 *      :http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%E5%AE%A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E5.9B.BE.E6.96 .87.E6.B6.88.E6.81.AF 例子：
	 *      XmlUtils.buildText(XmlUtils.getString(postStr, "FromUserName"),
	 *      XmlUtils.getString(postStr, "ToUserName"),list对象);
	 */
	public static String buildJsonNews(String toUser, List<NewsBean> items) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(toUser).append("\",");
		sb.append("\"msgtype\":\"news\",");
		sb.append("\"news\":{");
		sb.append("\"articles\":");
		if (items != null && !(items.size() > 10)) {
			sb.append(JSONArray.toJSONString(items));
		} else {
			if (items.size() > 10) {
				return NEWS_ERR;
			} else {
				sb.append("[]");
			}
		}
		sb.append("}}");
		return sb.toString();
	}
	
	
	/**
	 * 初始化上传图文消息
	 * 
	 * @param items
	 *            多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 * @return String 对应微信-上传图文的接口
	 */
	public static String uploadJsonNews(List<UploadNewsBean> items){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"articles\":");
		if (items != null && !(items.size() > 10)) {
			sb.append(JSONArray.toJSONString(items));
		} else {
			if (items.size() > 10) {
				return NEWS_ERR;
			} else {
				sb.append("[]");
			}
		}
		sb.append("}}");
		return sb.toString();
	}
	
	
	
	/**
	 * 初始化图文消息预览
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param mediaId
	 *            上传图文素材所返回的media_id
	 * @return String 对应微信-预览图文消息的接口
	 */
	public static String PreviewJsonMessageMASS(String openId,String mediaId){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(openId).append("\",");
		sb.append("\"msgtype\":\"mpnews\",");
		sb.append("\"mpnews\":{");
		sb.append("\"media_id\":\"").append(mediaId).append("\"");
		sb.append("}}");
		return sb.toString();
	}
	
	
	/**
	 * 初始化分组群发消息
	 * 
	 * @param mediaId
	 *            上传图文素材所返回的media_id
	 * @param bool
	 *            是否群发？
	 * @param gourpId
	 *            分组ID，若选择群发，则可不填
	 * @return String 对应微信-分组群发图文消息接口
	 */
	public static String SendAllJsonMessageMass(String mediaId,Boolean bool,String gourpId){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"msgtype\":\"mpnews\",");
		sb.append("\"mpnews\":{");
		sb.append("\"media_id\":\"").append(mediaId).append("\"");
		sb.append("},");
		sb.append("\"filter\":{");
		sb.append("\"is_to_all\":").append(bool);
		sb.append("\"group_id\":\"").append(gourpId).append("\"");
		sb.append("}}");
		return sb.toString();
	}
	
	/**
	 * 初始化查看发送状态
	 * 
	 * @param msgId
	 *            群发后的返回数据
	 * @return String 对应微信-查询群发消息发送状态接口
	 */	
	public static String getJsonMessageMass(String msgId){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"msg_id\":\"").append(msgId).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	/**
	 * 初始化获取模板ID
	 * 
	 * @param templateIdShort
	 *            模板在模版库中对应的编号
	 * @return String 对应微信-获取模板ID接口
	 */
	public static String getJsonTempleteId(String templateIdShort){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"template_id_short\":\"").append(templateIdShort).append("\"");
		sb.append("}");
		return sb.toString();
	}
	
	
	/**
	 * 主动回复文本消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）
	 * @param fromUser
	 *            开发者微信号 (无需要则填写null)
	 * @param items
	 *            多条图文消息信息，默认第一个item为大图,注意，如果图文数超过10，则将会无响应
	 * @return String 对应微信-发送被动响应消息(图文消息)接口
	 * @see 参考地址
	 *      :http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%
	 *      E5%AE
	 *      %A2%E6%9C%8D%E6%B6%88%E6%81%AF#.E5.8F.91.E9.80.81.E5.9B.BE.E6.96
	 *      .87.E6.B6.88.E6.81.AF 例子：
	 *      XmlUtils.buildText(XmlUtils.getString(postStr, "FromUserName"),
	 *      XmlUtils.getString(postStr, "ToUserName"),list对象);
	 */
	public static String buildJsonText(String toUser, String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(toUser).append("\",");
		sb.append("\"msgtype\":\"text\",");
		sb.append("\"text\":{");
		sb.append("\"content\":\"").append(content).append("\"");
		sb.append("}}");
		return sb.toString();
	}

	/**
	 * 发送图片消息给用户
	 * 
	 * @param toUser
	 *            接收者
	 * @param mediaId
	 *            素材id由微信产生
	 * @return
	 */
	public static String buildJsonImage(String toUser, String mediaId) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(toUser).append("\",");
		sb.append("\"msgtype\":\"image\",");
		sb.append("\"image\":{");
		sb.append("\"media_id\":\"").append(mediaId).append("\"");
		sb.append("}}");
		return sb.toString();
	}

	/**
	 * 
	 * 发送声音消息给用户
	 * 
	 * @param toUser
	 *            接收者
	 * @param mediaId
	 *            素材id由微信产生
	 * @return
	 */
	public static String buildJsonVoice(String toUser, String mediaId) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"touser\":\"").append(toUser).append("\",");
		sb.append("\"msgtype\":\"voice\",");
		sb.append("\"voice\":{");
		sb.append("\"media_id\":\"").append(mediaId).append("\"");
		sb.append("}}");
		return sb.toString();
	}

	/**
	 * 初始化回复文本消息
	 * 
	 * @param toUser
	 *            接收方帐号（收到的OpenID）,对应用户请求数据里面的FromUserName
	 * @param fromUser
	 *            开发者微信号 对应用户请求数据里面的ToUserName
	 * @param content
	 *            回复的消息内容（换行：在content中能够换行，微信客户端就支持换行显示）
	 * @return String 对应微信-发送被动响应消息(文本消息)接口
	 * @see 参考地址
	 *      :http://mp.weixin.qq.com/wiki/index.php?title=%E5%8F%91%E9%80%81%
	 *      E8%A2%AB%E5%8A%A8%E5%93%8D%E5%BA%94%E6%B6%88%E6%81%AF#.E5.9B
	 *      .9E.E5.A4.8D.E6.96.87.E6.9C.AC.E6.B6.88.E6.81.AF 例子：
	 *      XmlUtils.buildText(XmlUtils.getString(postStr, "FromUserName"),
	 *      XmlUtils.getString(postStr, "ToUserName"), "文本内容");
	 */
	public static String buildXmlText(String toUser, String fromUser, String content) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>");
		sb.append("<CreateTime><![CDATA[").append(new Date().getTime()).append("]]></CreateTime>");
		sb.append("<MsgType><![CDATA[").append("text").append("]]></MsgType>");
		sb.append("<Content><![CDATA[").append(content).append("]]></Content>");// 内容经过ISO转码处理
		sb.append("</xml>");
		return sb.toString();
	}
	

	
	
	/**
	 * 更新支付返回的xml数据,返回map
	 * @param xml 发起微支付返回后的数据
	 * @return map数据
	 */
	public static Map<String,Object> getXmlPayUnifiedOrder(String xml) {
		if (StringUtil.isBlank(xml)) {
			return null;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		String returnCode = XmlUtils.getString(xml, "return_code");
		if (returnCode!=null) {
			if (returnCode.equalsIgnoreCase("FAIL")) {
				param.put("return_code", returnCode);
				param.put("return_msg", XmlUtils.getString(xml, "return_msg"));				
			} else if  (returnCode.equalsIgnoreCase("SUCCESS")) {
				String resultCode = XmlUtils.getString(xml, "result_code");
				if (resultCode!=null && resultCode.equalsIgnoreCase("FAIL")) {
					param.put("return_code", returnCode);
					param.put("return_msg", XmlUtils.getString(xml, "return_msg"));	
					param.put("appid", XmlUtils.getString(xml, "appid"));
					param.put("mch_id", XmlUtils.getString(xml, "mch_id"));
					param.put("device_info", XmlUtils.getString(xml, "device_info"));
					param.put("nonce_str", XmlUtils.getString(xml, "nonce_str"));
					param.put("sign", XmlUtils.getString(xml, "sign"));
					param.put("err_code", XmlUtils.getString(xml, "err_code"));	
					param.put("err_code_des", XmlUtils.getString(xml, "err_code_des"));
					param.put("result_code", XmlUtils.getString(xml, "result_code"));
				} else {
					param.put("return_code", returnCode);
					param.put("return_msg", XmlUtils.getString(xml, "return_msg"));	
					param.put("appid", XmlUtils.getString(xml, "appid"));
					param.put("mch_id", XmlUtils.getString(xml, "mch_id"));
					param.put("device_info", XmlUtils.getString(xml, "device_info"));
					param.put("nonce_str", XmlUtils.getString(xml, "nonce_str"));
					param.put("sign", XmlUtils.getString(xml, "sign"));
					param.put("result_code", XmlUtils.getString(xml, "result_code"));
					param.put("openid", XmlUtils.getString(xml, "openid"));
					param.put("is_subscribe", XmlUtils.getString(xml, "is_subscribe"));
					param.put("trade_type", XmlUtils.getString(xml, "trade_type"));
					param.put("bank_type", XmlUtils.getString(xml, "bank_type"));
					param.put("total_fee", XmlUtils.getString(xml, "total_fee"));
					param.put("coupon_fee", XmlUtils.getString(xml, "coupon_fee"));
					param.put("fee_type", XmlUtils.getString(xml, "fee_type"));
					param.put("transaction_id", XmlUtils.getString(xml, "transaction_id"));
					param.put("out_trade_no", XmlUtils.getString(xml, "out_trade_no"));
					param.put("attach", XmlUtils.getString(xml, "attach"));
					param.put("time_end", XmlUtils.getString(xml, "time_end"));	
					param.put("prepay_id", XmlUtils.getString(xml, "prepay_id"));	
					param.put("code_url", XmlUtils.getString(xml, "code_url"));	
				
				}
			} 
		}

		param.put("err_code", XmlUtils.getString(xml, "err_code"));
		param.put("err_code_des", XmlUtils.getString(xml, "err_code_des"));
		return param;
	}
	
	public static Map<String,Object> getXmlPayOrderquery(String xml){
		LOG.debug("getXmlPayOrderquery xml"+ xml);
		if (xml==null) {
			return null;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		String returnCode = XmlUtils.getString(xml, "return_code");
		if (returnCode != null){
			if (returnCode.equalsIgnoreCase("FAIL")) {
				param.put("return_code", returnCode);
				param.put("return_msg", XmlUtils.getString(xml, "return_msg"));
			}else if(returnCode.equalsIgnoreCase("SUCCESS")){
				String resultCode = XmlUtils.getString(xml, "result_code");
				if (resultCode!=null && resultCode.equalsIgnoreCase("FAIL")) {
					param.put("appid", XmlUtils.getString(xml, "appid"));
					param.put("mch_id", XmlUtils.getString(xml, "mch_id"));					
					param.put("nonce_str", XmlUtils.getString(xml, "nonce_str"));
					param.put("sign", XmlUtils.getString(xml, "sign"));
					param.put("return_code", returnCode);//业务结果
					param.put("err_code", XmlUtils.getString(xml, "err_code"));	//错误代码
					param.put("err_code_des", XmlUtils.getString(xml, "err_code_des"));//错误代码描述
				} else {
					param.put("device_info", XmlUtils.getString(xml, "device_info"));//设备号
					param.put("openid", XmlUtils.getString(xml,"openid"));//用户标识
					param.put("is_subscribe", XmlUtils.getString(xml,"is_subscribe"));//是否关注公众账号
					param.put("trade_type", XmlUtils.getString(xml,"trade_type"));//交易类型
					param.put("trade_state", XmlUtils.getString(xml,"trade_state"));//交易状态
					param.put("bank_type", XmlUtils.getString(xml, "bank_type"));//付款银行
					param.put("total_fee", XmlUtils.getString(xml, "total_fee"));//总金额
					param.put("fee_type", XmlUtils.getString(xml, "fee_type"));//货币种类
					param.put("cash_fee", XmlUtils.getString(xml, "cash_fee"));//现金支付金额
					param.put("cash_fee_type", XmlUtils.getString(xml,"cash_fee_type"));//现金支付货币类型
					param.put("transaction_id", XmlUtils.getString(xml, "transaction_id"));//微信支付订单号
					param.put("out_trade_no", XmlUtils.getString(xml, "out_trade_no"));//商户订单号
					param.put("attach", XmlUtils.getString(xml, "attach"));//附加数据
					param.put("time_end", XmlUtils.getString(xml, "time_end"));//支付完成时间
					param.put("trade_state_desc", XmlUtils.getString(xml, "trade_state_desc"));//交易状态描述
				}				
			}
		}
		param.put("err_code", XmlUtils.getString(xml, "err_code"));
		param.put("err_code_des", XmlUtils.getString(xml, "err_code_des"));		
		return param;
	}
	
	/**
	 * 解析微信支付返回后的数据
	 * @param xml 字符串
	 * @return Map
	 */
	public static Map<String,Object> buildXmlPayNotify(String xml) {
		LOG.debug("buildXmlPayNotify xml"+ xml);
		if (xml==null) {
			return null;
		}
		Map<String,Object> param = new HashMap<String,Object>();
		String returnCode = XmlUtils.getString(xml, "return_code");
		if (returnCode!=null) {
			if (returnCode.equalsIgnoreCase("FAIL")) {
				param.put("return_code", returnCode);
				param.put("return_msg", XmlUtils.getString(xml, "return_msg"));				
			} else if  (returnCode.equalsIgnoreCase("SUCCESS")) {
				String resultCode = XmlUtils.getString(xml, "result_code");
				if (resultCode!=null && resultCode.equalsIgnoreCase("FAIL")) {
					param.put("result_code", resultCode);
					param.put("appid", XmlUtils.getString(xml, "appid"));	
					param.put("mch_id", XmlUtils.getString(xml, "mch_id"));
					param.put("device_info", XmlUtils.getString(xml, "device_info"));
					param.put("nonce_str", XmlUtils.getString(xml, "nonce_str"));
					param.put("sign", XmlUtils.getString(xml, "sign"));
					param.put("result_code", XmlUtils.getString(xml, "result_code"));	
					param.put("err_code", XmlUtils.getString(xml, "err_code"));	
					param.put("err_code_des", XmlUtils.getString(xml, "err_code_des"));
		
				} else {
					param.put("result_code", resultCode);
					param.put("return_msg", XmlUtils.getString(xml, "return_msg"));	
					param.put("openid", XmlUtils.getString(xml, "openid"));
					param.put("is_subscribe", XmlUtils.getString(xml, "is_subscribe")); //用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
					param.put("trade_type", XmlUtils.getString(xml, "trade_type"));//JSAPI、NATIVE、MICROPAY、			APP
					param.put("bank_type", XmlUtils.getString(xml, "bank_type"));
					param.put("total_fee", XmlUtils.getString(xml, "total_fee"));
					param.put("fee_type", XmlUtils.getString(xml, "fee_type"));
					param.put("transaction_id", XmlUtils.getString(xml, "transaction_id"));
					param.put("out_trade_no", XmlUtils.getString(xml, "out_trade_no"));
					param.put("attach", XmlUtils.getString(xml, "attach"));
					param.put("time_end", XmlUtils.getString(xml, "time_end"));
					param.put("return_code", XmlUtils.getString(xml, "return_code"));	
					param.put("return_msg", XmlUtils.getString(xml, "return_msg"));	 //返回信息，如非空，为错误	原因签名失败\参数格式校验错误
				}
			} 
		}
		return param;

	}
	
	
	
	/**
	 * 组织多客服
	 * @param toUser 接受者
	 * @param fromUser 发送者
	 * @return
	 */
	public static String buildDuoKeFu(String toUser, String fromUser) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>");
		sb.append("<CreateTime><![CDATA[").append(new Date().getTime()).append("]]></CreateTime>");
		sb.append("<MsgType><![CDATA[").append(XmlUtils.DUO_KE_FU).append("]]></MsgType>");
		sb.append("</xml>");
		return sb.toString();
	}
	

	/**
	 * 将微信平台返回的xml字符串，转换成document对象，便于获取
	 * 
	 * @param str
	 *            字符串
	 * @return 转换成功将返回Document，失败返回null
	 */
	public static Document stringToXml(String str) {
		try {
			return DocumentHelper.parseText(str);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 将微信平台返回的xml字符串，转换成document对象，同时根据key获取相应的值
	 * 
	 * @param str
	 *            字符串
	 * @param key
	 *            节点名称
	 * @return 转换成功将返回string，失败返回null
	 */
	public static String getString(String str, String key) {
		try {
			Document document = DocumentHelper.parseText(str);
			Element root = document.getRootElement();
			return root.elementTextTrim(key);
		} catch (Exception e) {
			e.printStackTrace();
			LOG.debug("Exception接受的xml值:" + str);
			LOG.debug("Exception读取的key:" + key);
		}
		return null;
	}

	/**
	 * 将微信平台返回的xml字符串，转换成document对象，同时根据key获取相应的值</br> 当xml有子标签存在时调用</br>
	 * 
	 * @param str
	 *            字符串
	 * @param key
	 *            节点名称
	 * @param sonKey 子节点名称
	 * @return 转换成功将返回string，失败返回null
	 */
	public static String getString(String str, String key, String sonKey) {
		try {
			Document document = DocumentHelper.parseText(str);
			Element root = document.getRootElement();
			for (Iterator<?> i = root.elementIterator(key); i.hasNext();) {
				Element foo = (Element) i.next();
				return foo.elementText(sonKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
			LOG.debug("Exception接受的xml值:" + str);
			LOG.debug("Exception读取的key:" + key);
		}
		return null;
	}
	
	
	
	
	/**
	 * 生成微信签名</br>
	 * 签名步骤一：按字典序排序参数</br>
	 * 签名步骤二：在string后加入KEY</br>
	 * 签名步骤三：MD5加密</br>
	 * 签名步骤四：所有字符转为大写</br>
	 * @param param 需要签名的参数</br>
	 * 			key:键(参数名)|value:值</br>
	 * @param Key 微信支付设置的Key
	 * @return 签名后的字符串
	 */
	public static String getPaySign(Map<String,String> params,String key){
		//签名步骤一：按字典序排序参数
		Map<String, String> temp = StringUtil.sortMapByKey(params);
		//签名步骤二：在string后加入KEY
		String sign = StringUtil.buildUrl("", temp).replace("?", "")+"&key="+key;
		LOG.debug("=====getPaySign:" + sign);
		//签名步骤三：MD5加密
		sign= MD5Util.MD5Encode(sign,"utf-8");
		//签名步骤四：所有字符转为大写
		sign = sign.toUpperCase();
		return sign;
	} 
	
}
