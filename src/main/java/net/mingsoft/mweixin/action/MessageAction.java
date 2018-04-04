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
 */
package net.mingsoft.mweixin.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.mingsoft.basic.constant.Const;
import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.bean.NewsBean;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.biz.IWeixinPeopleBiz;
import com.mingsoft.weixin.constant.ModelCode;
import com.mingsoft.weixin.constant.e.NewsTypeEnum;
import com.mingsoft.weixin.entity.NewsEntity;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.mingsoft.weixin.entity.WeixinPeopleEntity;
import com.mingsoft.weixin.util.MessageUtils;
import com.mingsoft.weixin.util.bean.NewsEntityUtils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassTagMessage;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.weixin.service.PortalService;

/**
 * 
 * @ClassName: MessageAction
 * @Description:TODO(消息发送控制层)
 * @author: 铭飞开发团队
 * @date: 2018年4月3日 上午9:03:12
 * 
 * @Copyright: 2018 www.mingsoft.net Inc. All rights reserved.
 */
@Controller("netMessageAction")
@RequestMapping("/${managerPath}/mweixin/message")
public class MessageAction extends BaseAction {

	/**
	 * 注入素材业务层
	 */
	@Autowired
	private INewsBiz newsBiz;

	/**
	 * 注入微信用户业务层
	 */
	@Autowired
	private IWeixinPeopleBiz weixinPeopleBiz;

	@Autowired
	private PortalService weixinService;

	/**
	 * 遍历openId伪群发文本消息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/guiseSendAllText")
	@ResponseBody
	public void guiseSendAllText(HttpServletRequest request, HttpServletResponse response) {
		// 获取微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		// 若微信或者微信相关数据不存在
		if (weixin == null || weixin.getWeixinId() <= 0 || StringUtil.isBlank(weixin.getWeixinAppSecret())
				|| StringUtil.isBlank(weixin.getWeixinAppId())) {
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		// 获取微信ID
		int weixinId = weixin.getWeixinId();
		// 获取应用ID
		int appId = this.getAppId(request);
		// 获取要发送的内容
		String content = request.getParameter("content");

		// 若内容为空，返回错误信息
		if (StringUtil.isBlank(content)) {
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false,
					this.getResString("err.empty", this.getResString("weixin.message.content")));
			return;
		}
		// 获取伪群发工具类
		MessageUtils messageUtil = new MessageUtils(weixin.getWeixinAppId(), weixin.getWeixinAppSecret());
		// 获取微信所有用户集合
		List<WeixinPeopleEntity> weixinPeopleList = this.queryUserInfoList(appId, weixinId);
		// 创建空的weixinPeople集合,将发送失败的实体add进List中
		List<WeixinPeopleEntity> weixinPeopleFailList = new ArrayList<WeixinPeopleEntity>();
		// 创建空的weixinPeople集合,将发送成功的实体add进List中
		List<WeixinPeopleEntity> weixinPeopleSucessList = new ArrayList<WeixinPeopleEntity>();
		// 遍历所有用户的openId伪群发文本消息
		for (int i = 0; i < weixinPeopleList.size(); i++) {
			// 遍历openId伪群发文本消息,发送成功为true
			Boolean bool = messageUtil.sendTextToUser(weixinPeopleList.get(i).getWeixinPeopleOpenId(), content);
			LOG.debug("群发后的返回值============" + bool);
			// 如果发送不成功，将该用户add进PeopleWeixinList
			if (!bool) {
				// 将发送失败的用户add进weixinPeopleArrayList
				weixinPeopleFailList.add(weixinPeopleList.get(i));
			} else {
				// 将发送成功的用户add进weixinPeopleArrayList
				weixinPeopleSucessList.add(weixinPeopleList.get(i));
			}
		}
		// 将发送失败的实体压进list后返回给页面
		this.outJson(response, ModelCode.WEIXIN_MESSAGE, true, JSONObject.toJSONString(weixinPeopleFailList),
				JSONObject.toJSONString(weixinPeopleSucessList));
	}

	/**
	 * 消息群发
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws WxErrorException
	 * @throws IOException
	 * @throws IORuntimeException
	 */
	@RequestMapping("/sendAll")
	@ResponseBody
	public void sendAll(ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws WxErrorException, IOException {

		// 获取微信对象
		WeixinEntity weixin = this.getWeixinSession(request);
		// 若微信不存在
		if (weixin == null || StringUtil.isBlank(weixin.getWeixinAppSecret())
				|| StringUtil.isBlank(weixin.getWeixinAppId())) {
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		// 获取图文素材ID
		String contentStr = BasicUtil.getString("content");
		String type = BasicUtil.getString("type");
		String gourpId = BasicUtil.getString("gourpId", null);
		// WxMpMassPreviewMessage massMessage = new WxMpMassPreviewMessage();
		// //测试群发
		WxMpMassTagMessage massMessage = new WxMpMassTagMessage();
		// 判断群发消息的类型
		if (type.equals(WxConsts.MassMsgType.TEXT)) {
			// 发送文本消息
			massMessage.setMsgType(WxConsts.MassMsgType.TEXT);
			massMessage.setContent(contentStr);
			massMessage.setSendAll(true);

		} else if (type.equals(WxConsts.MassMsgType.IMAGE)) {
			// 发送图文消息
			// 将素材ID转整形
			int contentInt = Integer.valueOf(contentStr);
			// 获取选取的素材实体
			NewsEntity _news = newsBiz.getNewsByNewsId(contentInt);
			if (_news == null) {
				this.outJson(response, null, false);
				return;
			}
			// 组织数据上传
			try (InputStream inputStream = FileUtil
					.getInputStream(BasicUtil.getRealPath(_news.getNewsMasterArticle().getBasicThumbnails()))) {
				// 上传照片到媒体库
				WxMediaUploadResult uploadMediaRes = weixinService.getMaterialService()
						.mediaUpload(WxConsts.MediaFileType.IMAGE, "jpg", inputStream);

				// 上传图文消息
				WxMpMassNews news = new WxMpMassNews();
				if (_news.getChilds().size() > 0) {
					for (ArticleEntity _article : _news.getChilds()) {
						WxMpMassNews.WxMpMassNewsArticle article = new WxMpMassNews.WxMpMassNewsArticle();
						article.setTitle(_article.getBasicTitle());
						article.setContent(_article.getArticleContent());
						article.setThumbMediaId(uploadMediaRes.getMediaId());
						news.addArticle(article);
					}
				}
				WxMpMassUploadResult massUploadResult = weixinService.getMassMessageService().massNewsUpload(news);
				massMessage.setMsgType(WxConsts.MassMsgType.MPNEWS);
				massMessage.setMediaId(massUploadResult.getMediaId());
				// massMessage.setToWxUserOpenid("ogHuk0w8LGN2zDM_M4JQbUkWat64");测试
				// 铭飞
			}
		}
		try {
			WxMpMassSendResult result = this.builderWeixinService(weixin.getWeixinNo()).getMassMessageService()
					.massGroupMessageSend(massMessage);
			LOG.debug("群发返回日志:" + JSONObject.toJSONString(result));
		} catch (WxErrorException e) {
			LOG.error("发送失败:异常");
			e.printStackTrace();
		}
	}

	/**
	 * 返回群发消息界面
	 * 
	 * @return 群发消息界面
	 */
	@RequestMapping("/index")
	public String index() {
		return Const.VIEW + "/weixin/message/index";
	}

	/**
	 * 给用户单独发送文本,无限发送
	 * 
	 * @param openId
	 *            用户在微信中的唯一标识
	 * @param request
	 * @param response
	 */
	@PostMapping("/sendToUser")
	@ResponseBody
	public void sendToUser(@PathVariable String openId, @PathVariable String type, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtil.isBlank(openId)) {
			this.outJson(response, null, false);
			return;
		}
		// 获取微信
		WeixinEntity weixin = this.getWeixinSession(request);
		// 若微信不存在
		if (weixin == null || StringUtil.isBlank(weixin.getWeixinAppSecret())
				|| StringUtil.isBlank(weixin.getWeixinAppId())) {
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		// 获取要发送的内容
		String content = request.getParameter("content");
		// 若内容为空
		if (StringUtil.isBlank(content)) {
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false,
					this.getResString("err.empty", this.getResString("weixin.message.content")));
			return;
		}

		WxMpKefuMessage kefuMessage = new WxMpKefuMessage();
		kefuMessage.setMsgType(WxConsts.MassMsgType.TEXT);
		kefuMessage.setContent(content);
		kefuMessage.setToUser("oI3eS1BUsYUpe1otVBQnWmHbxh7o");
		kefuMessage.setToUser(openId);

		try {
			boolean result = this.builderWeixinService(weixin.getWeixinNo()).getKefuService()
					.sendKefuMessage(kefuMessage);
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, result);
		} catch (WxErrorException e) {
			LOG.error("发送失败:异常");
			e.printStackTrace();
		}

	}



	/**
	 * 查询微信所有用户信息
	 * 
	 * @param appId
	 *            应用ID
	 * @param weixinId
	 *            微信ID
	 * @return 所有用户集合
	 */
	private List<WeixinPeopleEntity> queryUserInfoList(int appId, int weixinId) {
		if (appId <= 0 || weixinId <= 0) {
			return null;
		}
		// 根据微信ID查询微信用户列表
		List<WeixinPeopleEntity> weixinPeopleList = weixinPeopleBiz.queryListByAppIdAndWeixinId(appId, weixinId);
		// 判断所有用户信息数组合法性
		if (weixinPeopleList == null || weixinPeopleList.size() == 0) {
			return null;
		}
		return weixinPeopleList;
	}

	/**
	 * 订单回复
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@Deprecated
	@RequestMapping("/send")
	public String send(HttpServletRequest request, ModelMap model) {
		String openId = request.getParameter("openId");
		if (!StringUtil.isBlank(openId)) {
			model.addAttribute("openId", openId);
		}
		return Const.VIEW + "/weixin/message/send";
	}

	/**
	 * 遍历openId群发图文消息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/guiseSendAllNews")
	@ResponseBody
	public void guiseSendAllNews(HttpServletRequest request, HttpServletResponse response) {
		// 获取微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		// 若微信或者微信相关字段不存在
		if (weixin == null || weixin.getWeixinId() <= 0 || StringUtil.isBlank(weixin.getWeixinAppSecret())
				|| StringUtil.isBlank(weixin.getWeixinAppId())) {
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, false, this.getResString("weixin.not.found"));
			return;
		}
		// 获取伪群发工具类
		MessageUtils messageUtil = new MessageUtils(weixin.getWeixinAppId(), weixin.getWeixinAppSecret());
		// 获取微信ID
		int weixinId = weixin.getWeixinId();
		// 获取应用ID
		int appId = this.getAppId(request);
		// 获取素材ID，已在前台进行转化
		String contentStr = request.getParameter("content");
		String openId = request.getParameter("openId"); // 如果openid存在表示单独发，弥补不能给用户单独发素材内容的需求
		// 判断字符串是否转化为了int型
		if (!StringUtil.isInteger(contentStr)) {
			this.outJson(response, null, false);
			return;
		}
		// 获取素材ID
		int contentInt = Integer.valueOf(contentStr);
		// 获取素材ID对应的素材实体
		NewsEntity news = newsBiz.getNewsByNewsId(contentInt);
		if (news == null) {
			this.outJson(response, null, false);
			return;
		}
		// 不支持群发文本素材
		if (news.getNewsType() == NewsTypeEnum.TEXT.toInt()) {
			this.outJson(response, null, false);
			return;
		}
		// 将素材实体转化为群发素材实体类集合
		List<NewsBean> newsBeanList = NewsEntityUtils.newsEntityToNewsBeanList(this.getUrl(request), news, appId);
		// 若群发所需要的素材实体集合为空或长度等于0
		if (newsBeanList == null || newsBeanList.size() == 0) {
			this.outJson(response, null, false);
			return;
		}

		if (StringUtil.isBlank(openId)) {
			// 获取所有用户集合
			List<WeixinPeopleEntity> weixinPeopleList = this.queryUserInfoList(appId, weixinId);
			// 创建空的微信用户ArrayList数组,用于当前用户发送消息失败后将其压进数组
			List<WeixinPeopleEntity> weixinPeopleFailList = new ArrayList<WeixinPeopleEntity>();
			// 创建空的weixinPeople集合,将发送成功的实体add进List中
			List<WeixinPeopleEntity> weixinPeopleSucessList = new ArrayList<WeixinPeopleEntity>();
			// 遍历所有用户的openId伪群发文本消息,openId为用户在微信中的唯一标识
			for (int i = 0; i < weixinPeopleList.size(); i++) {
				// 遍历openId伪群发文本消息,,发送成功为true，失败为false
				Boolean bool = messageUtil.sendNewsToUser(weixinPeopleList.get(i).getWeixinPeopleOpenId(),
						newsBeanList);
				LOG.debug("群发后的返回值============" + bool);
				// 如果发送不成功，将该用户add进发送失败用户数组中
				if (!bool) {
					// 将发送失败的用户动态添加到数组中
					weixinPeopleFailList.add(weixinPeopleList.get(i));
				} else {
					// 将发送成功的用户add进weixinPeopleArrayList
					weixinPeopleSucessList.add(weixinPeopleList.get(i));
				}
			}
			LOG.debug("成功发送:" + weixinPeopleSucessList.size());
			LOG.debug("失败发送:" + weixinPeopleFailList.size());
			// 返回的数组包含了所有发送失败的用户及发送成功的用户，以便提示哪几个用户接收不到消息
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, true, JSONObject.toJSONString(weixinPeopleFailList),
					JSONObject.toJSONString(weixinPeopleSucessList));
			return;
		} else {
			Boolean bool = messageUtil.sendNewsToUser(openId, newsBeanList);
			LOG.debug("单独发送素材：" + bool);
			this.outJson(response, ModelCode.WEIXIN_MESSAGE, bool);
		}

	}
}
