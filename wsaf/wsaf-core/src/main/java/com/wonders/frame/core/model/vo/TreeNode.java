package com.wonders.frame.core.model.vo;

public class TreeNode<T,ID> {
	private ID pid;
	private ID nid;
	private T node;
	public ID getPid() {
		return pid;
	}
	public void setPid(ID pid) {
		this.pid = pid;
	}
	public ID getNid() {
		return nid;
	}
	public void setNid(ID nid) {
		this.nid = nid;
	}
	public T getNode() {
		return node;
	}
	public void setNode(T node) {
		this.node = node;
	}
}
