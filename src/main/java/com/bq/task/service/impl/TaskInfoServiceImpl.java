package com.bq.task.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bq.task.dao.TaskInfoDao;
import com.bq.task.entity.TaskInfoEntity;
import com.bq.task.response.ResponseData;
import com.bq.task.response.SuccessResponseData;
import com.bq.task.service.ScheduleJobService;
import com.bq.task.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author： John
 * @date： 2020/11/23
 * @description： 任务操作service
 */
@Slf4j
@Service
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoDao, TaskInfoEntity> implements TaskInfoService {

    @Autowired
    Scheduler scheduler;

    @Autowired
    ScheduleJobService scheduleJobService;

    @Override
    public void insertTaskInfo(TaskInfoEntity scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        baseMapper.insert(scheduleJob);
    }

    @Override
    public void updateTaskInfo(TaskInfoEntity scheduleJob) {
        scheduleJob.setUpdateTime(new Date());
        baseMapper.updateById(scheduleJob);
    }

    @Override
    public TaskInfoEntity getTaskInfoById(Long recordId) {
        LambdaQueryWrapper<TaskInfoEntity> select = Wrappers.<TaskInfoEntity>lambdaQuery().select();
        select.eq(TaskInfoEntity::getRecordId,recordId);
        select.eq(TaskInfoEntity::getDelFlg,0);
        return baseMapper.selectOne(select);
    }

    @Override
    public List<TaskInfoEntity> getTaskInfoList() {
        return baseMapper.getTaskInfosByCountOrType();
    }

    @Override
    public ResponseData<Long> createTask(TaskInfoEntity taskInfoEntity) {
        log.info("scheduleJob ===>" + taskInfoEntity);
        Long recordId = scheduleJobService.createSchedule(scheduler,taskInfoEntity);
        return ResponseData.success(SuccessResponseData.DEFAULT_SUCCESS_CODE,SuccessResponseData.DEFAULT_SUCCESS_MESSAGE,recordId);
    }

    @Override
    public ResponseData deleteTask(Long recordId) {
        scheduleJobService.deleteScheduleJob(scheduler,recordId);
        return ResponseData.success();
    }
}
