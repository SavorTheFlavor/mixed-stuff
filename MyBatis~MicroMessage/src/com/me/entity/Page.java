package com.me.entity;

/**
 *分页对应的实体类
 */
public class Page {

	/**
	 * 总条数
	 */
	private int totalNumber;
	
	/**
	 * 当前页数
	 */
	private int currentPage;
	
	/**
	 * 总页数
	 */
	private int totalPage;
	
	/**
	 * 每页显示条数
	 */
	private int pageNumber = 3;
	
	/**
	 * 数据库中的limit参数..
	 * 从第几条开始取
	 */
	private int dbIndex;
	
	/**
	 * 数据库中的limit参数..
	 * 一次取多少条
	 */
	private int dbNumber;
	
	/**
	 * 根据当前对象属性值计算并设置相关属性值
	 */
	public void count(){
		int totalPageTemp = totalNumber/pageNumber;
		if(totalPageTemp < 1){
			totalPageTemp = 1; 
		}
		if(totalPageTemp*pageNumber < totalNumber){
			totalPageTemp++;
		}
		totalPage = totalPageTemp;
		
		if(currentPage > totalPage){
			this.currentPage = totalPage;
		}
		
		if(currentPage < 1){
			currentPage = 1;
		}
		//设置limit参数
		this.dbIndex = (currentPage - 1)*pageNumber;
		this.dbNumber = pageNumber;
	}

	public int getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(int totalNumber) {
		this.totalNumber = totalNumber;
		this.count();
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(int dbIndex) {
		this.dbIndex = dbIndex;
	}

	public int getDbNumber() {
		return dbNumber;
	}

	public void setDbNumber(int dbNumber) {
		this.dbNumber = dbNumber;
	}
	
	
	
}
