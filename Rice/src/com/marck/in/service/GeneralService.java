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
import com.marck.common.Push;
import com.marck.common.dao.HDB;
import com.marck.common.model.User;
import com.marck.common.model.Apply;
import com.marck.common.model.UserIntergral;
import com.marck.common.model.UserIntergralQuery;
import com.marck.common.model.UserToken;

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
		if(apply.getType() == 1 && (user.getIntegral() < 50000 || apply.getNum()*1000 < 50000)){
			map.put("code", "0");
			map.put("msg", "积分不足或申请金额不正确，Q币兑换至少50000积分起！");
			return;
		}
		
		if(apply.getType() == 2 && (user.getIntegral() < 100000 || apply.getNum()*1000 < 100000)){
			map.put("code", "0");
			map.put("msg", "积分不足或申请金额不正确，支付宝兑换至少100000积分起！");
			return;
		}
		
		if(apply.getType() == 3 && (user.getIntegral() < 100000 || apply.getNum()*1000 < 100000)){
			map.put("code", "0");
			map.put("msg", "积分不足或申请金额不正确，财付通兑换至少100000积分起！");
			return;
		}
		
		if(apply.getType() == 4 && (user.getIntegral() < 5000 || apply.getNum()*1000 < 5000)){
			map.put("code", "0");
			map.put("msg", "积分不足或申请金额不正确，手机话费兑换至少5000积分起！");
			return;
		}
		
		if(apply.getType() == 5 && (user.getIntegral() < 100000 || apply.getNum()*1000 < 100000)){
			map.put("code", "0");
			map.put("msg", "积分不足或申请金额不正确，银行卡兑换至少100000积分起！");
			return;
		}
		
		if(user.getIntegral() < apply.getNum()*1000){
			map.put("code", "0");
			map.put("msg", "积分不足，无法申请！");
			return;
		}
		
		user.setIntegral(user.getIntegral()-apply.getNum()*1000);
		hdb.saveOrUpdate(user);

		
		apply.setAccount(CommonUtil.changeIos8859ToUtf8(apply.getAccount()));
		apply.setName(CommonUtil.changeIos8859ToUtf8(apply.getName()));
		apply.setAddTime(DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"));
		hdb.saveOrUpdate(apply);
		
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
	public boolean addIntergral(String userId, String openudid, String points, String adname, String addTime, String orderId, String price,Integer platForm, Map<String, Object> map) {
		// TODO Auto-generated method stub
		
		String hql = "from UserIntergral ui where ui.userId="+Integer.parseInt(userId)+" and ui.orderid='"+orderId+"' and ui.platform="+platForm;
		
		List<?> list = hdb.findHql(hql);
		
		if(list.size() != 0){
			map.put("message", "成功接收");
			map.put("success", true);
			return false;
		}
		
		hql = "from User u where u.id="+Integer.parseInt(userId);
		
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
		
		Integer temp = 0;
		
		if(!CommonUtil.validParams(user.getPid())){
			
			temp = CommonUtil.myRound(Double.parseDouble(points)*0.1);
			
			User recommend = (User) hdb.find(User.class, user.getPid());
			UserIntergral rui = new UserIntergral();
			rui.setTime(addTime);
			rui.setAdname("获得推荐用户《"+user.getPhone()+"》提成");
			rui.setIntergral(temp+"");
			rui.setOpenudid("");
			rui.setOrderid("");
			rui.setPlatform(1);
			rui.setPrice("");
			rui.setUserId(recommend.getId());
			hdb.saveOrUpdate(rui);
			recommend.setIntegral(recommend.getIntegral()+temp);
			hdb.saveOrUpdate(recommend);
			
			if(!CommonUtil.validParams(recommend.getPid())){
				
				User precommend = (User) hdb.find(User.class, recommend.getPid());
				
				UserIntergral prui = new UserIntergral();
				prui.setTime(addTime);
				prui.setAdname("获得推荐用户《"+recommend.getPhone()+"》提成");
				prui.setIntergral(temp/2+"");
				prui.setOpenudid("");
				prui.setOrderid("");
				prui.setPlatform(1);
				prui.setPrice("");
				prui.setUserId(precommend.getId());
				hdb.saveOrUpdate(prui);
				
				precommend.setIntegral(precommend.getIntegral()+temp/2);
				hdb.saveOrUpdate(precommend);
				
				temp += temp/2;
			}
			
		}
		
		user.setIntegral(user.getIntegral()+Integer.parseInt(points)-temp);
		hdb.saveOrUpdate(user);
		
		map.put("message", "成功接收");
		map.put("success", true);
		return true;
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

	/**
	 * 查询明细
	 * @param userId
	 * @param type
	 * @param platForm 
	 * @param pageNo
	 * @param pageNum
	 * @param map
	 */
	public void selUserDetail(String userId, String type, String platForm, Integer pageNo,
			Integer pageNum, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String hql = "";
		PageUtil pu = new PageUtil();
		switch (Integer.parseInt(type)) {
		case 1:
			hql = "select ui from User u,UserIntergral ui where u.id = ui.userId and u.id="+Integer.parseInt(userId);
			if(!CommonUtil.validParams(platForm)){
				hql += " and ui.platform="+Integer.parseInt(platForm);
			}else{
				hql += " and ui.platform <> 1 ";
			}
			pu = hdb.findHql(hql, pageNo, pageNum);
			break;
		case 2:
			hql = "select a from User u,Apply a where u.id = a.userId and u.id="+Integer.parseInt(userId);
			pu = hdb.findHql(hql, pageNo, pageNum);
			break;
		}
		map.put("hasNext", pu.getHasNext());
		map.put("data", pu.getData());
		map.put("code", 1);
		map.put("message", "查询成功");
	}

	/**
	 * 查询用户
	 * @param userId
	 * @param map
	 */
	public void selUser(String userId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		User user = (User) hdb.findUniqueHql("from User u where u.id="+Integer.parseInt(userId));
		map.put("data", user);
		map.put("code", 1);
		map.put("message", "查询成功");
	}

	/**
	 * 用户签到
	 * @param userId
	 * @param pageNo
	 * @param pageNum
	 * @param map
	 */
	public void sign(String userId, Map<String, Object> map) {
		// TODO Auto-generated method stub
		User user = (User) hdb.find(User.class, Integer.parseInt(userId));
		if(null != user ){
			if( !CommonUtil.validParams(user.getLastsign()) && user.getLastsign().substring(0,10).equals(DateUtil.getNowString("yyyy-MM-dd"))){
				map.put("msg", "今日已签到");
				map.put("code", 1);
			}else{
				user.setIntegral(user.getIntegral()+100);
				user.setLastsign(DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"));
				hdb.saveOrUpdate(user);
				map.put("msg", "签到成功");
				map.put("code", 1);
			}
		}else{
			map.put("msg", "帐号不存在");
			map.put("code", 1);
		}
	}

	/**
	 * 修改密码
	 * @param userId
	 * @param oldpw
	 * @param newpw
	 * @param map
	 */
	public void modifyPassword(String userId, String oldpw, String newpw,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		User user = (User) hdb.find(User.class, Integer.parseInt(userId));
		if( null == user ){
			map.put("msg", "用户不存在！");
			map.put("code", 0);
			return;
		}
		if(!user.getPassword().equals(CommonUtil.Md5(oldpw))){
			map.put("msg", "密码不正确！");
			map.put("code", 0);
			return;
		}
		
		user.setPassword(CommonUtil.Md5(newpw));
		hdb.saveOrUpdate(user);
		
		map.put("msg", "修改成功!");
		map.put("code", 1);
	}

	/**
	 * 推送
	 * @param userId
	 * @param string
	 * @param string2
	 */
	public void myPush(String userId, String path, String content) {
		// TODO Auto-generated method stub
		try {
			
			String hql = "from UserToken ut where ut.userId="+Integer.parseInt(userId);
			
			UserToken ut =  (UserToken) hdb.findUniqueHql(hql);
			
			Push.pushUser(path, "123456", ut.getToken(), content);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 更新用户token
	 * @param userId
	 * @param token
	 * @param map
	 */
	public void updateToken(String userId, String token, Map<String, Object> map) {
		// TODO Auto-generated method stub
		String hql = "from UserToken ut where ut.token='"+token+"'";
		UserToken ut =  (UserToken) hdb.findUniqueHql(hql);
		if( null == ut ){
			ut = new UserToken();
		}
		if( null != userId && !"".equals(userId)){
			ut.setUserId(Integer.parseInt(userId));
		}
		ut.setToken(token);
		ut.setAddTime(DateUtil.getNowString("yyyy-MM-dd HH:mm:ss"));
		hdb.saveOrUpdate(ut);
		map.put("code", 1);
		map.put("message", "更新成功");
	}
	
}
