package com.bq.task.controller;

import com.bq.task.entity.TaskInfoEntity;
import com.bq.task.response.ResponseData;
import com.bq.task.service.TaskInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/task")
@RestController
public class TaskInfoController {

    @Autowired
    TaskInfoService taskInfoService;

    @PostMapping("/createTask")
    public ResponseData<Long> createTask(@RequestBody TaskInfoEntity taskInfo) {
        log.info("taskInfo  ==>" + taskInfo);
        return taskInfoService.createTask(taskInfo);
    }

    @GetMapping("/deleteTask")
    public ResponseData deleteTask(Long recordId) {
        if (recordId == null) {
            return ResponseData.error(403,"recordId");
        } else {
            return taskInfoService.deleteTask(recordId);
        }
    }

}
