package com.yao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.yao.entity.DamageList;

/**
 * 商品报损单Repository接口
 * @author Administrator
 *
 */
public interface DamageListRepository extends JpaRepository<DamageList, Integer>,JpaSpecificationExecutor<DamageList>{

	/**
	 * 获取当天最大商品报损单号
	 * @return
	 */
	@Query(value="SELECT MAX(damage_number) FROM t_damage_list WHERE TO_DAYS(damage_date)=TO_DAYS(NOW())",nativeQuery=true)
	public String getTodayMaxDamageNumber();
}
