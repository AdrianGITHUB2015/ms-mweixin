<@ms.html5>
	<!--按钮部分-->
	<div id="menuTable-toolbar">
        <a href="javascript:void(0)" onclick="$('#menuDialog').dialog('open',true)" class="easyui-linkbutton" iconCls="icon-mini-plus" plain="true" >添加</a>
        <a href="javascript:void(0)" onclick="$('#menuDialog').dialog('open')" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >编辑</a>
        <a href="javascript:void(0)" id="del" class="easyui-linkbutton" iconCls="icon-mini-minus" plain="true" >删除</a>
        <div style="float:right;">
			<input class="easyui-textbox" type="text" id="menuId" prompt="请输入主键值"></input>
			<a href="#" class="easyui-linkbutton" id="search" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
		</div>
    </div>
	<!--内容部分-->
	<div style="padding:1px;width:100%;height:96%;border: 0;overflow:auto;" >
		<table id="menuTable"></table>
		<script>
			var menuTable = $('#menuTable');
			$(function() {
				var queryMenuTable={};
				queryMenuTable.employeeStatus = -1;
				queryMenuTable.pageSize = 50;
				//加载数据
				menuTable.datagrid({
					url:'${managerPath}/mweixin/menu/list.do',
					striped:true,
					rownumbers:true,
					method:'post',
					toolbar:'#menuTable-toolbar',
					queryParams:queryMenuTable,
	  				nowrap:true,
	  				idField:"menuId",
	              	remoteSort: true,
					border:false,
					pageSize:50,
					loadMsg:"正在处理，请稍待。。。",
					nowrap:false,
					pagination:true,
					singleSelect:true,
					showFooter:false,
					loadFilter: function(data){
						if (data.total==0 || data=="" || data.result == false){
							data= { total: 0, rows: []};
						}
						return data;
					},
					columns:[[
							{field:'menuId',title:'菜单自增长编号'},

							{field:'menuAppId',title:'菜单所属商家编号',align:'left',width:'150'},

							{field:'menuTitle',title:'单菜名称',align:'left',width:'150'},

							{field:'menuUrl',title:'单菜链接地址',align:'left',width:'150'},

							{field:'menuStatus',title:'菜单状态 0：不启用 1：启用',align:'left',width:'150'},

							{field:'menuMenuId',title:'父菜单编号',align:'left',width:'150'},

							{field:'menuType',title:'菜单属性 0:链接 1:回复',align:'left',width:'150'},

							{field:'menuSort',title:'',align:'left',width:'150'},

							{field:'menuStyle',title:'类型：1文本 2图文 4外链接',align:'left',width:'150'},

							{field:'menuOauthId',title:'授权数据编号',align:'left',width:'150'},

						]]
				});
				menuTable.datagrid("getPager").pagination({
					pageList:[10,20,30,40,50],
					onSelectPage:function(pageNumber, pageSize){
						var queryParams = menuTable.datagrid('options').queryParams;
						queryMenuTable.pageNo=pageNumber;
						queryMenuTable.pageSize=pageSize;
						menuTable.datagrid('reload',queryMenuTable);
					}
				});
			})
		</script>
	</div>
</@ms.html5>
<div id="menuDialog">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false">
			<form id="menuForm" method="post">
				<table  style="width:auto;margin:0 auto;margin-top:15px;" >
					<div style="display:none">
						<input type="hidden" name="menuId" id="menuId" value="0"/>
						<!-------------模板区域开始--------------------------------->
						<tr>
							<td align="right">
								菜单所属商家编号
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuAppId" 
									name="menuAppId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								单菜名称
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,15]']" 
									id="menuTitle" 
									name="menuTitle" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								单菜链接地址
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,300]']" 
									id="menuUrl" 
									name="menuUrl" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								菜单状态 0：不启用 1：启用
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuStatus" 
									name="menuStatus" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								父菜单编号
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuMenuId" 
									name="menuMenuId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								菜单属性 0:链接 1:回复
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuType" 
									name="menuType" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuSort" 
									name="menuSort" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								类型：1文本 2图文 4外链接
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuStyle" 
									name="menuStyle" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								授权数据编号
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuOauthId" 
									name="menuOauthId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								微信编号
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="menuWeixinId" 
									name="menuWeixinId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<!-------------模板区域结束--------------------------------->
					</div>
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false" style="text-align:right;padding:5px;border-top: 1px solid #D3D3D3;">
			<div  class="easyui-linkbutton" id="saveOrUpdate" data-options="iconCls:'icon-ok'">
				确认
			</div>
		</div>
	</div>
</div>
<script>
	$(function() {
		var menuDialog = $('#menuDialog');
		var menuForm = $("#menuForm");
		var menuFormAction = "${managerPath}/mweixin/menu/save.do";
		//弹窗初始化
		menuDialog.dialog({
		    title: '微信菜单管理',
		    width: 305,
		    height: 335,
		    onBeforeClose:function(){
				$('#menuDialog form').form('reset');
				menuFormAction = "${managerPath}/mweixin/menu/save.do";
			},
			modal:true,
			closed:true,
			iconCls:'',
			collapsible: false,
			minimizable:false,
			onBeforeOpen:function(){	//编辑
				var menu=menuTable.datagrid("getSelected"); //获取选中的记录
				if (menu==null) {
					<@ms.notify msg="请选择一条需要编辑的记录" type="warning"/>
					return false;
				}
				menuForm.form("load","${managerPath}/mweixin/menu/get.do?menuId="+menu.menuId); //初始化表单值 
				menuFormAction = '${managerPath}/mweixin/menu/update.do';
			}
		});
		//提交表单
		$("#saveOrUpdate").click(function() { //保存更新操作
			if (!menuForm.form('validate')) {//表单不合法
				return;
			}
			var today=new Date();		
			$("#menuUpdateDate").val(today);
			$("#menuCreateDate").val(today);
			$("#menuUpdateBy").val($("#saveOrUpdate").val());
			menuForm.attr("action",menuFormAction);
			$.ajax({
				type : "POST",
				url :menuFormAction,
				data :menuForm.serialize(),
				success : function(data) {
					if (data.result == false) {
						<@ms.notify msg="操作失败" type="fail"/>
				    } else {
				    	menuDialog.window("close");
				    	<@ms.notify msg= "操作成功" type= "success" />
				    	menuTable.datagrid('reload');
				    } 
				}
			});
		});
		//删除
		$("#del").click(function() {
			var menu=menuTable.datagrid("getSelected"); //获取选中的记录
			if (menu==null) {
				<@ms.notify msg="请选择一条需要删除的记录" type="warning"/>
				return;
			}
			menuForm.form("load",menu); //初始化表单值
			var arr = $.makeArray(menu);
			menu = JSON.stringify(arr);
			$.messager.confirm('提示信息','确认删除？',function() {
				$.ajax({
					type : "post",
					url : '${managerPath}/mweixin/menu/delete.do',
					data:menu,
					dataType:"json",
				  	contentType:"application/json",
					success : function(data) {
						if(data.result == false){
							<@ms.notify msg="操作失败" type="fail"/>
						}else{
					    	<@ms.notify msg= "操作成功" type= "success" />
					    	menuTable.datagrid('reload');
						}
					}
				});
			})
			menuTable.datagrid("clearSelections"); //清空选项
		});
		//搜索
		$("#search").click(function(){
			var queryParams =menuTable.datagrid('options').queryParams;
			queryParams.menuId = $("#menuId").val();
			menuTable.datagrid("reload");
		});
	})
</script>

