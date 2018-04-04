<@ms.html5>
	<style> 
		hr{margin:0;padding:0;}
		/*弹出窗口样式*/
		#WindowDialog .modal-dialog{width:auto;}
		.ms-weixin-message .bar{
			height: 40px;
   			border: 1px solid #ccc;
    		border-bottom: none;
		}
		.ms-weixin-message .bar ul li {
			float: left;
			width: 50px;
			text-align: center;
			line-height: 39px; font-size:18px;color:#ccc;cursor: pointer;
		}
		.ms-weixin-message .bar ul li:hover{color:#000}
		.ms-weixin-message .bar ul li.sel{color:#000}
		.ms-weixin-message .content {
			margin-bottom:30px;
			border:1px solid #ccc;
			padding: 14px 20px;
			outline: 0;
			word-wrap: break-word;
			word-break: break-all;
			border-top-left-radius: 0;
			-moz-border-radius-topleft: 0;
			-webkit-border-top-left-radius: 0;
			border-top-right-radius: 0;
			-moz-border-radius-topright: 0;
			-webkit-border-top-right-radius: 0;
			background-color: #fff;
			max-height:400px;
			min-height: 188px;
			overflow-y:auto;
			clear:left;		
		}
		body .row .col-md-12{
			padding:0;
		}
		body .modal-dialog{
			width: 90%;
    		margin: 5% auto;
		}
		body .modal-dialog .modal-content .modal-body{
			overflow-y: scroll;
    		height: 450px;
		}
	</style>
	<@ms.content>
		<@ms.contentBody>
			<@ms.contentNav title="素材管理">
				<@ms.button class="btn btn-success"  id="sendMessageBtn"  value="群发"/>
			</@ms.contentNav>
			<@ms.contentPanel>				
			  	<div class="row margin20"> 		  		
					<div class="col-md-12">
						<!--发送消息区域开始--> 
					   	<div class="ms-weixin-message"> 
					    	<div class="bar"> 
					     		<ul> 
						      		<li data-toggle="tooltip" class="sel" data-msg-type="text"><i class="icon iconfont" style="font-size:23px"></i></li> 
									<li data-toggle="tooltip" class="news" data-msg-type="mpnews"><i class="icon iconfont" style="font-size:23px"></i></li> 
							    </ul> 
					    	</div> 
						    <div class="content" contenteditable="true"></div> 
						</div> 
				  	</div>
				  	<div class="col-md-12">
				  	<intput type="hidden" id="groupId"/>
				  	</div>
			  	</div>
			  	<!--选择素材模态框-->
				<@ms.modal modalName="newsMessageListModal" title="选择素材" size="L">
					<@ms.modalBody></@ms.modalBody>
					<@ms.modalButton>
						<!--模态框按钮组-->
						<@ms.button  value="确认" id="newsMessageOk" />
					</@ms.modalButton>
				</@ms.modal>
			</@ms.contentPanel>		
		</@ms.contentBody>
	</@ms.content> 
	<#noparse>
		<!---被选中的图文模板---->
		<script id="groupListTempl" type="text/x-jquery-tmpl">
			<option value="${errcode}" >${errmsg}</option>
		</script>
	</#noparse>
</@ms.html5>
<script>
	//获取文章列表的弹出层
	function getArticleList(){
		$.ajax({
			type: "Post",
			data:$("#messageForm").serialize(),
			url:  base+"${baseManager}/weixin/news/listAjax.do",
			success: function(html){
				$(".newsMessageListModal").modal();
			   	$(".modal-body").append(html);
				$(".newsList").delegate(".appmsg","click",function(){
					$(".appmsg").find(".mask").hide();
					$(".appmsg").attr("contenteditable","false");
					$(".appmsg").find(".icon").hide();
					$(".appmsg").removeClass("msgSel");
					$(this).find(".mask").show();
					$(this).find(".icon").show();
					$(this).addClass("msgSel");
				});
				$(".appmsg").hover(
					function () {
						$(this).find(".mask").show();
					},
					function () {
						if (!$(this).hasClass("msgSel")) {
					    	$(this).find(".mask").hide();
						}
					}
				);											
			},
			error:function(e) {
			 	<@ms.notify msg="找不到相关素材" type="fail"/>
			}
		});	
	}
	//点击消息类别切换背景为黑色
	$(".bar li").click(function() {
		$(".bar li").removeClass("sel");
		$(this).addClass("sel");
		$(".content").html("");
		$(".content").focus();
		if($(this).attr("data-msg-type")=="mpnews"){
			getArticleList();
			$(".modal-body").html("");
		}
	});
	//选择图文消息后点击“确定”按钮事件
	$("#newsMessageOk").click(function() {
		$(".content").html("");
		var obj = $(".modal-body").find("div.msgSel").parent().html();
		$($.parseHTML(obj, document, true)).width(300).appendTo($(".content")); 
		$(".content").find(".mask").hide();
		$(".newsMessageListModal").modal('hide');
		$(".appmsg_content").css("border","none");
		$(".bar li").removeClass("sel");
		$(".bar .news").addClass("sel");
	});
	//点击发送按钮进行消息的群发
	$("#sendMessageBtn").click(function() {
		//默认是输入框里的文本内容
		var content;
		var url;
		//发送类型
		var type;
		//若为图文，取msgType值为0.content取素材ID
		if ($(".bar li.sel").hasClass("news")) {
			content = $(".content>div").attr("data-id");
			type = "image";
			if(content == undefined || content == ""){
			 	<@ms.notify msg="请选择素材！" type="warning"/>
				return;
			}
		}else{
			type = "text";
			content = $.trim($(".content").text());
			if(content == undefined || content == ""){
			 	<@ms.notify msg="请输入内容！" type="warning"/>
				return;
			}else if(content.length > 300){
			 	<@ms.notify msg="内容过长！" type="warning"/>
				return;
			}
			url = base+"${baseManager}/mweixin/message/sendAll.do";
		}
		$.ajax({
		   	type: "POST",
		   	data:"content="+content+"&type="+type,
		   	dataType:"json",
		   	url: url,
			beforeSend:function() {
		   		$("#sendMessageBtn").text("发送中");
		   		$("#sendMessageBtn").attr("disabled","disabled");
			},
		   	success: function(msg){
		   		if(msg.result){
		   			//得到发送失败的用户数组
			   		var objFail = jQuery.parseJSON(msg.resultMsg);
			   		var objSucess = jQuery.parseJSON(msg.resultData);
			   		if(objFail.length == 0){
			 			<@ms.notify msg="发送成功！" type="success"/>
			   		}else{ 
			 			<@ms.notify msg="发送失败！" type="fail"/> 				
			   		}	
		   		}else{
			 		<@ms.notify msg="发送失败！" type="fail"/>
		   		}	
		   		$("#sendMessageBtn").text("发送");
		   		$("#sendMessageBtn").attr("disabled",false);
		   	},
		   	error:function(XMLHttpRequest, textStatus, errorThrown){		   		
				 <@ms.notify msg="发送失败！" type="fail"/>
		   		location.reload();
		   	}	
		});	
	});	
	
</script>

