package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mysql.jdbc.StringUtils;
import org.example.entity.Project;
import org.example.entity.ProjectAppeal;
import org.example.entity.User;
import org.example.exception.CustomException;
import org.example.mapper.ProjectAppealMapper;
import org.example.mapper.ProjectMapper;
import org.example.query.ProjectAppealQuery;
import org.example.query.ProjectQuery;
import org.example.response.Result;
import org.example.response.ResultEnum;
import org.example.service.ProjectAppealService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Service
public class ProjectAppealServiceImpl implements ProjectAppealService {
    @Resource
    HttpSession session;
    @Resource
    ProjectAppealMapper projectAppealMapper;
    @Resource
    ProjectMapper projectMapper;

    @Override
    public Result appeal(ProjectAppeal appeal) {
        //1.先判断角色是否有申诉权限
        User user = (User) session.getAttribute("user");
        if(user.getRoleId() != 3){
            throw new CustomException(ResultEnum.NO_APPEAL_AUTHORITY);
        }
        //2.判断申诉的项目是否存在
        Project project = projectMapper.selectById(appeal.getProjectId());
        if(project == null){
            throw new CustomException(ResultEnum.PROJECT_NOT_EXISTS);
        }
        //3.判断申诉的项目状态
        //只有已经驳回的状态可以被申诉，当项目状态是已驳回的状态开启申诉通道
        if(project.getStatus() != 2) {
            throw new CustomException(ResultEnum.APPEAL_CHANNEL_NOT_ENABLED);
        }

        //4.判断该项目已申诉的次数,我要用哪个mapper哪个方法去统计 审批次数
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("project_id",appeal.getProjectId());
        Long count = projectAppealMapper.selectCount(qw);
        if(count == 3){
            throw new CustomException(ResultEnum.APPEAL_CHANNEL_IS_CLOSED);
        }
        //5.判断是否存在上一次的申诉,根据当前被申诉的项目id查申诉表，按申诉时间倒序排序截取第一行
        //6.判断上一次的申诉状态
        ProjectAppeal lastAppeal = projectAppealMapper.selectLastAppealByProjectId(appeal.getProjectId());
        if(lastAppeal != null && lastAppeal.getStatus() == 0){
            throw new CustomException(ResultEnum.APPEAL_ALREADY_EXISTS);
        }
        //以上所有的不允许申诉的条件都不满足，则可以申诉
        projectAppealMapper.insert(appeal);
        return Result.success();
    }

    @Override
    public Result page(ProjectAppealQuery query) {
        Page<ProjectAppeal> page = new Page<>(query.getCurrent(), query.getSize());
        QueryWrapper qw = new QueryWrapper<>();
        if (query.getAppealerId() == 3) {
            qw.eq("appealer_id", query.getAppealerId());
        }
        qw.eq(query.getProjectId() != null, "project_id", query.getProjectId());
        qw.gt(!StringUtils.isNullOrEmpty(query.getSubmitTime()), "submit_time", query.getSubmitTime());
        return Result.success(projectAppealMapper.selectPage(page, qw));
    }

    @Override
    public Result pass(ProjectAppeal appeal) {
        appeal.setStatus(1);
        projectAppealMapper.updateById(appeal);
        Project project = new Project();
        project.setId(appeal.getProjectId());
        project.setStatus(1);
        projectMapper.updateById(project);
        return Result.success();
    }

    @Override
    public Result reject(ProjectAppeal appeal) {
        projectAppealMapper.updateById(appeal);
        return Result.success();
    }
}
