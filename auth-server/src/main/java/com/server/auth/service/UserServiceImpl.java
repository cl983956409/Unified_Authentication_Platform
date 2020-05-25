package com.server.auth.service;

import com.server.auth.bean.Role;
import com.server.auth.bean.UserInfo;
import com.server.auth.dao.UserDao;
import com.server.auth.dao.UserRoleDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 【author：chenglonghy , QQ：983956409】
 * @date 2020/5/7 - 11:14
 * @history 2020/5/7 - 11:14 chenglonghy  create.
 */
@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService {

    @Resource
    UserDao userDao;
    @Resource
    UserRoleDao userRoleDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfo user = null;
        try {
            user = userDao.selectUserInfo(username);
            if (user == null) {
                throw new UsernameNotFoundException("not found");
            }
            List<Role> roles = userRoleDao.selectUserRoles(user.getId());
            user.setRoles(roles);
        } catch (SQLException e) {
            throw new UsernameNotFoundException("数据查询异常");
        }
        return user;
    }

}
