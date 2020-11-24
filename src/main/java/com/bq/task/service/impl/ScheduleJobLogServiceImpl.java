package com.bq.task.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bq.task.dao.ScheduleJobLogDao;
import com.bq.task.entity.ScheduleJobLogEntity;
import com.bq.task.service.ScheduleJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author： Jhon
 * @date： 2020/11/23
 * @description： 调度日志
 */
@Slf4j
@Service
public class ScheduleJobLogServiceImpl extends ServiceImpl<ScheduleJobLogDao, ScheduleJobLogEntity> implements ScheduleJobLogService {

    @Override
    public void insertScheduleJobLog(ScheduleJobLogEntity log) {
        baseMapper.insert(log);
    }
}
