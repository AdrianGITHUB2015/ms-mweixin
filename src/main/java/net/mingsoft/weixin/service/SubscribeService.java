package net.mingsoft.weixin.service;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.mingsoft.weixin.constant.SessionConst;
import com.mingsoft.weixin.entity.WeixinEntity;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.biz.IPassiveMessageBiz;
import net.mingsoft.mweixin.entity.PassiveMessageEntity;
import net.mingsoft.weixin.builder.TextBuilder;
import net.mingsoft.weixin.service.PortalService;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public class SubscribeService extends AbstractService {

	/**
	 * 注入微信被动消息回复业务层
	 */	
	@Resource(name="netPassiveMessageBizImpl")
	private IPassiveMessageBiz passiveMessageBiz;
	
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService,
      WxSessionManager sessionManager) throws WxErrorException {

    this.logger.info("新关注用户 OPENID: " + wxMessage.getFromUser());

    PortalService weixinService = (PortalService) wxMpService;

    // 获取微信用户基本信息
    WxMpUser userWxInfo = weixinService.getUserService().userInfo(wxMessage.getFromUser(), null);

    if (userWxInfo != null) {
      // TODO 可以添加关注用户到本地
    	
    }

    WxMpXmlOutMessage responseResult = null;
    try {
      responseResult = handleSpecial(wxMessage);
    } catch (Exception e) {
      this.logger.error(e.getMessage(), e);
    }

    if (responseResult != null) {
      return responseResult;
    }
    PassiveMessageEntity passiveMessage = new PassiveMessageEntity();
    passiveMessage.setPmWeixinId(weixinService.getWeixin().getWeixinId()); 
    passiveMessage.setPmAppId(BasicUtil.getAppId());
    //获取设置关注回复的内容
    passiveMessage.setPmType(PassiveMessageEntity.Type.TYPE_ATTENTION);
    passiveMessage = (PassiveMessageEntity) passiveMessageBiz.getEntity(passiveMessage);
    if(passiveMessage == null){
    	try {
	      return new TextBuilder().build("感谢关注", wxMessage, weixinService);
	    } catch (Exception e) {
	      this.logger.error(e.getMessage(), e);
	    }
    }else{
    	try {
  	      return new TextBuilder().build(passiveMessage.getPmContent(), wxMessage, weixinService);
  	    } catch (Exception e) {
  	      this.logger.error(e.getMessage(), e);
  	    }
    }
    return null;
  }

  /**
   * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
   */
  protected WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage) throws Exception {
    //TODO
    return null;
  }

}
