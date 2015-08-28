package com.wonders.frame.core.model.vo;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.wonders.frame.core.model.IMultiModel;
import com.wonders.frame.core.model.IWsafModel;
import com.wonders.frame.core.service.MultiCrudService.RelateType;
import com.wonders.frame.core.tags.MultiRelate;

/**
 * @author lushuaifeng
 *
 */
public class MultiModel implements IMultiModel {
	@Valid
	@MultiRelate(type=RelateType.MAIN)
	private IWsafModel mainObj;
	@Valid
	@MultiRelate(type=RelateType.PARENT,fk={"fk"})
	private List<IWsafModel> parentObj;
	@Valid
	@MultiRelate(type=RelateType.CHILD,fk={"fk"})
	private List<IWsafModel> childObj;

	public IWsafModel getMainObj() {
		return mainObj;
	}
	public void setMainObj(IWsafModel mainObj) {
		this.mainObj = mainObj;
	}
	public List<IWsafModel> getParentObj() {
		return parentObj;
	}
	public void setParentObj(List<IWsafModel> parentObj) {
		this.parentObj = parentObj;
	}
	public void setParentObj(IWsafModel wsafModel) {
		if(this.parentObj ==null){
			this.parentObj= new ArrayList<IWsafModel>();
		}
		this.parentObj.add(wsafModel);
	}
	public List<IWsafModel> getChildObj() {
		return childObj;
	}
	public void setChildObj(List<IWsafModel> childObj) {
		this.childObj = childObj;
	}
	public void setChildObj(IWsafModel wsafModel) {
		if(this.childObj ==null){
			this.childObj= new ArrayList<IWsafModel>();
		}
		this.childObj.add(wsafModel);
	}
}
