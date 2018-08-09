package com.yao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.PurchaseListGoods;

/**
 * 进货单商品Repository接口

 *
 */
public interface PurchaseListGoodsRepository extends JpaRepository<PurchaseListGoods, Integer>,JpaSpecificationExecutor<PurchaseListGoods>{

	/**
	 * 根据进货单id查询所有进货单商品
	 * @param purchaseListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_purchase_list_goods WHERE purchase_list_id=?1",nativeQuery=true)
	public List<PurchaseListGoods> listByPurchaseListId(Integer purchaseListId);
	
	/**
	 * 根据进货单id删除所有进货单商品
	 * @param purchaseListId
	 * @return
	 */
	@Query(value="delete FROM t_purchase_list_goods WHERE purchase_list_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByPurchaseListId(Integer purchaseListId);
}
