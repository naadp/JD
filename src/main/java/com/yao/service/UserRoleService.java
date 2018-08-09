package com.yao.service;

import com.yao.entity.UserRole;

/**
 * 用户角色关联service接口
 * @author Administrator
 *
 */
public interface UserRoleService {

	/**
	 * 根据用户id删除所有关联信息
	 * @param userId
	 */
	public void deleteByUserId(Integer userId);
	
	/**
	 * 添加或者修改用户角色关联
	 * @param userRole
	 */
	public void save(UserRole userRole);
	
	/**
	 * 根据角色id删除所有关联信息
	 * @param userId
	 */
	public void deleteByRoleId(Integer roleId);
}
