/**
The MIT License (MIT) * Copyright (c) 2016 铭飞科技

 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:

 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.

 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.mingsoft.mweixin.biz.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingsoft.base.biz.impl.BaseBizImpl;
import com.mingsoft.base.dao.IBaseDao;
import com.mingsoft.weixin.biz.IWeixinBiz;
import net.mingsoft.mweixin.biz.IPassiveMessageBiz;
import net.mingsoft.mweixin.dao.IPassiveMessageDao;
import net.mingsoft.mweixin.entity.PassiveMessageEntity;

/**
 * 微信被动消息回复管理持久化层
 * 
 * @author 铭飞
 * @version 版本号：100<br/>
 *          创建日期：2017-12-22 9:43:04<br/>
 *          历史修订：<br/>
 */
@Service("netPassiveMessageBizImpl")
public class PassiveMessageBizImpl extends BaseBizImpl implements IPassiveMessageBiz {

	@Resource(name = "netPassiveMessageDao")
	private IPassiveMessageDao passiveMessageDao;

	@Autowired
	private IWeixinBiz weixinBiz;

	@Override
	protected IBaseDao getDao() {
		// TODO Auto-generated method stub
		return passiveMessageDao;
	}

	@Override
	public PassiveMessageEntity getEntity(PassiveMessageEntity passiveMessage) {
		// TODO Auto-generated method stub
		return (PassiveMessageEntity) passiveMessageDao.getByEntity(passiveMessage);
	}

}