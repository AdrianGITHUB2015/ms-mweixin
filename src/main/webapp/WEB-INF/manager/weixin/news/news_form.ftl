<@ms.html5>
	<style>	
		i, cite, em, var, address, dfn {
			font-style:normal;
		}
		b{
			display:block;
			font-weight: 400;
			font-style: normal;
		}
		body .col-md-4{
			padding:0
		}
		/*点击添加文章的+号*/
		.font-size30{
			font-size:60px;
			font-family:Arial, Helvetica, sans-serif;
			color:#CCCCCC;
		}
		/*标题*/
		.title{
			font-weight: 400;
			font-style: normal;
			font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","微软雅黑","黑体",Arial,sans-serif;
			color: #222;
			font-size: 14px;
		}
		/*单图文*/
		.singleNews{
			float:left;
			position:relative;
			top:13px;
			left:60px;
		}
		.onl_tit{
			overflow: hidden;
			text-align:center;
			height: 34px;
			line-height: 33px;
			font-weight: 400;
			font-family: "Helvetica Neue","Hiragino Sans GB","Microsoft YaHei","微软雅黑","黑体",Arial,sans-serif;
			color: #222;
			font-size: 14px;
		}
		.onl_name{
			width:100%;
			height: 180px;
			display: block;
			color: #c0c0c0;
			text-align: center;
			line-height: 160px;
			font-weight: 400;
			font-style: normal;
			background-color: #ececec;
			font-size: 16px;
			margin: 0px auto;
		}
		.onl_img{
			display:none;
		}
		/*多图文*/
		.duo{
			border: 1px solid #e7e7eb;
			color: #8d8d8d;
		}
		.parentNews{
			height: 160px;
			display: block;
			color: #c0c0c0;
			text-align: center;
			line-height: 160px;
			font-size: 16px;
			z-index: 1;
			background: rgb(236, 236, 236);	
		}
		.duo_name_out{
			display: none;
			width:100%;
			height:162px;
			background: rgba(229,229,229,0.85)!important;
			position:absolute;
		    top: 0; 
			z-index:10000
		}
		/*多图文素材主图文标题*/
		.duo_tit{
			height: 40px;
			background: #5E5E5E;
			display:block;
			line-height: 38px;
			text-align: left;
			padding-left: 15px;
			color: #fff;
		}
		.childNewes{		
			position: relative;	
		}
		.duo_div{
			height: 126px;
			border-top: 1px solid #e7e7eb;
		}
		.duo_div_out{
			position:relative;
			z-index: 1;
			
			height: 126px;
			bottom:54px;
			display: none;
			background: rgba(229,229,229,0.85)!important;
		}
		.duo_cen{
			width: 180px;
		    overflow: hidden;
		    height: 100%;
		    line-height: 120px;
		    float: left;
		    margin-left: 15px;
		}
		.childNewes_img{
			display: block;
			color: #c0c0c0;
			text-align: center;
			line-height: 84px;
			background-color: #ececec;
			width: 80px;
		    height: 80px;
		    margin: 20px 0;
			float: right;
			margin-right: 15px;
			overflow: hidden;
		
		}
		.childNewes_i{
			background: url(../image/new.png);
			background-position: -70px 380px;
			width: 40px;
			height: 33px;
			display: block;
			margin: 20px auto;
		}
		.addChildNews{
			display: block;
			height: 70px;
			border: 2px dotted #d9dadc;
			margin: 20px 14px;
		}
		.addChildNews:hover{
			border-color: #b3b3b3;
		}
		/*编辑图片移上显示效果*/
		.pen_img,.maskDel{cursor:pointer;width:30px;color:#c0c0c0;font-size:20px;top:54px}
		.pen_img:hover,.maskDel:hover {color:#838383;font-size:20px}
		/*添加多文章遮罩层*/
		.mask{
			width: 100%;
			height: 126px;
			background: rgba(229,229,229,0.85)!important;
			position: absolute;
			z-index: 10000;
		}
		.childNews{
			position:relative; 
			height:126px
		}
		.duo .newsImg{
			height:120px;
		}
		.danTuWen{
			border: 1px solid #e7e7eb;
		}
		.newsImg img{width:100%;height:100%}
		/*单图文和多图文中缩略图*/
		.img img{float:left;width:100%;height:100%}
		/*选取栏目弹出的树型菜单框*/
		ul#treeDemo{
			overflow: auto;
		    max-height: 240px;
		    width: 36%;
		    display: none;
		    z-index: 5555;
		    position: absolute;
		    top: -1px;
		    left: 16px;
		    background-color: #ffffff;
		    border: 1px solid rgba(0, 0, 0, 0.15);
		    border-radius: 4px;
		    -webkit-box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
		    box-shadow: 0 6px 12px rgba(0, 0, 0, 0.175);
		    background-clip: padding-box;
		}	
		/*搜索按钮*/
		.search{
			margin-left: 4%;
    		width: 15%;
		}
		.category-sort{
		   	float: left;
		    height: 34px;
		    width: 37%;
		}
		/*搜索框*/
		body .form-group{
			width:40%;
			float:left;
			margin-left:4%;
		}
		/*选择栏目框*/
		body .form-control{
			overflow: hidden;
    		padding: 0 12px;
    		line-height: 34px;
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
	<@ms.content>
		<@ms.contentBody>
			<#if news?has_content>
				<#if news.newsType == 0>
					<@ms.contentNav title="编辑单图文">
						<@ms.updateButton class="btn btn-success"  id="saveOrUpdateNews"  value="更新"/>
						<@ms.contentNavBack onclick="javascript:history.go(-1)"  class="btn btn-default" value="返回" />
					</@ms.contentNav >
				<#elseif news.newsType == 1>
					<@ms.contentNav title="编辑多图文">
						<@ms.updateButton class="btn btn-success"  id="saveOrUpdateNews"  value="更新"/>
						<@ms.contentNavBack onclick="javascript:history.go(-1)"  class="btn btn-default" value="返回" />
					</@ms.contentNav >
				</#if>
			<#else>
				<#if newsType == 0>
					<@ms.contentNav title="新增单图文">
						<@ms.saveButton class="btn btn-success"  id="saveOrUpdateNews"  value="保存"/>
						<@ms.contentNavBack onclick="javascript:history.go(-1)"  class="btn btn-default" value="返回" />
					</@ms.contentNav >
				<#else>
					<@ms.contentNav title="新增多图文">
						<@ms.saveButton class="btn btn-success"  id="saveOrUpdateNews"  value="保存"/>
						<@ms.contentNavBack onclick="javascript:history.go(-1)"  class="btn btn-default" value="返回" />
					</@ms.contentNav >
				</#if>
			</#if>
		<@ms.contentPanel>
			<!---添加素材开始--->
			<div class="row">
				<!--==========左边开始==========-->
				<div class="col-md-4 col-xs-6">
					<!--显示图文开始-->
					<div class="row">
				           <#if newsType == 0  >
			               		<!--单图文添加开始-->	
			               		<div class="danTuWen marsterId" <#if news?has_content>data-id="${news.newsMasterArticle.basicId}"</#if>>
			                        <p class="onl_tit title"><#if news?has_content>${news.newsMasterArticle.basicTitle?default("标题")}<#else>标题</#if></p>
			                        <div class="onl_name newsImg img"> 
			                            <#if news?has_content>
			                            	<img src="${base}/${news.newsMasterArticle.basicThumbnails}"/>
			                            <#else>
				                            <img src="#" class="onl_img"/>
				                            <i>封面图片</i>		                            
			                            </#if>
			                        </div>
			                        <p class="onl_tit onl_description description">
			                        	<#if news?has_content>${news.newsMasterArticle.basicDescription?default("描述...")}<#else>描述...</#if>
			                        </p>
			                    </div>
			                    <!--单图文添加结束-->	
				            <#else>     
			                    <!--多图文添加开始-->
			                    <div class="duo">
			                 		<div class="parentNews marsterId" data-click="false" <#if news?has_content>data-id="${news.newsMasterArticle.basicId}"</#if>>
			                 			<div class="newsImg img">
				                            <#if news?has_content>
				                            	<img src="${base}/${news.newsMasterArticle.basicThumbnails}"/>
				                            <#else>
					                            <img src="#" class="onl_img"/>
					                            <i>封面图片</i>		                            
				                            </#if>
			                            </div>
			                            <p class="duo_tit title"><#if news?has_content>${news.newsMasterArticle.basicTitle?default("标题")}<#else>标题</#if></p>
			                            <div class="duo_name_out" data=1 >
			                				<i class="pen_img glyphicon glyphicon-pencil" style="top:1px" data-id='0' data-target="parentNews"></i>
			            				</div>                               
			                        </div>
			                        <!--文章1-->
			                        <div class="childNewes" data-id="0" data-click="false">                  
			                            <div class="mask">
											<div class="col-md-5 col-xs-5"></div>
			                                <i class="pen_img glyphicon glyphicon-pencil" data-id='1'></i>
			                                <i class="glyphicon glyphicon-trash maskDel" ></i>
			                            </div>
			                           	<#if news?has_content>
			                           		<#list news.childs as article>
								             	<div  class="childNews child${article_index+1}" data-id="${article.basicId}">
								                	<div class="duo_div">
								                	    <div class="duo_cen title">${article.basicTitle}</div>
								                	    <i class="childNewes_img img"><img src="${base}/${article.basicThumbnails}" height="100%"/></i> 
								                	 </div>
								            	 </div>	                           	
			                           		</#list>
			                           	</#if>                 
			                        </div>
			                        <a href="javascript:void(0)">
			                            <!--点击添加文章div-->
			                            <div class="addChildNews">
											<div class="col-md-5 col-xs-5"></div>
			                                <i class="duo_pen_img glyphicon glyphicon-plus font-size30" ></i>
			                            </div>
			                        </a>	   
								</div>
								<!--多图文添加结束-->
				             </#if>
			             </div>
			             <!--显示图文结束-->
		            </div>
		            <!--==========左边结束==========-->		            
					<!--增加子新闻的模版begin-->
		            <div class="childNewsPlug" style="display:none;">
			             <div  class="childNews">
			                <div class="duo_div">
			                    <div class="duo_cen title">标题</div>
			                    <i class="childNewes_img img">缩略图</i> 
			                 </div>
			             </div>
		            </div> 
				 	<!--增加子新闻的模版end-->			            
		            <!--==========右边开始==========-->
		            <div class="col-md-8 col-xs-6" style="padding-right:0">  
			            <!--文章列表开始-->    
		                <div class="row">		                	
	                        <div id="columnTreeButton">
					           <div class="category-sort">
					                <!--栏目名下拉框开始-->
									<div class="treeDemoContent"  data-toggle="tooltip">
										 <ul class="ztree"  role="menu" aria-labelledby="dLabel" id="treeDemo">
										 </ul>
									</div>
					                <!--栏目名下拉框结束-->
					               	<button type="text" id="treeLabelinputTree" class="form-control dropdown-toggle chooseCategory" data-toggle="dropdown" aria-expanded="true">
										选择栏目
									</button>
					            </div> 
	                        	<div class="form-group">
									<input type="text" name="keyword" class="form-control" title="请输入关键字" placeholder="请输入关键字">
								</div>
	                        	<button type="button" class="btn btn-primary search" onclick="searchArticle()">
									搜索
								</button>
							</div>
			                <table class="table table-hover table table-bordered">
			                	<thead>
				                    <tr>
				                        <td style="width:15%" class="col-md-4 col-xs-2 text-center"><b>栏目名</b></td>
				                        <td class="col-md-8 col-xs-10 text-left"><b>文章标题</b></td>
				                    </tr>
			                    </thead>
			                    <tbody id="articleList">		
			                    </tbody>
			                </table> 
		                </div> 
						<!--通用查询分页开始-->
						<ul class="pagination pull-right">
							<li class="prev"><a href="javascript:prev()"><i class="fa fa-angle-left">上一页</i></a></li>
							<li class="next"><a href="javascript:next()"><i class="fa fa-angle-right">下一页</i></a></li>
						</ul>
						<!--通用查询分页结束-->    
		                <!--文章列表结束-->               
		            </div>
		            <!--==========右边结束==========-->
		        </div>
		        <!---添加素材结束--->
			</@ms.contentPanel>		
		</@ms.contentBody>
	</@ms.content> 
	<script id='articleListTmpl'  type='text/x-jquery-tmpl'>
		<#noparse>
	    <tr class="articleInfo">
	        <td class="text-center">{{if column}}${column.categoryTitle}{{/if}}</td>
	        <td>${basicTitle}</td>
	        <!-- 信息载体开始 -->
	        <td style="display:none;">
	        	<p>{{if column}}${column.categoryId}{{else}}0{{/if}}</p><!-- 文章所属栏目ID -->
	        	<p>${basicId}</p><!-- 文章ID -->
	        	<p>${basicTitle}</p><!-- 文章标题 -->
	        	<p>${basicThumbnails}</p><!-- 文章缩略图 -->
	        	<p>${basicDescription}</p><!-- 文章描述 -->
	        </td>
	        <!-- 信息载体结束 -->
	    </tr>		
	    </#noparse>
	</script>
	<div id='noRecord' style="display:none">
    	<p class="alert alert-info" role="alert" style="margin:0">
    		<span class="alert-link">
    			该分类没有添加文章哦！
    		</span>
	  	</p>
	</div>
</@ms.html5>	
<script>
	var targetNews=""; //保存当前用户点击的的编辑区域
	<#if newsType == 0>
		targetNews  = "danTuWen";
	</#if>
	
	var newsType =" ${newsType?default("0")}";//0单图文 1:多图文
	var listColumn = "";//分类列表
	<#if listColumn?has_content>
		listColumn = ${listColumn?default('')}; 
	</#if>

	var curPage = 1; //当前页  		
	var pageSize = 10; //每页显示的条数
	var articleCount; //文章总数
	var pageCount; //总页数
	var categoryId = 0; //当前分类
	var newsCategoryId=<#if news?exists>${news.newsCategory.categoryId}<#else>0</#if>;//素材的分类编号
	var flag;
	
	//搜索关键字
	function searchArticle(){
		//获取关键字
		var keyword = $.trim($("input[name='keyword']").val());
		if(keyword=="" || keyword==undefined){
			<@ms.notify msg="关键词不能为空!" type="warning"/>
			return;
		}		
		if (curPage<1) {
			curPage = 1;
		}
		if (curPage>=pageCount) {
			curPage = pageCount;
		}		
	 	//读取文章列表
		$.ajax({
			type: "POST",
			url:"${base}/mcms/article/list.do",
		   	data: "pageNo="+curPage+"&basicCategoryId="+categoryId+"&basicTitle="+keyword,
		   	beforeSend: function() {
		   		$("#articleList").html("<tr><td colspan='3'><center>加载中...</center></td></tr>");
		   	},
		   	success: function(data){
		   		$("#articleList").html("");
		   		if (data.list.length==0) {
		   			$("#articleList").html("<tr><td colspan='3'><center>"+$("#noRecord").html()+"</td></tr>");
		   		}
		   		flag = true;
		   		$(".loadPage").hide();
		   		$(".searchPage").show();
		   		articleCount = data.total;
				//计算总页数
				if (articleCount==0) {
					pageCount = 0;
				} else {
					//判断是否有余数
					if(articleCount % pageSize==0){
						pageCount = articleCount / pageSize;
					} else {
						pageCount = parseInt(articleCount / pageSize )+ 1;
					}
				}			   				   		
		   		$('#articleListTmpl').tmpl(data.list).appendTo('#articleList');
		   	}			   
		});
	}
	//加载文章数据
	function loadArticle() {
		if (curPage<1) {
			curPage = 1;
		}
		if (curPage>=pageCount) {
			curPage = pageCount;
		}		
	 	//读取文章列表
		$.ajax({
			type:"post",
			url:"${base}/mcms/article/list.do",
		   	data: "pageNo="+curPage+"&pageSize="+pageSize+"&basicCategoryId="+categoryId,
		   	beforeSend: function() {
		   		$("#articleList").html("<tr><td colspan='3'><center>加载中...</center></td></tr>");
		   	},
		   	success: function(data){
		   		$("#articleList").html("");
		   		if (data.list.length==0) {
		   			$("#articleList").html("<tr><td colspan='3'><center>"+$("#noRecord").html()+"</td></tr>");
		 		}	
		   		flag = false;	   		
		   		articleCount = data.total;
				//计算总页数
				if (articleCount==0) {
					pageCount = 0;
				} else {
					//判断是否有余数
					if(articleCount % pageSize==0){
						pageCount = articleCount / pageSize;
					} else {
						pageCount = parseInt(articleCount / pageSize )+ 1;
					}
				}			   				   		
		   		$('#articleListTmpl').tmpl(data.list).appendTo('#articleList');
		   	}			   
		});
	}
	
	//通用查询分页：上一页
	function prev() {
		curPage = curPage-1;
		if(flag){
			searchArticle();
		}else{
			loadArticle();
		}	
	}
	//通用查询分页：下一页
	function next() {
		curPage = curPage+1
		if(flag){
			searchArticle();
		}else{
			loadArticle();
		}	
	}
	
	$(function() {
	
		loadArticle(); //默认加载全部数据
		
		$(".mask").hide();
		/**
		 * 点击右侧文章</br>
		 * 将用户点击的文章显示到左侧图文消息中</br>
		 */
		$("table").delegate(".articleInfo","click",function(){
			if (targetNews=="") {
			 	<@ms.notify msg="请选择左边需要编辑消息" type="warning"/>
				return;
			}
			newsCategoryId  = $(this).find("td").last().find("p").eq(0).text();//文章所属栏目ID
			var articleId = $(this).find("td").last().find("p").eq(1).text();//文章ID
			var articleTitle = $(this).find("td").last().find("p").eq(2).text(); //文章标题
			var articleImg = $(this).find("td").last().find("p").eq(3).text();//文章缩略图
			var articleDescription =$(this).find("td").last().find("p").eq(4).text(); //文章描述
			$("."+targetNews +" .title").html(articleTitle);
			$("."+targetNews +" .description").html(articleDescription);
			if (newsType==0) {
				$(".marsterId").attr("data-id",articleId);
				$("."+targetNews +" .img").html("<img src='${base}"+articleImg+"'  style='margin-top:0'/>");
			} else {
				$("."+targetNews).attr("data-id",articleId);
				$("."+targetNews +" .img").html("<img src='${base}"+articleImg+"' />");
			}
		});		
		
		/**
		 * 点击保存按钮
		 */
		$("#saveOrUpdateNews").click(function(){
			var btnWord = $(this).text();
			var url = "${base}${baseManager}/weixin/news/save.do";
			<#if news?has_content>
			 	url =  "${base}${baseManager}/weixin/news/${news.newsId}/update.do";
			</#if>
			var newsChildArticleIds = ""; //子图文消息
			$(".childNews[data-id]").each(function() {  //遍历子图文消息获取文章id 
				var dataId = $(this).attr("data-id");
				if (dataId=="" || dataId==null || typeof(dataId) == "undefined") { //如果子文章的id为空，说明用户没有选择，作出提示
			 		<@ms.notify msg="信息不完整！请重新填写。" type="warning"/>
					$("div.editTarget").removeClass("editTarget");
					$(this).addClass("editTarget");//提示用户那片文章没有选择
		  			return;
				}
				newsChildArticleIds+=dataId+",";
			});
		  	if (newsChildArticleIds==""　&& newsType==1) {
			 	<@ms.notify msg="信息不完整,请重新填写!" type="warning"/>
		  		return;
		  	} else if (newsType==0 && parseInt($(".marsterId").attr("data-id"))<=0 ||  typeof($(".marsterId").attr("data-id")) == "undefined") {
			 	<@ms.notify msg="请选择一篇文章作为单图文素材！" type="warning"/>
		  		return;
		  	}
		  	if (newsCategoryId=="" || newsCategoryId==0) {
			 	<@ms.notify msg="请选择素材所属分类" type="warning"/>
		  		return;
		  	}
			//发送添加请求
			$.ajax({
			   url:url,
			   data:"newsType="+newsType+"&newsMasterArticleId="+$(".marsterId").attr("data-id")+"&newsChildArticleIds="+newsChildArticleIds+"&newsCategoryId="+newsCategoryId,
			   beforeSend:function(){
			   		<#if news?has_content>							
			   			$("#saveOrUpdateNews").text("更新中");
		   			<#else>
			   			$("#saveOrUpdateNews").text("保存中");
					</#if>
					$("#saveOrUpdateNews").attr("disabled","disabled");
			   },
			   success: function(msg){
			   		if(msg.result == true){
			   			<#if news?has_content>							
			 				<@ms.notify msg="更新成功" type="success"/>
			   			<#else>
			 				<@ms.notify msg="添加成功" type="success"/>
						</#if>
			   			location.href = base+"${baseManager}/weixin/news/list.do"; 
			   		}else{
			 			<@ms.notify msg="请重试！" type="warning"/>
			   			$("#saveOrUpdateNews").text(btnWord);
			   			$("#saveOrUpdateNews").attr("disabled",false);	
			   		}
				}
			}); 
		});
	
		//获取栏目节点列表
		var zNodes = new Array();
		zNodes[0]={ id:0, pId:0,name:'全部栏目', open:true};
		//遍历节点，添加到数字中
		for( var i = 0 ; i < listColumn.length; i++){
			zNodes[i+1] = { id:listColumn[i].categoryId, pId:listColumn[i].categoryCategoryId,name:listColumn[i].categoryTitle, open:true};
		}	
		 
		//父栏目选择下拉点击事件
		$(".chooseCategory").click(function(event){
			$("#treeDemo").toggle();
		});		
		
		//文章过滤分类
		var setting = {
			callback: {
				onClick: function(event, treeId, treeNode) {
					$("#columnTreeButton .chooseCategory").text(treeNode.name);
					$("#categoryCategoryId").val(treeNode.id);
					$("#treeDemo").hide();
					categoryId = treeNode.id;
					loadArticle();
					event.stopPropagation();
				}
			},		
			data: {
				simpleData: {
				enable: true
				}
			}
		};		
   		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
		});		 	
			
		//鼠标点击事件不在节点上时隐藏下拉框
		$("body").bind("mousedown", function(event) {  
            if (!(event.target.id == "columnSuperTitle" || $(event.target).parents(".treeDemoContent").length > 0)) {  
                $("#treeDemo").hide();
            }  
        });		
	
	
		var i=0;
		<#if !news?has_content> //如果是编辑模式就不需要初始化一个子消息
			addChildNews();
		</#if>
		
		/*************************************************************************/			
		//删除，根据mask上的data-index属性
		$(".mask .maskDel").on("click",function(){
			var u = $(".childNewes").height();		//获取外层大div的高度
			if(u!=126){								//如果这不是最后一个div则可以进行删除，否则不能删除				 	
				$(".childNews").eq(parseInt($(".mask").attr("data-index"))-1).remove();	//条件成立删除遮罩层下的div
				$(".mask").attr("data-index",$(".mask").attr("data-index")-1);			//可到遮罩层上移的位置即data-index								
				var obj = $(".mask").attr("data-index");		//重新获取遮罩层的位置
				$(".mask").animate({top:(obj-1)*126},150);		//遮罩层向上移动(上移的位置再乘以每格要偏移的像素数（*126）)
			}else{
			 	<@ms.notify msg="多文章标题不得少于一条！" type="warning"/>
			}
		});	
		
		//给“+”div增加新增childNews
		$(".addChildNews").click(function() {
			addChildNews(); //添加子新闻
		});							
			
		//添加大标题遮罩事件
		$(".parentNews").hover(function() {								
				$(".duo_name_out").show();
				$(".mask").hide();
			},function(){
				$(".duo_name_out").hide();
				//$(this).find(".duo_name_out").remove();
		});
		
		//点击编辑按钮,激活对应的相应内容
		$(".duo").delegate(".pen_img","click",function(){
			$("div.editTarget").removeClass("editTarget");
			$("."+$(this).attr("data-target")).addClass("editTarget");
			targetNews = 	$(this).attr("data-target");
		});
		
		//移除所有遮罩
		$(".mask").hover(function() { 
			//setTimeout("$('.mask').hide()",1000);
		},function(){
			$('.mask').hide();
		});
		
	});
		
	/**
	*增加一个子新闻
	*/
	function addChildNews() {
		if ($(".childNews").length>7) {
			<@ms.notify msg="最多8条图文消息！" type="warning"/>
			return;
		}
		$(".childNewes").append($(".childNewsPlug").html());
	}	
	 //给子新闻增加鼠标效果
   	$(".childNewes").delegate(".childNews","mouseover",function() {
		//$("data-index")当前新闻位置
		$(".mask").show();	//滑块遮罩层始终存在
		$(".mask").animate({top:($(this).index()-1)*126},100);	//添加一个文章标题div
		$(".mask").attr("data-index",$(this).index());	
		$(this).addClass("child"+$(this).index());			
		$(".mask .pen_img").attr("data-target","child"+$(this).index());
	});	
</script>