<@ms.html5> 
	<@ms.panel>
    	<!--title对应板块名称-->
        <@ms.nav title="微信用户"></@ms.nav>
        <@ms.searchForm   name="searchForm" action="">
			<@ms.text label="用户昵称"  name="puNickname" value="" title="请输入用户昵称"  placeholder="请输入用户昵称"/>
			<@ms.searchFormButton>
				<@ms.queryButton id="submitSearch"/>								
			</@ms.searchFormButton>
		</@ms.searchForm>
        <!--使用bootstrap-table的toolbar添加按钮-->
        <div id="toolbar">
        	<@ms.button class="btn btn-success"  id="synchronousPeople"  value="一键同步"/>
        </div>
       	<!--表格标题-->
        <table id="peopleListTable"
			data-show-refresh="true"
	        data-show-columns="true"
	        data-show-export="true"
			data-method="post" 
			data-detail-formatter="detailFormatter" 
			data-pagination="true"
			data-page-size="50"
			data-side-pagination="server">
		</table>                                     
	</@ms.panel>
  	<@ms.modal  modalName="messageModel" title="发送消息" >
		<@ms.modalBody style="width:100%">
			<@ms.form name="textForm" id="textForm" isvalidation=true  action="" method="post"  >
				<@ms.textarea rows="7" style="height:300px;width:100%" label="发送内容" maxlength="600" size="600"  placeholder="请输入600个字符以内的文本素材！" name="messageContent"/>				
			</@ms.form>
		</@ms.modalBody>
		<@ms.modalButton>
			<!--模态框按钮组-->
			<@ms.button  value="发送"  id="sendMessageButton"/>
		</@ms.modalButton>
	</@ms.modal>
</@ms.html5>
<script>
	$(function(){
		$("#peopleListTable").bootstrapTable({
        	url:"${managerPath}/weixin/weixinPeople/list.do",
        	contentType : "application/x-www-form-urlencoded",
        	queryParamsType : "undefined",
        	toolbar:"#toolbar",
			columns: [{
				align:'center',
			   	field: 'weixinPeopleHeadimgUrl',
			    title: '用户头像',
			    width:'80',
			    formatter:function(value,row,index){return "<img src=" + row.weixinPeopleHeadimgUrl + " style='border-radius:12px;width:25px;height:25px;'>"}
			}, {
			    align:'left',
			    field: 'puNickname',
			    title: '用户昵称'
			},{
			   	align:'center',
			    field: 'weixinPeopleProvince',
			    title: '用户所在地',
			    formatter:function(value,row,index){return row.weixinPeopleProvince + "/" + row.weixinPeopleCity}
			}, {
			    align:'center',
			    field: 'peoplePhone',
			    title: '用户电话',
			    width:'200'
			},{
			    align:'center',
			    field: 'weixinPeopleCity',
			    title: '操作',
			    formatter:function(value,row,index){return "<button style='line-height: 9px;'  type='button' class='btn btn-success col-md sendMessage' data-id=" + row.weixinPeopleOpenId + ">发送消息</button>"}
			}]
        });
    	//点击查询按钮触发
        $("#submitSearch").click(function(){
        	var search = $("form[name='searchForm']").serializeJSON();
        	 var params = $('#peopleListTable').bootstrapTable('getOptions');
        	 params.queryParams = function(params) {  
        	 	$.extend(params,search);
	            return params;  
       		 }  
   	 		$("#peopleListTable").bootstrapTable('refresh', {query:$("form[name='searchForm']").serializeJSON()});
		})
	    //同步微信公众号的用户到数据库中
	    $("#synchronousPeople").click(function(){
	    	$.ajax({
	        	type:"post",
	        	dataType: "json",
	        	url:"${managerPath}/net/weixin/weixinPeople/importPeople.do",
	        	beforeSend:function(){
	        		$("#synchronousPeople").text("同步中..");
	        		$(".sendMessage").attr("disabled","true");
	               	$("#synchronousPeople").attr("disabled","true");
	        	},
	        	success: function(msg){
					if(msg.result == true){
						<@ms.notify msg="同步成功" type="success"/>
					}else{
						<@ms.notify msg="同步失败" type="fail"/>
					}
					location.reload();
				}
	       })
	   })
	   //发送按钮弹出发送消息框
	   $("body").delegate(".sendMessage","click",function() {
	      $(".messageModel").modal();
	   });
	   //点击发送按钮开始发送消息，只支持文本发送
	   $("body").delegate("#sendMessageButton","click",function(){
			var content = $.trim($("textarea[name='messageContent']").val());
	   		if(content == "" || content == undefined){
	   			<@ms.notify msg="发送内容不能为空!" type="warning"/>
	        	return;
	        }else if(content.length>300){
	        	<@ms.notify msg="内容过长!" type="warning"/>
	            return;
	       	}
	        $.ajax({
	        	type: "post",
	            dataType: "json",
	            url:  "${base}${baseManager}/weixin/message/"+$(".sendMessage").attr("data-id")+"/sendText.do",
	            data: "content=" + content,
	            beforeSend:function(){
	            	$("#sendMessageButton").text("发送中..");
	               	$("#sendMessageButton").attr("disabled",true);
	            },
	            success: function(msg){
	            	if(msg.result == true){
	            		<@ms.notify msg="发送成功!" type="success"/>
	                }else{
	                	<@ms.notify msg="发送失败!" type="fail"/>
	                }
	                $(".messageModel").modal("hide");
	                $("#sendMessageButton").text("发送");
	               	$("#sendMessageButton").attr("disabled",false);
	        	}
			});
		});
	})   
</script>