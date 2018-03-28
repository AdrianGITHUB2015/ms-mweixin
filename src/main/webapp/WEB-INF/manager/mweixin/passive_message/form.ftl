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
	 <@ms.nav title="微信消息回复编辑" back=true>
			 <@ms.button class="btn btn-success" onclick="save()" value="保存"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="passiveMessageForm" isvalidation=true>
    		<@ms.hidden name="pmId" value="${(passiveMessageEntity.pmId)?default('')}"/>
    			<#if passiveMessageEntity.pmType == 1>
    				<@ms.text label="事件关键字" name="pmKey" value="${(passiveMessageEntity.pmKey)?default('')}"  width="240px;" placeholder="请输入事件关键字" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"事件关键字长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			</#if>
    			<@ms.hidden name="pmType" value="${(passiveMessageEntity.pmType)?default('')}"/>
				<@ms.hidden name="pmNewType" value="1"/>
				<!--<@ms.select 
    				id="pmNewType"
				    name="pmNewType" 
				    label="素材类型" 
				    width="240"  
				    list=[{"id":1,"value":"文本"},{"id":2,"value":"图片"},{"id":3,"value":"语音"},{"id":4,"value":"视频"},{"id":5,"value":"音乐"},{"id":6,"value":"图文"}] 
				    value="${(passiveMessageEntity.pmNewType)?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>-->
    			<@ms.textarea colSm="2" name="pmContent"  wrap="Soft" rows="3"  size=""  label="回复内容" value="${(passiveMessageEntity.pmContent)?default('')}" placeholder="请输入回复内容" validation={"required":"true","maxlength":"150","data-bv-stringlength-message":"回复内容长度不能超过150个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	$("#pmType").select2({width: "210px"});
	$("#pmNewType").select2({width: "210px"});
	var url = "${managerPath}/mweixin/passiveMessage/save.do";
	if($("input[name = 'pmId']").val() > 0){
		url = "${managerPath}/mweixin/passiveMessage/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#passiveMessageForm").data("bootstrapValidator").validate();
			var isValid = $("#passiveMessageForm").data("bootstrapValidator").isValid();
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
			data:$("form[name = 'passiveMessageForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.pmWeixinId > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					if(status.pmType == 1){
						location.href = "${managerPath}/mweixin/passiveMessage/index.do?pmType="+1;
					}else{
						$(".btn-success").text("更新");
						$(".btn-success").removeAttr("disabled");
					}
				}
				else{
					$(".btn-success").text(btnWord);
					$(".btn-success").removeAttr("disabled");
					<@ms.notify msg="保存或更新失败" type= "warning" />
					$(".btn-success").text(btnWord);
				}
			}
		})
	}	
</script>
