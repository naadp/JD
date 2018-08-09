package com.yao.service;

import com.yao.entity.RoleMenu;

/**
 * 角色菜单关联service接口
 * @author Administrator
 *
 */
public interface RoleMenuService {
	
	/**
	 * 根据角色id删除所有关联信息
	 * @param userId
	 */
	public void deleteByRoleId(Integer roleId);
	
	/**
	 * 保存实体
	 * @param roleMenu
	 */
	public void save(RoleMenu roleMenu);
}
