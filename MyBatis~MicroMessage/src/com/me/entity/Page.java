package com.me.entity;

/**
 *��ҳ��Ӧ��ʵ����
 */
public class Page {

	/**
	 * ������
	 */
	private int totalNumber;
	
	/**
	 * ��ǰҳ��
	 */
	private int currentPage;
	
	/**
	 * ��ҳ��
	 */
	private int totalPage;
	
	/**
	 * ÿҳ��ʾ����
	 */
	private int pageNumber = 3;
	
	/**
	 * ���ݿ��е�limit����..
	 * �ӵڼ�����ʼȡ
	 */
	private int dbIndex;
	
	/**
	 * ���ݿ��е�limit����..
	 * һ��ȡ������
	 */
	private int dbNumber;
	
	/**
	 * ���ݵ�ǰ��������ֵ���㲢�����������ֵ
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
		//����limit����
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
