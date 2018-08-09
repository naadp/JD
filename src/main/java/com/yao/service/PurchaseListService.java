package com.yao.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yao.entity.PurchaseList;
import com.yao.entity.PurchaseListGoods;

/**
 * 进货单Service接口
 * @author Administrator
 *
 */
public interface PurchaseListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public PurchaseList findById(Integer id);
	
	/**
	 * 获取当天最大进货单号
	 * @return
	 */
	public String getTodayMaxPurchaseNumber();
	
	/**
	 * 添加进货单 以及所有进货单商品  以及 修改商品成本价 库存数量 上次进价
	 * @param purchaseList
	 * @param purchaseListGoodsList
	 */
	public void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList);
	
	/**
	 * 根据条件查询进货单信息
	 * @param purchaseList
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<PurchaseList> list(PurchaseList purchaseList, Direction direction, String... properties);
	
	/**
	 * 根据id删除进货单信息 包括进货单里的所有商品
	 * @param id
	 */
	public void delete(Integer id);
	
	/**
	 * 更新进货单
	 * @param purchaseList
	 */
	public void update(PurchaseList purchaseList);
}
