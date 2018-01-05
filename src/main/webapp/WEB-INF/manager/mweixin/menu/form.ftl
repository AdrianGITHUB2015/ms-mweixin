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
    			<@ms.number label="父菜单编号" min=0 max=9999 maxlength="4" name="menuMenuId" value="${(menuEntity.menuMenuId)?default('')}" width="240px;" placeholder="请输入父菜单编号" validation={"required":"true", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="菜单名称" name="menuTitle" value="${(menuEntity.menuTitle)?default('')}"  width="240px;" placeholder="请输入菜单名称" validation={"required":"true","maxlength":"7","data-bv-stringlength-message":"菜单名称长度不能超过七个字符长度!", "data-bv-notempty-message":"必填项目"}/>
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
				<@ms.textarea colSm="2" name="menuContent" label="菜单内容" wrap="Soft" rows="3"  size=""  value="${(menuEntity.menuContent)?default('')}" placeholder="请输入菜单内容" validation={"required":"true","maxlength":"200","data-bv-stringlength-message":"菜单内容长度不能超过两百个字符长度!", "data-bv-notempty-message":"必填项目"}/>
				<@ms.hidden name="menuStatus" value="1"/>
				<@ms.hidden name="menuStyle" value="1"/>
				<!--<@ms.select 
    				id="menuStatus"
				    name="menuStatus" 
				    label="菜单状态" 
				    width="240"  
				    list=[{"id":1,"value":"启用"},{"id":2,"value":"不启用"}] 
				    value="${(menuEntity.menuStatus)?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>-->
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
		var menuData = $("form[name = 'menuForm']").serialize();
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:menuData,
			url:"${managerPath}/mweixin/menu/check.do",
			success: function(data) {
				if(data.result == true) { 
					$.ajax({
						type:"post",
						dataType:"json",
						data:menuData,
						url:url,
						success: function(data) {
							if(data.menuId > 0) { 
								<@ms.notify msg="保存或更新成功" type= "success" />
								location.href = "${managerPath}/mweixin/menu/index.do";
							}
							else{
								$(".btn-success").text(btnWord);
								$(".btn-success").removeAttr("disabled");
								$('.ms-notifications').offset({top:43}).notify({
								   type:'fail',
								   message: { text:"更新或保存失败" }
								}).show();
							}
						}
					})
				}	
				else{
					$(".btn-success").text(btnWord);
					$(".btn-success").removeAttr("disabled");
					$('.ms-notifications').offset({top:43}).notify({
					   type:'fail',
					   message: { text:data.resultMsg }
					}).show();
				}
			}
		})
		}
</script>
