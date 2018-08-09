package com.yao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.GoodsType;

/**
 * 商品类别Repository接口

 *
 */
public interface GoodsTypeRepository extends JpaRepository<GoodsType, Integer>{

	
	/**
	 * 根据父节点查找所有子节点
	 * @param parentId
	 * @return
	 */
	@Query(value="select * from t_goodstype where p_id=?1",nativeQuery=true)
	public List<GoodsType> findByParentId(int parentId);
}
