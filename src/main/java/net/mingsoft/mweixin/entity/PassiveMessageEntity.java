package net.mingsoft.mweixin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 微信被动消息回复实体
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
public class PassiveMessageEntity extends BaseEntity {

	private static final long serialVersionUID = 1513906984116L;
	
	/**
	 * 微信编号
	 */
	private Integer pmWeixinId; 
	/**
	 * 自增长ID
	 */
	private Integer pmId; 
	/**
	 * 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框
	 */
	private Integer pmEventId; 
	/**
	 * 回复的素材ID
	 */
	private Integer pmNewsId; 
	/**
	 * 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个
	 */
	private Integer pmMessageId; 
	/**
	 * 该回复所属的应用ID
	 */
	private Integer pmAppId; 
	/**
	 * 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复
	 */
	private Integer pmReplyNum; 
	/**
	 * 事件关键字
	 */
	private String pmKey; 
	/**
	 * 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)
	 */
	private Integer pmType; 
	/**
	 * 
	 */
	private String pmTag; 
	
	/**
	 * 消息回复内容
	 */
	private String pmContent; 
	
	/**
	 * 素材类型 1 回复文本消息、2 回复图片消息、3 回复语音消息、4 回复视频消息、5 回复音乐消息、6 回复图文消息
	 */
	private String pmNewType; 
	
	/**
	 * 获取素材类型
	 * @return
	 */
	public String getPmNewType() {
		return pmNewType;
	}

	/**
	 * 设置素材类型
	 * @param pmNewType
	 */
	public void setPmNewType(String pmNewType) {
		this.pmNewType = pmNewType;
	}

	/**
	 * 获取消息回复内容
	 * @return
	 */
	public String getPmContent() {
		return pmContent;
	}

	/**
	 * 设置消息回复内容
	 * @param pmContent
	 */
	public void setPmContent(String pmContent) {
		this.pmContent = pmContent;
	}

	/**
	 * 设置微信编号
	 */
	public void setPmWeixinId(Integer pmWeixinId) {
		this.pmWeixinId = pmWeixinId;
	}

	/**
	 * 获取微信编号
	 */
	public Integer getPmWeixinId() {
		return this.pmWeixinId;
	}
	/**
	 * 设置自增长ID
	 */
	public void setPmId(Integer pmId) {
		this.pmId = pmId;
	}

	/**
	 * 获取自增长ID
	 */
	public Integer getPmId() {
		return this.pmId;
	}
	/**
	 * 设置该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框
	 */
	public void setPmEventId(Integer pmEventId) {
		this.pmEventId = pmEventId;
	}

	/**
	 * 获取该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框
	 */
	public Integer getPmEventId() {
		return this.pmEventId;
	}
	/**
	 * 设置回复的素材ID
	 */
	public void setPmNewsId(Integer pmNewsId) {
		this.pmNewsId = pmNewsId;
	}

	/**
	 * 获取回复的素材ID
	 */
	public Integer getPmNewsId() {
		return this.pmNewsId;
	}
	/**
	 * 设置对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个
	 */
	public void setPmMessageId(Integer pmMessageId) {
		this.pmMessageId = pmMessageId;
	}

	/**
	 * 获取对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个
	 */
	public Integer getPmMessageId() {
		return this.pmMessageId;
	}
	/**
	 * 设置该回复所属的应用ID
	 */
	public void setPmAppId(Integer pmAppId) {
		this.pmAppId = pmAppId;
	}

	/**
	 * 获取该回复所属的应用ID
	 */
	public Integer getPmAppId() {
		return this.pmAppId;
	}
	/**
	 * 设置被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复
	 */
	public void setPmReplyNum(Integer pmReplyNum) {
		this.pmReplyNum = pmReplyNum;
	}

	/**
	 * 获取被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复
	 */
	public Integer getPmReplyNum() {
		return this.pmReplyNum;
	}
	/**
	 * 设置事件关键字
	 */
	public void setPmKey(String pmKey) {
		this.pmKey = pmKey;
	}

	/**
	 * 获取事件关键字
	 */
	public String getPmKey() {
		return this.pmKey;
	}
	/**
	 * 设置回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)
	 */
	public void setPmType(Integer pmType) {
		this.pmType = pmType;
	}

	/**
	 * 获取回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)
	 */
	public Integer getPmType() {
		return this.pmType;
	}
	/**
	 * 设置
	 */
	public void setPmTag(String pmTag) {
		this.pmTag = pmTag;
	}

	/**
	 * 获取
	 */
	public String getPmTag() {
		return this.pmTag;
	}
}