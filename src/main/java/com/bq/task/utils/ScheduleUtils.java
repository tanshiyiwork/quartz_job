package com.bq.task.utils;

import org.quartz.*;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import java.util.Date;

import static org.quartz.CalendarIntervalScheduleBuilder.calendarIntervalSchedule;
import static org.quartz.DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule;


public class ScheduleUtils {

    /**
     * cron 定时执行器
     * 优点:适用于各种方式执行策略
     * 缺点: 只能不能设置执行次数
     *
     * @param cron
     * @param startDate 开始时间
     * @param endDate   结束时间  可以传null  null 就是没有结束时间
     * @param misfireInstruction  执行策略
     * @return
     */
    public static CronTrigger getCronTrigger(String cron, Date startDate, Date endDate,Integer misfireInstruction) {
        CronTrigger trigger = TriggerBuilder
                .newTrigger()
                .startAt(startDate) // 定时执行
                .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                .endAt(endDate)
                .build();
        if (misfireInstruction != null){
            ((CronTriggerImpl)trigger).setMisfireInstruction(misfireInstruction);
        }else{
            ((CronTriggerImpl)trigger).setMisfireInstruction(CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING);
        }
        return trigger;
    }

    /**
     * 简单执行器
     * 优点 可以设置什么时候执行
     * 缺点 功能太少
     * @param count     执行次数   0是执行1次
     * @param startDate 执行时间
     * @param interval  间隔
     * @param misfireInstruction
     * @return
     */
    public static SimpleTrigger getSimpleTrigger(Integer count, Date startDate, Integer interval, Integer misfireInstruction) {
        if (interval == null || count == null || count - 1 < 0 || (count - 1 > 0  && interval == 0)) {
            throw new RuntimeException("Repeat Interval cannot be zero");
        }
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(count - 1).withIntervalInSeconds(interval);
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .startAt(startDate)
                .withSchedule(simpleScheduleBuilder)
                .build();
        if (misfireInstruction != null){
            ((SimpleTriggerImpl)trigger).setMisfireInstruction(misfireInstruction);
        }
        return trigger;
    }

    /**
     * 定时执行器（秒 second，分钟 minute，小时 hour，天 day，月 months，年 year，星期 week）
     * 可以定制
     * @param startDate 从什么时候开始
     * @param calendar  定时执行的期限
     * @param count     执行次数
     * @return
     */
    public static CalendarIntervalTrigger getCalendarIntervalTrigger(Date startDate, String calendar, Integer count) {
        CalendarIntervalScheduleBuilder calendarIntervalScheduleBuilder = null;
        switch (calendar) {
            case "second":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInSeconds(count);
                break;
            case "minute":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInMinutes(count);
                break;
            case "hour":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInHours(count);
                break;
            case "day":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInDays(count);
                break;
            case "week":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInWeeks(count);
                break;
            case "year":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInYears(count);
                break;
            case "months":
                calendarIntervalScheduleBuilder = calendarIntervalSchedule()
                        .withIntervalInMonths(count);
                break;
        }
        return TriggerBuilder.newTrigger()
                .startAt(startDate)
                .withSchedule(calendarIntervalScheduleBuilder)
                .build();
    }


    /**
     * 指定每天的某个时间段内，以一定的时间间隔执行任务。
     *
     * @param startTime 每天开始时间
     * @param endTime   每天结束时间
     * @param startDate 开始时间
     * @param count     执行次数
     * @param interval  执行间隔
     * @return
     */
    public static DailyTimeIntervalTrigger getDailyTimeIntervalTrigger(TimeOfDay startTime, TimeOfDay endTime, Date startDate, Integer count, Integer interval) {
        return TriggerBuilder.newTrigger()
                .startAt(startDate)
                .withSchedule(
                        dailyTimeIntervalSchedule()
                                .startingDailyAt(startTime)
                                .endingDailyAt(endTime)
                                .withIntervalInHours(interval)
                                .withRepeatCount(count))
                .build();
    }

}
