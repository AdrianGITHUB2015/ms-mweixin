package net.mingsoft.mweixin.action.web;

import javax.ws.rs.POST;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.exception.WxErrorException;
import net.mingsoft.mweixin.action.BaseAction;
import net.mingsoft.weixin.service.PortalService;

/**
 * 微信js-sdk接口
 * @author 铭飞开发团队
 * @version 
 * 版本号：100-000-000<br/>
 * 创建日期：2017年10月26日<br/>
 * 历史修订：<br/>
 */
@Controller("jsSdkActionWeb")
@RequestMapping("/mweixin/jsSdk")
public class JsSdkAction extends BaseAction {

	/**
	 * 构建微信js
	 * @param url 调用的页面url
	 * @param weixinNo 微信号
	 * @return
	 */
	@ResponseBody
	@PostMapping("/createJsapiSignature")
	public WxJsapiSignature createJsapiSignature(String url,String weixinNo) {
		PortalService weixinService = super.builderWeixinService(weixinNo);
		try {
			WxJsapiSignature temp = weixinService.createJsapiSignature(url);
			return temp;
		} catch (WxErrorException e) {
			e.printStackTrace();
		}
		return null;
	}
}
