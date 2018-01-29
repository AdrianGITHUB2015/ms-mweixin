package net.mingsoft.mweixin.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.mingsoft.base.entity.BaseEntity;
import com.mingsoft.base.filter.DateValueFilter;
import com.mingsoft.base.filter.DoubleValueFilter;
import com.mingsoft.util.StringUtil;
import com.mingsoft.weixin.entity.WeixinEntity;
import com.xiaoleilu.hutool.util.ObjectUtil;
import com.xiaoleilu.hutool.util.StrUtil;

import me.chanjar.weixin.common.api.WxConsts.MenuButtonType;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.exception.WxErrorException;
import net.mingsoft.base.util.JSONObject;
import net.mingsoft.basic.bean.EUListBean;
import net.mingsoft.basic.util.BasicUtil;
import net.mingsoft.mweixin.biz.IMenuBiz;
import net.mingsoft.mweixin.entity.MenuEntity;
	
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
	
	 private String menuId = null;
	
	 
	/**
	 */
	@RequestMapping("/create")
	public void create(HttpServletResponse response,HttpServletRequest request){
		MenuEntity menuEntity = new MenuEntity();
		menuEntity.setMenuAppId(BasicUtil.getAppId());
		//取出微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin == null || weixin.getWeixinId() <= 0){
			this.outJson(response, null, false);
			return;
		}
		menuEntity.setMenuWeixinId(weixin.getWeixinId());
		//第一步读取当前微信对应的所有菜单信息
		List<MenuEntity> menuList = menuBiz.query(menuEntity);
		WxMenu menu = new WxMenu();
		//第二步将本地菜单赋值到WxMenuButton对象
		for(MenuEntity _menuEntity : menuList){
			WxMenuButton parentButton = new WxMenuButton();
			//按钮启用才发布
			if(_menuEntity.getMenuStatus() == 1){
				if(_menuEntity.getMenuMenuId() == null){
					//获取一级按钮
					switch (_menuEntity.getMenuType()){
						case 2: parentButton.setType(MenuButtonType.CLICK);
						parentButton.setKey(_menuEntity.getMenuId()+"");
						break;
						case 1: parentButton.setType(MenuButtonType.VIEW);
						parentButton.setUrl(_menuEntity.getMenuContent());
						break;
					}	
					parentButton.setName(_menuEntity.getMenuTitle());
			        MenuEntity parenMenutEntity = new MenuEntity();
			        parenMenutEntity.setMenuMenuId(_menuEntity.getMenuId());
			        //查询该按钮下的子按钮
			        menuList = menuBiz.query(parenMenutEntity);
			        for(MenuEntity menuSubEntity : menuList){
			        	if(menuSubEntity.getMenuStatus() == 1){
				        	WxMenuButton subButton = new WxMenuButton();
				        	switch (menuSubEntity.getMenuType()){
								case 2: subButton.setType(MenuButtonType.CLICK);
								subButton.setKey(menuSubEntity.getMenuId()+"");
								break;
								case 1: subButton.setType(MenuButtonType.VIEW);
								subButton.setUrl(menuSubEntity.getMenuContent());
								break;
							}
				        	subButton.setName(menuSubEntity.getMenuTitle());
				        	parentButton.getSubButtons().add(subButton);
			        	}
			        }
			        //第三步将WxMenuButton赋值WxMenu
			        menu.getButtons().add(parentButton);
				}
			}
		}
		//当前的微信信息
		String weixinNo = weixin.getWeixinNo();
		// 菜单发布
		try {
			this.builderWeixinService(weixinNo).getMenuService().menuCreate(menu);
			this.outJson(response, null, true);
			return;
		} catch (WxErrorException e) {
			e.printStackTrace();
			this.outJson(response, null, false);
			return;
		}
	}
	 
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
	 * menuTitle 菜单名称<br/>
	 * menuUrl 菜单链接地址<br/>
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
	 * menuTitle: 菜单名称<br/>
	 * menuUrl: 菜单链接地址<br/>
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
		menu.setMenuAppId(BasicUtil.getAppId());
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return;
		}
		menu.setMenuWeixinId(weixin.getWeixinId());
		BasicUtil.startPage();
		List menuList = menuBiz.query(menu);
		this.outJson(response, net.mingsoft.base.util.JSONArray.toJSONString(new EUListBean(menuList,(int)BasicUtil.endPage(menuList).getTotal()),new DoubleValueFilter(),new DateValueFilter()));
	}
	
	/**
	 * 返回编辑界面menu_form
	 */
	@RequestMapping("/form")
	public String form(@ModelAttribute MenuEntity menu,HttpServletResponse response,HttpServletRequest request,ModelMap model){
		MenuEntity menuSuper = new MenuEntity();
		if(menu.getMenuId() != null){
			MenuEntity menuEntity = (MenuEntity) menuBiz.getEntity(menu.getMenuId());
			if(ObjectUtil.isNotNull(menuEntity)){
				if(menuEntity.getMenuMenuId() != null){
					//获取微信菜单的父级菜单
					menuSuper = (MenuEntity) menuBiz.getEntity(menuEntity.getMenuMenuId());
				}
			}
			model.addAttribute("menuEntity",menuEntity);
		}
		//获取微信实体
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return null;
		}
		MenuEntity _menu = new MenuEntity();
		_menu.setMenuWeixinId(weixin.getWeixinId());
		_menu.setMenuAppId(BasicUtil.getAppId());
		_menu.setMenuMenuId(null);
		List<MenuEntity> listMenu = menuBiz.query(_menu);
		model.addAttribute("listMenu",JSONArray.toJSONString(listMenu));
		model.addAttribute("menuSuper", menuSuper);
		return view ("/mweixin/menu/form");
	}
	
	/**
	 * 获取微信菜单
	 * @param menu 微信菜单实体
	 * <i>menu参数包含字段信息参考：</i><br/>
	 * menuId 菜单自增长编号<br/>
	 * menuAppId 菜单所属商家编号<br/>
	 * menuTitle 菜单名称<br/>
	 * menuUrl 菜单链接地址<br/>
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
	 * menuTitle: 菜单名称<br/>
	 * menuUrl: 菜单链接地址<br/>
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
	 * menuTitle 菜单名称<br/>
	 * menuUrl 菜单链接地址<br/>
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
	 * menuTitle: 菜单名称<br/>
	 * menuUrl: 菜单链接地址<br/>
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
	public void save(@ModelAttribute MenuEntity menu, HttpServletResponse response, HttpServletRequest request,BindingResult result) {
		//验证菜单名称的值是否合法			
		if(StringUtil.isBlank(menu.getMenuTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.title")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuTitle()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.title"), "1", "15"));
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
		//验证菜单属性 0:链接 1:回复的值是否合法			
		if(StringUtil.isBlank(menu.getMenuType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.type")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuType()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.type"), "1", "10"));
			return;			
		}
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return;
		}
		menu.setMenuWeixinId(weixin.getWeixinId()); 
		menu.setMenuAppId(BasicUtil.getAppId());
		menuBiz.saveEntity(menu);
		this.outJson(response,JSONObject.toJSONString(menu));
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
	 * menuTitle 菜单名称<br/>
	 * menuUrl 菜单链接地址<br/>
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
	 * menuTitle: 菜单名称<br/>
	 * menuUrl: 菜单链接地址<br/>
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
	public void update(@ModelAttribute MenuEntity menu, HttpServletResponse response,
			HttpServletRequest request) {
		//验证菜单名称的值是否合法			
		if(StringUtil.isBlank(menu.getMenuTitle())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.title")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuTitle()+"", 1, 15)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.title"), "1", "15"));
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
		//验证菜单属性 0:链接 1:回复的值是否合法			
		if(StringUtil.isBlank(menu.getMenuType())){
			this.outJson(response, null,false,getResString("err.empty", this.getResString("menu.type")));
			return;			
		}
		if(!StringUtil.checkLength(menu.getMenuType()+"", 1, 10)){
			this.outJson(response, null, false, getResString("err.length", this.getResString("menu.type"), "1", "10"));
			return;			
		}
		menuBiz.updateEntity(menu);
		this.outJson(response, JSONObject.toJSONString(menu));
	}
	
	/**
	 * 检查的菜单的数量是否符合微信的
	 * @param menu
	 * @return
	 */
	@RequestMapping("/check")
	@ResponseBody
	public void check(@ModelAttribute MenuEntity menu, HttpServletResponse response,
			HttpServletRequest request){
		WeixinEntity weixin = this.getWeixinSession(request);
		if(weixin == null || weixin.getWeixinId()<=0){
			this.outJson(response, null, false);
			return;
		}
		MenuEntity menuEntity = new MenuEntity();
		menuEntity.setMenuAppId(BasicUtil.getAppId());
		menuEntity.setMenuWeixinId(weixin.getWeixinId());
		List<MenuEntity> menuList = menuBiz.query(menuEntity);
		//计算一级菜单的总数
		int i = 0;
		for(MenuEntity _menuEntity : menuList){
			if(_menuEntity.getMenuMenuId() == null){
				i++;
				if(i > 3){
					this.outJson(response,null,false,this.getResString("menu.parent.max"));
					return ;
				}
				MenuEntity subMenu = new MenuEntity();
				subMenu.setMenuMenuId(_menuEntity.getMenuId());
				if(menuBiz.query(subMenu).size() > 7){
					this.outJson(response,null,false,this.getResString("menu.son.max"));
					return ;
				}
			}
		}
		this.outJson(response, true);
	}
}