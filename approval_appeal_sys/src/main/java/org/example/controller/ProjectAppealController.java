package org.example.controller;

import io.swagger.annotations.ApiOperation;
import org.example.entity.Project;
import org.example.entity.ProjectAppeal;
import org.example.query.ProjectAppealQuery;
import org.example.service.ProjectAppealService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/project-appeal")
@RestController
public class ProjectAppealController {
    @Resource
    private ProjectAppealService projectAppealService;

    @ApiOperation(value = "申诉")
    @PostMapping("/appeal")
    public Object appeal(ProjectAppeal appeal) {
        return projectAppealService.appeal(appeal);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Object page(ProjectAppealQuery query) {
        return projectAppealService.page(query);
    }

    @ApiOperation(value = "申诉通过")
    @PostMapping("/pass")
    public Object pass(ProjectAppeal projectAppeal) {
        return projectAppealService.pass(projectAppeal);
    }

    @ApiOperation(value = "申诉驳回")
    @PostMapping("/reject")
    public Object reject(ProjectAppeal projectAppeal) {
        return projectAppealService.reject(projectAppeal);
    }
}
