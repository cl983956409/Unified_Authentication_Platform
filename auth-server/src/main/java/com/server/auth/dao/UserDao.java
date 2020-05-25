package com.server.auth.dao;

import com.server.auth.bean.UserInfo;

import java.sql.SQLException;

/**
 * @author chenglonghy
 * @date 2020/4/22
 * <p>
 * 功能说明:
 */
public interface UserDao {

    /**
     * 根据用户名查询用户信息
     *
     * @param name
     * @return
     * @throws SQLException
     */
    UserInfo selectUserInfo(String name) throws SQLException;

}
