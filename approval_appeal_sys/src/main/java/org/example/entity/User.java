package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
    //用户id
    @TableId(type= IdType.AUTO)
    private Integer id;
    //用户账号
    private String username;
    //用户密码
    private String password;
    //用户头像
    private String avatar;
    //用户邮箱
    private String email;
    //用户电话
    private String phone;
    //角色id
    private Integer roleId;
    //用户角色名字
    private String roleName;
    //真实姓名
    private String realname;
    //是否被封号
    private Integer isLock;
    @TableField(exist = false)
    private String token;
}
