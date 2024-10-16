package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.ProjectAppeal;

public interface ProjectAppealMapper extends BaseMapper<ProjectAppeal> {
    ProjectAppeal selectLastAppealByProjectId(@Param("pid") Integer projectId);
}
