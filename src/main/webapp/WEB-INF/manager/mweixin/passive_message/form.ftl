<@ms.html5>
	 <@ms.nav title="微信被动消息回复编辑" back=true>
    	<@ms.saveButton  onclick="save()"/>
    </@ms.nav>
    <@ms.panel>
    	<@ms.form name="passiveMessageForm" isvalidation=true>
    		<@ms.hidden name="pmId" value="${(passiveMessageEntity.pmId)?default('')}"/>
    			<@ms.number label="微信编号" name="pmWeixinId" value="${(passiveMessageEntity.pmWeixinId)?default('')}" width="240px;" placeholder="请输入微信编号" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"微信编号长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框" name="pmEventId" value="${(passiveMessageEntity.pmEventId)?default('')}" width="240px;" placeholder="请输入该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"该回复属于的分类中的事件ID,1新关注2二维码扫描5未关注扫描二维码6点击事件4文本消息3二维码扫描&提示框长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="回复的素材ID" name="pmNewsId" value="${(passiveMessageEntity.pmNewsId)?default('')}" width="240px;" placeholder="请输入回复的素材ID" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"回复的素材ID长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个" name="pmMessageId" value="${(passiveMessageEntity.pmMessageId)?default('')}" width="240px;" placeholder="请输入对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"对应自定义模板回复消息id,与PM_NEWS_ID只能同时存在一个长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="该回复所属的应用ID" name="pmAppId" value="${(passiveMessageEntity.pmAppId)?default('')}" width="240px;" placeholder="请输入该回复所属的应用ID" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"该回复所属的应用ID长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复" name="pmReplyNum" value="${(passiveMessageEntity.pmReplyNum)?default('')}" width="240px;" placeholder="请输入被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"被动回复的次数;为1时表示用户第一次被动响应消息,依次递增,当超出时取值为0的进行回复长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="事件关键字" name="pmKey" value="${(passiveMessageEntity.pmKey)?default('')}"  width="240px;" placeholder="请输入事件关键字" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"事件关键字长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.number label="回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)" name="pmType" value="${(passiveMessageEntity.pmType)?default('')}" width="240px;" placeholder="请输入回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)" validation={"required":"false","maxlength":"50","data-bv-stringlength-message":"回复属性:1.最终回复;达到迭代次数之后的回复消息,2.拓展回复迭代回复(优先迭代回复)长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    			<@ms.text label="" name="pmTag" value="${(passiveMessageEntity.pmTag)?default('')}"  width="240px;" placeholder="请输入" validation={"required":"true","maxlength":"50","data-bv-stringlength-message":"长度不能超过五十个字符长度!", "data-bv-notempty-message":"必填项目"}/>
    	</@ms.form>
    </@ms.panel>
</@ms.html5>
<script>
	var url = "${managerPath}/mweixin/passiveMessage/save.do";
	if($("input[name = 'pmId']").val() > 0){
		url = "${managerPath}/mweixin/passiveMessage/update.do";
		$(".btn-success").text("更新");
	}
	//编辑按钮onclick
	function save() {
		$("#passiveMessageForm").data("bootstrapValidator").validate();
			var isValid = $("#passiveMessageForm").data("bootstrapValidator").isValid();
			if(!isValid) {
				<@ms.notify msg= "数据提交失败，请检查数据格式！" type= "warning" />
				return;
		}
		var btnWord =$(".btn-success").text();
		$(".btn-success").text(btnWord+"中...");
		$(".btn-success").prop("disabled",true);
		$.ajax({
			type:"post",
			dataType:"json",
			data:$("form[name = 'passiveMessageForm']").serialize(),
			url:url,
			success: function(status) {
				if(status.pmWeixinId > 0) { 
					<@ms.notify msg="保存或更新成功" type= "success" />
					location.href = "${managerPath}/mweixin/passiveMessage/index.do";
				}
				else{
					$(".btn-success").text(btnWord);
					$(".btn-success").removeAttr("disabled");
					<@ms.notify msg= "保存或更新失败！" type= "fail" />
				}
			}
		})
	}	
</script>
