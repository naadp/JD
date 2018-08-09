package com.yao.service;

import java.util.List;

import com.yao.entity.CustomerReturnListGoods;

/**
 * 客户退货单商品Service接口
 * @author Administrator
 *
 */
public interface CustomerReturnListGoodsService {

	/**
	 * 根据客户退货单id查询所有客户退货单商品
	 * @param customerReturnListId
	 * @return
	 */
	public List<CustomerReturnListGoods> listByCustomerReturnListId(Integer customerReturnListId);
	
	/**
	 * 统计某个商品的退货总数
	 * @param goodsId
	 * @return
	 */
	public Integer getTotalByGoodsId(Integer goodsId);
	
	/**
	 * 根据条件查询客户退货单商品
	 * @param customerReturnListGoods
	 * @return
	 */
	public List<CustomerReturnListGoods> list(CustomerReturnListGoods customerReturnListGoods);
	
}
