package com.yao.controller.admin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
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
import com.yao.entity.DamageList;
import com.yao.entity.DamageListGoods;
import com.yao.service.LogService;
import com.yao.service.DamageListGoodsService;
import com.yao.service.DamageListService;
import com.yao.service.UserService;
import com.yao.util.DateUtil;
import com.yao.util.StringUtil;

/**
 * 后台管理报损单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/damageList")
public class DamageListAdminController {

	@Resource
	private DamageListService damageListService;
	
	@Resource
	private DamageListGoodsService damageListGoodsService;
	
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
	 * 获取报损单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value="商品报损")
	public String genCode()throws Exception{
		StringBuffer code=new StringBuffer("BS");
		code.append(DateUtil.getCurrentDateStr());
		String damageNumber=damageListService.getTodayMaxDamageNumber();
		if(damageNumber!=null){
			code.append(StringUtil.formatCode(damageNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加报损单 以及所有报损单商品
	 * @param damageList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="商品报损")
	public Map<String,Object> save(DamageList damageList,String goodsJson)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		damageList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal())); // 设置操作用户
		Gson gson=new Gson();
		List<DamageListGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<DamageListGoods>>(){}.getType());
		damageListService.save(damageList, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加报损单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据条件查询所有报损单信息
	 * @param damageList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String,Object> list(DamageList damageList)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<DamageList> damageListList=damageListService.list(damageList, Direction.DESC, "damageDate");
		resultMap.put("rows", damageListList);
		logService.save(new Log(Log.SEARCH_ACTION,"报损单查询"));
		return resultMap;
	}
	
	/**
	 * 根据报损单id查询所有报损单商品
	 * @param damageListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String,Object> listGoods(Integer damageListId)throws Exception{
		if(damageListId==null){
			return null;
		}
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("rows", damageListGoodsService.listByDamageListId(damageListId));
		logService.save(new Log(Log.SEARCH_ACTION,"报损单商品查询"));
		return resultMap;
	}
	
	
}
