package com.bq.task.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bq.task.entity.TaskInfoEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskInfoDao extends BaseMapper<TaskInfoEntity> {

    List<TaskInfoEntity> getTaskInfosByCountOrType();

}
