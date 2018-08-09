package com.yao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.Goods;

/**
 * 商品Repository接口
 * @author Administrator
 *
 */
public interface GoodsRepository extends JpaRepository<Goods, Integer>,JpaSpecificationExecutor<Goods>{

	
	/**
	 * 查询某个类别下的所有商品
	 * @param typeId
	 * @return
	 */
	@Query(value="select * from t_goods where type_id=?1",nativeQuery=true)
	public List<Goods> findByTypeId(int typeId);
	
	/**
	 * 获取最大的商品编码
	 * @return
	 */
	@Query(value="SELECT MAX(CODE) FROM t_goods",nativeQuery=true)
	public String getMaxGoodsCode();
	
	/**
	 * 查询库存报警商品，实际库存小于库存下限的商品
	 * @return
	 */
	@Query(value="SELECT * FROM t_goods WHERE inventory_quantity<min_num",nativeQuery=true)
	public List<Goods> listAlarm();
}
