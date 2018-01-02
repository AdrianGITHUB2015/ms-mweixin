package net.mingsoft.weixin.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import net.mingsoft.mweixin.biz.IPassiveMessageBiz;
import net.mingsoft.mweixin.entity.PassiveMessageEntity;
import net.mingsoft.mweixin.entity.PassiveMessageEntity.TypeEnum;
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
    
    //通过获取的信息，查询关键字表
    passiveMessage = passiveMessageBiz.getEntity(passiveMessage);
    if(passiveMessage == null){
    	//被动回复
    	passiveMessage = new PassiveMessageEntity();
    	passiveMessage.setPmType(TypeEnum.TYPE_PASSIVE.toInt());
    	passiveMessage = passiveMessageBiz.getEntity(passiveMessage);
    	//没有设置被动回复返回null
    	if(passiveMessage == null){
    		return null;
    	}
    	return new TextBuilder().build(passiveMessage.getPmContent(), wxMessage, weixinService);
    }
    switch (passiveMessage.getPmNewType()){
	    case 1: 
	    	return new TextBuilder().build(passiveMessage.getPmContent(), wxMessage, weixinService);
//	    case : 
//	    	//图片消息的配置
//	    case 3: 
//	    	//语音消息的配置
//	    case 4: 
//	    	//视频消息的配置
//	    case 5: 
//	    	//音乐消息的配置
//	    case 6: 
//	    	//图文消息的配置
    }
    //设置content
//    String content = passiveMessage.getPmContent();;
//    return new TextBuilder().build(content, wxMessage, weixinService);
	return null;

  }

}
