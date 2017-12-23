<@ms.html5>
	 <@ms.nav title="微信菜单编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="menuForm" isvalidation=true>
    		<@ms.hidden name="menuId" value="${(menuEntity.menuId)?default('')}"/>
    			<@ms.number label="菜单所属商家编号" name="menuAppId" value="${(menuEntity.menuAppId)?default('')}" width="240px;" placeholder="请输入菜单所属商家编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"菜单所属商家编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="单菜名称" name="menuTitle" value="${(menuEntity.menuTitle)?default('')}"  width="240px;" placeholder="请输入单菜名称" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"单菜名称长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="单菜链接地址" name="menuUrl" value="${(menuEntity.menuUrl)?default('')}"  width="240px;" placeholder="请输入单菜链接地址" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"单菜链接地址长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="菜单状态 0：不启用 1：启用" name="menuStatus" value="${(menuEntity.menuStatus)?default('')}" width="240px;" placeholder="请输入菜单状态 0：不启用 1：启用" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"菜单状态 0：不启用 1：启用长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="父菜单编号" name="menuMenuId" value="${(menuEntity.menuMenuId)?default('')}" width="240px;" placeholder="请输入父菜单编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"父菜单编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="菜单属性 0:链接 1:回复" name="menuType" value="${(menuEntity.menuType)?default('')}" width="240px;" placeholder="请输入菜单属性 0:链接 1:回复" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"菜单属性 0:链接 1:回复长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="" name="menuSort" value="${(menuEntity.menuSort)?default('')}" width="240px;" placeholder="请输入" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="类型：1文本 2图文 4外链接" name="menuStyle" value="${(menuEntity.menuStyle)?default('')}" width="240px;" placeholder="请输入类型：1文本 2图文 4外链接" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"类型：1文本 2图文 4外链接长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="授权数据编号" name="menuOauthId" value="${(menuEntity.menuOauthId)?default('')}" width="240px;" placeholder="请输入授权数据编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"授权数据编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="微信编号" name="menuWeixinId" value="${(menuEntity.menuWeixinId)?default('')}" width="240px;" placeholder="请输入微信编号" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"微信编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
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
