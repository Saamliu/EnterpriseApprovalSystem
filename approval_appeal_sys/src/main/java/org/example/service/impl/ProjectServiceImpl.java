package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.jdbc.StringUtils;
import org.example.entity.Project;
import org.example.entity.User;
import org.example.exception.CustomException;
import org.example.mapper.ProjectMapper;
import org.example.query.ProjectQuery;
import org.example.response.Result;
import org.example.response.ResultEnum;
import org.example.service.ProjectService;
import org.example.util.ExcelUtil;
import org.example.util.JWTUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Resource
    ProjectMapper projectMapper;
    @Resource
    HttpSession session;

    @Override
    public Result save(Project project) {
        //HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("project_name", project.getProjectName());
        //1.先检查项目是否已经存在
        Project one = projectMapper.selectOne(queryWrapper);
        if (one != null) {
            throw new CustomException(ResultEnum.PROJECT_ALREADY_EXISTS);
        }
        project.setSubmitterId(user.getId());
        //保存项目
        projectMapper.insert(project);

        return Result.success(project);
    }

    @Override
    public Result page(ProjectQuery query) {

        Page<Project> page = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper qw = new QueryWrapper<>();
        qw.like(!StringUtils.isNullOrEmpty(query.getProjectName()), "project_name", query.getProjectName());
        qw.eq(query.getStatus() != null, "status", query.getStatus());
        qw.eq(query.getSubmitterId() != null, "submitter_id", query.getSubmitterId());
        qw.eq(query.getApproverId() != null, "approver_id", query.getApproverId());
        qw.gt(!StringUtils.isNullOrEmpty(query.getSubmitTime()), "submit_time", query.getSubmitTime());
        // is_delete为0的才显示
        qw.eq("is_delete", 0);
        return Result.success(projectMapper.selectPage(page, qw));
    }

    @Override
    public Result approval(Project project) {
        //需要知道当前是谁在审批这个项目，根据token获取当前用户
        User user = (User) session.getAttribute("user");
        if (user.getRoleId() == 3) {
            throw new CustomException(ResultEnum.NO_APPROVAL_AUTHORITY);
        }
        Project one = projectMapper.selectById(project.getId());
        if (one == null) {
            throw new CustomException(ResultEnum.PROJECT_NOT_EXISTS);
        }
        if (one.getStatus() != 0) {
            throw new CustomException(ResultEnum.PROJECT_ALREADY_APPROVAL);
        }

        project.setApproverId(user.getId());
        //设置更新日期
        project.setApproveTime(LocalDateTime.now().toString());
        projectMapper.updateById(project);
        return Result.success();
    }

    @Override
    public void exportExcel(String ids, String token, HttpServletResponse resp) throws Exception {
        // 校验token
        JWTUtil.parseToken(token);
        // 对前端传回的ids解码
        ids = URLDecoder.decode(ids, "UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] idList = objectMapper.readValue(ids, Integer[].class);
        // 用ids向数据库取数据
        // 生成的sql语句：select * from project where id in (?, ?, ?, ?)
        List<Project> projectList = projectMapper.selectBatchIds(Arrays.asList(idList));
        // 调用工具类中的方法，导出Excel
        ExcelUtil.excelExport(resp, "项目数据", Project.class, "项目", projectList);
    }

    @Override
    public Result batchDel(String ids) throws JsonProcessingException {
        // 使用jackson中提供的ObjectMapper类来解析json
        ObjectMapper objectMapper = new ObjectMapper();
        Integer[] idList = objectMapper.readValue(ids, Integer[].class);
        projectMapper.logicBatchDelByIds(Arrays.asList(idList));
        return Result.success();
    }

    @Override
    public Result selectById(Integer id) {
        return Result.success(projectMapper.selectById(id));
    }
}
