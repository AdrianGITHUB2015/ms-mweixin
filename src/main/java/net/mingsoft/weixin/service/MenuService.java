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
	@Resource(name="netMenuBizImpl")
	private IMenuBiz menuBiz;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
    Map<String, Object> context, WxMpService wxMpService,
    WxSessionManager sessionManager) {
		
    PortalService weixinService = (PortalService) wxMpService;
    String key = wxMessage.getEventKey();
    this.logger.debug("点击菜单类型："+key);
    MenuEntity menu = (MenuEntity) menuBiz.getEntity(Integer.parseInt(key));
    if(menu != null){
    	return null;
    }
    AbstractBuilder builder = null;
    switch ( wxMessage.getEvent()) {
	    case MenuButtonType.CLICK:
	    builder = new TextBuilder();
	    break;
	//  case XmlMsgType.IMAGE:
	//    builder = new ImageBuilder();
	//    break;
	//  case XmlMsgType.VOICE:
	//    break;
	//  case XmlMsgType.VIDEO:
	//    break;
	//  case XmlMsgType.NEWS:
	//    break;
	  	default:
	  		break;
    }
    try {
    	 return builder.build(menu.getMenuContent(), wxMessage, weixinService);
    } catch (Exception e) {
    	this.logger.error(e.getMessage(), e);
    }
//    switch (menuKey.getType()) {
//    case XmlMsgType.TEXT:
//      builder = new TextBuilder();
//      break;
//    case XmlMsgType.IMAGE:
//      builder = new ImageBuilder();
//      break;
//    case XmlMsgType.VOICE:
//      break;
//    case XmlMsgType.VIDEO:
//      break;
//    case XmlMsgType.NEWS:
//      break;
//    default:
//      break;
//    }

//    if (builder != null) {
//      try {
//        return builder.build(menuKey.getContent(), wxMessage, weixinService);
//      } catch (Exception e) {
//        this.logger.error(e.getMessage(), e);
//      }
//    }

    return null;

  }

}
