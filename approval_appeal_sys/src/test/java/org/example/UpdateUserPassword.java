package org.example;

import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class UpdateUserPassword {
    @Resource
    private UserMapper userMapper;
    @Test
    public void updateUserPassword() {
        List<User> userList = userMapper.selectList(null);
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.get(i);
            if (user.getPassword().length() == 32) {
                continue;
            }
            User one = new User();
            one.setId(user.getId());
            one.setPassword(DigestUtils.md5Hex(user.getPassword()));
            userMapper.updateById(one);
        }
    }
}
