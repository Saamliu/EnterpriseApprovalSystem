package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.entity.User;
import org.example.exception.CustomException;
import org.example.mapper.UserMapper;
import org.example.response.Result;
import org.example.response.ResultEnum;
import org.example.service.UserService;
import org.example.util.JWTUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    UserMapper userMapper;
    @Override
    public Result<User> login(User user) {
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("username",user.getUsername());
        User one = userMapper.selectOne(qw);
        if(one == null){
            throw new CustomException(ResultEnum.USER_NOT_EXISTS);
        }
        if(one.getIsLock() == 1){
            throw new CustomException(ResultEnum.USER_ALREADY_LOCK);
        }
        if(!one.getPassword().equals(DigestUtils.md5Hex(user.getPassword()))){
            throw new CustomException(ResultEnum.USER_PASSWORD_ERROR);
        }
        String token = JWTUtil.createToken(one, 30*60*24);
        user.setPassword(null);
        user.setId(one.getId());
        user.setRealname(one.getRealname());
        user.setRoleId(one.getRoleId());
        user.setRoleName(one.getRoleName());
        user.setAvatar(one.getAvatar());
        user.setToken(token);
        return Result.success(user);
    }

    @Override
    public Result<User> register(User user) {
        // 用户提交过来的user密码是明文，在业务层对其进行加密后入库
        String md5Hex = DigestUtils.md5Hex(user.getPassword());
        user.setPassword(md5Hex);
        userMapper.insert(user);
        return Result.success();
    }

    @Override
    public Result logout() {
        return Result.success();
    }
}
