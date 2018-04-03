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

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.UploadNewsBean;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.util.MessageMassUtils;
import com.mingsoft.weixin.util.UploadDownUtils;
import com.mingsoft.weixin.util.XmlUtils;
import com.mingsoft.weixin.util.bean.NewsEntityUtils;

import net.mingsoft.basic.util.BasicUtil;

/**
 * 
 * @ClassName:  MessageMassAction   
 * @Description:TODO(微信群发消息控制层)   
 * @author: 铭飞开发团队
 * @date:   2018年4月3日 上午9:11:32   
 *     
 * @Copyright: 2018 www.mingsoft.net Inc. All rights reserved.
 */
@Deprecated
@Controller
@RequestMapping("/${managerPath}/weixin/messageMass")
public class MessageMassAction extends BaseAction{
	
	/**
	 * 注入素材业务层
	 */
	@Autowired
	private INewsBiz newsBiz;
	
	/**
	 * 调用微信正规群发接口群发
	 * @param request
	 * @param response
	 */
	@RequestMapping("/sendToAll")
	@ResponseBody
	public void sendToAll(HttpServletRequest request, HttpServletResponse response){
		//获取微信对象
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信不存在
		if(weixin == null || StringUtil.isBlank(weixin.getWeixinAppSecret()) || StringUtil.isBlank(weixin.getWeixinAppId())){
			return;
		}
		//引入messageMass的工具类
		MessageMassUtils messageMassUtils = new MessageMassUtils(weixin.getWeixinAppId(),weixin.getWeixinAppSecret());
		//获取图文素材ID
		String contentStr = BasicUtil.getString("content");
		String gourpId = BasicUtil.getString("gourpId",null);
		//判断字符串是否转化为了int型
		if(!StringUtil.isInteger(contentStr)){
			this.outJson(response, null, false);
			return;
		}
		//将素材ID转整形
		int contentInt = Integer.valueOf(contentStr);		
		//获取选取的素材实体
		NewsEntity news = newsBiz.getNewsByNewsId(contentInt);	
		if(news == null){
			this.outJson(response, null, false);
			return;
		}
		//不支持群发文本素材
		if(news.getNewsType() == NewsTypeEnum.TEXT.toInt()){
			this.outJson(response, null, false);
			return;
		}
		//上传图文素材并返回上传图文素材的mediaId
		String mediaId =  this.uploadNews(request,weixin,news);	
		if(StringUtil.isBlank(mediaId)){
			this.outJson(response, null, false);
			return;
		}
		//调用微信官方接口群发图文消息(true表示群发，null表示不分组发送)并返回是否发送成功
			Boolean temp = messageMassUtils.sendAllMessageMass(mediaId, gourpId==null, gourpId);
		this.outJson(response, null, temp);
		LOG.debug("群发状态======="+temp);
	}
	
	
	/**
	 * 给指定用户预览图文消息
	 * @param openId 用户在微信中的唯一标识
	 * @param request
	 * @param response
	 */
	@RequestMapping("/preview")
	@ResponseBody
	public void preview(HttpServletRequest request, HttpServletResponse response){
		//得到用户openId
		String openId = request.getParameter("openId");
		WeixinEntity weixin = this.getWeixinSession(request);
		//若微信不存在
		if(weixin == null || StringUtil.isBlank(weixin.getWeixinNo()) || StringUtil.isBlank(weixin.getWeixinAppSecret()) || StringUtil.isBlank(weixin.getWeixinAppId())){
			return;
		}
		MessageMassUtils messageMassUtils = new MessageMassUtils(weixin.getWeixinAppId(),weixin.getWeixinAppSecret());
		//content为图文素材ID
		String contentStr = request.getParameter("content");
		//判断字符串是否转化为了int型
		if(!StringUtil.isInteger(contentStr)){
			this.outJson(response, null, false);
			return;
		}
		//获取选取的素材ID
		int contentInt = Integer.valueOf(contentStr);	
		//获取选取的素材实体
		NewsEntity news = newsBiz.getNewsByNewsId(contentInt);
		if(news == null){
			this.outJson(response, null, false);
			return;
		}
		//不支持预览文本素材
		if(news.getNewsType() == NewsTypeEnum.TEXT.toInt()){
			this.outJson(response, null, false);
			return;
		}
		//获取上传图文素材返回的的唯一标识mediaId
		String mediaId =  this.uploadNews(request,weixin,news);	
		if(StringUtil.isBlank(mediaId)){
			this.outJson(response, null, false);
			return;
		}
		//给当前微信预览图文消息,返回true/false
		Boolean tmp = messageMassUtils.preivewMessageMass(openId,mediaId);
		this.outJson(response, null, tmp);
	}
	
	/**
	 * 上传图文素材
	 * @param weixin 微信对象
	 * @param news 素材实体
	 * @return 图文素材所对应的唯一ID
	 */
	private String uploadNews(HttpServletRequest request,WeixinEntity weixin,NewsEntity news){
		UploadDownUtils uploadownUitls = new UploadDownUtils(weixin.getWeixinAppId(),weixin.getWeixinAppSecret());
		//将素材实体转化为上传所需要的素材实体列表
		List<UploadNewsBean> uploadNewsBeanList = NewsEntityUtils.newsEnttiyToUploadNewsBeanList(weixin,news,request);
		if(uploadNewsBeanList == null || uploadNewsBeanList.size()==0){
			return null;
		}
		//获得请求需要的post数据
		String newsUploadJson = XmlUtils.uploadJsonNews(uploadNewsBeanList);
		if(StringUtil.isBlank(newsUploadJson)){
			return null;
		}
		//上传图文素材，得到图文素材的media_id；用于群发图文消息
		String mediaId = uploadownUitls.uploadNews(newsUploadJson);
		//返回图文素材mediaId；
		return mediaId;
	}
}
