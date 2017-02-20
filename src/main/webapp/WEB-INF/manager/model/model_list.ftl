<!DOCTYPE html>
<html lang="zh">
<head>
	<#include "${managerViewPath}/include/macro.ftl"/>
	<#include "${managerViewPath}/include/meta.ftl"/>	
	<style>
		#addEditModelDialog .modal-body{
			overflow: inherit;
		}
	</style>
</head>
<body>	
	<script id="beforedmodelTree" type="text/x-jquery-tmpl">
		<td class="text-center" style="width:10%;">
			<i class="icon iconfont icon-logo">
				{{if modelIcon}}{{= modelIcon}}{{else}}{{/if}}
			</i>		
		</td> 
	</script>
	<script id="aftermodelTree" type="text/x-jquery-tmpl">
		<td style="width:30%;">{{= modelCode}}</td>
		<td class="text-left" style="width: 20%;" >{{= modelUrl}}</td>
		<td class="text-center">{{if modelIsMenu > 0 }}是{{else}}否{{/if}}</td>
		<td class="text-center">			
			<a class="btn btn-xs red tooltips editModal" onclick="editModal(this)" data-toggle="tooltip" data-id="{{= modelId}}" data-original-title="编辑">
         		<i class="glyphicon glyphicon-pencil"></i>
        	</a>
			<a class="btn btn-xs red tooltips deleteModal" onclick="deleteModal(this)" data-toggle="tooltip" data-id="{{= modelId}}" data-original-title="删除" >
         		<i class="glyphicon glyphicon-trash"></i>
        	</a>
		</td>
	</script>
<@ms.content>
<@ms.contentBody >
	<@ms.contentNav title="模块管理列表" ></@ms.contentNav >
	<@ms.contentPanel>
		<@ms.panelNav >
			<@ms.panelNavBtnGroup>
				<@ms.panelNavBtnAdd/>
			</@ms.panelNavBtnGroup>
		</@ms.panelNav>
		<@ms.nodata style="text-align:left;" content="模块编码:8位整型组成，00-00-00-00-00,表示：项目-模块-子功能-CURDO(查:0添:1删:2改:3等其他)
			<br/>
			特别说明： 通用模块编号：分类(99) 文章(98) 订单(97) 订单状态(96) 支付方式(95) 配送方式(94)  前端模块(93)  "/>
		<@ms.table head=["<th style='width:7%'>模块编号</th>","<th class='text-center'>模块图标</th>",'模块标题','模块编码(项目-模块-子功能-CURDO)','模块链接地址',"<th class='text-center'>是否是菜单</th>","<th style='text-align:center;'>操作</th>"] id="tableConterent">
			<#if parentModelList?has_content>
	        	<@ms.treeTable style="width:20%" treeId="modelTree" tmplBefored="true" tbodyId="tableConterent" jsonName="modelTitle" jsonId="modelId" jsonPid="modelModelId" json="${parentModelList?default('')}"/>
	      	<#else>
             <tr>
		       	<td colspan="7" class="text-center">
		          		<@ms.nodata content="暂无模块"/>
				</td>
	          </tr>
        	</#if>
		</@ms.table>	
		 <!--添加模块-->    
		<@ms.modal modalName="addEditModel" title="添加模块">
			<@ms.modalBody height="350">
				<@ms.form isvalidation=true name="addEditForm"  action="" method="post"  >
					<@ms.text name="modelTitle"  label="标题" title="模块标题" placeholder="请输入模块标题" value="" validation={"required":"true", "data-bv-notempty-message":"请输入模块标题!"} />
					<@ms.text name="modelCode"  label="编码" title="模块编码" placeholder="请输入模块编码" value="" validation={"required":"true", "data-bv-notempty-message":"请输入模块编码!"} />
					<@ms.text name="modelIcon"  label="图标" title="模块图标"  placeholder="请输入模块图标" value=""/>
					<@ms.text name="modelUrl"  label="链接地址" title="模块链接地址"  placeholder="请输入模块链接地址" value=""/>
					<#assign isMenu=[{"id":"0","name":"否"},{"id":"1","name":"是"}]>
					<@ms.select name="modelIsMenu" style="width: 25%;" id="modelMenuSelect" list=isMenu  listKey="id" listValue="name" label="是否是菜单"  value="0" title="是否是菜单" />
					<@ms.formRow label="父亲菜单">
							<@ms.treeInput treeId="modelInputTree" json="${parentModelList?default('')}" jsonId="modelId" jsonPid="modelModelId" jsonName="modelTitle"  addNodesName="顶级模块" buttonText="顶级模块" inputName="modelModelId" inputValue="0"  expandAll="true" showIcon="true"/>
					</@ms.formRow>
					<@ms.hidden name="modelId" value="" id="hideModelId"/>
				</@ms.form>
			</@ms.modalBody>
			<@ms.modalButton>
				<@ms.button  value=""  id="addEditBtn"/>
			</@ms.modalButton>
		</@ms.modal>	
		<@ms.modal modalName="deleteModel" title="删除提示!">
			<@ms.modalBody>
			  		确认删除？？
			</@ms.modalBody>
			<@ms.modalButton>
				<@ms.button value="确认" id="rightDelete" url="${base}${baseManager}/loginOut.do"/>
			</@ms.modalButton>
		</@ms.modal>		
	</@ms.contentPanel>
</@ms.contentBody>
</@ms.content>        

<script>
var modelId;
var postMessage;
$(function(){
	//新增模块
	$("#addButton").click(function(){
		postMessage="保存成功！";
		$("#addEditBtn").text("保存");
		$("#addEditForm").attr("action","${base}${baseManager}/model/save.do");
		$("#addEditForm")[0].reset(); 
		$(".addEditModel").modal();
		$("#hideModelId").attr("disabled",true);
	});
	
	
	$("#addEditBtn").on("click",function(){
		var vobj = $("#addEditForm").data('bootstrapValidator').validate();
		if(vobj.isValid()){
			$(this).postForm("#addEditForm",{func:function(msg) {
				if(msg.result){
					alert(postMessage);
			    	location.reload();
				}
			}});
		}
	});
	
	
	
	//删除模块
	$("#rightDelete").on("click",function(){
		var rightUrl = base+"${baseManager}/model/"+modelId+"/isChildModel.do";
		$(this).request({url:rightUrl,method:"post",func:function(data){
			if(data==true){
				alert("请先删除子模块！！");
				$(".deleteModel").modal("hide");
			}else{
				var actionUrl=base+"${baseManager}/model/"+data+"/delete.do";
				$(this).request({url:actionUrl,type:"json",method:"post",func:function(msg) {
					alert("删除成功");
					location.reload();
				}});
			}
		}});
	});
	
});

//编辑模块
function editModal(obj){
	modelId = $(obj).attr("data-id");
	var editUrl=base+"${baseManager}/model/edit/"+modelId+".do";
	$(this).request({url:editUrl,type:"json",method:"post",func:function(data){
		var model = data.model;
		// 给表单赋值
		if(model!=null){
			$("input[name='modelId']").val(model.modelId);
			if(model.modelIcon!=undefined){
				$("input[name='modelIcon']").val(model.modelIcon);
			}
			
			$("input[name='modelTitle']").val(model.modelTitle);
			$("input[name='modelCode']").val(model.modelCode);
			$("input[name='modelUrl']").val(model.modelUrl);
			$("#modelMenuSelect option[value="+model.modelMenu+"]").attr("selected",true);
			$("input[name='modelModelId']").val(model.modelModelId);
			if(model.modelModelId==0){
				$("#treeLabelmodelInputTree").text("顶级模块");
			}else{
				$("#treeLabelmodelInputTree").text(data.parentModel.modelTitle);
			}
			$(".addEditModel").modal();
			postMessage="更新成功！";
			$("#addEditBtn").text("更新");
			$("#hideModelId").attr("disabled",false);
			$("#hideModelId").val(model.modelId);
			$("#addEditForm").attr("action","${base}${baseManager}/model/update.do");
		}		
	}});
}

//删除模块
function deleteModal(obj){
	modelId = $(obj).attr("data-id");
	$(".deleteModel").modal();
}
</script>
</body>
</html>













