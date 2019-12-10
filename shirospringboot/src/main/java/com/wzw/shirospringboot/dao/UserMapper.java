package com.wzw.shirospringboot.dao;

import com.wzw.shirospringboot.entity.User;

import java.util.Set;

public interface UserMapper {
    /**
     * 	根据用户名查询用户信息
     * @param userName
     * @return
     */
    User getByUserName(String userName);

    /**
     * 根据用户名获取该用户拥有的角色
     * @param userName
     * @return
     * @throws Exception
     */
    Set<String> getRoles(String userName) throws Exception;

    /**
     * 根据用户名获取该用户拥有的权限
     * @param userName
     * @return
     * @throws Exception
     */
    Set<String> getPermissions(String userName) throws Exception;
}
