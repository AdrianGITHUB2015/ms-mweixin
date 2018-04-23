package net.mingsoft.weixin.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.api.WxConsts.XmlMsgType;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import net.mingsoft.mweixin.biz.IMenuBiz;
import net.mingsoft.mweixin.entity.MenuEntity;
import net.mingsoft.mweixin.entity.MenuEntity.Type;
import net.mingsoft.weixin.builder.AbstractBuilder;
import net.mingsoft.weixin.builder.ImageBuilder;
import net.mingsoft.weixin.builder.TextBuilder;
import net.mingsoft.weixin.service.PortalService;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class MenuService extends AbstractService {

	/**
	 * 注入微信菜单业务层
	 */
	@Resource(name = "netMenuBizImpl")
	private IMenuBiz menuBiz;

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
			WxSessionManager sessionManager) {

		PortalService weixinService = (PortalService) wxMpService;
		String key = wxMessage.getEventKey();

		WxMpXmlOutMessage outMessage = null;

		String event = wxMessage.getEvent();
		if (event.equalsIgnoreCase(MenuButtonType.CLICK)) {
			MenuEntity menu = (MenuEntity) menuBiz.getEntity(Integer.parseInt(key));
			if (menu == null) {
				return null;
			}
			switch (menu.getMenuStyle()) {
			// 文本
			case Type.TEXT:
				AbstractBuilder builder = new TextBuilder();
				outMessage = builder.build(menu.getMenuContent(), wxMessage, weixinService);
				break;
			// 图片
			case Type.IMAGE:
				AbstractBuilder imageBuilder = new ImageBuilder();
				outMessage = imageBuilder.build(menu.getMenuContent(), wxMessage, weixinService);
				break;
			// 图文
			case Type.IMAGE_TEXT:
				break;
			// 卡券
			case Type.CARD:
				break;
			default:

			}
		}

		if (outMessage != null) {
			return outMessage;
		}

		return null;

	}

}
