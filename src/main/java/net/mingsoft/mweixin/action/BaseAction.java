package net.mingsoft.mweixin.action;

import java.util.MissingResourceException;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.mingsoft.base.constant.Const;
import com.mingsoft.base.constant.e.BaseEnum;
import com.mingsoft.base.entity.SessionEntity;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.util.StringUtil;

import net.mingsoft.basic.util.BasicUtil;

/**
 * mweixin基础控制层
 * @author 铭飞
 * @version 
 * 版本号：100<br/>
 * 创建日期：2017-12-22 9:43:04<br/>
 * 历史修订：<br/>
 */
public class BaseAction extends com.mingsoft.basic.action.BaseAction{

	@Override
	protected String getResString(String key) {
		// TODO Auto-generated method stub
		String str = "";
		try {
			str = super.getResString(key);
		} catch (MissingResourceException e) {
			str = net.mingsoft.mweixin.constant.Const.RESOURCES.getString(key);
		}

		return str;
	}

}
