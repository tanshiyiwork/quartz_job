package com.bq.task.service;


import com.bq.task.entity.TaskInfoEntity;
import org.quartz.Scheduler;

/**
 * @Author: John
 * @Date: 2020/11/24
 * @Description: 调度程序处理
 */
public interface ScheduleJobService {


    /**
     * 创建调度程序
     * @param scheduler
     * @param taskInfo
     * @return
     */
    Long createSchedule(Scheduler scheduler, TaskInfoEntity taskInfo);

    /**
     * 删除调度程序
     * @param scheduler
     * @param recordId
     */
    void deleteScheduleJob(Scheduler scheduler, Long recordId);

}
