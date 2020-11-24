package com.bq.task.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.bq.task.enums.TriggerType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("task_info")
public class TaskInfoEntity extends Model<TaskInfoEntity> implements Serializable {

    public static final String MP_JOB_PARAM_KEY = "MP_JOB_PARAM_KEY";

    /**
     * 记录ID
     */
    @TableId
    private Long recordId;

    /**
     * 定时器名称
     */
    private String jobName;

    /**
     * 定时器类型
     */
    private String jobType;

    /**
     * 触发器类型
     */
    private TriggerType triggerType;

    /**
     * 触发器名称
     */
    private String triggerName;

    /**
     * 开始时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date startDate;

    /**
     * 表达式
     */
    private String cron;

    /**
     * 结束时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date endDate;

    /**
     * 执行次数
     */
    private Integer count;

    /**
     * 执行间隔
     */
    private Integer interval;

    /**
     * 期限
     */
    private String calendar;

    /**
     * 每天开始时间
     */
    private Date startTime;

    /**
     * 每天结束时间
     */
    private Date endTime;

    /**
     * 剩余执行次数
     */
    private Integer lastCount;

    /**
     * 伦理删除标记
     */
    private Long delFlg;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新者ID
     */
    private String updateId;

    /**
     * 创建时间
     */
    @JsonFormat(shape=JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd hh:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建者ID
     */
    private String createId;

    private String beanName;

    private String methodName;

    private String params;

    /**
     *  失败策略 可从 misfire 中取
     */
    Integer misfireInstruction;

}