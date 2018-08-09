package com.yao.service;

import java.util.List;

import com.yao.entity.OverflowListGoods;

/**
 * 商品报溢单商品Service接口
 * @author Administrator
 *
 */
public interface OverflowListGoodsService {

	/**
	 * 根据商品报溢单id查询所有商品报溢单商品
	 * @param overflowListId
	 * @return
	 */
	public List<OverflowListGoods> listByOverflowListId(Integer overflowListId);
	
}
