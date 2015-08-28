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
import com.wonders.frame.core.tags.ShowInView;
import com.wonders.frame.core.tags.ShowInView.OperateType;

/**
 * AfAttach entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "AF_ATTACH")
public class Attach implements IDefaultModel {

	@ShowInView(noUse=true)
	private static final long serialVersionUID = 1475619285122489084L;
	// Fields
	@ShowInView(name="ID",operateType=OperateType.HIDDEN)
	private Integer id;
	@ShowInView(name="文件名")
	private String fileName;
	private String extName;
	private String filePath;	
	private String fileSize;
	private String uploader;
	private Date uploadDate;
	private String fileType;
	private String memo;
	private String version;
	private String keyWord;
	private String modelName;
	private String modelId;
	private String groupName;	
	@ShowInView(name="逻辑删除标志位",operateType=OperateType.HIDDEN)
	private Integer removed;

	// Constructors

	/** default constructor */
	public Attach() {
		this.removed=0;
	}

	/** full constructor */
	public Attach(String fileName, String extName,String filePath,  String fileSize, 
			String uploader, Date uploadDate,String fileType, String memo,
			String version, String keyWord, String modelName, String modelId,
			String groupName,Integer removed) {
		this.fileName = fileName;
		this.extName = extName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.uploader = uploader;
		this.uploadDate = uploadDate;		
		this.fileType = fileType;
		this.memo = memo;
		this.version = version;
		this.keyWord = keyWord;
		this.modelName=modelName;
		this.modelId=modelId;
		this.groupName=groupName;
		this.removed = removed;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator ="Generator")   
	@SequenceGenerator(name = "Generator", sequenceName  = "seq_af_attach",allocationSize=1)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "FILE_NAME", length = 200)
	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "EXT_NAME", length = 10)
	public String getExtName() {
		return this.extName;
	}

	public void setExtName(String extName) {
		this.extName = extName;
	}

	@Column(name = "FILE_PATH", length = 200)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "FILE_SIZE", length=10)
	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	@Column(name = "UPLOADER", length = 20)
	public String getUploader() {
		return this.uploader;
	}

	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPLOAD_DATE")
	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Column(name = "FILE_TYPE", length = 20)
	public String getFileType() {
		return this.fileType;
	}
	
	public void setFileType(String fileType) {
		this.fileType=fileType;
	}

	@Column(name = "MEMO", length = 200)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "VERSION", length = 10)
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Column(name = "KEY_WORD", length = 200)
	public String getKeyWord() {
		return this.keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	@Column(name = "MODEL_NAME", length = 50)
	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	@Column(name = "MODEL_ID", length = 50)
	public String getModelId() {
		return this.modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	
	@Column(name = "GROUP_NAME",length = 50)
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Column(name = "REMOVED", precision = 22, scale = 0)
	public Integer getRemoved() {
		return this.removed;
	}

	public void setRemoved(Integer removed) {
		this.removed = removed;
	}


}