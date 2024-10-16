package org.example.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class ProjectQuery extends BaseQuery{
    //支持项目名模糊查询
    private String projectName;
    //支持根据项目的审批状态进行查询
    private Integer status;
    //支持根据提交人id查询
    private Integer submitterId;
    //支持根据审批人id查询
    private Integer approverId;
    //支持根据提交日期查询
    private String submitTime;


}
