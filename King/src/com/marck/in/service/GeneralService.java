package com.marck.in.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.marck.common.CommonUtil;
import com.marck.common.DateUtil;
import com.marck.common.PageUtil;
import com.marck.common.dao.HDB;
import com.marck.common.model.User;
import com.marck.common.model.Apply;
import com.marck.common.model.UserIntergral;
import com.marck.common.model.UserIntergralQuery;

@Component("generalService")
@Transactional(readOnly = false,propagation = Propagation.REQUIRED)
public class GeneralService {
	
	@Autowired
	private HDB hdb;


	
	/**
	 * 查询列表
	 * @param type 
	 * @param time
	 * @param pageNo
	 * @param pageNum
	 * @param map
	 */
	public void selUserList(Integer type, String time, Integer pageNo,Integer pageNum,Map<String, Object> map){
		String hql = "";
		PageUtil pu = null;
		
		switch (type) {
		case 0:
			hql = "from User u ";
			
			if(!CommonUtil.validParams(time)){
				hql += " where u.lastlogin > '"+time+"'";
			}
			
			hql += " order by u.lastlogin desc";
			
			pu = hdb.findHql(hql, pageNo, pageNum);
			
			map.put("msg", "查询成功");
			map.put("code", 1);
			map.put("list", pu.getData());
			map.put("hasNext",pu.getHasNext());
			break;
		case 1:
			hql = "from UserMessage um ";
			
			if(!CommonUtil.validParams(time)){
				hql += " where um.addTime > '"+time+"'";
			}
			
			hql += " order by um.addTime desc";
			
			pu = hdb.findHql(hql, pageNo, pageNum);
			
			map.put("msg", "查询成功");
			map.put("code", 1);
			map.put("data", pu.getData());
			map.put("hasNext",pu.getHasNext());
			break;
		case 2:
			hql = "from User u ";
			
			if(!CommonUtil.validParams(time)){
				hql += " where u.lastsignin > '"+time+"'";
			}
			
			hql += " order by u.lastsignin desc";
			
			pu = hdb.findHql(hql, pageNo, pageNum);
			map.put("msg", "查询成功");
			map.put("code", 1);
			map.put("data", pu.getData());
			map.put("hasNext",pu.getHasNext());
			break;
		}
		
	}

	/**
	 * 提交申请
	 * @param apply
	 * @param map
	 */
	public void apply(Apply apply, Map<String, Object> map) {
		// TODO Auto-generated method stub
		User user = (User) hdb.find(User.class, apply.getUserId());
		if(user.getIntegral() < apply.getNum()*100){
			map.put("code", "0");
			map.put("msg", "积分不足，无法申请！");
			return;
		}
		
		user.setIntegral(user.getIntegral()-apply.getNum()*100);
		hdb.saveOrUpdate(user);

		
		apply.setAlipay(CommonUtil.changeIos8859ToUtf8(apply.getAlipay()));
		apply.setName(CommonUtil.changeIos8859ToUtf8(apply.getName()));
		apply.setAddTime(DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"));
		hdb.saveOrUpdate(apply);
		
		map.put("phone", user.getPhone());
		map.put("code", "1");
		map.put("msg", "申请成功！");
	}

	/**
	 * 添加积分
	 * @param map 
	 * @param orderId 
	 * @param addTime 
	 * @param adname 
	 * @param points 
	 * @param openudid 
	 * @param userId 
	 * @param open_udid
	 * @param points
	 * @param orderId 
	 * @param addTime 
	 * @param adname 
	 * @param points2 
	 * @param map
	 * @param map 
	 */
	public void addIntergral(String userId, String openudid, String points, String adname, String addTime, String orderId, String price,Integer platForm, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String hql = "from User u where u.id="+Integer.parseInt(userId);
		
		UserIntergral ui = new UserIntergral();
		ui.setTime(addTime);
		ui.setAdname(CommonUtil.changeIos8859ToUtf8(adname));
		ui.setIntergral(points);
		ui.setOpenudid(openudid);
		ui.setOrderid(orderId);
		ui.setPlatform(platForm);
		ui.setPrice(price);
		ui.setUserId(Integer.parseInt(userId));
		hdb.saveOrUpdate(ui);
		
		User user =  (User) hdb.findUniqueHql(hql);
		user.setIntegral(user.getIntegral()+Integer.parseInt(points));
		hdb.saveOrUpdate(user);
		
		map.put("message", "成功接收");
		map.put("success", true);
		
	}

	/**
	 * 查询用户积分
	 * @param user
	 * @param map
	 */
	public void selUserIntergral(String userId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		User user = (User) hdb.find(User.class, Integer.parseInt(userId));
		String sql = "SELECT ui.platform,SUM(ui.intergral) FROM userintergral ui where ui.userId = "+Integer.parseInt(userId)+" GROUP BY ui.platform";
		List<Object[]> list  = (List<Object[]>) hdb.findSql(sql);
		List<UserIntergralQuery> uiqs = new ArrayList<UserIntergralQuery>();
		for(Object[] obj : list){
			UserIntergralQuery uiq = new UserIntergralQuery();
			uiq.setPlatform((Integer) obj[0]);
			uiq.setTotal((Double) obj[1]);
			uiqs.add(uiq);
		}
		user.setUserIntergralQueries(uiqs);
		user.setPassword("");
		map.put("data", user);
		map.put("code", 1);
		map.put("message", "查询成功");
	}

	/**
	 * 查询用户申请
	 * @param userId
	 * @param pageNum 
	 * @param pageNo 
	 * @param map
	 */
	public void selUserApply(String userId, Integer pageNo, Integer pageNum, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String hql = "from Apply a where a.userId="+Integer.parseInt(userId);
		PageUtil pu = hdb.findHql(hql, pageNo, pageNum);
		map.put("data", pu.getData());
		map.put("hasNext", pu.getHasNext());
		map.put("code", 1);
		map.put("message", "查询成功");
	}
	
}
