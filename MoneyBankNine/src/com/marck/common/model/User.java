package com.marck.common.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column
	private Integer id;
	@Column
	private String phone = "";
	@Column
	private String password = "";
	@Column
	private Integer integral = 0;
	@Column
	private String lastlogin = "";
	@Column
	private String ip = "";
	@Column
	private String recommend = "";
	@Column
	private Integer isJaiBreak = 0;
	@Transient
	private List<UserIntergralQuery> userIntergralQueries = new ArrayList<UserIntergralQuery>();
	@Transient
	private Double tj;
	@Transient
	private Double gm;
	@Transient
	private Double jp;
	@Transient
	private Double mp;
	@Transient
	private Double aw;
	@Transient
	private Double yjf;
	@Transient
	private Double dl;
	@Transient
	private Double dm;
	@Transient
	private Double wp;
	@Transient
	private Double qm;
	@Transient
	private Double md;
	@Transient
	private Double ck;
	@Transient
	private Double ym;

	public Double getCk() {
		return null == ck ? 0 : ck;
	
	}
	public void setCk(Double ck) {
		this.ck = ck;
	}
	public Double getYm() {
		return null == ym ? 0 : ym;
	
	}
	public void setYm(Double ym) {
		this.ym = ym;
	}
	public Integer getIsJaiBreak() {
		return null == isJaiBreak ? 0 : isJaiBreak;
	
	}
	public void setIsJaiBreak(Integer isJaiBreak) {
		this.isJaiBreak = isJaiBreak;
	}
	public Double getAw() {
		return null == aw ? 0 : aw;
	
	}
	public void setAw(Double aw) {
		this.aw = aw;
	}
	public Double getQm() {
		return null == qm ? 0 : qm;
	
	}
	public void setQm(Double qm) {
		this.qm = qm;
	}
	public Double getMd() {
		return null == md ? 0 : md;
	
	}
	public void setMd(Double md) {
		this.md = md;
	}
	public Double getDm() {
		return null == dm ? 0 : dm;
	
	}
	public void setDm(Double dm) {
		this.dm = dm;
	}
	public Double getWp() {
		return null == wp ? 0 : wp;
	
	}
	public void setWp(Double wp) {
		this.wp = wp;
	}
	public Double getYjf() {
		return null == yjf ? 0 : yjf;
	
	}
	public void setYjf(Double yjf) {
		this.yjf = yjf;
	}
	public Double getDl() {
		return null == dl ? 0 : dl;
	
	}
	public void setDl(Double dl) {
		this.dl = dl;
	}
	public Double getTj() {
		return null == tj ? 0 : tj;
	
	}
	public void setTj(Double tj) {
		this.tj = tj;
	}
	public String getRecommend() {
		return null == recommend ? "" : recommend;
	
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
	public Double getGm() {
		return null == gm ? 0 : gm;
	
	}
	public void setGm(Double gm) {
		this.gm = gm;
	}
	public String getIp() {
		return null == ip ? "" : ip;
	
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Double getMp() {
		return null == mp ? 0 : mp;
	
	}
	public void setMp(Double mp) {
		this.mp = mp;
	}
	public Double getJp() {
		return null == jp ? 0 : jp;
	
	}
	public void setJp(Double jp) {
		this.jp = jp;
	}
	public List<UserIntergralQuery> getUserIntergralQueries() {
		return userIntergralQueries;
	
	}
	public void setUserIntergralQueries(
			List<UserIntergralQuery> userIntergralQueries) {
		this.userIntergralQueries = userIntergralQueries;
	}
	public Integer getId() {
		return null == id ? 0 : id;
	
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return null == phone ? "" : phone;
	
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPassword() {
		return null == password ? "" : password;
	
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getIntegral() {
		return null == integral ? 0 : integral;
	
	}
	public void setIntegral(Integer integral) {
		this.integral = integral;
	}
	public String getLastlogin() {
		return null == lastlogin ? "" : lastlogin;
	
	}
	public void setLastlogin(String lastlogin) {
		this.lastlogin = lastlogin;
	}
	
}
