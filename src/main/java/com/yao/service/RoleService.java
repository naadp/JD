package com.yao.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yao.entity.Role;

/**
 * 角色Service接口
 * @author Administrator
 *
 */
public interface RoleService {

	/**
	 * 根据用户id查角色集合
	 * @param id
	 * @return
	 */
	public List<Role> findByUserId(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public Role findById(Integer id);
	
	/**
	 * 查询所有角色信息
	 * @return
	 */
	public List<Role> listAll();
	
	/**
	 * 根据条件分页查询角色信息
	 * @param role
	 * @param page
	 * @param pageSize
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<Role> list(Role role, Integer page, Integer pageSize, Direction direction, String... properties);
	
	/**
	 * 获取总记录数
	 * @param role
	 * @return
	 */
	public Long getCount(Role role);
	
	/**
	 * 添加或者修改角色信息
	 * @param role
	 */
	public void save(Role role);
	
	/**
	 * 根据id删除角色
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据角色名查找角色实体
	 * @param roleName
	 * @return
	 */
	public Role findByRoleName(String roleName);
}
