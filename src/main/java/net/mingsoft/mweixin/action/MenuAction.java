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
import net.mingsoft.mweixin.biz.IMenuBiz;
import net.mingsoft.mweixin.entity.MenuEntity;
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
 * 微信菜单管理控制层
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
@Controller("netMenuAction")
@RequestMapping("/${managerPath}/mweixin/menu")
public class MenuAction extends net.mingsoft.mweixin.action.BaseAction{
	
	/**
	 * 注入微信菜单业务层
	 */	
	@Resource(name="netMenuBizImpl")
	private IMenuBiz menuBiz;
	
	/**
	 * 返回主界面index
	 */
	@RequestMapping("/index")
	public String index(HttpServletResponse response,HttpServletRequest request){
		return view ("/mweixin/menu/index");
	}
	
	/**
	 * 查询微信菜单列表
	 * @param menu 微信菜单实体
	 * <i>menu参数包含字段信息参考：</i><br/>
	 * menuId 菜单自增长编号<br/>
	 * menuAppId 菜单所属商家编号<br/>
	 * menuTitle 单菜名称<br/>
	 * menuUrl 单菜链接地址<br/>
	 * menuStatus 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId 父菜单编号<br/>
	 * menuType 菜单属性 0:链接 1:回复<br/>
	 * menuSort <br/>
	 * menuStyle 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId 授权数据编号<br/>
	 * menuWeixinId 微信编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>[<br/>
	 * { <br/>
	 * menuId: 菜单自增长编号<br/>
	 * menuAppId: 菜单所属商家编号<br/>
	 * menuTitle: 单菜名称<br/>
	 * menuUrl: 单菜链接地址<br/>
	 * menuStatus: 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId: 父菜单编号<br/>
	 * menuType: 菜单属性 0:链接 1:回复<br/>
	 * menuSort: <br/>
	 * menuStyle: 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId: 授权数据编号<br/>
	 * menuWeixinId: 微信编号<br/>
	 * }<br/>
	 * ]</dd><br/>	 
	 */
	@RequestMapping("/list")
	@ResponseBody
	public void list(@ModelAttribute MenuEntity menu,HttpServletResponse response, HttpServletRequest request,ModelMap model,BindingResult result) {
		BasicUtil.startPage();
		List menuList = menuBiz.query(menu);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(menuList,(int)BasicUtil.endPage(menuList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面menu_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute MenuEntity menu,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		if(menu.getMenuId()<=0){
			BaseEntity menuEntity = menuBiz.getEntity(menu.getMenuId());			
			model.addAttribute("menuEntity",menuEntity);
		}
		return view ("/mweixin/menu/form");
	}
	
	/**
	 * 获取微信菜单
	 * @param menu 微信菜单实体
	 * <i>menu参数包含字段信息参考：</i><br/>
	 * menuId 菜单自增长编号<br/>
	 * menuAppId 菜单所属商家编号<br/>
	 * menuTitle 单菜名称<br/>
	 * menuUrl 单菜链接地址<br/>
	 * menuStatus 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId 父菜单编号<br/>
	 * menuType 菜单属性 0:链接 1:回复<br/>
	 * menuSort <br/>
	 * menuStyle 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId 授权数据编号<br/>
	 * menuWeixinId 微信编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * menuId: 菜单自增长编号<br/>
	 * menuAppId: 菜单所属商家编号<br/>
	 * menuTitle: 单菜名称<br/>
	 * menuUrl: 单菜链接地址<br/>
	 * menuStatus: 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId: 父菜单编号<br/>
	 * menuType: 菜单属性 0:链接 1:回复<br/>
	 * menuSort: <br/>
	 * menuStyle: 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId: 授权数据编号<br/>
	 * menuWeixinId: 微信编号<br/>
	 * }</dd><br/>
	 */
	@RequestMapping("/get")
	@ResponseBody
	public void get(@ModelAttribute MenuEntity menu,HttpServletResponse response, HttpServletRequest request,ModelMap model){
		if(menu.getMenuId()<=0) {
			this.outJson(response, null, false, getResString("err.error", this.getResString("menu.id")));
			return;
		}
		MenuEntity _menu = (MenuEntity)menuBiz.getEntity(menu.getMenuId());
		this.outJson(response, _menu);
	}
	
	/**
	 * 保存微信菜单实体
	 * @param menu 微信菜单实体
	 * <i>menu参数包含字段信息参考：</i><br/>
	 * menuId 菜单自增长编号<br/>
	 * menuAppId 菜单所属商家编号<br/>
	 * menuTitle 单菜名称<br/>
	 * menuUrl 单菜链接地址<br/>
	 * menuStatus 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId 父菜单编号<br/>
	 * menuType 菜单属性 0:链接 1:回复<br/>
	 * menuSort <br/>
	 * menuStyle 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId 授权数据编号<br/>
	 * menuWeixinId 微信编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * menuId: 菜单自增长编号<br/>
	 * menuAppId: 菜单所属商家编号<br/>
	 * menuTitle: 单菜名称<br/>
	 * menuUrl: 单菜链接地址<br/>
	 * menuStatus: 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId: 父菜单编号<br/>
	 * menuType: 菜单属性 0:链接 1:回复<br/>
	 * menuSort: <br/>
	 * menuStyle: 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId: 授权数据编号<br/>
	 * menuWeixinId: 微信编号<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/save")
	@ResponseBody
	@RequiresPermissions("menu:save")
	public void save(@ModelAttribute MenuEntity menu, HttpServletResponse response, HttpServletRequest request,BindingResult result) {
		//验证菜单所属商家编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuAppId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.app.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuAppId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.app.id"), "1", "10"));
			return;			
		}
		//验证单菜名称的值是否合法			
		if(StringUtil.isBlank(menu.getMenuTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.title")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuTitle()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.title"), "1", "15"));
			return;			
		}
		//验证单菜链接地址的值是否合法			
		if(StringUtil.isBlank(menu.getMenuUrl())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.url")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuUrl()+"", 1, 300)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.url"), "1", "300"));
			return;			
		}
		//验证菜单状态 0：不启用 1：启用的值是否合法			
		if(StringUtil.isBlank(menu.getMenuStatus())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.status")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuStatus()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.status"), "1", "10"));
			return;			
		}
		//验证父菜单编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuMenuId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.menu.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuMenuId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.menu.id"), "1", "10"));
			return;			
		}
		//验证菜单属性 0:链接 1:回复的值是否合法			
		if(StringUtil.isBlank(menu.getMenuType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.type")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuType()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.type"), "1", "10"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(menu.getMenuSort())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.sort")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuSort()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.sort"), "1", "10"));
			return;			
		}
		//验证类型：1文本 2图文 4外链接的值是否合法			
		if(StringUtil.isBlank(menu.getMenuStyle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.style")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuStyle()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.style"), "1", "10"));
			return;			
		}
		//验证授权数据编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuOauthId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.oauth.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuOauthId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.oauth.id"), "1", "10"));
			return;			
		}
		//验证微信编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuWeixinId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.weixin.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuWeixinId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.weixin.id"), "1", "10"));
			return;			
		}
		menuBiz.saveEntity(menu);
		this.outJson(response, JSONObject.toJSONString(menu));
	}
	
	/**
	 * @param menu 微信菜单实体
	 * <i>menu参数包含字段信息参考：</i><br/>
	 * menuId:多个menuId直接用逗号隔开,例如menuId=1,2,3,4
	 * 批量删除微信菜单
	 *            <dt><span class="strong">返回</span></dt><br/>
	 *            <dd>{code:"错误编码",<br/>
	 *            result:"true｜false",<br/>
	 *            resultMsg:"错误信息"<br/>
	 *            }</dd>
	 */
	@RequestMapping("/delete")
	@ResponseBody
	@RequiresPermissions("menu:del")
	public void delete(@RequestBody List<MenuEntity> menus,HttpServletResponse response, HttpServletRequest request) {
		int[] ids = new int[menus.size()];
		for(int i = 0;i<menus.size();i++){
			ids[i] =menus.get(i).getMenuId() ;
		}
		menuBiz.delete(ids);		
		this.outJson(response, true);
	}
	
	/** 
	 * 更新微信菜单信息微信菜单
	 * @param menu 微信菜单实体
	 * <i>menu参数包含字段信息参考：</i><br/>
	 * menuId 菜单自增长编号<br/>
	 * menuAppId 菜单所属商家编号<br/>
	 * menuTitle 单菜名称<br/>
	 * menuUrl 单菜链接地址<br/>
	 * menuStatus 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId 父菜单编号<br/>
	 * menuType 菜单属性 0:链接 1:回复<br/>
	 * menuSort <br/>
	 * menuStyle 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId 授权数据编号<br/>
	 * menuWeixinId 微信编号<br/>
	 * <dt><span class="strong">返回</span></dt><br/>
	 * <dd>{ <br/>
	 * menuId: 菜单自增长编号<br/>
	 * menuAppId: 菜单所属商家编号<br/>
	 * menuTitle: 单菜名称<br/>
	 * menuUrl: 单菜链接地址<br/>
	 * menuStatus: 菜单状态 0：不启用 1：启用<br/>
	 * menuMenuId: 父菜单编号<br/>
	 * menuType: 菜单属性 0:链接 1:回复<br/>
	 * menuSort: <br/>
	 * menuStyle: 类型：1文本 2图文 4外链接<br/>
	 * menuOauthId: 授权数据编号<br/>
	 * menuWeixinId: 微信编号<br/>
	 * }</dd><br/>
	 */
	@PostMapping("/update")
	@ResponseBody	
	@RequiresPermissions("menu:update") 
	public void update(@ModelAttribute MenuEntity menu, HttpServletResponse response,
			HttpServletRequest request) {
		//验证菜单所属商家编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuAppId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.app.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuAppId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.app.id"), "1", "10"));
			return;			
		}
		//验证单菜名称的值是否合法			
		if(StringUtil.isBlank(menu.getMenuTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.title")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuTitle()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.title"), "1", "15"));
			return;			
		}
		//验证单菜链接地址的值是否合法			
		if(StringUtil.isBlank(menu.getMenuUrl())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.url")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuUrl()+"", 1, 300)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.url"), "1", "300"));
			return;			
		}
		//验证菜单状态 0：不启用 1：启用的值是否合法			
		if(StringUtil.isBlank(menu.getMenuStatus())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.status")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuStatus()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.status"), "1", "10"));
			return;			
		}
		//验证父菜单编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuMenuId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.menu.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuMenuId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.menu.id"), "1", "10"));
			return;			
		}
		//验证菜单属性 0:链接 1:回复的值是否合法			
		if(StringUtil.isBlank(menu.getMenuType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.type")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuType()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.type"), "1", "10"));
			return;			
		}
		//验证的值是否合法			
		if(StringUtil.isBlank(menu.getMenuSort())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.sort")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuSort()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.sort"), "1", "10"));
			return;			
		}
		//验证类型：1文本 2图文 4外链接的值是否合法			
		if(StringUtil.isBlank(menu.getMenuStyle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.style")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuStyle()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.style"), "1", "10"));
			return;			
		}
		//验证授权数据编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuOauthId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.oauth.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuOauthId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.oauth.id"), "1", "10"));
			return;			
		}
		//验证微信编号的值是否合法			
		if(StringUtil.isBlank(menu.getMenuWeixinId())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.weixin.id")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuWeixinId()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.weixin.id"), "1", "10"));
			return;			
		}
		menuBiz.updateEntity(menu);
		this.outJson(response, JSONObject.toJSONString(menu));
	}
		
}