<@ms.html5>
	<style>
		.select2-container .select2-container--default {  
		 	height: 34px;  
		} 
		.select2-container .select2-selection--single{
			font: inherit;
			border: 1px solid #ccc;
		    display: block;
		    height: 34px;
		    padding: 2px 3px;
    		font-size: 14px;
    		color: rgb(85, 85, 85);
		}
	</style> 
	 <@ms.nav title="微信菜单编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="menuForm" isvalidation=true>
    		<@ms.hidden name="menuId" value="${(menuEntity.menuId)?default('')}"/>
    			<@ms.number label="菜单所属商家编号" name="menuAppId" value="${(menuEntity.menuAppId)?default('')}" width="240px;" placeholder="请输入菜单所属商家编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"菜单所属商家编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="菜单名称" name="menuTitle" value="${(menuEntity.menuTitle)?default('')}"  width="240px;" placeholder="请输入菜单名称" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"菜单名称长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="菜单链接地址" name="menuUrl" value="${(menuEntity.menuUrl)?default('')}"  width="240px;" placeholder="请输入菜单链接地址" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"菜单链接地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.select 
    				id="menuStatus"
				    name="menuStatus" 
				    label="菜单状态" 
				    width="240"  
				    list=[{"id":1,"value":"不启用"},{"id":2,"value":"启用"}] 
				    value="${(menuEntity.menuStatus)?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>
    			<@ms.number label="父菜单编号" name="menuMenuId" value="${(menuEntity.menuMenuId)?default('')}" width="240px;" placeholder="请输入父菜单编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"父菜单编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.select 
    				id="menuType"
				    name="menuType" 
				    label="菜单属性" 
				    width="240"  
				    list=[{"id":1,"value":"链接"},{"id":2,"value":"回复"}] 
				    value="${(menuEntity.menuType)?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>
    			<@ms.number label="排序" name="menuSort" value="${(menuEntity.menuSort)?default('')}" width="240px;" placeholder="请输入" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.select 
    				id="menuStyle"
				    name="menuStyle" 
				    label="菜单类型" 
				    width="240"  
				    list=[{"id":1,"value":"文本"},{"id":2,"value":"图文"},{"id":3,"value":"外链接"}] 
				    value="${(menuEntity.menuStyle)?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>
    			<@ms.number label="授权数据编号" name="menuOauthId" value="${(menuEntity.menuOauthId)?default('')}" width="240px;" placeholder="请输入授权数据编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"授权数据编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="微信编号" name="menuWeixinId" value="${(menuEntity.menuWeixinId)?default('')}" width="240px;" placeholder="请输入微信编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"微信编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	$("#menuStyle").select2({width: "210px"});
	$("#menuType").select2({width: "210px"});
	$("#menuStatus").select2({width: "210px"});
	var url = "${managerPath}/mweixin/menu/save.do";
	if($("input[name = 'menuId']").val() > 0){
		url = "${managerPath}/mweixin/menu/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#menuForm").data("bootstrapValidator").validate();
			var isValid = $("#menuForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'menuForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.menuId > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mweixin/menu/index.do";
				}
				else{
					$(".btn-success").text(btnWord);
					$(".btn-success").removeAttr("disabled");
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
				}
			}
		})
	}	
</script>
