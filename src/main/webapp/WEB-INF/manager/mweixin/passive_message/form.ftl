<@ms.html5>
	<style> 
		hr{margin:0;padding:0;}
		/*弹出窗口样式*/
		#WindowDialog .modal-dialog{width:auto;}
		.ms-weixin-message .bar{
			height: 40px;
   			border: 1px solid #ccc;
    		border-bottom: none;
    		border-radius: 4px;
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
			border-radius: 4px;
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
	 <@ms.nav title="微信消息回复编辑" back=true>
		<#if passiveMessageEntity.pmId?has_content>
    		<@ms.updateButton class="btn btn-success" value="更新"  onclick="save()"/>
		<#else>
			<@ms.saveButton class="btn btn-success" value="保存" onclick="save()"/>
    	</#if>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="passiveMessageForm" isvalidation=true>
    		<@ms.hidden name="pmId" value="${(passiveMessageEntity.pmId)?default('')}"/>
    			<#if passiveMessageEntity.pmType == 1>
    				<@ms.text label="事件关键字" name="pmKey" value="${(passiveMessageEntity.pmKey)?default('')}"  width="240px;" placeholder="请输入事件关键字" validation={"required":"true","maxlength":"30","data-bv-stringlength-message":"事件关键字长度不能超过三十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			</#if>
    			<@ms.hidden name="pmType" value="${(passiveMessageEntity.pmType)?default('')}"/>
				<!--{"id":2,"value":"图片"},{"id":3,"value":"语音"},{"id":4,"value":"视频"},{"id":5,"value":"音乐"}
				<@ms.select 
    				id="pmNewType"
				    name="pmNewType" 
				    label="素材类型" 
				    width="240"  
				    list=[{"id":1,"value":"文本"},{"id":6,"value":"图文"}] 
				    value="${(passiveMessageEntity.pmNewType)?default('')}"
				    listKey="id" 
				    listValue="value"  
				    validation={"required":"true", "data-bv-notempty-message":"必选项目"}
				/>-->
    			<#if passiveMessageEntity.pmType == 1>
    				<div class="form-group ms-form-group has-feedback">	
						<label for="pmContent" class="col-sm-2 control-label ">回复内容</label>
						<div class="ms-form-control ms-from-group-input col-sm-9" style="">		
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
						  	</div>
						<small class="help-block" data-bv-validator="notEmpty" data-bv-for="pmContent" data-bv-result="NOT_VALIDATED" style="display: none;">必填项目</small><small class="help-block" data-bv-validator="stringLength" data-bv-for="pmContent" data-bv-result="NOT_VALIDATED" style="display: none;">回复内容长度不能超过150个字符长度!</small></div>
					</div>
    			<#else>
    				<@ms.textarea colSm="2" name="pmContent"  wrap="Soft" rows="7"  size=""  label="回复内容" value="${(passiveMessageEntity.pmContent)?default('')}" placeholder="请输入回复内容" validation={"required":"true","maxlength":"150","data-bv-stringlength-message":"回复内容长度不能超过150个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			</#if>
    	</@ms.form>
    	<!--选择素材模态框-->
		<@ms.modal modalName="newsMessageListModal" title="选择素材" size="L">
			<@ms.modalBody></@ms.modalBody>
			<@ms.modalButton>
				<!--模态框按钮组-->
				<@ms.button  value="确认" id="newsMessageOk" />
			</@ms.modalButton>
		</@ms.modal>
    </@ms.panel>
    <#noparse>
		<!---被选中的图文模板---->
		<script id="groupListTempl" type="text/x-jquery-tmpl">
			<option value="${errcode}" >${errmsg}</option>
		</script>
	</#noparse>
</@ms.html5>
<script>
	$("#pmType").select2({width: "210px"});
	var url = "${managerPath}/mweixin/passiveMessage/save.do";
	var pmId = $("input[name = 'pmId']").val();
	var pmType = $("input[name = 'pmType']").val();
	if(pmId > 0){
		url = "${managerPath}/mweixin/passiveMessage/update.do";
		$(".btn-success").text("更新");
		if(${(passiveMessageEntity.pmNewType)?default(0)} == 6){
			$.ajax({
				type:"post",
				dataType:"json",
				async: false,
				url:"${managerPath}/weixin/news/${(passiveMessageEntity.pmContent)?default(0)}/getHtml.do",
				success: function(data) {
					if(data != null && data != ""){
						$(".content").html(data);
					}
				},
				error: function(dataa){
					var da= 1;
					$(".content").html(dataa.responseText);
				}
			})
		}else{
			$(".content").html("${(passiveMessageEntity.pmContent)?default("")}");
		}
	}
	
	//编辑按钮onclick
	function save() {
		$("#passiveMessageForm").data("bootstrapValidator").validate();
			var isValid = $("#passiveMessageForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var data = $("form[name = 'passiveMessageForm']").serialize();
		//若为关键字回复
		if(pmType == 1){
			//回复内容，素材就保存id
			var pmContent;
			//关键字回复类型
			var pmNewType;
			if ($(".bar li.sel").hasClass("news")) {
				//图文
				pmNewType = 6;
				pmContent = $(".content>div").attr("data-id");
				if(pmContent == undefined || pmContent == ""){
				 	<@ms.notify msg="请选择素材！" type="warning"/>
					return;
				}
			}else{
				//图文
				pmNewType = 1;
				pmContent = $.trim($(".content").text());
				if(pmContent == undefined || pmContent == ""){
				 	<@ms.notify msg="请输入内容！" type="warning"/>
					return;
				}else if(pmContent.length > 300){
				 	<@ms.notify msg="内容过长！" type="warning"/>
					return;
				}
			}
			data = $.param({"pmContent":pmContent,"pmNewType":pmNewType}) + "&" + $("form[name = 'passiveMessageForm']").serialize();
		}	
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:data,
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
					if(status.pmType != 1){
						location.href="${managerPath}/mweixin/passiveMessage/form.do?pmID="+pmId+"&pmType="+pmType;
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
	
	
</script>
