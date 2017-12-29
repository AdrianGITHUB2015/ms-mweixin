<@ms.html5>
	<@ms.nav title="微信被动消息回复管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
		<@ms.text label="关键字" maxlength="20" name="pmKey" value=""  width="170px;" placeholder="请输入关键字" />
			<@ms.searchFormButton>
				 <@ms.queryButton onclick="search()"/> 
			</@ms.searchFormButton>			
	</@ms.searchForm>
	<@ms.panel>
		<div id="toolbar">
			<@ms.panelNavBtnGroup>
				<@ms.panelNavBtnAdd id="addPassiveMessageBtn" title=""/> 
				<@ms.panelNavBtnDel id="delPassiveMessageBtn" title=""/>
			</@ms.panelNavBtnGroup>
		</div>
		<table id="passiveMessageList" 
			data-show-refresh="true"
			data-show-columns="true"
			data-show-export="true"
			data-method="post" 
			data-pagination="true"
			data-page-size="10"
			data-side-pagination="server">
		</table>
	</@ms.panel>
	
	<@ms.modal  modalName="delPassiveMessage" title="微信被动消息回复数据删除" >
		<@ms.modalBody>删除此微信被动消息回复
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认" class="btn btn-danger rightDelete"  id="deletePassiveMessageBtn"  />
			</@ms.modalButton>
		</@ms.modalBody>
	</@ms.modal>
</@ms.html5>

<script>
	$(function(){
		$("#passiveMessageList").bootstrapTable({
			url:"${managerPath}/mweixin/passiveMessage/list.do",
			contentType : "application/x-www-form-urlencoded",
			queryParamsType : "undefined",
			toolbar: "#toolbar",
	    	columns: [{ checkbox: true},
				    	{
				        	field: 'pmId',
				        	title: '编号',
				        	width:'10',
				        	align: 'center'
				    	},{
				        	field: 'pmKey',
				        	title: '关键字',
				        	width:'100',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/mweixin/passiveMessage/form.do?pmId="+row.pmId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},{
				        	field: 'pmContent',
				        	title: '回复内容标签',
				        	width:'200',
				        	align: 'center'
				    	}
			]
	    })
	})
	//增加按钮
	$("#addPassiveMessageBtn").click(function(){
		location.href ="${managerPath}/mweixin/passiveMessage/form.do"; 
	})
	//删除按钮
	$("#delPassiveMessageBtn").click(function(){
		//获取checkbox选中的数据
		var rows = $("#passiveMessageList").bootstrapTable("getSelections");
		//没有选中checkbox
		if(rows.length <= 0){
			<@ms.notify msg="请选择需要删除的记录" type="warning"/>
		}else{
			$(".delPassiveMessage").modal();
		}
	})
	
	$("#deletePassiveMessageBtn").click(function(){
		var rows = $("#passiveMessageList").bootstrapTable("getSelections");
		$(this).text("正在删除...");
		$(this).attr("disabled","true");
		$.ajax({
			type: "post",
			url: "${managerPath}/mweixin/passiveMessage/delete.do",
			data: JSON.stringify(rows),
			dataType: "json",
			contentType: "application/json",
			success:function(msg) {
				if(msg.result == true) {
					<@ms.notify msg= "删除成功" type= "success" />
				}else {
					<@ms.notify msg= "删除失败" type= "fail" />
				}
				location.reload();
			}
		})
	});
	//查询功能
	function search(){
		var search = $("form[name='searchForm']").serializeJSON();
        var params = $('#passiveMessageList').bootstrapTable('getOptions');
        params.queryParams = function(params) {  
        	$.extend(params,search);
	        return params;  
       	}  
   	 	$("#passiveMessageList").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
	}
</script>