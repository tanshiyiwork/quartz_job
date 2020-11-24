package com.bq.task.service;

import com.bq.task.entity.ScheduleJobLogEntity;

/**
 * @author： Jhon
 * @date： 2020/11/23
 * @description： 调度任务日志信息
 */
public interface ScheduleJobLogService {

    /**
     * 插入调度程序日志信息
     * @param log
     */
    void insertScheduleJobLog(ScheduleJobLogEntity log);

}
