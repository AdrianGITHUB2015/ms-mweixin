package net.mingsoft.weixin.service;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.mingsoft.weixin.entity.WeixinEntity;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Binary Wang
 */
public class PayService extends WxPayServiceImpl{

  public WxPayService wxPayService(WeixinEntity weixin) {
	  WxPayConfig payConfig = new WxPayConfig();
	  payConfig.setAppId(weixin.getWeixinAppId()); //微信APPID
	  payConfig.setMchId(weixin.getWeixinPayMchId()); //微信支付商户号
	  payConfig.setMchKey(weixin.getWeixinPayKey()); //支付key
	  
	  //payConfig.setSubAppId(StringUtils.trimToNull(this.properties.getSubAppId()));
	  //payConfig.setSubMchId(StringUtils.trimToNull(this.properties.getSubMchId()));
	  //payConfig.setKeyPath(weixin);
	  
	  WxPayService wxPayService = new WxPayServiceImpl();
	  wxPayService.setConfig(payConfig);
	  return wxPayService;
  }

}