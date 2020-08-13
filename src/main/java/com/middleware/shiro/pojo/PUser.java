package com.middleware.shiro.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PUser {
    private String id;
    private String username;
    private String password;

    private PRole role;
    private List<PPermission> permissionList;
}
