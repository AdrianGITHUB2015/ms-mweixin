package net.mingsoft.weixin.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import net.mingsoft.mweixin.biz.IPassiveMessageBiz;
import net.mingsoft.mweixin.entity.PassiveMessageEntity;
import net.mingsoft.weixin.builder.TextBuilder;
import net.mingsoft.weixin.service.WeixinService;

/**
 * @author Binary Wang
 */
@Component
public class MsgHandler extends AbstractHandler {
	
	/**
	 * 注入微信被动消息回复业务层
	 */	
	@Resource(name="netPassiveMessageBizImpl")
	private IPassiveMessageBiz passiveMessageBiz;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) {

    WeixinService weixinService = (WeixinService) wxMpService;

    if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
      //TODO 可以选择将消息保存到本地
    }
    String msg = wxMessage.getContent();
    //获取信息
    PassiveMessageEntity passiveMessage = new PassiveMessageEntity();
    passiveMessage.setPmKey(msg);
    //需要对应上微信类型
    
    //通过获取的信息，查询关键字表
    if(passiveMessageBiz.getEntity(passiveMessage) == null){
    	return null;
    }
    //设置content
    String content = passiveMessage.getPmContent();;
    return new TextBuilder().build(content, wxMessage, weixinService);

  }

}
