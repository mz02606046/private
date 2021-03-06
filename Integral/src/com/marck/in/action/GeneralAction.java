package com.marck.in.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.marck.common.BaseAction;
import com.marck.common.CommonUtil;
import com.marck.common.DateUtil;
import com.marck.common.model.User;
import com.marck.common.model.Apply;
import com.marck.in.service.GeneralService;

@Component("generalAction")
@Scope("prototype")
public class GeneralAction extends BaseAction {

	@Autowired
	private GeneralService generalService;
	
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	private Apply apply;
	private Integer pageNo;
	private Integer pageNum;
	
	/**
	 * 申请提现
	 */
	public String apply(){
		try {
			
			if( apply == null || CommonUtil.validParams(apply.getUserId(),apply.getAlipay(),apply.getNum(),apply.getName()) ){
				map.put("msg", "缺少必传参数");
				map.put("code", 0);
				return SUCCESS;
			}
			
			generalService.apply(apply,map);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询评论列表异常",e);
			map.put("msg", "查询异常");
			map.put("code", 0);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询用户积分
	 * @return
	 */
	public String selUserIntergral(){
		try {
			
			String userId = request.getParameter("userId");
			
			if( CommonUtil.validParams(userId) ){
				map.put("msg", "缺少必传参数");
				map.put("code", 0);
				return SUCCESS;
			}
			
			generalService.selUserIntergral(userId,map);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询用户积分异常",e);
			map.put("msg", "查询异常");
			map.put("code", 0);
		}
		return SUCCESS;
	}
	
	/**
	 * 查询用户申请
	 * @return
	 */
	public String selUserApply(){
		try {
			
			String userId = request.getParameter("userId");
			
			if( CommonUtil.validParams(userId) ){
				map.put("msg", "缺少必传参数");
				map.put("code", 0);
				return SUCCESS;
			}
			
			generalService.selUserApply(userId,pageNo,pageNum,map);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("查询用户积分异常",e);
			map.put("msg", "查询异常");
			map.put("code", 0);
		}
		return SUCCESS;
	}
	
	/**
	 * 万普接口
	 * @return
	 */
	public String wp(){
		try {
			
			String userId = request.getParameter("key");
			String openudid = request.getParameter("open_udid");
			String points = request.getParameter("points");
			String adname = request.getParameter("ad_name");
			String orderId = request.getParameter("order_id");
			
			generalService.addIntergral(userId,openudid,points,adname,DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"),orderId,null,1,map);
			
		} catch (Exception e) {
			// TODO: handle exception
			log.error("发送信息异常",e);
			map.put("message", "无效数据");
			map.put("success", false);
		}
		return SUCCESS;
	}
	
	/**
	 * 巨朋
	 * @return
	 */
	public String jp(){
		try {
			
			String userId = request.getParameter("userdef");
			String openudid = request.getParameter("udid");
			String points = request.getParameter("score");
			String adname = request.getParameter("adShowName");
			String orderId = request.getParameter("follow");
			
			generalService.addIntergral(userId,openudid,points,adname,DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"),orderId,null,2,map);
			
			PrintWriter pt = response.getWriter();
			pt.print("success");
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				PrintWriter pt = response.getWriter();
				pt.print("fail");
				log.error("发送信息异常",e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 点乐
	 * @return
	 */
	public String dl(){
		try {
			
			String userId = request.getParameter("snuid");
			String openudid = request.getParameter("device_id");
			String points = request.getParameter("currency");
			String adname = request.getParameter("ad_name");
			
			generalService.addIntergral(userId,openudid,points,adname,DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"),null,null,3,map);
			
			PrintWriter pt = response.getWriter();
			pt.print("200");
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				PrintWriter pt = response.getWriter();
				pt.print("403");
				log.error("发送信息异常",e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return null;
	}

	/**
	 * 迷迪
	 * @return
	 */
	public String md(){
		try {
			
			String userId = request.getParameter("param0");
			String openudid = request.getParameter("imei");
			String points = request.getParameter("cash");
			String adname = request.getParameter("appName");
			String orderId = request.getParameter("trand_no");
			
			generalService.addIntergral(userId,openudid,points,adname,DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"),orderId,null,4,map);
			
			PrintWriter pt = response.getWriter();
			pt.print("200");
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				PrintWriter pt = response.getWriter();
				pt.print("403");
				log.error("发送信息异常",e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 多盟
	 * @return
	 */
	public String dm(){
		try {
			
			String userId = request.getParameter("user");
			String openudid = request.getParameter("device");
			String points = request.getParameter("point");
			String adname = request.getParameter("ad");
			String orderId = request.getParameter("orderid");
			String price = request.getParameter("price");
			
			generalService.addIntergral(userId,openudid,points,adname,DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"),orderId,price,5,map);
			
			PrintWriter pt = response.getWriter();
			pt.print("200");
			
		} catch (Exception e) {
			// TODO: handle exception
			try {
				PrintWriter pt = response.getWriter();
				pt.print("403");
				log.error("发送信息异常",e);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return null;
	}
	
	public Integer getPageNo() {
		return pageNo;
	
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageNum() {
		return pageNum;
	
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Map<String, Object> getMap() {
		return map;//null == map?"":map;
	
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public Apply getApply() {
		return apply;
	
	}

	public void setApply(Apply apply) {
		this.apply = apply;
	}

}
