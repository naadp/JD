package com.yao.service;

import java.util.List;

import org.springframework.data.domain.Sort.Direction;

import com.yao.entity.OverflowList;
import com.yao.entity.OverflowListGoods;

/**
 * 商品报溢单Service接口
 * @author Administrator
 *
 */
public interface OverflowListService {

	/**
	 * 根据id查询实体
	 * @param id
	 * @return
	 */
	public OverflowList findById(Integer id);
	
	/**
	 * 获取当天最大商品报溢单号
	 * @return
	 */
	public String getTodayMaxOverflowNumber();
	
	/**
	 * 添加商品报溢单 以及所有商品报溢单商品  以及 修改商品成本价 库存数量 上次进价
	 * @param overflowList
	 * @param overflowListGoodsList
	 */
	public void save(OverflowList overflowList, List<OverflowListGoods> overflowListGoodsList);
	
	/**
	 * 根据条件查询商品报溢单信息
	 * @param overflowList
	 * @param direction
	 * @param properties
	 * @return
	 */
	public List<OverflowList> list(OverflowList overflowList, Direction direction, String... properties);
	
}
