package com.bq.task.job;

import com.bq.task.entity.TaskInfoEntity;
import com.bq.task.entity.ScheduleJobLogEntity;
import com.bq.task.service.ScheduleJobLogService;
import com.bq.task.service.TaskInfoService;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @Author: jimmy
 * @Date: 2019/4/15 10:56
 * @Description: 自定义定时器job
 */
@Component
public class MyJob extends QuartzJobBean {

    private ExecutorService service = Executors.newSingleThreadExecutor();

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    ScheduleJobLogService scheduleJobLogService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        TaskInfoEntity scheduleJob = (TaskInfoEntity) context.getMergedJobDataMap()
                .get(TaskInfoEntity.MP_JOB_PARAM_KEY);
        ScheduleJobLogEntity log = new ScheduleJobLogEntity();
        log.setJobId(scheduleJob.getRecordId());
        log.setBeanName(scheduleJob.getBeanName());
        log.setMethodName(scheduleJob.getMethodName());
        log.setParams(scheduleJob.getParams());
        log.setCreateTime(new Date());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        // 查看 scheduleJob 是否已经保存
        if (scheduleJob.getEndDate() != null) {
            long nowTime = System.currentTimeMillis();
            long time = scheduleJob.getEndDate().getTime();
            if (time < nowTime) {
//                scheduleJob.setDelFlg(2L);
//
//                mpScheduleService.updateScheduleJob(scheduleJob);

                log.setError("定时任务时间已超时 现在时间:" + new Date() + " ; 任务时间 " + scheduleJob.getEndDate());

                log.setStatus(new Byte("1"));

                scheduleJobLogService.insertScheduleJobLog(log);
//                return;
            }
        }
        try {
            ScheduleRunnable task = new ScheduleRunnable(scheduleJob.getBeanName(),
                    scheduleJob.getMethodName(), scheduleJob.getParams());

            Future<?> future = service.submit(task);

            future.get();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);

            if (scheduleJob.getLastCount() != null) {

                scheduleJob.setLastCount(scheduleJob.getLastCount() - 1);
                taskInfoService.updateTaskInfo(scheduleJob);
            }
            log.setStatus(new Byte("0"));
        } catch (Exception e) {
            e.printStackTrace();

            //任务执行总时长
            long times = System.currentTimeMillis() - startTime;
            log.setTimes((int) times);

            //任务状态    0：成功    1：失败
            log.setStatus(new Byte("1"));
            log.setError(StringUtils.substring(e.toString(), 0, 2000));
        } finally {
            scheduleJobLogService.insertScheduleJobLog(log);
        }
    }

}

