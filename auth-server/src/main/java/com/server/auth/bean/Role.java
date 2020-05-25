package com.server.auth.bean;

import lombok.Data;

/**
 * @author 【author：chenglonghy , QQ：983956409】
 * @date 2020/5/7 - 10:53
 * @history 2020/5/7 - 10:53 chenglonghy  create.
 */
@Data
public class Role {
    private int id;
    private int userId;
    private String role;
}
