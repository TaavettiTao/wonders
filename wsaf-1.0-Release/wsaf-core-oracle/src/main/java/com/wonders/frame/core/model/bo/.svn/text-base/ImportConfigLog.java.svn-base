package com.wonders.frame.core.model.bo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wonders.frame.core.model.IDefaultModel;

/**
 * 
 * @author taoweiwei
 * 作用：导入Excel数据操作日志
 */
@Entity
@Table(name = "AF_IMPORT_CONFIG_LOG")
public class ImportConfigLog implements IDefaultModel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//编号
	private Integer id;
	//原始文件名
	private String originalFilename;
	//保存文件名
	private String saveFilename;
	//文件类型
	private String fileType;
	//保存路径
	private String savePath;
	//导入总记录数
	private Integer recordNum;
	//导入是否成功：true 成功，false 失败
	private String flag;
	//导入时间
	private Date importTime;
	//逻辑删除标志位：0 可用，1 删除
	private Integer removed;
	
	
	/** default constructor */
	public ImportConfigLog() {
		this.removed = 0;
	}

	public ImportConfigLog(String originalFilename, String saveFilename,
			String fileType, String savePath, Integer recordNum, String flag,
			Date importTime) {
		this.originalFilename = originalFilename;
		this.saveFilename = saveFilename;
		this.fileType = fileType;
		this.savePath = savePath;
		this.recordNum = recordNum;
		this.flag = flag;
		this.importTime = importTime;
		this.removed = 0;
	}


	

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="Generator")
	@SequenceGenerator(name="Generator",sequenceName="seq_af_import_config_log",allocationSize=1)
	@Column(name="ID",unique=true,nullable=false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="ORIGINAL_FILENAME",length=200)
	public String getOriginalFilename() {
		return originalFilename;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	@Column(name="SAVE_FILENAME",length=200)
	public String getSaveFilename() {
		return saveFilename;
	}

	public void setSaveFilename(String saveFilename) {
		this.saveFilename = saveFilename;
	}

	@Column(name="FILETYPE",length=10)
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name="SAVE_PATH",length=500)
	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	
	@Column(name="RECORD_NUM")
	public Integer getRecordNum() {
		return recordNum;
	}
	
	public void setRecordNum(Integer recordNum) {
		this.recordNum = recordNum;
	}

	@Column(name="FLAG")
	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="IMPORT_TIME")
	public Date getImportTime() {
		return importTime;
	}

	public void setImportTime(Date importTime) {
		this.importTime = importTime;
	}

	@Column(name="REMOVED")
	public Integer getRemoved() {
		return removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}
}
