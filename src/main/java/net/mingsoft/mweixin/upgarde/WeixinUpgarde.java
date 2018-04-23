package net.mingsoft.mweixin.upgarde;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.mingsoft.base.constant.Const;
import com.mingsoft.base.entity.ResultJson;
import com.mingsoft.basic.biz.IModelBiz;
import com.mingsoft.basic.biz.IRoleModelBiz;
import com.mingsoft.basic.biz.impl.ModelBizImpl;
import com.mingsoft.basic.biz.impl.RoleModelBizImpl;
import com.mingsoft.basic.entity.ModelEntity;
import com.mingsoft.basic.entity.RoleModelEntity;
import com.mingsoft.util.AESUtil;
import com.mingsoft.util.StringUtil;
//import com.mingsoft.util.proxy.Header;
//import com.mingsoft.util.proxy.Proxy;
import net.mingsoft.mweixin.action.BaseAction;

import net.mingsoft.base.util.PropertiesUtil;
import net.mingsoft.base.util.SpringUtil;

public class WeixinUpgarde  extends BaseAction {
	/**
	 * 支付升级包
	 */
	private static final long serialVersionUID = 7575749647205573895L;
	
	IModelBiz modelBiz = (IModelBiz) SpringUtil.getBean(IModelBiz.class);
	IRoleModelBiz roleModelBiz = (IRoleModelBiz) SpringUtil.getBean(IRoleModelBiz.class);
	
	public ResultJson setup() {
		ResultJson result = new ResultJson();

//		// 检查当前系统是拥有代码
//		if (!this.checkModel("com.mingsoft.pay.action.PayAction")) {
//			result.setResult(false);
//			result.setResultMsg("请先使用源码或Maven方式加载模块到系统！");
//			return result;
//		}
//
//		// 获取当前模块版本号
//		String version = this.getVersion("com/mingsoft/pay", "21000000");
//		if (version == null) {
//			result.setResult(false);
//			result.setResultMsg("当前系统模块版本号异常！");
//			return result;
//		}

		// 业务处理代码
		IModelBiz modelBiz = (IModelBiz) SpringUtil.getBean(IModelBiz.class);
		IRoleModelBiz roleModelBiz = (IRoleModelBiz) SpringUtil.getBean(IRoleModelBiz.class);
		ModelEntity model = modelBiz.getEntityByModelCode("21000000");
		ModelEntity modelChild = modelBiz.getEntityByModelCode("21000100");
		if (modelChild != null && !StringUtil.isBlank(modelChild.getModelParentIds())) {
			result.setResult(false);
			result.setResultMsg("当前版本已经是最新！");
			return result;
		}
		  if(model == null && modelChild == null){
		    	model = new ModelEntity();
		    	modelChild =  new ModelEntity();
				model.setModelTitle("支付交易");
				model.setModelCode("21000000");
				model.setModelIcon("&#xe971;");
				model.setModelManagerId(0);
				model.setModelIsMenu(1);
				model.setModelDatetime(new Timestamp(System.currentTimeMillis()));
				modelBiz.saveEntity(model);
			// 子菜单的安装
				modelChild = new ModelEntity();
				modelChild.setModelModelId(model.getModelId());
				modelChild.setModelTitle("微信支付设置");
				modelChild.setModelCode("21000100");
				modelChild.setModelUrl("/bank/pay/alipay.do");
				modelChild.setModelManagerId(0);
				modelChild.setModelIsMenu(1);
				modelChild.setModelParentIds(model.getModelId()+"");
				modelChild.setModelDatetime(new Timestamp(System.currentTimeMillis()));
				modelBiz.saveEntity(modelChild);
				
				// 角色的权限
				List list = new ArrayList();
				HttpServletRequest request = SpringUtil.getRequest();
				RoleModelEntity roleMode = new RoleModelEntity();
				roleMode.setModelId(model.getModelId());
				roleMode.setRoleId(this.getManagerBySession(request).getManagerRoleID());
				list.add(roleMode);
				RoleModelEntity _roleMode = new RoleModelEntity();
				_roleMode.setModelId(modelChild.getModelId());
				_roleMode.setRoleId(this.getManagerBySession(request).getManagerRoleID());
				list.add(_roleMode);
				roleModelBiz.saveEntity(list);
		} else {
			modelChild.setModelParentIds(model.getModelId() + "");
			modelBiz.updateEntity(modelChild);
		}
		//功能添加
		ModelEntity modelEntity = modelBiz.getEntityByModelCode("21000100");
		int modelId = modelEntity.getModelId();
		String modelParentIds = modelId+"";
		if(modelParentIds != null){
			modelParentIds = modelId + "," + modelEntity.getModelParentIds();
		}
		//组织子功能的sql
		String functionSql = "INSERT INTO model (model_title,model_code,model_modelid,model_url,model_ismenu,model_parent_ids)VALUES('查看','21000101',"+modelId+",'pay:view',0,'"+modelParentIds+"')";
		modelBiz.excuteSql(functionSql);
		//子功能查看权限
		List list = new ArrayList();
		HttpServletRequest request = SpringUtil.getRequest();
		ModelEntity functionModel = modelBiz.getEntityByModelCode("21000101");
		int people = this.getManagerBySession(request).getManagerRoleID();
		RoleModelEntity roleModeView = new RoleModelEntity();
		roleModeView.setModelId(functionModel.getModelId());
		roleModeView.setRoleId(people);
		list.add(roleModeView);
		roleModelBiz.saveEntity(list);
		result.setResult(true);
		result.setResultMsg("安装升级成功");
		//this.finish(ck, id);
		return result;
	}
	
	
//	private void finish(String ck,String id) {
//		Header header = new Header("mstore.mingsoft.net", Const.UTF8);
//		header.setHeader("ms", "upgrader");
//		header.setHeader("cookie", ck);
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("id", id);
//		Proxy.post("http://mstore.mingsoft.net/people/people/mstore/finsh.do", header, params, Const.UTF8);
//	}

	private boolean checkModel(String className) {
		try {
			Class cls = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String getVersion(String pak,String modelCode) {
		try {
			String value = PropertiesUtil.get(pak+"/resources/resources_zh_CN.properties", "version");
			value = AESUtil.decrypt(value, StringUtil.Md5(modelCode).substring(16));
			if(value==null) {
				return null;
			}
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}