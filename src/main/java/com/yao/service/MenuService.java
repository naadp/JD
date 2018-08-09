package com.yao.service;

import java.util.List;

import com.yao.entity.Menu;

/**
 * 权限菜单Service接口
 * @author Administrator
 *
 */
public interface MenuService {
	
	/**
	 * 根据id获取实体
	 * @param id
	 * @return
	 */
	public Menu findById(Integer id);

	/**
	 * 根据父节点以及用户角色id查询子节点
	 * @param id
	 * @return
	 */
	public List<Menu> findByParentIdAndRoleId(int parentId, int roleId);
	
	/**
	 * 根据角色id获取菜单集合
	 * @param roleId
	 * @return
	 */
	public List<Menu> findByRoleId(int roleId);
	
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<Menu> findByParentId(int parentId);
}
