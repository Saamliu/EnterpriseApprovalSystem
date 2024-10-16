package org.example.service;

import org.example.entity.ProjectAppeal;
import org.example.query.ProjectAppealQuery;
import org.example.response.Result;

public interface ProjectAppealService {
    /**
     * 用户申诉业务方法
     *
     * @param appeal 申诉对象
     * @return 返回申诉结果
     */
    Result appeal(ProjectAppeal appeal);

    // 查看申诉列表
    Result page(ProjectAppealQuery query);

    Result pass(ProjectAppeal appeal);

    Result reject(ProjectAppeal appeal);
}
