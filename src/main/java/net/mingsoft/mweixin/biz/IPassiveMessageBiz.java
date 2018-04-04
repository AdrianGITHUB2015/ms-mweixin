package net.mingsoft.mweixin.biz;

import java.util.List;

import com.mingsoft.base.biz.IBaseBiz;

import net.mingsoft.mweixin.entity.PassiveMessageEntity;

/**
 * 微信被动消息回复业务
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
public interface IPassiveMessageBiz extends IBaseBiz {

	/**
	 * 获取回复消息实体
	 * @param passiveMessage
	 * @return
	 */
	public PassiveMessageEntity getEntity(PassiveMessageEntity passiveMessage);
	
	/**
	 * 查询关键字列表，后台使用如果类型的图文，newsTitle显示素材的标题
	 * @param passiveMessage
	 * @return
	 */
	public List<PassiveMessageEntity> query(PassiveMessageEntity passiveMessage);

}