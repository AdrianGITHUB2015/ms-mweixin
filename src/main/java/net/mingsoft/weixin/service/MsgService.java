package net.mingsoft.weixin.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mingsoft.cms.entity.ArticleEntity;
import com.mingsoft.weixin.biz.INewsBiz;
import com.mingsoft.weixin.entity.NewsEntity;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpMassNews;
import me.chanjar.weixin.mp.bean.WxMpMassOpenIdsMessage;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpMassSendResult;
import me.chanjar.weixin.mp.bean.result.WxMpMassUploadResult;
import net.mingsoft.base.util.BaseUtil;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.biz.IPassiveMessageBiz;
import net.mingsoft.mweixin.entity.PassiveMessageEntity;
import net.mingsoft.weixin.builder.TextBuilder;
import net.mingsoft.weixin.service.PortalService;

/**
 * @author Binary Wang
 */
@Component
public class MsgService extends AbstractService {
	
	/**
	 * 注入微信被动消息回复业务层
	 */	
	@Resource(name="netPassiveMessageBizImpl")
	private IPassiveMessageBiz passiveMessageBiz;
	
	/**
	 * 素材业务层注入
	 */
	@Autowired
	private INewsBiz newsBiz;
	
	@Override
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                  Map<String, Object> context, WxMpService wxMpService,
                                  WxSessionManager sessionManager) throws WxErrorException {

    PortalService weixinService = (PortalService) wxMpService;

    if (!wxMessage.getMsgType().equals(WxConsts.XmlMsgType.EVENT)) {
      //TODO 可以选择将消息保存到本地
    }
    String msg = wxMessage.getContent();
    //获取信息
    PassiveMessageEntity passiveMessage = new PassiveMessageEntity();
    passiveMessage.setPmWeixinId(weixinService.getWeixin().getWeixinId()); 
    passiveMessage.setPmAppId(BasicUtil.getAppId());
    passiveMessage.setPmKey(msg);
    
    //通过获取的信息，查询关键字表
    passiveMessage = passiveMessageBiz.getEntity(passiveMessage);
    if(passiveMessage == null){
    	//被动回复
    	passiveMessage = new PassiveMessageEntity();
    	passiveMessage.setPmType(PassiveMessageEntity.Type.TYPE_PASSIVE);
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
	    case 6:
	    	//获取关键字对应的素材
	    	NewsEntity _news = (NewsEntity) newsBiz.getNewsByNewsId(Integer.parseInt(passiveMessage.getPmContent()));
	    	try (InputStream inputStream = ClassLoader.getSystemResourceAsStream(_news.getNewsMasterArticle().getBasicThumbnails())) {
	            // 上传照片到媒体库
	            WxMediaUploadResult uploadMediaRes = weixinService.getMaterialService()
	              .mediaUpload(WxConsts.MediaFileType.IMAGE, "jpg", inputStream);

	            // 上传图文消息
	            WxMpMassNews news = new WxMpMassNews();
	            if(_news.getChilds().size() > 0){
	            	for(ArticleEntity _article : _news.getChilds()){
	            		WxMpMassNews.WxMpMassNewsArticle article = new WxMpMassNews.WxMpMassNewsArticle();
	            		article.setTitle(_article.getBasicTitle());
	     	            article.setContent(_article.getArticleContent());
	     	            article.setThumbMediaId(uploadMediaRes.getMediaId());
	     	            news.addArticle(article);
	            	}
	            }
		    	WxMpMassUploadResult massUploadResult = wxMpService.getMassMessageService().massNewsUpload(news);
	
		    	WxMpMassOpenIdsMessage massMessage = new WxMpMassOpenIdsMessage();
		    	massMessage.setMsgType(WxConsts.KefuMsgType.NEWS);
		    	massMessage.setMediaId(massUploadResult.getMediaId());
		    	massMessage.getToUsers().add(wxMessage.getOpenId());
		    	WxMpMassSendResult massResult = wxMpService.getMassMessageService().massOpenIdsMessageSend(massMessage);
		    } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    //设置content
//    String content = passiveMessage.getPmContent();;
//    return new TextBuilder().build(content, wxMessage, weixinService);
	return null;

  }
}
