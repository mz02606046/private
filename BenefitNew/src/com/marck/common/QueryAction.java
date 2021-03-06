package com.marck.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.marck.common.dao.HDB;
import com.marck.common.model.User;

@Component("queryAction")
@Scope("prototype")
public class QueryAction extends BaseAction{

	private PageUtil pu;
	private Integer pageNow;
	private Integer limit;
	
	private String queryValue;
	
	private Integer id;
	private User user;
	
	@Autowired
	private QueryService queryService;
	
	/**
	 * 登录
	 * @return
	 */
	public String login(){
		try {
			String password = request.getParameter("password");
			
			Boolean isPass = queryService.checkPassword(password);
			
			if( isPass  ){
				request.getSession().setAttribute("isLogin", true);
				return "loginSuccess";
			}else{
				request.setAttribute("msg", "密码不正确");
				return "loginFail";
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "loginFail";
	}
	
	public String account(){
		try {
			
			String resUrl = request.getRequestURI();
			request.getSession().setAttribute("url", resUrl.substring(resUrl.indexOf("/",1)+1)+"?");
			
			pu = queryService.findAccountList(queryValue,pageNow,limit);
			
			return "accountList";
		} catch (Exception e) {
			// TODO: handle exception
		}
		return ERROR;
	}
	
	/**
	 * 跳转添加修改账户页面
	 * @return
	 */
	public String goAddOrUpdateAccount(){
		try {
			if( CommonUtil.validParams(user) ){
				request.setAttribute("title", "添加账户");
			}else{
				user = queryService.getUser(user);
				request.setAttribute("title", "修改账户");
			}
			return "goAddOrUpdateAccount";
		} catch (Exception e) {
			// TODO: handle exception
			log.error("跳转用户列表出错！",e);
			request.setAttribute("msg", "操作失败！");
		}
		return ERROR;
	}
	
	/**
	 * 添加修改账户信息
	 * @return
	 */
	public String addOrUpdateAccount(){
		try {
			if(queryService.addOrUpdateUser(user)){
				user = null;
				request.setAttribute("msg", "添加成功，是否继续添加？");
			}
			return goAddOrUpdateAccount();
		} catch (Exception e) {
			// TODO: handle exception
			log.error("添加修改账户信息出错！",e);
			request.setAttribute("msg", "操作失败！");
		}
		return ERROR;
	}
	
	
	/**
	 * 删除账户
	 * @return
	 */
	public String delAccount(){
		try {
			
			queryService.delAccount(id);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return account();
	}
	
	/**
	 * 提现申请管理
	 * @return
	 */
	public String apply(){
		try {
			
			String resUrl = request.getRequestURI();
			request.getSession().setAttribute("url", resUrl.substring(resUrl.indexOf("/",1)+1)+"?");
			
			pu = queryService.findApplyList(queryValue,pageNow,limit);
			
			return "applyList";
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ERROR;
	}
	
	/**
	 * 处理申请
	 * @return
	 */
	public String delApply(){
		try {
			
			queryService.delApply(id);
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return apply();
	}
	
	/**
	 * 查看ip
	 * @return
	 */
	public String showIp(){
		try {
			
			String id = request.getParameter("id");
			String resUrl = request.getRequestURI();
			request.getSession().setAttribute("url", resUrl.substring(resUrl.indexOf("/",1)+1)+"?id="+id);
			
			pu = queryService.findUserIp(id,pageNow,limit);
			
			return "userip";
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return ERROR;
	}
	
	public PageUtil getPu() {
		return pu;
	
	}

	public void setPu(PageUtil pu) {
		this.pu = pu;
	}

	public Integer getPageNow() {
		return pageNow;
	
	}

	public void setPageNow(Integer pageNow) {
		this.pageNow = pageNow;
	}

	public Integer getLimit() {
		return limit;
	
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getQueryValue() {
		return queryValue;
	
	}

	public void setQueryValue(String queryValue) {
		this.queryValue = queryValue;
	}

	public Integer getId() {
		return id;
	
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
	
}
