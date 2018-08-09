package com.yao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.OverflowListGoods;

/**
 * 商品报溢单商品Repository接口
 * @author Administrator
 *
 */
public interface OverflowListGoodsRepository extends JpaRepository<OverflowListGoods, Integer>,JpaSpecificationExecutor<OverflowListGoods>{

	/**
	 * 根据商品报溢单id查询所有商品报溢单商品
	 * @param overflowListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_overflow_list_goods WHERE overflow_list_id=?1",nativeQuery=true)
	public List<OverflowListGoods> listByOverflowListId(Integer overflowListId);
	
	/**
	 * 根据商品报溢单id删除所有商品报溢单商品
	 * @param overflowListId
	 * @return
	 */
	@Query(value="delete FROM t_overflow_list_goods WHERE overflow_list_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByOverflowListId(Integer overflowListId);
}
