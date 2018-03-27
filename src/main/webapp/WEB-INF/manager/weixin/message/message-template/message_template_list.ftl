<!DOCTYPE html>
<html lang="zh">
 <head>
  	<#include "${managerViewPath}/include/macro.ftl"/>
  	<#include "${managerViewPath}/include/meta.ftl"/>   
</head>
	
<body>	
	<@ms.content>
		<@ms.contentBody>
			<@ms.contentNav title="短信模板管理"/>
			<@ms.contentPanel>
				<@ms.panelNav>
					<@ms.panelNavBtnGroup>
						<@ms.panelNavBtnAdd  id="totalAdd"/>
						<@ms.panelNavBtnDel  id="totalDelete"/>
					</@ms.panelNavBtnGroup>
				</@ms.panelNav>
	               <@ms.table head=['<input type="checkbox" id="allCheck" value="全选" style="margin-left:-6px;"/>','模板id','消息状态','对应微信消息应用id','对应模块编号','模板消息标题','模板详细信息链接地址','模板消息关键字']>
	           			<#if messageTemplateList?has_content>
	           				<#list messageTemplateList as messageTemplateList>
	                    	<tr class="on-focus">
	                    		<td class="text-center">
	                            	<@ms.checkbox name="radioCategoryId" value="${messageTemplateList.messageTemplateId?c?default(0)}"/> 
	                            </td>
	                            <td class="text-center messageTemplateId"><a href="#"><span data-original-title="点击编辑" data-toggle="tooltip" style="cursor:pointer">${messageTemplateList.messageTemplateId?c?default(0)}</span></a></td>
	                            <td class="text-center">${messageTemplateList.messageTemplateStatus?c?default(0)}</td>
	                            <td class="text-center">${messageTemplateList.messageTemplateAppId?c?default(0)}</td>
	                            <td class="text-center">${messageTemplateList.messageTemplateModelId?c?default(0)}</td>
	                            <td class="text-center">${messageTemplateList.messageTemplateTitle?default("")}</td>
	                            <td class="text-center">${messageTemplateList.messageTemplateUrl?default("")}</td>
	                            <td class="text-center">${messageTemplateList.messageTemplateKeyword?default("")}</td>
	                    	</tr>  
	                    	</#list>
	                 		<#else>
		             		<@ms.nodata content="您还没有添加微信模板,请添加微信模板!" style="text-align:center"/>
	        			</#if>	
	        			</@ms.table>
			</@ms.contentPanel>		
		</@ms.contentBody>
		<!--=================模态框部分开始=================-->
		
				<!--修改/保存的模态框开始-->
		<@ms.modal modalName="update" title="修改微信信息模板，您当前选定的模板：">
			 <@ms.modalBody>
					<@ms.form isvalidation=true name="updateSms" class="form-inline" action="">	
			    		<@ms.select name="messageTemplateStatus" id="smsType" multiple=false  label="消息状态:" list={"0":"未发送","1":"已发送"} validation={"required":"true", "data-bv-notempty-message":"消息状态不能为空"}/>
			    		<@ms.text name="messageTemplateTopcolor" label="标题颜色" title="标题颜色" size="5" validation={"required":"true", "data-bv-notempty-message":"标题颜色不能为空"}/>
			    		<@ms.text name="messageTemplateTitleColor" label="模板颜色" title="模板消息标题显示颜色" size="5" validation={"required":"true", "data-bv-notempty-message":"模板颜色不能为空"}/>
			    		<@ms.text name="messageTemplateRemarkColor" label="摘要颜色" title="模板消息摘要显示颜色" size="5" validation={"required":"true", "data-bv-notempty-message":"摘要颜不能为空"}/>
			    		<@ms.text name="messageTemplateTemplateId" label="对应微信模板消息编号" title="对应微信模板消息编号" size="5" validation={"required":"true", "data-bv-notempty-message":"消息id不能为空"}/>
			    		<@ms.text name="messageTemplateModelId" label="模块id" title="模块id" size="5" validation={"required":"true", "data-bv-notempty-message":"模块id不能为空"}/>
			    		<@ms.text name="messageTemplateTitle" label="消息标题" title="消息标题" size="5" validation={"required":"true", "data-bv-notempty-message":"消息标题不能为空"}/>
			    		<@ms.textarea name="messageTemplateRemark" label="消息摘要" title="模板消息摘要" size="5" validation={"required":"true", "data-bv-notempty-message":"消息摘要不能为空"}/>
			    		<@ms.textarea name="messageTemplateUrl" label="链接地址" title="模板详细信息链接地址" size="5" validation={"required":"true", "data-bv-notempty-message":"链接地址不能为空"}/>
			    		<@ms.text name="messageTemplateKeyword" label="关键字" title="模板消息关键字" size="5" validation={"required":"true", "data-bv-notempty-message":"关键字不能为空"}/>
			    	</@ms.form>	  	      	
		     </@ms.modalBody>
		     <@ms.modalButton>
			    		<@ms.button value="返回" id="closeUpdate"/>  
			 			<@ms.savebutton id="updateButton"/>  
			 </@ms.modalButton>	
		</@ms.modal>
		<!--修改/保存的模态框结束-->
		
		<!--删除的模态框开始-->
		<@ms.modal modalName="delete" title="删除手机接口">
			 <@ms.modalBody>
				您确定删除您所勾选的模块么？
		     </@ms.modalBody>
		     <@ms.modalButton>
	    		<@ms.button value="返回" id="closeDelete"/>  
	 			<@ms.button class="btn btn-danger" id="deleteButton" value="删除"/>  
	 		 </@ms.modalButton>
		</@ms.modal>
		<!--保存的模态框结束-->
		<!--=================模态框部分结束=================-->
	</@ms.content>
</body>
</html>

<script>
	//按钮打开模态框
	$("#totalAdd").click(function(){
		$("#updateSms").attr("action","${base}${baseManager}/weixin/messageTemplate/save.do");	
		$(".update").modal();//打开
	})
	$("#totalDelete").click(function(){
		var radioData = $("input[name='radioCategoryId']").serialize();
		if(radioData!=""){
			$(".delete").modal();//打开
		}else{
			 <@ms.notify msg="请选择需要删除的模板！" type="warning"/>				
		}
	})
		//点击每一条数据的id
	$(".messageTemplateId").click(function(){
		//找到当前的ID
			var messageTemplateId = $(this).find("span").text();
		
		//ajax把模板id传给后台，查找相应的模板实体，赋值给模态框
		$.ajax({ 
				type: "POST", 
				url: base+"${base}${baseManager}/weixin/messageTemplate/getEntity.do",
				data:"messageTemplateId="+messageTemplateId,
				dataType:"json",
    			success:function(msg){
					var json =JSON.parse(msg.resultMsg);
					var messageTemplateId = json.messageTemplateId;
					
					//根据后台提供的数据进行对模态框的赋值
					$(".update select[name='messageTemplateStatus']").val(json.messageTemplateStatus);
					$(".update input[name='messageTemplateTopcolor']").val(json.messageTemplateTopcolor);
					$(".update input[name='messageTemplateTitleColor']").val(json.messageTemplateTitleColor);
					$(".update input[name='messageTemplateRemarkColor']").val(json.messageTemplateRemarkColor);
					$(".update input[name='messageTemplateTemplateId']").val(json.messageTemplateTemplateId);
					$(".update input[name='messageTemplateModelId']").val(json.messageTemplateModelId);
					$(".update input[name='messageTemplateTitle']").val(json.messageTemplateTitle);
					$(".update input[name='messageTemplateKeyword']").val(json.messageTemplateKeyword);
					$(".update textarea[name='messageTemplateRemark']").val(json.messageTemplateRemark);
					$(".update textarea[name='messageTemplateUrl']").val(json.messageTemplateUrl);
					
					//对action的提交地址赋值
    				$("#updateSms").attr("action","${base}${baseManager}/weixin/messageTemplate/"+messageTemplateId+"/update.do");	
					
    				$(".update").modal();//打开模态框
    			}
    		})				
		
		//打开模态框
        $(".update").modal();//打开	
	})
	
	
	
	//按钮关闭模态框
	$("#closeUpdate").click(function(){
		$(".update").modal("hide");//关闭
	})
	$("#closeDelete").click(function(){
		$(".delete").modal("hide");//关闭
	})
	$("#loginOutBtn").click(function() {	
			$(this).request({func:function(data) {
		 		location.reload();
			}});	
		});
		
	 //全选或取消
	   	$("#allCheck").on("click",function(){   
			if(this.checked){   
				$("input[name='radioCategoryId']").each(function(){this.checked=true;});
			}else{   
				$("input[name='radioCategoryId']").each(function(){this.checked=false;});   
			}   
		}); 
	
	
	 //点击更新按钮
	 $("#updateButton").click(function() {
			//序列化开始
			var vobj = $("#updateSms").data('bootstrapValidator').validate();
			if(vobj.isValid()){
					// idValid()：功能检查对象变量是否已经实例化
				$(this).postForm("#updateSms",{func:function(data) {
					$(".update").modal("hide");//关闭
					location.reload();
					<@ms.notify msg="更新成功!" type="success"/>
				}})
			}else {
				<@ms.notify msg="更新失败" type="fail"/>
			}    	
	 });

	 
	 //点击删除按钮
	 $("#deleteButton").click(function(){
	 	var ids = $("input[name='radioCategoryId']").serialize();
	 	if(ids!=""){
				$.ajax({ 
					type: "POST", 
					url: base+"${baseManager}/weixin/messageTemplate/delete.do",
					data:ids,
					dataType:"Json",
	    			success: function(msg){
	    				if (msg.result) {
	    					location.reload();
							<@ms.notify msg="删除成功！" type="success"/>
	    				}    				
	    			}
	    		});
	    	}else{
	    		$(".delete").modal("hide");
				<@ms.notify msg="请选择模块！" type="warning"/>
	        }
	 })
</script>










