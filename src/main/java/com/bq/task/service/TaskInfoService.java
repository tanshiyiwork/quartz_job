package com.bq.task.service;

import com.bq.task.entity.TaskInfoEntity;
import com.bq.task.response.ResponseData;

import java.util.List;

/**
 * @Author: John
 * @Date: 2020/11/24
 * @Description: 自定义任务处理
 */
public interface TaskInfoService {


    /**
     * 插入任务信息
     * @param scheduleJob
     */
    void insertTaskInfo(TaskInfoEntity scheduleJob);

    /**
     * 更新任务信息
     * @param scheduleJob
     */
    void updateTaskInfo(TaskInfoEntity scheduleJob);

    /**
     * 根据任务ID查询任务信息
     * @param recordId
     * @return
     */
    TaskInfoEntity getTaskInfoById(Long recordId);

    /**
     * 初始化时查询任务信息
     * @return
     */
    List<TaskInfoEntity> getTaskInfoList();

    /**
     * 创建任务
     * @param taskInfoEntity
     * @return
     */
    ResponseData<Long> createTask(TaskInfoEntity taskInfoEntity);

    /***
     * 删除任务
     * @param recordId
     * @return
     */
    ResponseData deleteTask(Long recordId);

}
