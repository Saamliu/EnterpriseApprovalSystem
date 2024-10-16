package org.example.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import org.example.entity.Project;
import org.example.query.ProjectQuery;
import org.example.response.Result;
import org.example.service.ProjectService;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RequestMapping("/project")
@RestController
public class ProjectController {
    @Resource
    ProjectService projectService;

    @ApiOperation(value = "保存项目")
    @PostMapping("/save")
    public Object save(Project project){
        return projectService.save(project);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping("/page")
    public Object page(ProjectQuery query){
        return projectService.page(query);
    }

    @ApiOperation(value = "审批")
    @PostMapping("/approval")
    public Object approval(Project project){
        return projectService.approval(project);
    }

    @ApiOperation(value = "导出Excel")
    @GetMapping("/export-excel")
    public void exportExcel(String ids, String token, HttpServletResponse resp) throws Exception {
        projectService.exportExcel(ids, token, resp);
    }

    // 批量删除
    @ApiOperation(value = "批量删除")
    @PostMapping("/batchDel")
    public Object delete(String ids) throws JsonProcessingException {
        return projectService.batchDel(ids);
    }

    // 查看详情
    @ApiOperation(value = "查看详情")
    @GetMapping("/detail")
    public Object detail(Integer id) {
        return projectService.selectById(id);
    }
}
