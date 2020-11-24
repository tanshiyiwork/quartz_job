package com.bq.task.init;

import com.bq.task.entity.TaskInfoEntity;
import com.bq.task.service.ScheduleJobService;
import com.bq.task.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author： Jhon
 * @date： 2020/11/24
 * @description：
 */
@Slf4j
@Component
public class ScheduleInit {

    @Autowired
    Scheduler scheduler;

    @Autowired
    TaskInfoService taskInfoService;

    @Autowired
    ScheduleJobService scheduleJobService;

    /**
     * 设置服务器重启之后  创建触发器
     */
    @PostConstruct
    public void init() {
        // 查询所有触发器
        List<TaskInfoEntity> taskInfoList = taskInfoService.getTaskInfoList();
        for (TaskInfoEntity taskInfo : taskInfoList) {
            scheduleJobService.createSchedule(scheduler, taskInfo);
        }
    }

}
