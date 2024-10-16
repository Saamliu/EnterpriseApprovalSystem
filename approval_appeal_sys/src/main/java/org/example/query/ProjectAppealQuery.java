package org.example.query;

import lombok.Data;


@Data
public class ProjectAppealQuery extends BaseQuery{
    private Integer appealerId;
    // 支持项目id查询
    private Integer projectId;
    //支持根据提交日期查询
    private String submitTime;
}
