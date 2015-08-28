package com.wonders.frame.core.model.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.wonders.frame.core.model.IDefaultModel;

/**
 * 
 * @author taoweiwei
 * 导入Excel数据配置实体
 *
 */
@Entity
@Table(name="AF_IMPORT_CONFIG")
public class ImportConfig implements IDefaultModel{

	private static final long serialVersionUID = 1L;
	
	//编号
	private Integer id;
	//实体
	private String entity;
	//实体字段
	private String entityField;
	//excel导入数据对应模式：列对应、title对应
	private String excelField;
	//Excel导入数据类型
	private String type;
	//是否有title：1 title,0 column 
	private Integer hasTitle;
	//逻辑删除标志位：0  可用 ，1 删除
	private Integer removed;
	
	
	/** default constructor */
	public ImportConfig() {
		this.removed=0;
	}



	public ImportConfig(String entity, String entityField,
			String excelField, String type, Integer hasTitle,
			Integer removed) {
		super();
		this.entity = entity;
		this.entityField = entityField;
		this.excelField = excelField;
		this.type = type;
		this.hasTitle = hasTitle;
		this.removed = removed;
	}



	/** full constructor */
	public ImportConfig(Integer id, String entity, String entityField,
			String excelField, String type, Integer hasTitle,
			Integer removed) {
		super();
		this.id = id;
		this.entity = entity;
		this.entityField = entityField;
		this.excelField = excelField;
		this.type = type;
		this.hasTitle = hasTitle;
		this.removed = removed;
	}



	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Generator")
	@SequenceGenerator(name="Generator",sequenceName="seq_af_import_config",allocationSize=1)
	@Column(name="ID",unique=true,nullable=false)
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ENTITY",length=20,nullable=false)
	public String getEntity() {
		return entity;
	}



	public void setEntity(String entity) {
		this.entity = entity;
	}


	@Column(name="ENTITY_FIELD",length=50,nullable=false)
	public String getEntityField() {
		return entityField;
	}



	public void setEntityField(String entityField) {
		this.entityField = entityField;
	}

    @Column(name="EXCEL_FIELD",length=50,nullable=false)
    public String getExcelField() {
		return excelField;
	}

    public void setExcelField(String excelField) {
		this.excelField = excelField;
	}

    @Column(name="TYPE",length=20,nullable=false)
	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}


    @Column(name="HAS_TITLE",nullable=false)
	public Integer getHasTitle() {
		return hasTitle;
	}



	public void setHasTitle(Integer hasTitle) {
		this.hasTitle = hasTitle;
	}


	@Column(name="REMOVED",nullable=false)
	public Integer getRemoved() {
		return removed;
	}



	public void setRemoved(Integer removed) {
		this.removed = removed;
	}


}
