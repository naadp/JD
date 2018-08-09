package com.yao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.DamageListGoods;

/**
 * 商品报损单商品Repository接口
 * @author Administrator
 *
 */
public interface DamageListGoodsRepository extends JpaRepository<DamageListGoods, Integer>,JpaSpecificationExecutor<DamageListGoods>{

	/**
	 * 根据商品报损单id查询所有商品报损单商品
	 * @param damageListId
	 * @return
	 */
	@Query(value="SELECT * FROM t_damage_list_goods WHERE damage_list_id=?1",nativeQuery=true)
	public List<DamageListGoods> listByDamageListId(Integer damageListId);
	
	/**
	 * 根据商品报损单id删除所有商品报损单商品
	 * @param damageListId
	 * @return
	 */
	@Query(value="delete FROM t_damage_list_goods WHERE damage_list_id=?1",nativeQuery=true)
	@Modifying
	public void deleteByDamageListId(Integer damageListId);
}
