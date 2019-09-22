package org.lf.community.community.service;

import org.lf.community.community.mapper.UserMapper;
import org.lf.community.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(User user) {
        User userDb = userMapper.findByAccountId(user.getAccountId());
        if(userDb == null) {
            user.setGmtModified(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }else {
            userDb.setGmtModified(System.currentTimeMillis());
            userDb.setAvatarUrl(user.getAvatarUrl());
            userDb.setName(user.getName());
            userDb.setToken(user.getToken());
            userDb.setBio(user.getBio());
            userMapper.update(userDb);
        }
    }
}
