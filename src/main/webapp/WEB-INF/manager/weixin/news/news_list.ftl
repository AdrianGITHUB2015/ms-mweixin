<@ms.html5>
	<style> 	
		body .row{
			margin-top:0px;
			margin-bottom:20px;
		}
		/*模态框部分*/
		body .modal-dialog{
			width:450px;
		}
		textarea{
			resize: none;
		}
		body .form-horizontal .form-group{margin:0}
		/*素材列表*/
		.newsList{
			margin-left:2%;
			width:30%;
			margin-right:1%;
			float:left;
			margin-bottom: 20px;
			border: 1px solid #e7e7eb;
		}
		/*单图文部分*/
		.appmsg {
			overflow: hidden;	
			background-color: #fff;
			color: #666
		}
		.appmsg_info {
			font-size: 13px;
			line-height: 20px;
			padding-bottom: 10px;
			padding-top:14px;
		}
		.appmsg_date {
			font-weight: 400;
			font-style: normal
		}
		/*素材内容主体部分*/
		.appmsg_content {
			padding: 0 14px;
			*zoom: 1
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
			height: 174px;
			overflow: hidden
		}
		/*素材主图文缩略图*/
		.appmsg_thumb {
			width: 100%;
			height:100%;
		}
		.appmsg_desc {
			overflow: hidden;
			text-overflow: ellipsis; 
			-webkit-line-clamp: 2;
			-webkit-box-orient: vertical;
			display: -webkit-box;
			padding: 13px 0 5px;
			word-wrap: break-word;
			word-break: break-all
		}
		.desc_content{		
			min-height:230px;
			overflow:hidden;
		}
		.appmsg_opr {
			background-color: #f4f4f4;
			border-top: 1px solid #e7e7eb
		}
		.appmsg_opr ul {
			overflow: hidden;
			*zoom: 1
		}
		.appmsg_opr_item {
			float: left;
			line-height: 44px;
			height: 44px;
			width: 49%;
			font-size: 17px;
		}
		.appmsg_opr_item a {
			display: block;
			border-right: 1px solid #e7e7eb;
			text-align: center;
			text-decoration: none;
			cursor: pointer;	
		}	
		.appmsg_opr_item a:hover {
			text-decoration: none
		}	
		.appmsg_opr_item a.no_extra {
			border-right-width: 0
		}
		.appmsg_item {
			*zoom: 1;
			position: relative;
			padding: 6px 14px;
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
		/*多图文部分*/
		.multi .appmsg_info {
			padding-left: 14px;
			padding-right: 14px
		}
		.multi .appmsg_content {
			padding: 0
		}
		.multi .appmsg_title {
			font-size: 14px;
			padding-top: 0
		}
		.cover_appmsg_item {
			position: relative;
			margin: 0 14px 14px
		}
		.cover_appmsg_item .appmsg_title {
			position: absolute;
			bottom: 0;
			left: 0;
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
		.dialog_wrp .appmsg:hover {
			cursor: pointer
		}
		.ms-content--body-title{
			color: rgb(102, 102, 102);
    		line-height: 45px;
    		width: 100%;
    		z-index: 1500;
    		position: fixed;
    		right: 0px;
    		top: 45px;
   		text-align: right;
    		border-bottom: 1px solid rgb(211, 215, 219);
    		background: rgb(255, 255, 255);
    		padding: 0px 10px;
		}
	</style>
	<link href='/static/plugins/puBuLiu/css/style.css' rel='stylesheet'>
	<script src='/static/plugins/puBuLiu/js/velocity.js'></script>
	<script src='/static/plugins/puBuLiu/js/stackgrid.adem.js'></script>
	<script src='/static/plugins/puBuLiu/js/website.js'></script>
	<@ms.content>
        <@ms.contentBody>
            <!--title对应板块名称-->
            <@ms.contentNav title="素材管理">
            <button type="button" class="btn btn-success col-md" id="addSingleNews" >新增单图文&nbsp;<span class="glyphicon glyphicon-file"></span></button>
				<button type="button" class="btn btn-success col-md" id="addManyNews" >新增多图文&nbsp;<span class="glyphicon glyphicon-th-list"></span></button>
				<button type="button" class="btn btn-success col-md" id="addTextNews">新增文本&nbsp;<span class="glyphicon glyphicon-list-alt"></span></button>
            </@ms.contentNav>
            <@ms.contentPanel>
				<!--列表开始-->
				<div class="row grid-wrapper" id="newsListContainer">	
		   			<#if newsList?has_content>
		   				<#list newsList as list>
		   					<#if list.newsType==0>
								<!--单图文开始-->
								<div class="newsList grid-item">
									<div class="appmsg ">
							    		<div class="appmsg_content">
							       			<h4 class="appmsg_title"><#if list.newsMasterArticle?has_content>${list.newsMasterArticle.basicTitle?default('')}</#if></h4>
								            <div class="appmsg_info">
								                <em class="appmsg_date">${list.newsDateTime?string('MM月dd日')}</em>
								            </div>
							            	<div class="appmsg_thumb_wrp"><img src="<#if list.newsMasterArticle?has_content>${base}/${list.newsMasterArticle.basicThumbnails?default('')}</#if>" alt="" class="appmsg_thumb"></div>
							            	<p class="appmsg_desc"><#if list.newsMasterArticle?has_content>${list.newsMasterArticle.basicDescription?default('')}</#if></p>
							        	</div>				    
									    <div class="appmsg_opr">
									        <ul>
									            <li class="appmsg_opr_item grid_item size1of2">
									            	<a class="js_tooltip editNews" data-toggle="tooltip"  data-original-title="修改" data-id="${list.newsId?c?default(0)}" data-type="${list.newsType?c?default(1)}">&nbsp;<i class="icon18_common edit_gray glyphicon glyphicon-pencil"></i></a>
									            </li>
									            <li class="appmsg_opr_item grid_item size1of2 no_extra">
									                <a class="js_del no_extra js_tooltip deleteNews" data-toggle="tooltip" data-original-title="删除" data-id="${list.newsId?c?default(0)}">&nbsp;<i class="icon18_common del_gray glyphicon  glyphicon-trash"></i></a>
									            </li>
									        </ul>
									    </div>				    
									</div>
								</div>
								<!--单图文结束-->
							<#elseif list.newsType==1>	
								<!--多图文开始-->
								<div  class="newsList grid-item">
									<div class="appmsg multi">
									    <div class="appmsg_content">							        
								            <div class="appmsg_info">
								                <em class="appmsg_date">${list.newsDateTime?string('MM月dd日')}</em>
								            </div>
								            <div class="cover_appmsg_item">
								                <h4 class="appmsg_title">${list.newsMasterArticle.basicTitle?default('')}</h4>
								                <div class="appmsg_thumb_wrp"><img src="${base}/${list.newsMasterArticle.basicThumbnails?default('')}" alt="" class="appmsg_thumb"></div>
								            </div>
					       					<#if list.childs?has_content>
					       						<#list list.childs as listChild>
										            <div class="appmsg_item">
										                <img src="${base}/${listChild.basicThumbnails?default('')}" alt="" class="appmsg_thumb">
										                <h4 class="appmsg_title">${listChild.basicTitle?default('')}</h4>
										            </div>		       							
					       						</#list>
					       					</#if>					            							        
									    </div>							    
									    <div class="appmsg_opr">
									        <ul>
									            <li class="appmsg_opr_item grid_item size1of2">
									            	<a class="js_tooltip editNews" data-toggle="tooltip"  data-original-title="修改" data-id="${list.newsId?c?default(0)}" data-type="${list.newsType?c?default(1)}">&nbsp;<i class="icon18_common edit_gray glyphicon glyphicon-pencil"></i></a>
									            </li>
									            <li class="appmsg_opr_item grid_item size1of2 no_extra">
									                <a class="js_del no_extra js_tooltip deleteNews" data-toggle="tooltip" data-original-title="删除" data-id="${list.newsId?c?default(0)}">&nbsp;<i class="icon18_common del_gray glyphicon  glyphicon-trash"></i></a>
									            </li>
									        </ul>
									    </div>
									</div>
								</div>
								<!--多图文结束-->
							<#elseif list.newsType==2>
								<!--文本开始-->
								<div  class="newsList grid-item">
									<div class="appmsg ">
									    <div class="appmsg_content">
								            <div class="appmsg_info">
								                <em class="appmsg_date">${list.newsDateTime?string('MM月dd日')}</em>
								            </div>					        
								            <p class="appmsg_desc desc_content">${list.newsContent?default('')}</p>							        
									    </div>						    
									    <div class="appmsg_opr">
									        <ul>
									            <li class="appmsg_opr_item grid_item size1of2">
									            	<a class="js_tooltip editNews" data-toggle="tooltip"  data-original-title="修改" data-id="${list.newsId?c?default(0)}" data-type="${list.newsType?c?default(1)}">&nbsp;<i class="icon18_common edit_gray glyphicon glyphicon-pencil"></i></a>
									            </li>
									            <li class="appmsg_opr_item grid_item size1of2 no_extra">
									                <a class="js_del no_extra js_tooltip deleteNews" data-toggle="tooltip" data-original-title="删除" data-id="${list.newsId?c?default(0)}">&nbsp;<i class="icon18_common del_gray glyphicon  glyphicon-trash"></i></a>
									            </li>
									        </ul>
									    </div>						    
									</div>
								</div>
								<!--文本结束-->
							</#if>
						</#list>
					<#else>
						<div class="alert alert-info" role="alert">您还没有添加素材</div>
					</#if>				
				</div>			    
				<!--列表结束--> 
                <!--列表分页-->
            </@ms.contentPanel>
        </@ms.contentBody>
    </@ms.content>
    <!--删除文本素材模态框开始-->
	<@ms.modal  modalName="deleteModel" title="删除素材" >
		<@ms.modalBody>
			确定要删除该素材？
		</@ms.modalBody>
		<@ms.modalButton>
			<!--模态框按钮组-->
			<@ms.button  value="确定" id="deleteNews"/>
		</@ms.modalButton>
	</@ms.modal>
	<!--删除文本素材模态框结束-->	
	<!--新增or编辑文本模态框-->
	<@ms.modal  modalName="textModel" title="">
		<@ms.modalBody>
			<@ms.form isvalidation=true name="textNewsForm" id="textNewsForm"  action="" method="post"  >
				<@ms.hidden name="newsType" value="2"/>
				<@ms.textarea label="" name="newsContent" placeholder="请输入文本内容"  rows="4" style="height:200px;" maxlength="500" size="500"  />				
            </@ms.form>
		</@ms.modalBody>
		<@ms.modalButton>
			<!--模态框按钮组-->
			<@ms.button  value=""  id="saveOrUpdateButton"  />
		</@ms.modalButton>
	</@ms.modal>
</@ms.html5>
<script>
	$(function(){	
		var newsTextId = 0;
		
		//新增单图文
		$("#addSingleNews").click(function(){
			location.href = base+"${baseManager}/weixin/news/0/add.do"; 
		});
		
		//新增多图文
		$("#addManyNews").click(function(){
			location.href = base+"${baseManager}/weixin/news/1/add.do"; 
		})
		
		//打开新增文本模态框
		$("#addTextNews").click(function() {
			$("#textModelTitle").text("新增文本");
			$("#textNewsForm .newsContent").val("");
			$("#textNewsForm input[name=newsId]").val("");
			$("#saveOrUpdateButton").attr("data-type","");
			$("#saveOrUpdateButton").text("保存");
			$(".textModel").modal();
		});
		
		//编辑素材
		$(".editNews").click(function() {
			var newsContent = $(this).parent().parent().parent().prev("div").find("p").text();
			//素材类型
			var newsType = $(this).attr("data-type");
			//素材ID
			var newsId = $(this).attr('data-id');
			$("#saveOrUpdateButton").text("更新");
			if(newsType=="1" || newsType=="0") {
				location.href = base+"${baseManager}/weixin/news/"+$(this).attr('data-id')+"/edit.do";
			}else if (newsType=="2") {
	   			$("#textModelTitle").text("编辑文本");
	   			$("#textNewsForm textarea").val($.trim(newsContent));
	   			$("#textNewsForm input[name=newsId]").val($(this).attr("data-id"));
	   			$("#saveOrUpdateButton").attr("data-type","update");
	   		    $("#saveOrUpdateButton").attr("data-id",newsId);
	   		    $(".textModel").modal();				   				
			}
		});
		
		//打开删除素材模态框
		$(".deleteNews").click(function(){
			newsTextId = $(this).attr("data-id");
			$(".deleteModel").modal();
		});
		
		//删除
		$("#deleteNews").click(function(){
			$.ajax({
			  	url: base+"${baseManager}/weixin/news/"+newsTextId+"/delete.do",
			  	beforeSend:function(){
			  		$("#deleteNews").text("删除中");
			  		$("#deleteNews").attr("disabled",true);
			  	},
			    success: function(msg){
			   		if(msg.result == true){
			   			location.href = base+"${baseManager}/weixin/news/list.do"; 
			   		}else{
			   			<@ms.notify msg="该素材已被绑定，请先删除其绑定内容!" type="warning"/>
			   			$(".deleteModel").modal("hide");
			  			$("#deleteNews").attr("disabled",false);
			  			$("#deleteNews").text("删除");
			   		}
			   }
			});
		})	
		
		
		//新增or更新素材
		$("#saveOrUpdateButton").click(function(){
			var btnWord = $(this).text();
			//表单验证开始
			var vobj = $("#textNewsForm").data('bootstrapValidator').validate();
			if(vobj.isValid()){	
				//保存路径		
				var url = base+"${baseManager}/weixin/news/save.do";
				if($(this).attr("data-type")=="update") {				
					//更新路径
					url = base+"${baseManager}/weixin/news/"+$(this).attr("data-id")+"/update.do"; 
				}			
				//发送添加请求
				$.ajax({
				  	url: url,
				  	type:"post",
				  	data:$("#textNewsForm").serialize(),
				  	beforeSend:function(){
				  		$("#saveOrUpdateButton").text(btnWord+"中");
				  		$("#saveOrUpdateButton").attr("disabled","disabled");
				  	},
				    success: function(msg){
				    	var obj = msg.result;
				   		if(obj == true){
				   			<@ms.notify msg="更新成功" type="success"/>
				   			if($("#saveOrUpdateButton").attr("data-type")=="update") {
								location.href="${managerPath}/weixin/news/"+msg.resultData; 	
							}else{
								<@ms.notify msg="保存成功" type="success"/>
								location.href = base+"${baseManager}/weixin/news/list.do"; 
							}
				   		}
				   	}
				});	
			}	
		});	
	})
	
</script>	
