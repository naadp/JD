package com.yao.service.impl;


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yao.entity.GoodsUnit;
import com.yao.repository.GoodsUnitRepository;
import com.yao.service.GoodsUnitService;

/**
 * 商品单位Service实现类
 * @author Administrator
 *
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl implements GoodsUnitService{

	@Resource
	private GoodsUnitRepository goodsUnitRepository;


	@Override
	public void save(GoodsUnit goodsUnit) {
		goodsUnitRepository.save(goodsUnit);
	}

	@Override
	public void delete(Integer id) {
		goodsUnitRepository.delete(id);
	}

	@Override
	public GoodsUnit findById(Integer id) {
		return goodsUnitRepository.findOne(id);
	}

	@Override
	public List<GoodsUnit> listAll() {
		return goodsUnitRepository.findAll();
	}




}
