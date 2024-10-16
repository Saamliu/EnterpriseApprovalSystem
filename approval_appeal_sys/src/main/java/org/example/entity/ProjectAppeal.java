package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ProjectAppeal {
    //申诉id
    @TableId(type= IdType.AUTO)
    private Integer id;
    //项目id
    private Integer projectId;
    //申诉人id
    private Integer appealerId;
    //申诉理由
    private String appealReason;
    //申诉时间
    private String submitTime;
    //审批人id
    private Integer approverId;
    //审批时间
    private String approveTime;
    //拒绝原因
    private String rejectReason;
    //审批状态
    private Integer status;
}
