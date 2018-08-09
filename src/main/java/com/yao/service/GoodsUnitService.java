package com.yao.service;

import java.util.List;


import com.yao.entity.GoodsUnit;

/**
 * 商品单位Service接口
 * @author Administrator
 *
 */
public interface GoodsUnitService {

	/**
	 * 查询所有商品单位信息
	 * @return
	 */
	public List<GoodsUnit> listAll();
	
	/**
	 * 添加或者修改商品单位信息
	 * @param user
	 */
	public void save(GoodsUnit goodsUnit);
	
	/**
	 * 根据id删除商品单位
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public GoodsUnit findById(Integer id);
}
