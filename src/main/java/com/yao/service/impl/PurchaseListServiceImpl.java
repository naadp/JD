package com.yao.service.impl;


import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.yao.entity.Goods;
import com.yao.entity.PurchaseList;
import com.yao.entity.PurchaseListGoods;
import com.yao.repository.GoodsRepository;
import com.yao.repository.GoodsTypeRepository;
import com.yao.repository.PurchaseListGoodsRepository;
import com.yao.repository.PurchaseListRepository;
import com.yao.service.PurchaseListService;
import com.yao.util.MathUtil;
import com.yao.util.StringUtil;

/**
 * 进货单Service实现类
 * @author Administrator
 *
 */
@Service("purchaseListService")
public class PurchaseListServiceImpl implements PurchaseListService{

	@Resource
	private PurchaseListRepository purchaseListRepository;
	
	@Resource
	private GoodsTypeRepository goodsTypeRepository;
	
	@Resource
	private GoodsRepository goodsRepository;
	
	@Resource
	private PurchaseListGoodsRepository purchaseListGoodsRepository;

	@Override
	public String getTodayMaxPurchaseNumber() {
		return purchaseListRepository.getTodayMaxPurchaseNumber();
	}

	@Transactional
	public void save(PurchaseList purchaseList, List<PurchaseListGoods> purchaseListGoodsList) {
		for(PurchaseListGoods purchaseListGoods:purchaseListGoodsList){
			purchaseListGoods.setType(goodsTypeRepository.findOne(purchaseListGoods.getTypeId())); // 设置类别
			purchaseListGoods.setPurchaseList(purchaseList); // 设置进货单
			purchaseListGoodsRepository.save(purchaseListGoods);
			// 修改商品库存 成本均价 以及上次进价
			Goods goods=goodsRepository.findOne(purchaseListGoods.getGoodsId());
			
			float svePurchasePrice=(goods.getPurchasingPrice()*goods.getInventoryQuantity()+purchaseListGoods.getPrice()*purchaseListGoods.getNum())/(goods.getInventoryQuantity()+purchaseListGoods.getNum());
			goods.setPurchasingPrice(MathUtil.format2Bit(svePurchasePrice));
			goods.setInventoryQuantity(goods.getInventoryQuantity()+purchaseListGoods.getNum());
			goods.setLastPurchasingPrice(purchaseListGoods.getPrice());
			goods.setState(2);
			goodsRepository.save(goods);
		}
		purchaseListRepository.save(purchaseList); // 保存进货单
	}

	@Override
	public List<PurchaseList> list(PurchaseList purchaseList, Direction direction, String... properties) {
		return purchaseListRepository.findAll(new Specification<PurchaseList>() {
			
			@Override
			public Predicate toPredicate(Root<PurchaseList> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate predicate=cb.conjunction();
				if(purchaseList!=null){
					if(StringUtil.isNotEmpty(purchaseList.getPurchaseNumber())){
						predicate.getExpressions().add(cb.like(root.get("purchaseNumber"), "%"+purchaseList.getPurchaseNumber().trim()+"%"));
					}
					if(purchaseList.getSupplier()!=null && purchaseList.getSupplier().getId()!=null){
						predicate.getExpressions().add(cb.equal(root.get("supplier").get("id"), purchaseList.getSupplier().getId()));
					}
					if(purchaseList.getState()!=null){
						predicate.getExpressions().add(cb.equal(root.get("state"), purchaseList.getState()));
					}
					if(purchaseList.getbPurchaseDate()!=null){
						predicate.getExpressions().add(cb.greaterThanOrEqualTo(root.get("purchaseDate"), purchaseList.getbPurchaseDate()));
					}
					if(purchaseList.getePurchaseDate()!=null){
						predicate.getExpressions().add(cb.lessThanOrEqualTo(root.get("purchaseDate"), purchaseList.getePurchaseDate()));
					}
				}
				return predicate;
			}
		},new Sort(direction, properties));
	}

	@Override
	public PurchaseList findById(Integer id) {
		return purchaseListRepository.findOne(id);
	}

	@Transactional
	public void delete(Integer id) {
		purchaseListGoodsRepository.deleteByPurchaseListId(id);
		purchaseListRepository.delete(id);
	}

	@Override
	public void update(PurchaseList purchaseList) {
		purchaseListRepository.save(purchaseList);
	}

	

}
