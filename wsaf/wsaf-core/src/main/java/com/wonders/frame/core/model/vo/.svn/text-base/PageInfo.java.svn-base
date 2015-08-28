/** 
* @Title: PageInfo.java 
* @Package com.wonders.frame.core.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;

/** 
 * @ClassName: PageInfo 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class PageInfo {
	private int totalRecord; 		
	private int pageSize=20;
	private int currentPage; 
	private int totalPages; 
	private int startRecord; 
	public boolean hasNext=true;
	public boolean hasPrevious=false;
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public PageInfo(int totalRecord) {
		init(totalRecord);
	}
	public PageInfo(int totalRecord,int pageSize) {
		if(pageSize>0){
			this.pageSize = pageSize;
		}
		init(totalRecord);
	}
	
	private void init(int totalRecord){
		this.totalRecord = totalRecord;
		this.totalPages = totalRecord / pageSize;
		int mod = totalRecord % pageSize;
		if (mod > 0) {
			this.totalPages++;
		}
		if(this.pageSize >= this.totalRecord){
			this.hasNext=false;
			this.hasPrevious=false;
		}
		this.currentPage = 1;
		this.startRecord = 0;
	}
	
	

	public void first() {		
		this.currentPage = 1;
		this.startRecord = 0;		
		this.hasPrevious=false;
		this.hasNext=true;
		if(this.pageSize >= this.totalRecord){
			this.hasNext=false;
		}
	}

	public void last() {
		this.currentPage = this.totalPages;
		this.startRecord = (this.currentPage - 1) * this.pageSize;
		this.hasPrevious=true;
		this.hasNext=false;
		if(this.pageSize >= this.totalRecord){
			this.hasPrevious=false;
		}
	}
	
	public void refresh(int currentPage) {
		this.currentPage = currentPage;		
		if(this.currentPage<=1){
			first();
		}else if(this.currentPage>=this.totalPages){
			last();
		}else{
			this.hasPrevious=true;
			this.hasNext=true;
			this.startRecord = (this.currentPage - 1) * this.pageSize;
		}
		
	}
	
	public void previous() {
		refresh(this.currentPage-1);
	}

	public void next() {
		refresh(this.currentPage+1);
	}



	

	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	
	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}
}
