/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package springboottest.pojo.es;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 管理员表
 *
 * @author dzh
 * @date Wed Apr 29 17:25:12 CST 2020
 */
@Data
@TableName("shop_admin")
@Document(indexName = "data1", type = "shopadmintable")
public class ShopAdmin {
    private static final long serialVersionUID = 1L;

    /**
     * 管理员ID
     */
    @TableId
    @Id
    private String adminId;
    /**
     * 管理员名称
     */
    private String adminName;
    /**
     *
     */
    private String adminPassword;
    /**
     * 登录时间
     */
    private Long adminLoginTime;
    /**
     * 登录次数
     */
    private Integer adminLoginNum;
    /**
     * 是否超级管理员
     */
    private Integer adminIsSuper;
    /**
     * 权限组ID
     */
    private String adminGid;
    /**
     * 删除状态
     */
    private Integer isDel;
    /**
     * 角色id
     */
    private String roleId;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 员工名称
     */
    private String empName;
    /**
     * 账号创建时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime createTime;
    /**
     * 账号创建管理员id
     */
    private String createAdminId;
    /**
     * 账号状态{0:不可用  ,  1:可用}
     */
    private Integer status;
    /**
     * 账号更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-ddTHH:mm:ss.SSS")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime updateTime;
    /**
     * 账号更新管理员id
     */
    private String updateAdminId;
}
