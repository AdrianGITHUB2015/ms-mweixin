<@ms.html5>
	<style>
		.ztree{
			margin-top:0px;
			padding-left:40px;
			padding-right:40px;
		}
		body .ms-content-body{
			width:85%;
		}
		
		body .ms-content-body-panel{
			margin:0;
			padding:0;
			overflow-y: hidden;
		}
		.ms-content--body-title{
		border-bottom: 1px solid #d3d7db;
   		background: #fff;
   		color: #666;
   		line-height: 45px;
   		padding: 0 10px;
    	width: 100%;
    	z-index: 1500;
    	text-align: center;
		font-weight:900;
		}
	</style>
	<@ms.content>
		<@ms.contentMenu>
			<div class="ms-content--body-title" style="background:none;">${weixinName?default('暂无')}</div>
		 	<ul id="menuTree" class="ztree"></ul> 
			<!-- 树形模块菜单结束 -->
		</@ms.contentMenu>
		<@ms.panel style="width: 85%;right: 0; position: absolute;">
				<iframe src="${managerPath}/weixin/weixinPeople/index.do"  style="width:100%;maring:0;padding:0;border:none;height:100%;" scrolling="no" id="listFrame" target="listFrame" ></iframe>
		</@ms.panel>
	</@ms.content>
</@ms.html5>
<script>
	$(function(){
		$.fn.zTree.init($("#menuTree"),setting,nodes);
	})
	/******设置节点信息开始******/
	//设置信息
	var setting = {
		edit: {
			enable: false,
			showRemoveBtn: false,
			showRenameBtn: false,
			drag: {
				prev: true,
				next: true,
				inner: true,
				isMove: false
			}
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		view: {
			showLine: false,
			showIcon: false
		}
	};
	var nodes = [
		{ "id":0, "name":"微信设置", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/'+${weixinId}+'/edit.do')"},
		{ "id":1, "name":"微信菜单", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/mweixin/menu/index.do')"},
		{ "id":1, "name":"素材列表", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/news/list.do')"},
		{ "id":1, "name":"微信用户", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/weixinPeople/index.do')"},
		{ "id":1, "name":"群发消息", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/message/index.do')"},
		{ "id":1, "name":"关注回复", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/mweixin/passiveMessage/form.do?pmType='+2)"},
		{ "id":1, "name":"被动回复", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/mweixin/passiveMessage/form.do?pmType='+3)"},
		{ "id":1, "name":"关键字回复", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/mweixin/passiveMessage/index.do')"},
		{ "id":1, "name":"返回", "url":"", "click":"$('#listFrame').attr('src','${managerPath}/weixin/index.do'),$('.ms-content-menu').hide(),$('.ms-content-body').css('width','100%'),$('.ms-content-body-panel').css('width','100%')"}
	]
	/******设置节点信息结束******/
</script>