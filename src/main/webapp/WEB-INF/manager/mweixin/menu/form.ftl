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
    			<@ms.formRow label="所属菜单" width="240">
					<@ms.treeInput treeId="inputTree"  json="${listMenu?default('')}"  jsonId="menuId" jsonPid="menuMenuId" jsonName="menuTitle"  inputName="menuMenuId" inputValue="${(menu.menuMenuId)?default(0)}" buttonText="${(menuSuper.menuTitle)?default('顶级菜单')}" clickZtreeId="clickZtreeId(event,treeId,treeNode);"  expandAll="true" showIcon="true"/>
				</@ms.formRow>
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
				<div id="content">
					<@ms.textarea colSm="2" name="menuContent" label="菜单内容" wrap="Soft" rows="3"  size=""  value="${(menuEntity.menuContent)?default('')}" placeholder="请输入菜单内容" validation={"required":"true","maxlength":"200","data-bv-stringlength-message":"菜单内容长度不能超过两百个字符长度!", "data-bv-notempty-message":"必填项目"}/>
				</div>
				<div id="url">
					<@ms.text label="菜单链接" name="menuContent" value="${(menuEntity.menuContent)?default('')}"  width="500px;" placeholder="请输入菜单链接" validation={"required":"true","maxlength":"100","data-bv-stringlength-message":"菜单链接长度不能超过一百个字符长度!", "data-bv-notempty-message":"必填项目"}/>
				</div>
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
	//加载树形
	$.fn.zTree.init($("#treeDomeinputTree"),settinginputTree,zNodesinputTree);
	$('#menuType').on('select2:select', function (e) {
		if(e.params.data.id == 2){
			$("#content").show();
			$("#url").hide();
		}else{
			$("#content").hide();
			$("#url").show();
		}
	});
	if(${(menuEntity.menuType)?default(1)} == 1){
		$("#content").hide();
		$("#url").show();
	}else{
		$("#content").show();
		$("#url").hide();
	}
	$("#menuStyle").select2({width: "210px"});
	$("#menuType").select2({width: "210px"});
	$("#menuStatus").select2({width: "210px"});
	var url = "${managerPath}/mweixin/menu/save.do";
	if($("input[name = 'menuId']").val() > 0){
		url = "${managerPath}/mweixin/menu/update.do";
		$(".btn-success").text("更新");
	}
	//选择栏目后查询自定义模型
	function clickZtreeId(event,treeId,treeNode){
		if(treeNode.level == 0){
		if(treeNode.check_Child_State == 0)
			if(treeNode.children.length == 7){
				<@ms.notify msg= "二级菜单对多只能存在7个" type= "warning" />
				return false;
			}
		}else if(treeNode.level == 1){
			<@ms.notify msg= "微信菜单只能存在两级,不能选择二级菜单为父级" type= "warning" />
			return false;
		}
		
		
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
					   type:'warning',
					   message: { text:data.resultMsg }
					}).show();
				}
			}
		})
		}
</script>
