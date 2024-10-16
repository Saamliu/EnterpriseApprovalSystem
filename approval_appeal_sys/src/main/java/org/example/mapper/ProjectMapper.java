package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.Project;

import java.util.List;

public interface ProjectMapper extends BaseMapper<Project> {
    int logicBatchDelByIds(@Param("ids") List<Integer> list);
}
