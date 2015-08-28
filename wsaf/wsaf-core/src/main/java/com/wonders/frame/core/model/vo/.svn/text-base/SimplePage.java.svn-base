package com.wonders.frame.core.model.vo;
import java.util.List;

/**
 * @author mengjie
 * 封装查询结构的对象
 * content 查询结果
 * pageInfo 页面基本信息
 */
public class SimplePage<T> {

	private List<T> content;
	
	private PageInfo pageInfo;
	public SimplePage(){
		
	}
	public SimplePage(PageInfo pageInfo){
		this.pageInfo=pageInfo;
	}
	
	public SimplePage(PageInfo pageInfo,List<T> content){
		this.pageInfo=pageInfo;
		this.content=content;
	}
	public List<T> getContent() {
		return content;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public PageInfo getPageInfo() {
		return pageInfo;
	}
	public void setPageInfo(PageInfo pageInfo) {
		this.pageInfo = pageInfo;
	}
	
}
