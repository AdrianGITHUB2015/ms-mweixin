package net.mingsoft.mweixin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import com.mingsoft.base.entity.BaseEntity;
import java.util.Date;

 /**
 * 微信菜单实体
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
public class MenuEntity extends BaseEntity {

	private static final long serialVersionUID = 1513906984073L;
	
	/**
	 * 菜单自增长编号
	 */
	private Integer menuId; 
	/**
	 * 菜单所属商家编号
	 */
	private Integer menuAppId; 
	/**
	 * 单菜名称
	 */
	private String menuTitle; 
	/**
	 * 单菜链接地址
	 */
	private String menuUrl; 
	/**
	 * 菜单状态 0：不启用 1：启用
	 */
	private Integer menuStatus; 
	/**
	 * 父菜单编号
	 */
	private Integer menuMenuId; 
	/**
	 * 菜单属性 0:链接 1:回复
	 */
	private Integer menuType; 
	/**
	 * 
	 */
	private Integer menuSort; 
	/**
	 * 类型：1文本 2图文 4外链接
	 */
	private Integer menuStyle; 
	/**
	 * 授权数据编号
	 */
	private Integer menuOauthId; 
	/**
	 * 微信编号
	 */
	private Integer menuWeixinId; 
	
		
	/**
	 * 设置菜单自增长编号
	 */
	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	/**
	 * 获取菜单自增长编号
	 */
	public Integer getMenuId() {
		return this.menuId;
	}
	/**
	 * 设置菜单所属商家编号
	 */
	public void setMenuAppId(Integer menuAppId) {
		this.menuAppId = menuAppId;
	}

	/**
	 * 获取菜单所属商家编号
	 */
	public Integer getMenuAppId() {
		return this.menuAppId;
	}
	/**
	 * 设置单菜名称
	 */
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	/**
	 * 获取单菜名称
	 */
	public String getMenuTitle() {
		return this.menuTitle;
	}
	/**
	 * 设置单菜链接地址
	 */
	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/**
	 * 获取单菜链接地址
	 */
	public String getMenuUrl() {
		return this.menuUrl;
	}
	/**
	 * 设置菜单状态 0：不启用 1：启用
	 */
	public void setMenuStatus(Integer menuStatus) {
		this.menuStatus = menuStatus;
	}

	/**
	 * 获取菜单状态 0：不启用 1：启用
	 */
	public Integer getMenuStatus() {
		return this.menuStatus;
	}
	/**
	 * 设置父菜单编号
	 */
	public void setMenuMenuId(Integer menuMenuId) {
		this.menuMenuId = menuMenuId;
	}

	/**
	 * 获取父菜单编号
	 */
	public Integer getMenuMenuId() {
		return this.menuMenuId;
	}
	/**
	 * 设置菜单属性 0:链接 1:回复
	 */
	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	/**
	 * 获取菜单属性 0:链接 1:回复
	 */
	public Integer getMenuType() {
		return this.menuType;
	}
	/**
	 * 设置
	 */
	public void setMenuSort(Integer menuSort) {
		this.menuSort = menuSort;
	}

	/**
	 * 获取
	 */
	public Integer getMenuSort() {
		return this.menuSort;
	}
	/**
	 * 设置类型：1文本 2图文 4外链接
	 */
	public void setMenuStyle(Integer menuStyle) {
		this.menuStyle = menuStyle;
	}

	/**
	 * 获取类型：1文本 2图文 4外链接
	 */
	public Integer getMenuStyle() {
		return this.menuStyle;
	}
	/**
	 * 设置授权数据编号
	 */
	public void setMenuOauthId(Integer menuOauthId) {
		this.menuOauthId = menuOauthId;
	}

	/**
	 * 获取授权数据编号
	 */
	public Integer getMenuOauthId() {
		return this.menuOauthId;
	}
	/**
	 * 设置微信编号
	 */
	public void setMenuWeixinId(Integer menuWeixinId) {
		this.menuWeixinId = menuWeixinId;
	}

	/**
	 * 获取微信编号
	 */
	public Integer getMenuWeixinId() {
		return this.menuWeixinId;
	}
}