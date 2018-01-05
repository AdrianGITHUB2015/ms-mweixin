package net.mingsoft.mweixin.action.web;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mingsoft.weixin.biz.IWeixinBiz;
import com.mingsoft.weixin.entity.WeixinEntity;

import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.action.BaseAction;
import net.mingsoft.weixin.service.PortalService;

/**
 * 微信入口
 * @author 铭飞开发团队
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-11-18 11:23:59<br/>
 * 历史修订：<br/>
 */
@RestController
@RequestMapping("/mweixin/portal")
public class PortalAction extends BaseAction {
	
	@Autowired
	private PortalService wxService;
	@Autowired
	private IWeixinBiz weixinBiz;

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
	@GetMapping(produces = "text/plain;charset=utf-8")
	public String get(@RequestParam(name = "signature", required = false) String signature,
			@RequestParam(name = "timestamp", required = false) String timestamp,
			@RequestParam(name = "nonce", required = false) String nonce,
			@RequestParam(name = "echostr", required = false) String echostr) {
		String weixinNo = BasicUtil.getString("weixinNo");
		//获取微信实体，构建服务
		WeixinEntity weixin = weixinBiz.getByWeixinNo(weixinNo);
		wxService = wxService.build(weixin);
		
		this.logger.debug("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", new String[] { signature, timestamp, nonce, echostr });
		if (StringUtils.isAnyBlank(signature, timestamp, nonce, echostr)) {
			throw new IllegalArgumentException("请求参数非法，请核实!");
		}

		if (wxService.checkSignature(timestamp, nonce, signature)) {
			return echostr;
		}

		return "非法请求";
	}

	@ResponseBody
	@PostMapping(produces = "application/xml; charset=UTF-8")
	public String post(@RequestBody String requestBody, @RequestParam("signature") String signature,
			@RequestParam(name = "encrypt_type", required = false) String encType,
			@RequestParam(name = "msg_signature", required = false) String msgSignature,
			@RequestParam("timestamp") String timestamp, @RequestParam("nonce") String nonce) {
		
		String weixinNo = BasicUtil.getString("weixinNo");
		this.logger.debug(
				"\n接收微信请求：[weixinNo＝[{}]signature=[{}], encType=[{}], msgSignature=[{}],"
						+ " timestamp=[{}], nonce=[{}], requestBody=[\n{}\n] ",
				new String[] { weixinNo,signature, encType, msgSignature, timestamp, nonce, requestBody });

		//获取微信实体，构建服务
		WeixinEntity weixin = weixinBiz.getByWeixinNo(weixinNo);
		wxService = wxService.build(weixin);
		if (!this.wxService.checkSignature(timestamp, nonce, signature)) {
			throw new IllegalArgumentException("非法请求，可能属于伪造的请求！");
		}

		String out = null;
		if (encType == null) {
			// 明文传输的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(requestBody);
			WxMpXmlOutMessage outMessage = wxService.route(inMessage);
			if (outMessage == null) {
				return "";
			}
			//需要设置用户session
			this.setWeixinPeopleSession(inMessage.getFromUser());
			out = outMessage.toXml();
		} else if ("aes".equals(encType)) {
			// aes加密的消息
			WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(requestBody, wxService.getWxMpConfigStorage(),
					timestamp, nonce, msgSignature);
			this.logger.debug("\n消息解密后内容为：\n{} ", inMessage.toString());
			WxMpXmlOutMessage outMessage = wxService.route(inMessage);
			if (outMessage == null) {
				return "";
			}
			//需要设置用户session
			this.setWeixinPeopleSession(inMessage.getFromUser());
			out = outMessage.toEncryptedXml(wxService.getWxMpConfigStorage());
		}

		this.logger.debug("\n组装回复信息：{}", out);

		return out;
	}

}
