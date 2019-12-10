package com.wzw.shirospringboot.service;

import com.wzw.shirospringboot.dao.UserMapper;
import com.wzw.shirospringboot.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User getByUserName(String userName) {
        return userMapper.getByUserName(userName);
    }

    @Override
    public Set<String> getRoles(String userName) throws Exception {
        return userMapper.getRoles(userName);
    }

    @Override
    public Set<String> getPermissions(String userName) throws Exception {
        return userMapper.getPermissions(userName);
    }
}
