package com.yao.service;

import java.util.List;


import com.yao.entity.GoodsType;

/**
 * 商品类别Service接口
 * @author Administrator
 *
 */
public interface GoodsTypeService {

	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	public List<GoodsType> findByParentId(int parentId);
	
	/**
	 * 添加或者修改商品类别信息
	 * @param user
	 */
	public void save(GoodsType goodsType);
	
	/**
	 * 根据id删除商品类别
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public GoodsType findById(Integer id);
}
