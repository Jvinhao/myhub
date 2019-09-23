package org.lf.community.service;

import org.lf.community.mapper.UserMapper;
import org.lf.community.model.User;
import org.lf.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertSelective(user);
        }else {
            User userDb = users.get(0);
            User userUpdate = new User();
            userUpdate.setGmtModified(System.currentTimeMillis());
            userUpdate.setAvatarUrl(user.getAvatarUrl());
            userUpdate.setName(user.getName());
            userUpdate.setToken(user.getToken());
            userUpdate.setBio(user.getBio());
            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(userDb.getId());
            userMapper.updateByExampleSelective(userUpdate,example);

        }
    }
}
