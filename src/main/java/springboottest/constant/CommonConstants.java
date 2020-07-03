/*
 *
 *      Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the pig4cloud.com developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: lengleng (wangiegie@gmail.com)
 *
 */

package springboottest.constant;

/**
 * @author lengleng
 * @date 2017/10/29
 */
public interface CommonConstants {

	/**
	 * 是否分页0-不分，1-分
	 */
	Integer NO_PAGING=0;
	/**
	 *分页
	 */
	Integer YES_PAGE=1;
	/**
	 * 起始页
	 */
	Integer CURRENT_PAGE=1;

	/**
	 * 默认分页显示
	 */
	Integer PAGE_SIZE=10;
	/**
	 * header 中租户ID
	 */
	String TENANT_ID = "TENANTID";
	/**
	 * header 中用户名
	 */
	String USER_NAME = "USERNAME";

	/**
	 * header 租户CODE
	 */
	String TENANT_CODE = "TENANTCODE";

	/**
	 * header 会员主键
	 */
	String MEMBER_ID = "MEMBERID";


	/**
	 * header 中版本信息
	 */
	String VERSION = "VERSION";

	/**
	 * 租户ID
	 */
	Long TENANT_ID_1 = 1L;

	/**
	 * 删除
	 */
	String STATUS_DEL = "1";
	/**
	 * 正常
	 */
	String STATUS_NORMAL = "0";


	/**
	 * 状态 0-冻结，1-正常
	 */
	Integer STATUS = 1;

	/**
	 * 锁定
	 */
	String STATUS_LOCK = "9";

	/**
	 * 菜单
	 */
	String MENU = "0";

	/**
	 * 菜单树根节点
	 */
	Integer MENU_TREE_ROOT_ID = -1;

	/**
	 * 编码
	 */
	String UTF8 = "UTF-8";

	/**
	 * 前端工程名
	 */
	String FRONT_END_PROJECT = "cscloud-ui";

	/**
	 * 后端工程名
	 */
	String BACK_END_PROJECT = "cscloud";

	/**
	 * 加密前缀
	 */
	String ENCRYPTION_PREFIX = "{bcrypt}";

	/**
	 * 公共参数
	 */
	String PIG_PUBLIC_PARAM_KEY = "PIG_PUBLIC_PARAM_KEY";

	/**
	 * 成功标记
	 */
	Integer SUCCESS = 0;
	/**
	 * 失败标记
	 */
	Integer FAIL = 1;

	/**
	 * 默认存储bucket
	 */
	String BUCKET_NAME = "ciics";

	Integer DS_SCOPE = 2;

	Integer DS_Type = 0;

	/**
	 * 分割字符串
	 */
	String SPLIT_STR="###";

	String OPERATE_FAILED = "操作失败";
}
