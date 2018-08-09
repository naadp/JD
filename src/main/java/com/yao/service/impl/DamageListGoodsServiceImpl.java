package com.yao.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yao.entity.DamageListGoods;
import com.yao.repository.DamageListGoodsRepository;
import com.yao.service.DamageListGoodsService;

/**
 * 商品报损单商品Service实现类
 * @author Administrator
 *
 */
@Service("damageListGoodsService")
public class DamageListGoodsServiceImpl implements DamageListGoodsService{

	@Resource
	private DamageListGoodsRepository damageListGoodsRepository;

	@Override
	public List<DamageListGoods> listByDamageListId(Integer damageListId) {
		return damageListGoodsRepository.listByDamageListId(damageListId);
	}

	
	
}
