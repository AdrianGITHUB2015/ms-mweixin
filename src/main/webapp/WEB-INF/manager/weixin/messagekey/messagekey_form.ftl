<@ms.html5>
	<style>
		.ms-weixin-message .bar{
			height: 40px;
   			border: 1px solid #ccc;
    		border-bottom: none;
    		margin-bottom:0;
		}
		.ms-weixin-message .bar label{
			float:left;
			line-height:40px;
			margin-bottom:0px;
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
			border:1px solid #ccc;
			padding: 14px 10px;
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
		.ms-weixin-message .content .appmsg{
			border:none
		}
		.bar span{
			display:block;
			line-height:41px;
			float:left;
			font-weight:700;
		}
		.appmsg .mask{
			display: none;
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 100%;
			background-color: #000;
			-moz-opacity: .6;
			-khtml-opacity: .6;
			opacity: .6;
			filter: alpha(opacity = 60);
			z-index: 1;
			color: white;
		}
		.appmsg_info {
			font-size: 13px;
			line-height: 20px;
			padding-bottom: 10px
		}
		.appmsg_date {
			font-weight: 400;
			font-style: normal
		}
		.appmsg_content {
			*zoom: 1;
			border: 1px solid #e7e7eb;
		}
		.appmsg_title {
			font-weight: 400;
			font-style: normal;
			font-size: 16px;
			padding-top: 10px;
			line-height: 28px;
			margin:0;
			overflow: hidden;
			word-wrap: break-word;
			word-break: break-all
		}
		.appmsg_title a {
			display: block;
			color: #666
		}
		.appmsg_thumb_wrp {
			height: 160px;
			overflow: hidden
		}
		.appmsg_item {
			*zoom: 1;
			position: relative;
			padding: 20px 14px;
			border-top: 1px solid #e7e7eb
		}
		.appmsg_item:after {
			content: "\200B";
			display: block;
			height: 0;
			clear: both
		}
		.appmsg_item .appmsg_title {
			line-height: 24px;
			max-height: 48px;
			overflow: hidden;
			*zoom: 1;
			margin-top: 14px
		}
		.appmsg_item .appmsg_thumb {
			float: right;
			width: 78px;
			height: 78px;
			margin-left: 14px
		}
		/*素材通用*/
		.singleNews{width:280px;padding:0 14px}
		.multi .appmsg_info {
			padding-top: 14px;
			padding-left: 14px;
			padding-right: 14px
		}
		.multi .appmsg_content{padding: 0}
		.multi .appmsg_title {
			font-size: 14px;
			padding-top: 0
		}
		.cover_appmsg_item {
			position: relative;
			margin: 0 14px 14px
		}
		.cover_appmsg_item .appmsg_title {
			margin:0;
			width: 100%;
			background: rgba(0,0,0,0.6)!important;
			color: white;
			filter: progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#99000000',endcolorstr = '#99000000')
		}
		.cover_appmsg_item .appmsg_title a {
			padding: 0 8px;
			color: #fff
		}
		.cover_appmsg_item .appmsg_thumb{
			width:100%;
			height:100%;
		}
		/*选择图文弹出框样式*/
		body .modal-dialog{
			width: 90%;
    		margin: 5% auto;
		}
		body .modal-dialog .modal-content .modal-body{
			overflow-y: scroll;
    		height: 450px;
		}
		body .col-md-12{
			padding-left:0;
		}
		/*固定文本域*/
		textarea{
			resize: none;
		}
		.tip{
			color:red;
			line-height:34px;
			margin-left:10px;
		}
		.replyContent{
			width:80%;
			float:left;
			margin-bottom:50px;
		}
		.appmsg_thumb_wrp img{
			width:100%;
			height:100%;
		}
	</style>
	<@ms.content>
		<@ms.contentBody>
			<#if news?has_content>
				<@ms.contentNav title="编辑关键字回复">
					<@ms.updateButton class="btn btn-success"  id="saveOrUpdateMessage"  value="更新"/>
					<@ms.contentNavBack onclick="javascript:history.go(-1)"  class="btn btn-default" value="返回" />
				</@ms.contentNav>
			<#else>
				<@ms.contentNav title="新增关键字回复">
					<@ms.saveButton class="btn btn-success" id="saveOrUpdateMessage"  value="保存"/>
					<@ms.contentNavBack onclick="javascript:history.go(-1)"  class="btn btn-default" value="返回" />
				</@ms.contentNav>
			</#if>
			<@ms.contentPanel> 			  		
				<@ms.form name="replyForm"  isvalidation=true>		
				   	<div class="ms-weixin-message"> 
				   		<!--关键词-->
			   			<@ms.text name="passiveMessageKey" width="613"  label="关键词:" value="${passiveMessage.passiveMessageKey?default('')}" title="关键词" placeholder="请输入关键词" validation={"required":"true","maxlength":"300","data-bv-stringlength-message":"关键字在300个字符以内!", "data-bv-notempty-message":"必填项目"}/>
				   		<@ms.formRow label="回复内容">
					   		<div class="replyContent">
						    	<div class="bar"> 
						     		<ul> 
										<li data-toggle="tooltip" class="text sel" data-msg-type="2"><i class="icon iconfont" style="font-size:23px"></i></li> 
						      			<li data-toggle="tooltip" class="news" data-msg-type="1" ><i class="icon iconfont" style="font-size:23px"></i></li> 
						     		</ul> 
						    	</div> 
						    	<div class="content" contenteditable="true">
							    	<#if news?has_content>		    		
							    		<#if news.newsType==0>				    	
							    			<!--单图文-->
							    			<div class="appmsg_content singleNews" contenteditable="false" data-id="${news.newsId}">			        
									            <h4 class="appmsg_title">${news.newsMasterArticle.basicTitle?default("标题")}</h4>
									            <div class="appmsg_info">
									                <em class="appmsg_date">${news.newsDateTime?string('yyyy年MM月dd日')}</em>
									            </div>
								            	<div class="appmsg_thumb_wrp"><img src="${news.newsMasterArticle.basicThumbnails?default('')}" alt=""></div>
								            		<p class="appmsg_desc">${news.newsMasterArticle.basicDescription?default("描述...")}</p>
									    		</div>
									    		<div class="mask" style="display: none;"><i class="icon iconfont" style="font-size: 60px; position: absolute; top: 36%; left: 40%;"></i></div>
								    		</div>
								    	</#if>
						    			<#if news.newsType==1 && news.newsMasterArticle?has_content>
						    				<!--多图文-->
					    					<div class="appmsg multi msgSel" data-id="${news.newsId}" contenteditable="false" style="width:280px;">
				    							<div class="appmsg_content">
					        						<div class="appmsg_info">
					            						<em class="appmsg_date">${news.newsDateTime?string('yyyy年MM月dd日')}</em>
					        						</div>
				            						<div class="cover_appmsg_item">
				                						<div class="appmsg_thumb_wrp">
				                                        	<img src="${news.newsMasterArticle.basicThumbnails?default('')}" alt="" class="appmsg_thumb">
				                                        </div>
				                                        <h4 class="appmsg_title">${news.newsMasterArticle.basicTitle?default('')}</h4>
				            						</div>
				            						<#if news.childs?has_content>
														<#list news.childs as listChild>
								            				<div class="appmsg_item">
								                				<img src="${listChild.basicThumbnails?default('')}" alt="${listChild.basicTitle}" class="appmsg_thumb">
								                				<h4 class="appmsg_title">${listChild.basicTitle}</h4>
								            				</div>	
						            					</#list>
													</#if>	 						            							
				    							</div>
											</div>
						    			</#if>
						    			<#if news.newsType==2>
						    				${news.newsContent}
						    			</#if>
						    		</#if>	
					    		</div>	
					    	</div>		 				   		
				   		</@ms.formRow>
				   	</div>
				</@ms.form>
				<!--素材选择模态框-->
				<@ms.modal modalName="newsMessageListModal" title="选择素材" size="L">
					<@ms.modalBody>
					</@ms.modalBody>
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
		$(".modal-body").html("");
		$(".newsMessageListModal").modal();
		$.ajax({
			type: "Post",
			data:$("#messageForm").serialize(),
			url:  base+"${baseManager}/weixin/news/listAjax.do",
			success: function(html){
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
				<@ms.notify msg= "提交失败" type="fail"/>
			}
		});	
	}
	$(function(){
		<#if news?has_content>
			if(${news.newsType} == 2){
				$(".bar ul li").removeClass("sel");
				$(".bar ul li:first").addClass("sel");
			}else{
				$(".bar ul li").removeClass("sel");
				$(".bar ul li:last").addClass("sel");
			}
		</#if>
		$(".bar li").click(function() {
			$(".bar li").removeClass("sel");
			$(this).addClass("sel");
			$(".content").html("");
			if($(this).attr("data-msg-type")=="1"){
				getArticleList();
			}
		});
		//选择图文消息后点击“确定”按钮事件
		$("#newsMessageOk").click(function() {
			$(".content").html("");
			var obj = $(".modal-body").find("div.msgSel").parent().html();
			$($.parseHTML(obj, document, true)).width(200).appendTo($(".content")); 
			$(".content").find(".mask").hide();
			$(".newsMessageListModal").modal('hide');
			$(".bar li").removeClass("sel");
			$(".bar .news").addClass("sel");
		});
		//点击保存关键字回复内容
		$("#saveOrUpdateMessage").click(function() {
			var vobj = $("#replyForm").data('bootstrapValidator').validate();
			if(vobj.isValid()){
				var btnTips = $(this).text();
				$(".erro").remove();
				var passiveMessageKey = $("input[name='passiveMessageKey']").val();
				//回复类型
				var replyType = $(".bar ul").find(".sel").attr("data-msg-type");
				//回复内容
				var content;
				if ($(".bar li.sel").hasClass("news")) {
					content = $(".content>div").attr("data-id");				
					if(content == undefined || content == ""){
						$(".content").after("<small style='color:#a94442' class='erro'>请选择素材！</small>")
						return;
					}
				}else{
					content = $.trim($(".content").text());
					if(content == undefined || content == ""){
						$(".content").after("<small style='color:#a94442' class='erro'>请输入回复内容</small>")
						return;
					}else if(content.length > 300){
						$(".content").after("<small style='color:#a94442' class='erro'>内容过长</small>")
						return;
					}
				}
				var url = "${managerPath}/weixin/messagekey/save.do";
				$(this).text("保存中")
				<#if news?has_content>
					url = "${managerPath}/weixin/messagekey/${passiveMessage.passiveMessageId}/update.do"
					$(this).text("更新中");
				</#if>
				$(this).attr("disabled",true);
				$(this).request({
					method:"post",
					data:"content="+content+"&replyType="+replyType+"&passiveMessageKey="+passiveMessageKey,
					url:url,
					func:function(data) {
						var obj = data.result;
						if(obj == true){
							<#if news?has_content>
			 					<@ms.notify msg="更新成功" type="success"/>
								location.href="${managerPath}/weixin/messagekey/index.do"; 	
							<#else>
			 					<@ms.notify msg="保存成功" type="success"/>						
								location.href="${managerPath}/weixin/messagekey/index.do";
							</#if>									
						}else{
							$(".content").after("<small style='color:#a94442' class='erro'>关键字已存在，请输入其他关键字</small>")
							$("#saveOrUpdateMessage").attr("disabled", false);
							$("#saveOrUpdateMessage").text(btnTips);
						}
					}
				});
			}
		});
	})
</script>

