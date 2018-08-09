package com.yao.service;

import java.util.List;

import com.yao.entity.SaleListGoods;

/**
 * 销售单商品Service接口
 * @author Administrator
 *
 */
public interface SaleListGoodsService {

	/**
	 * 根据销售单id查询所有销售单商品
	 * @param saleListId
	 * @return
	 */
	public List<SaleListGoods> listBySaleListId(Integer saleListId);
	
	/**
	 * 统计某个商品的销售总数
	 * @param goodsId
	 * @return
	 */
	public Integer getTotalByGoodsId(Integer goodsId);
	
	/**
	 * 根据条件查询销售单商品
	 * @param saleListGoods
	 * @return
	 */
	public List<SaleListGoods> list(SaleListGoods saleListGoods);
	
}
