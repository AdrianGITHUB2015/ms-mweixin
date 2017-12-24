<@ms.html5>
	<@ms.nav title="微信被动消息回复管理"></@ms.nav>
	<@ms.searchForm name="searchForm" isvalidation=true>
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
				        	field: 'pmWeixinId',
				        	title: '微信编号',
				        	width:'10',
				        	align: 'center'
				    	},
							    	{
				        	field: 'pmId',
				        	title: '自增长ID',
				        	width:'10',
				        	align: 'center'
				    	},
							    	{
				        	field: 'pmEventId',
				        	title: '该回复属于的分类中的事件ID',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		switch(value){
				        			case 1: return "新关注";break;
				        			case 2: return "二维码扫描";break;
				        			case 3: return "二维码扫描&提示框";break;
				        			case 4: return "文本消息";break;
				        			case 5: return "未关注扫描二维码";break;
				        			case 6: return "点击事件";break;
				        		}
				        	}
				    	},
							    	{
				        	field: 'pmNewsId',
				        	title: '回复的素材ID',
				        	width:'10',
				        	align: 'center'
				    	},
							    	{
				        	field: 'pmMessageId',
				        	title: '对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个',
				        	width:'10',
				        	align: 'center',
				        	
				    	},
							    	{
				        	field: 'pmAppId',
				        	title: '该回复所属的应用ID',
				        	width:'10',
				        	align: 'center'
				    	},
							    	{
				        	field: 'pmReplyNum',
				        	title: '被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/mweixin/passiveMessage/form.do?pmId="+row.pmId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},
							    	{
				        	field: 'pmKey',
				        	title: '事件关键字',
				        	width:'300',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		var url = "${managerPath}/mweixin/passiveMessage/form.do?pmId="+row.pmId;
				        		return "<a href=" +url+ " target='_self'>" + value + "</a>";
				        	}
				    	},
							    	{
				        	field: 'pmType',
				        	title: '回复属性',
				        	width:'10',
				        	align: 'center',
				        	formatter:function(value,row,index) {
				        		switch(value){
				        			case 1: return "最终回复";break;
				        			case 2: return "拓展回复迭代回复";break;
				        		}
				        	}
				    	},
							    	{
				        	field: 'pmTag',
				        	title: '扩展标签',
				        	width:'30',
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