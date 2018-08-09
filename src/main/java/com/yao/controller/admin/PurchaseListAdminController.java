package com.yao.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yao.entity.Log;
import com.yao.entity.PurchaseList;
import com.yao.entity.PurchaseListGoods;
import com.yao.service.LogService;
import com.yao.service.PurchaseListGoodsService;
import com.yao.service.PurchaseListService;
import com.yao.service.UserService;
import com.yao.util.DateUtil;
import com.yao.util.StringUtil;

/**
 * 后台管理进货单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/purchaseList")
public class PurchaseListAdminController {

	@Resource
	private PurchaseListService purchaseListService;
	
	@Resource
	private PurchaseListGoodsService purchaseListGoodsService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private LogService logService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(true);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));   //true:允许输入空值，false:不能为空值
	}
	
	/**
	 * 获取进货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value="进货入库")
	public String genCode()throws Exception{
		StringBuffer code=new StringBuffer("JH");
		code.append(DateUtil.getCurrentDateStr());
		String purchaseNumber=purchaseListService.getTodayMaxPurchaseNumber();
		if(purchaseNumber!=null){
			code.append(StringUtil.formatCode(purchaseNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加进货单 以及所有进货单商品
	 * @param purchaseList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="进货入库")
	public Map<String,Object> save(PurchaseList purchaseList,String goodsJson)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		purchaseList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal())); // 设置操作用户
		Gson gson=new Gson();
		List<PurchaseListGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<PurchaseListGoods>>(){}.getType());
		purchaseListService.save(purchaseList, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加进货单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据条件查询所有进货单信息
	 * @param purchaseList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value={"进货单据查询","供应商统计"},logical=Logical.OR)
	public Map<String,Object> list(PurchaseList purchaseList)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<PurchaseList> purchaseListList=purchaseListService.list(purchaseList, Direction.DESC, "purchaseDate");
		resultMap.put("rows", purchaseListList);
		logService.save(new Log(Log.SEARCH_ACTION,"进货单查询"));
		return resultMap;
	}
	
	/**
	 * 根据条件获取商品采购信息
	 * @param purchaseList
	 * @param purchaseListGoods
	 * @return
	 * @throws Exception
	 */
	//这里的代码逻辑有问题，因为这里返回的每个购货单下的商品肯定是一样的。
	@RequestMapping("/listCount")
	@RequiresPermissions(value="商品采购统计")
	public Map<String,Object> listCount(PurchaseList purchaseList,PurchaseListGoods purchaseListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		//根据 开始时间 和 结束时间 条件查询购货单
		List<PurchaseList> purchaseListList=purchaseListService.list(purchaseList, Direction.DESC, "purchaseDate");
		//遍历购货单
		for(PurchaseList pl:purchaseListList){
			//对商品设置购货单
			purchaseListGoods.setPurchaseList(pl);
			//根据商品的类别、编码、name 来查询商品，得到商品集合。
			List<PurchaseListGoods> plgList=purchaseListGoodsService.list(purchaseListGoods);
			//为了防止死循环，而做的一个操作。因为在返回的时候，会造成一个死循环，循环引用的问题。
			//但是在这么写了之后,会自动的给你发出一条SQL语句, 让你数据库的那个真的变为了null.
			//可以在实体类上加 JsonIgnore注解.
			for(PurchaseListGoods plg:plgList){
				//plg.setPurchaseList(null);
			}
			pl.setPurchaseListGoodsList(plgList);
		}
		resultMap.put("rows", purchaseListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品采购统计查询"));
		return resultMap;
	}
	
	
	/**
	 * 根据进货单id查询所有进货单商品
	 * @param purchaseListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="进货单据查询")
	public Map<String,Object> listGoods(Integer purchaseListId)throws Exception{
		if(purchaseListId==null){
			return null;
		}
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("rows", purchaseListGoodsService.listByPurchaseListId(purchaseListId));
		logService.save(new Log(Log.SEARCH_ACTION,"进货单商品查询"));
		return resultMap;
	}
	
	/**
	 * 删除进货单 以及进货单里的商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="进货单据查询")
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		purchaseListService.delete(id);
		logService.save(new Log(Log.DELETE_ACTION,"删除进货单信息："+purchaseListService.findById(id)));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 修改进货单的支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value="供应商统计")
	public Map<String,Object> update(Integer id)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		PurchaseList purchaseList=purchaseListService.findById(id);
		purchaseList.setState(1);
		purchaseListService.update(purchaseList);
		resultMap.put("success", true);
		return resultMap;
	}
}
