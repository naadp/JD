package com.yao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.SaleListGoods;

/**
 * 销售单商品Repository接口
 * @author Administrator
 *
 */
public interface SaleListGoodsRepository extends JpaRepository<SaleListGoods, Integer>,JpaSpecificationExecutor<SaleListGoods>{

	/**
	 * 根据销售单id查询所有销售单商品
	 * @param saleListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_sale_list_goods WHERE sale_list_id=?1",nativeQuery=true)
	public List<SaleListGoods> listBySaleListId(Integer saleListId);
	
	/**
	 * 根据销售单id删除所有销售单商品
	 * @param saleListId
	 * @return
	 */
	@Query(value="delete FROM t_sale_list_goods WHERE sale_list_id=?1",nativeQuery=true)
	@Modifying
	public void deleteBySaleListId(Integer saleListId);
	
	/**
	 * 统计某个商品的销售总数
	 * @param goodsId
	 * @return
	 */
	@Query(value="SELECT SUM(num) AS total FROM t_sale_list_goods WHERE goods_id=?1",nativeQuery=true)
	public Integer getTotalByGoodsId(Integer goodsId);
}
