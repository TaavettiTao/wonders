/** 
* @Title: RelationObj.java 
* @Package com.wonders.frame.core.model.vo 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lushuaifeng
* @version V1.0 
*/
package com.wonders.frame.core.model.vo;


/** 
 * @ClassName: RelationObj 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 */
public class NodeObj<T> {
	private T data;
	private Object relatedNode;
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public Object getRelatedNode() {
		return relatedNode;
	}
	public void setRelatedNode(Object relatedNode) {
		this.relatedNode = relatedNode;
	}
	

}
