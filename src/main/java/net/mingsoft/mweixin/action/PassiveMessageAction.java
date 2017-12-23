package net.mingsoft.mweixin.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindingResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.ModelMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.mingsoft.mweixin.biz.IPassiveMessageBiz;
import net.mingsoft.mweixin.entity.PassiveMessageEntity;
import net.mingsoft.base.util.JSONObject;
import com.mingsoft.util.PageUtil;
import com.mingsoft.util.StringUtil;
import com.mingsoft.base.entity.BaseEntity;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.basic.bean.ListBean;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import net.mingsoft.basic.bean.EUListBean;
	
/**
 * 微信被动消息回复管理控制层
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
@Controller("netPassiveMessageAction")
@RequestMapping("/${managerPath}/mweixin/passiveMessage")
public class PassiveMessageAction extends net.mingsoft.mweixin.action.BaseAction{
	
	/**
	 * 注入微信被动消息回复业务层
	 */	
	@Resource(name="netPassiveMessageBizImpl")
	private IPassiveMessageBiz passiveMessageBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/mweixin/passive_message/index");
	}
	
	/**
	 * 查询微信被动消息回复列表
	 * @param passiveMessage 微信被动消息回复实体
	 * <i>passiveMessage参数包含字段信息参考：</i><br/>
	 * pmWeixinId 微信编号<br/>
	 * pmId 自增长ID<br/>
	 * pmEventId 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId 回复的素材ID<br/>
	 * pmMessageId 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId 该回复所属的应用ID<br/>
	 * pmReplyNum 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey 事件关键字<br/>
	 * pmType 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag <br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * pmWeixinId: 微信编号<br/>
	 * pmId: 自增长ID<br/>
	 * pmEventId: 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId: 回复的素材ID<br/>
	 * pmMessageId: 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId: 该回复所属的应用ID<br/>
	 * pmReplyNum: 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey: 事件关键字<br/>
	 * pmType: 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag: <br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute PassiveMessageEntity passiveMessage,HttpServletResponse response, HttpServletRequest request,ModelMap model,BindingResult result) {
		BasicUtil.startPage();
		List passiveMessageList = passiveMessageBiz.query(passiveMessage);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(passiveMessageList,(int)BasicUtil.endPage(passiveMessageList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面passiveMessage_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute PassiveMessageEntity passiveMessage,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(passiveMessage.getPmId()<=0){
			BaseEntity passiveMessageEntity = passiveMessageBiz.getEntity(passiveMessage.getPmId());			
			model.addAttribute("passiveMessageEntity",passiveMessageEntity);
		}
		return view ("/mweixin/passive_message/form");
	}
	
	/**
	 * 获取微信被动消息回复
	 * @param passiveMessage 微信被动消息回复实体
	 * <i>passiveMessage参数包含字段信息参考：</i><br/>
	 * pmWeixinId 微信编号<br/>
	 * pmId 自增长ID<br/>
	 * pmEventId 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId 回复的素材ID<br/>
	 * pmMessageId 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId 该回复所属的应用ID<br/>
	 * pmReplyNum 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey 事件关键字<br/>
	 * pmType 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag <br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * pmWeixinId: 微信编号<br/>
	 * pmId: 自增长ID<br/>
	 * pmEventId: 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId: 回复的素材ID<br/>
	 * pmMessageId: 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId: 该回复所属的应用ID<br/>
	 * pmReplyNum: 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey: 事件关键字<br/>
	 * pmType: 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag: <br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute PassiveMessageEntity passiveMessage,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(passiveMessage.getPmId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("pm.id")));
			return;
		}
		PassiveMessageEntity _passiveMessage = (PassiveMessageEntity)passiveMessageBiz.getEntity(passiveMessage.getPmId());
		this.outJson(response, _passiveMessage);
	}
	
	/**
	 * 保存微信被动消息回复实体
	 * @param passiveMessage 微信被动消息回复实体
	 * <i>passiveMessage参数包含字段信息参考：</i><br/>
	 * pmWeixinId 微信编号<br/>
	 * pmId 自增长ID<br/>
	 * pmEventId 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId 回复的素材ID<br/>
	 * pmMessageId 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId 该回复所属的应用ID<br/>
	 * pmReplyNum 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey 事件关键字<br/>
	 * pmType 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag <br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * pmWeixinId: 微信编号<br/>
	 * pmId: 自增长ID<br/>
	 * pmEventId: 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId: 回复的素材ID<br/>
	 * pmMessageId: 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId: 该回复所属的应用ID<br/>
	 * pmReplyNum: 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey: 事件关键字<br/>
	 * pmType: 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag: <br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	@RequiresPermissions("passiveMessage:save")
	public void save(@ModelAttribute PassiveMessageEntity passiveMessage, HttpServletResponse response, HttpServletRequest request,BindingResult result) {
		//验证回复的素材ID的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmNewsId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.news.id")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmNewsId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.news.id"), "1", "10"));
			return;			
		}
		//验证被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmReplyNum())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.reply.num")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmReplyNum()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.reply.num"), "1", "10"));
			return;			
		}
		//验证事件关键字的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmKey())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.key")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmKey()+"", 1, 300)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.key"), "1", "300"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmTag())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.tag")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmTag()+"", 1, 30)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.tag"), "1", "30"));
			return;			
		}
		passiveMessageBiz.saveEntity(passiveMessage);
		this.outJson(response, JSONObject.toJSONString(passiveMessage));
	}
	
	/**
	 * @param passiveMessage 微信被动消息回复实体
	 * <i>passiveMessage参数包含字段信息参考：</i><br/>
	 * pmId:多个pmId直接用逗号隔开,例如pmId=1,2,3,4
	 * 批量删除微信被动消息回复
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("passiveMessage:del")
	public void delete(@RequestBody List<PassiveMessageEntity> passiveMessages,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[passiveMessages.size()];
		for(int i = 0;i<passiveMessages.size();i++){
			ids[i] =passiveMessages.get(i).getPmId() ;
		}
		passiveMessageBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新微信被动消息回复信息微信被动消息回复
	 * @param passiveMessage 微信被动消息回复实体
	 * <i>passiveMessage参数包含字段信息参考：</i><br/>
	 * pmWeixinId 微信编号<br/>
	 * pmId 自增长ID<br/>
	 * pmEventId 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId 回复的素材ID<br/>
	 * pmMessageId 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId 该回复所属的应用ID<br/>
	 * pmReplyNum 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey 事件关键字<br/>
	 * pmType 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag <br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * pmWeixinId: 微信编号<br/>
	 * pmId: 自增长ID<br/>
	 * pmEventId: 该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框<br/>
	 * pmNewsId: 回复的素材ID<br/>
	 * pmMessageId: 对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个<br/>
	 * pmAppId: 该回复所属的应用ID<br/>
	 * pmReplyNum: 被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复<br/>
	 * pmKey: 事件关键字<br/>
	 * pmType: 回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)<br/>
	 * pmTag: <br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	
	@RequiresPermissions("passiveMessage:update") 
	public void update(@ModelAttribute PassiveMessageEntity passiveMessage, HttpServletResponse response,
			HttpServletRequest request) {
		//验证回复的素材ID的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmNewsId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.news.id")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmNewsId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.news.id"), "1", "10"));
			return;			
		}
		//验证被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmReplyNum())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.reply.num")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmReplyNum()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.reply.num"), "1", "10"));
			return;			
		}
		//验证事件关键字的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmKey())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.key")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmKey()+"", 1, 300)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.key"), "1", "300"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(passiveMessage.getPmTag())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("pm.tag")));
			return;			
		}
		if(!StringUtil.checkLength(passiveMessage.getPmTag()+"", 1, 30)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("pm.tag"), "1", "30"));
			return;			
		}
		passiveMessageBiz.updateEntity(passiveMessage);
		this.outJson(response, JSONObject.toJSONString(passiveMessage));
	}
		
}