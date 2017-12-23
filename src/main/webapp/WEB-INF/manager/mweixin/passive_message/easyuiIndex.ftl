<@ms.html5>
	<!--按钮部分-->
	<div id="passiveMessageTable-toolbar">
        <a href="javascript:void(0)" onclick="$('#passiveMessageDialog').dialog('open',true)" class="easyui-linkbutton" iconCls="icon-mini-plus" plain="true" >添加</a>
        <a href="javascript:void(0)" onclick="$('#passiveMessageDialog').dialog('open')" class="easyui-linkbutton" iconCls="icon-edit" plain="true" >编辑</a>
        <a href="javascript:void(0)" id="del" class="easyui-linkbutton" iconCls="icon-mini-minus" plain="true" >删除</a>
        <div style="float:right;">
			<input class="easyui-textbox" type="text" id="pmId" prompt="请输入主键值"></input>
			<a href="#" class="easyui-linkbutton" id="search" data-options="iconCls:'icon-search'" style="width:80px">查询</a>
		</div>
    </div>
	<!--内容部分-->
	<div style="padding:1px;width:100%;height:96%;border: 0;overflow:auto;" >
		<table id="passiveMessageTable"></table>
		<script>
			var passiveMessageTable = $('#passiveMessageTable');
			$(function() {
				var queryPassiveMessageTable={};
				queryPassiveMessageTable.employeeStatus = -1;
				queryPassiveMessageTable.pageSize = 50;
				//加载数据
				passiveMessageTable.datagrid({
					url:'${managerPath}/mweixin/passiveMessage/list.do',
					striped:true,
					rownumbers:true,
					method:'post',
					toolbar:'#passiveMessageTable-toolbar',
					queryParams:queryPassiveMessageTable,
	  				nowrap:true,
	  				idField:"passiveMessageId",
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

							{field:'pmWeixinId',title:'微信编号',align:'left',width:'150'},
							{field:'pmId',title:'自增长ID'},

							{field:'pmEventId',title:'该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框',align:'left',width:'150'},

							{field:'pmNewsId',title:'回复的素材ID',align:'left',width:'150'},

							{field:'pmMessageId',title:'对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个',align:'left',width:'150'},

							{field:'pmAppId',title:'该回复所属的应用ID',align:'left',width:'150'},

							{field:'pmReplyNum',title:'被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复',align:'left',width:'150'},

							{field:'pmKey',title:'事件关键字',align:'left',width:'150'},

							{field:'pmType',title:'回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)',align:'left',width:'150'},

						]]
				});
				passiveMessageTable.datagrid("getPager").pagination({
					pageList:[10,20,30,40,50],
					onSelectPage:function(pageNumber, pageSize){
						var queryParams = passiveMessageTable.datagrid('options').queryParams;
						queryPassiveMessageTable.pageNo=pageNumber;
						queryPassiveMessageTable.pageSize=pageSize;
						passiveMessageTable.datagrid('reload',queryPassiveMessageTable);
					}
				});
			})
		</script>
	</div>
</@ms.html5>
<div id="passiveMessageDialog">
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false">
			<form id="passiveMessageForm" method="post">
				<table  style="width:auto;margin:0 auto;margin-top:15px;" >
					<div style="display:none">
						<input type="hidden" name="pmId" id="pmId" value="0"/>
						<!-------------模板区域开始--------------------------------->
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
									id="pmWeixinId" 
									name="pmWeixinId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="pmEventId" 
									name="pmEventId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								回复的素材ID
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="pmNewsId" 
									name="pmNewsId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="pmMessageId" 
									name="pmMessageId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								该回复所属的应用ID
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="pmAppId" 
									name="pmAppId" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="pmReplyNum" 
									name="pmReplyNum" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								事件关键字
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,300]']" 
									id="pmKey" 
									name="pmKey" 
									class="easyui-textbox"
								/>
							</td>
						</tr>
						<tr>
							<td align="right">
								回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)
							</td>
							<td>
								<input style="height:28px;" 
									value="" 
									type="text" 
									data-options="
										prompt:'请输入',
										required:true,
										validType:['number','length[1,10]']" 
									id="pmType" 
									name="pmType" 
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
										validType:['number','length[1,30]']" 
									id="pmTag" 
									name="pmTag" 
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
		var passiveMessageDialog = $('#passiveMessageDialog');
		var passiveMessageForm = $("#passiveMessageForm");
		var passiveMessageFormAction = "${managerPath}/mweixin/passiveMessage/save.do";
		//弹窗初始化
		passiveMessageDialog.dialog({
		    title: '微信被动消息回复管理',
		    width: 305,
		    height: 335,
		    onBeforeClose:function(){
				$('#passiveMessageDialog form').form('reset');
				passiveMessageFormAction = "${managerPath}/mweixin/passiveMessage/save.do";
			},
			modal:true,
			closed:true,
			iconCls:'',
			collapsible: false,
			minimizable:false,
			onBeforeOpen:function(){	//编辑
				var passiveMessage=passiveMessageTable.datagrid("getSelected"); //获取选中的记录
				if (passiveMessage==null) {
					<@ms.notify msg="请选择一条需要编辑的记录" type="warning"/>
					return false;
				}
				passiveMessageForm.form("load","${managerPath}/mweixin/passiveMessage/get.do?passiveMessageId="+passiveMessage.passiveMessageId); //初始化表单值 
				passiveMessageFormAction = '${managerPath}/mweixin/passiveMessage/update.do';
			}
		});
		//提交表单
		$("#saveOrUpdate").click(function() { //保存更新操作
			if (!passiveMessageForm.form('validate')) {//表单不合法
				return;
			}
			var today=new Date();		
			$("#passiveMessageUpdateDate").val(today);
			$("#passiveMessageCreateDate").val(today);
			$("#passiveMessageUpdateBy").val($("#saveOrUpdate").val());
			passiveMessageForm.attr("action",passiveMessageFormAction);
			$.ajax({
				type : "POST",
				url :passiveMessageFormAction,
				data :passiveMessageForm.serialize(),
				success : function(data) {
					if (data.result == false) {
						<@ms.notify msg="操作失败" type="fail"/>
				    } else {
				    	passiveMessageDialog.window("close");
				    	<@ms.notify msg= "操作成功" type= "success" />
				    	passiveMessageTable.datagrid('reload');
				    } 
				}
			});
		});
		//删除
		$("#del").click(function() {
			var passiveMessage=passiveMessageTable.datagrid("getSelected"); //获取选中的记录
			if (passiveMessage==null) {
				<@ms.notify msg="请选择一条需要删除的记录" type="warning"/>
				return;
			}
			passiveMessageForm.form("load",passiveMessage); //初始化表单值
			var arr = $.makeArray(passiveMessage);
			passiveMessage = JSON.stringify(arr);
			$.messager.confirm('提示信息','确认删除？',function() {
				$.ajax({
					type : "post",
					url : '${managerPath}/mweixin/passiveMessage/delete.do',
					data:passiveMessage,
					dataType:"json",
				  	contentType:"application/json",
					success : function(data) {
						if(data.result == false){
							<@ms.notify msg="操作失败" type="fail"/>
						}else{
					    	<@ms.notify msg= "操作成功" type= "success" />
					    	passiveMessageTable.datagrid('reload');
						}
					}
				});
			})
			passiveMessageTable.datagrid("clearSelections"); //清空选项
		});
		//搜索
		$("#search").click(function(){
			var queryParams =passiveMessageTable.datagrid('options').queryParams;
			queryParams.pmId = $("#pmId").val();
			passiveMessageTable.datagrid("reload");
		});
	})
</script>

