package com.me.bean;

public class User {
	private Long user_id;
	private String user_name;
	private String password;
	private String phone;
	private Integer vip_id;
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getVip_id() {
		return vip_id;
	}
	public void setVip_id(Integer vip_id) {
		this.vip_id = vip_id;
	}
	@Override
	public String toString() {
		return "User [user_id=" + user_id + ", user_name=" + user_name + ", password=" + password + ", phone=" + phone
				+ ", vip_id=" + vip_id + "]";
	}
	
}
