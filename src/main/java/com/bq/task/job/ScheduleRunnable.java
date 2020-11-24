/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.bq.task.job;


import com.bq.task.exception.RRException;
import com.bq.task.utils.SpringContextUtils;
import com.esotericsoftware.reflectasm.MethodAccess;
import org.apache.commons.lang.StringUtils;

/**
 * 执行定时任务
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.2.0 2016-11-28
 */
public class ScheduleRunnable implements Runnable {
	private Object target;
	private MethodAccess methodAccess;
	private String params;
	private Integer methodIndex;
	
	public ScheduleRunnable(String beanName, String methodName, String params) throws NoSuchMethodException, SecurityException {
		this.target = SpringContextUtils.getBean(beanName);
		this.params = params;
		
		methodAccess = MethodAccess.get(target.getClass());

		methodIndex = methodAccess.getIndex(methodName);
	}

	@Override
	public void run() {
		try {
			if(StringUtils.isNotBlank(params)){

//				Object[] split = params.split(",");
				methodAccess.invoke(target,methodIndex, params);
			}else{
				methodAccess.invoke(target,methodIndex);
			}
		}catch (Exception e) {
			throw new RRException("执行定时任务失败", e);
		}
	}

}
