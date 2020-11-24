package com.bq.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bq.task.dao.TaskInfoDao;
import com.bq.task.entity.TaskInfoEntity;
import com.bq.task.enums.TriggerType;
import com.bq.task.exception.RRException;
import com.bq.task.service.ScheduleJobService;
import com.bq.task.service.TaskInfoService;
import com.bq.task.job.MyJob;
import com.bq.task.utils.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

/**
 * @author： John
 * @date： 2020/11/23
 * @description： 调度操作service
 */
@Slf4j
@Service
public class ScheduleJobServiceImpl extends ServiceImpl<TaskInfoDao, TaskInfoEntity> implements ScheduleJobService {


    @Autowired
    TaskInfoService taskInfoService;

    /**
     * 创建定时任务   JobName 不能重复
     * <p>
     * triggerType   传递参数
     * <p>
     * CRON        cron  StartDate  EndDate(可传null)                适用于各种方式执行策略
     * <p>
     * SIMPLE      Count(执行次数)  StartDate  Interval(执行间隔)    可以设置什么时候执行
     * <p>
     * CALENDAR   startDate     calendar       count                定时执行器（秒 second，分钟 minute，小时 hour，天 day，月 months，年 year，星期 week）
     * <p>
     * DAILY      startTime   endTime (每天的开始时间和结束时间)  startDate  count  interval    指定每天的某个时间段内，以一定的时间间隔执行任务。
     */
    public Long createSchedule(Scheduler scheduler, TaskInfoEntity taskInfo) {

        if (taskInfo == null || taskInfo.getTriggerType() == null) {
            throw new RRException("参数不合法");
        }

        if (StringUtils.isBlank(taskInfo.getBeanName()) || StringUtils.isBlank(taskInfo.getMethodName())) {
            throw new RRException("请设置要执行的bean和method");
        }

        if (taskInfo.getCount() != null && taskInfo.getLastCount() == null) {
            taskInfo.setLastCount(taskInfo.getCount());
        }

        TriggerType triggerType = taskInfo.getTriggerType();

        Trigger trigger = null;

        try {
            switch (triggerType) {
                case CRON:

                    if (StringUtils.isBlank(taskInfo.getCron()) || taskInfo.getStartDate() == null) {
                        throw new RRException("参数不合法");
                    }

                    trigger = ScheduleUtils.getCronTrigger(taskInfo.getCron(), taskInfo.getStartDate(), taskInfo.getEndDate(),
                            taskInfo.getMisfireInstruction());
                    break;

                case SIMPLE:

                    if (taskInfo.getStartDate() == null) {
                        throw new RRException("参数不合法");
                    }

                    trigger = ScheduleUtils.getSimpleTrigger(taskInfo.getLastCount(), taskInfo.getStartDate(), taskInfo.getInterval()
                            , taskInfo.getMisfireInstruction());
                    break;

                case CALENDAR:

                    if (taskInfo.getStartDate() == null || StringUtils.isBlank(taskInfo.getCalendar())) {
                        throw new RRException("参数不合法");
                    }

                    trigger = ScheduleUtils.getCalendarIntervalTrigger(taskInfo.getStartDate(), taskInfo.getCalendar(), taskInfo.getCount());
                    break;

                case DAILY:
                    trigger = ScheduleUtils.getDailyTimeIntervalTrigger(TimeOfDay.hourAndMinuteAndSecondFromDate(taskInfo.getStartTime()), TimeOfDay.hourAndMinuteAndSecondFromDate(taskInfo.getEndTime()),
                            taskInfo.getStartDate(), taskInfo.getLastCount(), taskInfo.getInterval());
                    break;
            }
            // 插入 日志
            String oldJobName = taskInfo.getJobName();
            UUID uuid = UUID.randomUUID();
            taskInfo.setJobName(uuid.toString());

            if (taskInfo.getRecordId() == null) {//新增任务
                taskInfoService.insertTaskInfo(taskInfo);
            } else {//修改任务
                taskInfoService.updateTaskInfo(taskInfo);//更新修改信息至数据库
                try {
                    scheduler.deleteJob(JobKey.jobKey(oldJobName));//删除池中原有的任务
                } catch (SchedulerException e) {
                    e.printStackTrace();
                    throw new RRException("修改任务信息时删除触发器失败");
                }
            }
            // 创建job 任务
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class).withIdentity(uuid.toString()).build();
            //放入参数，运行时的方法可以获取
            jobDetail.getJobDataMap().put(TaskInfoEntity.MP_JOB_PARAM_KEY, taskInfo);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException(e.toString());
        }
        /*scheduleJob.setCreateTime(new Date());
        scheduleJob.setUpdateTime(new Date());
        scheduleJob.setDelFlg(0L);*/
        return taskInfo.getRecordId();
    }

    public void deleteScheduleJob(Scheduler scheduler, Long recordId) {
        // 删除 触发器
        TaskInfoEntity taskInfo = taskInfoService.getTaskInfoById(recordId);
        if (taskInfo == null) {
            return;
        }
        String jobName = taskInfo.getJobName();

        try {
            scheduler.deleteJob(JobKey.jobKey(jobName));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException("删除触发器失败");
        }
        taskInfo.setDelFlg(1L);
        taskInfo.setUpdateTime(new Date());
        taskInfoService.updateTaskInfo(taskInfo);
    }
}
