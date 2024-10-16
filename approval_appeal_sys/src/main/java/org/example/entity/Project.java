package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "项目信息", description = "用于描述一个项目审批的完整信息")
public class Project {
    //项目Id
    @TableId(type= IdType.AUTO)
    @ApiModelProperty(value = "项目Id")
    private Integer id;
    //项目名字
    private String projectName;
    //项目说明
    private String projectDescription;
    //项目附件
    private String attachment;
    //提交人id
    private Integer submitterId;
    //审批人id
    private Integer approverId;
    //审批状态
    private Integer status;
    //提交时间
    private String submitTime;
    //审批时间
    private String approveTime;
    //拒绝原因
    private String rejectReason;
    //逻辑删除判断
    private Integer isDelete;
    // 提交人姓名
    private String submitterName;
    // 审批人姓名
    private String approverName;
}
