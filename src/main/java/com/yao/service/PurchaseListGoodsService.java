package com.yao.service;

import java.util.List;

import com.yao.entity.PurchaseListGoods;

/**
 * 进货单商品Service接口
 * @author Administrator
 *
 */
public interface PurchaseListGoodsService {

	/**
	 * 根据进货单id查询所有进货单商品
	 * @param purchaseListId
	 * @return
	 */
	public List<PurchaseListGoods> listByPurchaseListId(Integer purchaseListId);
	
	/**
	 * 根据条件查询进货单商品
	 * @param purchaseListGoods
	 * @return
	 */
	public List<PurchaseListGoods> list(PurchaseListGoods purchaseListGoods);
	
}
