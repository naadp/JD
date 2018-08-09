package com.yao.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

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
import com.yao.entity.ReturnList;
import com.yao.entity.ReturnListGoods;
import com.yao.service.LogService;
import com.yao.service.ReturnListGoodsService;
import com.yao.service.ReturnListService;
import com.yao.service.UserService;
import com.yao.util.DateUtil;
import com.yao.util.StringUtil;

/**
 * 后台管理退货单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/returnList")
public class ReturnListAdminController {

	@Resource
	private ReturnListService returnListService;
	
	@Resource
	private ReturnListGoodsService returnListGoodsService;
	
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
	 * 获取退货单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value="退货出库")
	public String genCode()throws Exception{

		new Cookie().

		StringBuffer code=new StringBuffer("TH");
		code.append(DateUtil.getCurrentDateStr());
		String returnNumber=returnListService.getTodayMaxReturnNumber();
		if(returnNumber!=null){
			code.append(StringUtil.formatCode(returnNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加退货单 以及所有退货单商品
	 * @param returnList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="退货出库")
	public Map<String,Object> save(ReturnList returnList,String goodsJson)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		returnList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal())); // 设置操作用户
		Gson gson=new Gson();
		List<ReturnListGoods> rlgList=gson.fromJson(goodsJson,new TypeToken<List<ReturnListGoods>>(){}.getType());
		returnListService.save(returnList, rlgList);
		logService.save(new Log(Log.ADD_ACTION,"添加退货单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据条件查询所有退货单信息
	 * @param returnList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value={"退货单据查询","供应商统计"},logical=Logical.OR)
	public Map<String,Object> list(ReturnList returnList)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<ReturnList> returnListList=returnListService.list(returnList, Direction.DESC, "returnDate");
		resultMap.put("rows", returnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"退货单查询"));
		return resultMap;
	}
	
	/**
	 * 根据条件获取商品采购信息
	 * @param returnList
	 * @param returnListGoods
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listCount")
	@RequiresPermissions(value="商品采购统计")
	public Map<String,Object> listCount(ReturnList returnList,ReturnListGoods returnListGoods)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<ReturnList> returnListList=returnListService.list(returnList, Direction.DESC, "returnDate");
		for(ReturnList rl:returnListList){
			returnListGoods.setReturnList(rl);
			List<ReturnListGoods> rlgList=returnListGoodsService.list(returnListGoods);
			for(ReturnListGoods rlg:rlgList){
				//rlg.setReturnList(null);
			}
			rl.setReturnListGoodsList(rlgList);
		}
		resultMap.put("rows", returnListList);
		logService.save(new Log(Log.SEARCH_ACTION,"商品采购统计查询"));
		return resultMap;
	}
	
	/**
	 * 根据退货单id查询所有退货单商品
	 * @param returnListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="退货单据查询")
	public Map<String,Object> listGoods(Integer returnListId)throws Exception{
		if(returnListId==null){
			return null;
		}
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("rows", returnListGoodsService.listByReturnListId(returnListId));
		logService.save(new Log(Log.SEARCH_ACTION,"退货单商品查询"));
		return resultMap;
	}
	
	/**
	 * 删除退货单 以及退货单里的商品
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/delete")
	@RequiresPermissions(value="退货单据查询")
	public Map<String,Object> delete(Integer id)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		returnListService.delete(id);
		logService.save(new Log(Log.DELETE_ACTION,"删除退货单信息："+returnListService.findById(id)));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 修改退货单的支付状态
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/update")
	@RequiresPermissions(value="供应商统计")
	public Map<String,Object> update(Integer id)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		ReturnList returnList=returnListService.findById(id);
		returnList.setState(1);
		returnListService.update(returnList);
		resultMap.put("success", true);
		return resultMap;
	}
}
