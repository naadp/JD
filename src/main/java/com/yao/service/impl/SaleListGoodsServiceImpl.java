package com.yao.service.impl;


import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yao.entity.SaleListGoods;
import com.yao.repository.SaleListGoodsRepository;
import com.yao.service.SaleListGoodsService;
import com.yao.util.StringUtil;

/**
 * 销售单商品Service实现类
 * @author Administrator
 *
 */
@Service("saleListGoodsService")
public class SaleListGoodsServiceImpl implements SaleListGoodsService{

	@Resource
	private SaleListGoodsRepository saleListGoodsRepository;

	@Override
	public List<SaleListGoods> listBySaleListId(Integer saleListId) {
		return saleListGoodsRepository.listBySaleListId(saleListId);
	}

	@Override
	public Integer getTotalByGoodsId(Integer goodsId) {
		return saleListGoodsRepository.getTotalByGoodsId(goodsId)==null?0:saleListGoodsRepository.getTotalByGoodsId(goodsId);
	}

	@Override
	public List<SaleListGoods> list(SaleListGoods saleListGoods) {
		return saleListGoodsRepository.findAll(new Specification<SaleListGoods>() {
			
			@Override
			public Predicate toPredicate(Root<SaleListGoods> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(saleListGoods!=null){
					if(saleListGoods.getType()!=null && saleListGoods.getType().getId()!=null && saleListGoods.getType().getId()!=1){
						predicate.getExpressions().add(cb.equal(root.get("type").get("id"), saleListGoods.getType().getId()));
					}
					if(StringUtil.isNotEmpty(saleListGoods.getCodeOrName())){
						predicate.getExpressions().add(cb.or(cb.like(root.get("code"), "%"+saleListGoods.getCodeOrName()+"%"), cb.like(root.get("name"), "%"+saleListGoods.getCodeOrName()+"%")));
					}
				}
				return predicate;
			}
		});
	}

	
	
}
