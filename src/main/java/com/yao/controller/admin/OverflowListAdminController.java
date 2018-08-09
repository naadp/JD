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
import com.yao.entity.OverflowList;
import com.yao.entity.OverflowListGoods;
import com.yao.service.LogService;
import com.yao.service.OverflowListGoodsService;
import com.yao.service.OverflowListService;
import com.yao.service.UserService;
import com.yao.util.DateUtil;
import com.yao.util.StringUtil;

/**
 * 后台管理报溢单Controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/admin/overflowList")
public class OverflowListAdminController {

	@Resource
	private OverflowListService overflowListService;
	
	@Resource
	private OverflowListGoodsService overflowListGoodsService;
	
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
	 * 获取报溢单号
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/genCode")
	@RequiresPermissions(value="商品报溢")
	public String genCode()throws Exception{
		StringBuffer code=new StringBuffer("BY");
		code.append(DateUtil.getCurrentDateStr());
		String overflowNumber=overflowListService.getTodayMaxOverflowNumber();
		if(overflowNumber!=null){
			code.append(StringUtil.formatCode(overflowNumber));
		}else{
			code.append("0001");
		}
		return code.toString();
	}
	
	/**
	 * 添加报溢单 以及所有报溢单商品
	 * @param overflowList
	 * @param goodsJson
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	@RequiresPermissions(value="商品报溢")
	public Map<String,Object> save(OverflowList overflowList,String goodsJson)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		overflowList.setUser(userService.findByUserName((String) SecurityUtils.getSubject().getPrincipal())); // 设置操作用户
		Gson gson=new Gson();
		List<OverflowListGoods> plgList=gson.fromJson(goodsJson,new TypeToken<List<OverflowListGoods>>(){}.getType());
		overflowListService.save(overflowList, plgList);
		logService.save(new Log(Log.ADD_ACTION,"添加报溢单"));
		resultMap.put("success", true);
		return resultMap;
	}
	
	/**
	 * 根据条件查询所有报溢单信息
	 * @param overflowList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String,Object> list(OverflowList overflowList)throws Exception{
		Map<String,Object> resultMap=new HashMap<>();
		List<OverflowList> overflowListList=overflowListService.list(overflowList, Direction.DESC, "overflowDate");
		resultMap.put("rows", overflowListList);
		logService.save(new Log(Log.SEARCH_ACTION,"报溢单查询"));
		return resultMap;
	}
	
	/**
	 * 根据报溢单id查询所有报溢单商品
	 * @param overflowListId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listGoods")
	@RequiresPermissions(value="报损报溢查询")
	public Map<String,Object> listGoods(Integer overflowListId)throws Exception{
		if(overflowListId==null){
			return null;
		}
		Map<String,Object> resultMap=new HashMap<>();
		resultMap.put("rows", overflowListGoodsService.listByOverflowListId(overflowListId));
		logService.save(new Log(Log.SEARCH_ACTION,"报溢单商品查询"));
		return resultMap;
	}
	
	
}
