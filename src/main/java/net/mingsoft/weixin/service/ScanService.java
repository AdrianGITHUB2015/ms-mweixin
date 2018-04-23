package net.mingsoft.weixin.service;

import java.util.Map;

import org.springframework.stereotype.Component;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

/**
 * 
 * @author Binary Wang
 *
 */
@Component
public  class ScanService extends AbstractService {

	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage arg0, Map<String, Object> arg1, WxMpService arg2,
			WxSessionManager arg3) throws WxErrorException {
		// TODO Auto-generated method stub
		return null;
	}

}
