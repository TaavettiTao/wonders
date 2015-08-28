DROP SEQUENCE SEQ_AF_RULE_TYPE;

CREATE SEQUENCE SEQ_AF_RULE_TYPE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;
  
DROP SEQUENCE SEQ_AF_RULE;

CREATE SEQUENCE SEQ_AF_RULE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;  

DROP SEQUENCE SEQ_AF_RELATION;

CREATE SEQUENCE SEQ_AF_RELATION
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_OBJ_INFO;

CREATE SEQUENCE SEQ_AF_OBJ_INFO
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_ATTACH;

CREATE SEQUENCE SEQ_AF_ATTACH
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;
  
drop sequence SEQ_AF_CCATE;

create sequence SEQ_AF_CCATE
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 minvalue 1
nocycle
 cache 20
order;

drop sequence SEQ_AF_CODES;

create sequence SEQ_AF_CODES
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 minvalue 1
nocycle
 cache 20
order;

drop sequence SEQ_AF_IMPORT_CONFIG;
CREATE SEQUENCE SEQ_AF_IMPORT_CONFIG
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

drop sequence SEQ_AF_IMPORT_CONFIG_LOG;  
CREATE SEQUENCE SEQ_AF_IMPORT_CONFIG_LOG
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;  

drop table AF_RULE_TYPE cascade constraints;

drop table AF_RULE cascade constraints;

drop table AF_RELATION cascade constraints;

drop table AF_OBJ_INFO cascade constraints;

drop table AF_ATTACH cascade constraints;

alter table AF_CODES
   drop constraint FK_CODES_REFERENCE_CCATE;

drop table AF_CCATE cascade constraints;

drop table AF_CODES cascade constraints;

drop table AF_IMPORT_CONFIG cascade constraints;

drop table AF_IMPORT_CONFIG_LOG cascade constraints;

/*==============================================================*/
/* Table: AF_RULE_TYPE                                       */
/*==============================================================*/
create table AF_RULE_TYPE
(
  ID           INTEGER not null,
  NAME         VARCHAR2(50) not null,
  OBJ_IDS 		   VARCHAR2(1000),
  OBJ_TYPES 		   VARCHAR2(1000),
  REMOVED      INTEGER default 0 not null,
  constraint PK_AF_RULE_TYPE primary key (ID)
);
comment on table AF_RULE_TYPE is
'关联规则类型表';
comment on column AF_RULE_TYPE.NAME is
'规则名';
comment on column AF_RULE_TYPE.OBJ_IDS is
'包含对象ID';
comment on column AF_RULE_TYPE.OBJ_TYPES is
'包含对象类型';

/*==============================================================*/
/* Table: AF_RULE                                       */
/*==============================================================*/
create table AF_RULE
(
  ID      INTEGER not null,
  RULE_TYPE_ID    INTEGER not null,
  P_OBJ_TYPE  VARCHAR2(50) not null,
  N_OBJ_TYPE  VARCHAR2(50) not null,  
  P_OBJ_ID  INTEGER not null,
  N_OBJ_ID  INTEGER not null,  
  removed INTEGER default 0 not null,
  constraint PK_AF_RULE primary key (ID)
);
comment on table AF_RULE is
'关联规则表';

comment on column AF_RULE.RULE_TYPE_ID is
'规则类型ID';

comment on column AF_RULE.P_OBJ_TYPE is
'前置对象（主对象）类型';

comment on column AF_RULE.N_OBJ_TYPE is
'后置对象（从属对象）类型';

comment on column AF_RULE.P_OBJ_ID is
'前置对象（主对象）ID';

comment on column AF_RULE.N_OBJ_ID is
'后置对象（从属对象）ID';

/*==============================================================*/
/* Table: AF_OBJ_RELATION                                       */
/*==============================================================*/
create table AF_RELATION 
(
   ID                   INTEGER              not null,
   P_TYPE               VARCHAR2(50)  not null,
   P_ID                 INTEGER  not null,
   N_TYPE               VARCHAR2(50)  not null,
   N_ID                 INTEGER  not null,
   RULE_TYPE_ID         INTEGER  not null,   
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_RELATION primary key (ID)
);

comment on table AF_RELATION is
'业务对象关系表';
comment on column AF_RELATION.P_TYPE is
'前置对象类型';
comment on column AF_RELATION.P_ID is
'前置对象记录ID';
comment on column AF_RELATION.N_TYPE is
'后置对象类型';
comment on column AF_RELATION.N_ID is
'后置对象ID';
comment on column AF_RELATION.RULE_TYPE_ID is
'规则类型ID';

/*==============================================================*/
/* Table: AF_OBJ_INFO                                */
/*==============================================================*/
create table AF_OBJ_INFO 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)  not null,
   TYPE                 VARCHAR2(50)  not null,
   PARAMS               VARCHAR2(500)  not null,
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_OBJ_INFO primary key (ID)
);

comment on table AF_OBJ_INFO is
'对象信息表';
comment on column AF_OBJ_INFO.NAME is
'对象名称';
comment on column AF_OBJ_INFO.TYPE is
'对象类型';
comment on column AF_OBJ_INFO.PARAMS is
'对象参数,json格式，记录对象实体类全路径';
/*==============================================================*/
/* Table: AF_ATTACH                                */
/*==============================================================*/

create table AF_ATTACH
(
  ID                  INTEGER not null,
  FILE_NAME           VARCHAR2(200),
  EXT_NAME             VARCHAR2(10),
  FILE_PATH           VARCHAR2(200),
  FILE_SIZE           VARCHAR2(10),
  UPLOADER            VARCHAR2(20),
  UPLOAD_DATE         DATE,
  FILE_TYPE           VARCHAR2(20),
  MEMO                VARCHAR2(200),
  VERSION             VARCHAR2(10),
  KEY_WORD            VARCHAR2(200),
  MODEL_NAME          VARCHAR2(50),
  MODEL_ID            VARCHAR2(50),
  GROUP_NAME  VARCHAR2(50),
  REMOVED             INTEGER default(0) not null,  
  constraint PK_AF_ATTACH primary key (ID)
);
comment on table AF_ATTACH is
'附件表';
comment on column AF_ATTACH.FILE_NAME is
'文件名';
comment on column AF_ATTACH.EXT_NAME is
'扩展名';
comment on column AF_ATTACH.FILE_PATH is
'文件存储路径';
comment on column AF_ATTACH.FILE_SIZE is
'文件大小';
comment on column AF_ATTACH.UPLOADER is
'上传人';
comment on column AF_ATTACH.UPLOAD_DATE is
'上传时间';
comment on column AF_ATTACH.FILE_TYPE is
'文件类型';
comment on column AF_ATTACH.MEMO is
'备注';
comment on column AF_ATTACH.VERSION is
'版本';
comment on column AF_ATTACH.KEY_WORD is
'关键字';
comment on column AF_ATTACH.MODEL_NAMEis
'模块（对象）名';
comment on column AF_ATTACH.MODEL_ID is
'模块（对象记录）ID';
comment on column AF_ATTACH.GROUP_NAME is
'组名';
/*==============================================================*/
/* Table: AF_CCATE                                                 */
/*==============================================================*/
create table AF_CCATE 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR2(50) not null,
   NAME                 VARCHAR2(50) not null,
   DESCRIPTION          VARCHAR2(100),
   REMOVED              INTEGER default 0,
   constraint PK_AF_CCATE primary key (ID)
);

comment on table AF_CCATE is
'数据编码类';
comment on column AF_CCATE.NAME is
'编码类名称';
comment on column AF_CCATE.TYPE is
'编码类类型';
comment on column AF_CCATE.DESCRIPTION is
'编码类描述';
/*==============================================================*/
/* Table: AF_CODES                                                 */
/*==============================================================*/
create table AF_CODES 
(
   ID                   INTEGER              not null,
   CCATE_ID             INTEGER not null,
   PCODE                VARCHAR2(50),
   CODE                 VARCHAR2(50) not null,
   DISPLAY              VARCHAR2(200),
   DESCRIPTION          VARCHAR2(200) not null,
   ORDERS               INTEGER,   
   REMOVED              INTEGER default 0,
   constraint PK_AF_CODES primary key (ID)
);

comment on table AF_CODES is
'数据编码项';
comment on column AF_CODES.CCATE_ID is
'编码类ID';
comment on column AF_CODES.PCODE is
'父编码值';
comment on column AF_CODES.CODE is
'编码值';
comment on column AF_CODES.DISPLAY is
'展示值';
comment on column AF_CODES.DESCRIPTION is
'编码项描述';
comment on column AF_CODES.ORDERS is
'排序';
alter table AF_CODES
   add constraint FK_CODES_REFERENCE_CCATE foreign key (CCATE_ID)
      references AF_CCATE (ID);
      
/*==============================================================*/
/* Table: AF_IMPORT_CONFIG                                                 */
/*==============================================================*/      
create table AF_IMPORT_CONFIG
(
   ID INTEGER NOT NULL,
   ENTITY VARCHAR2(20) NOT NULL,
   ENTITY_FIELD VARCHAR2(50) NOT NULL,
   EXCEL_FIELD VARCHAR2(50) NOT NULL,
   TYPE VARCHAR2(20) NOT NULL,
   HAS_TITLE INTEGER NOT NULL,   
   REMOVED INTEGER DEFAULT(0) NOT NULL,
   constraint PK_AF_IMPORT_CONFIG primary key (ID)
);

comment on table AF_IMPORT_CONFIG is
'导入配置表';
comment on column AF_IMPORT_CONFIG.ENTITY is
'对象名';
comment on column AF_IMPORT_CONFIG.ENTITY_FIELD is
'对象属性字段';
comment on column AF_IMPORT_CONFIG.EXCEL_FIELD is
'Excel字段';
comment on column AF_IMPORT_CONFIG.TYPE is
'区分类型';
comment on column AF_IMPORT_CONFIG.HAS_TITLE is
'Excel是否有抬头';

/*==============================================================*/
/* Table: AF_IMPORT_CONFIG_LOG                                                 */
/*==============================================================*/      
create table AF_IMPORT_CONFIG_LOG
(
     ID INTEGER not null,
     ORIGINAL_FILENAME VARCHAR2(200),
     SAVE_FILENAME VARCHAR2(200),
     SAVE_PATH VARCHAR2(500),
     FILETYPE VARCHAR2(10),
     RECORD_NUM INTEGER ,
     FLAG VARCHAR2(10),
     IMPORT_TIME DATE,
     REMOVED INTEGER DEFAULT(0) NOT NULL,
     constraint PK_AF_IMPORT_CONFIG_LOG primary key (ID)
);

comment on table AF_IMPORT_CONFIG_LOG is
'导入日志表';
comment on column AF_IMPORT_CONFIG_LOG.ORIGINAL_FILENAME is
'源文件名';
comment on column AF_IMPORT_CONFIG_LOG.SAVE_FILENAME is
'保存文件名';
comment on column AF_IMPORT_CONFIG_LOG.SAVE_PATH is
'保存路径';
comment on column AF_IMPORT_CONFIG_LOG.FILETYPE is
'文件扩展名';
comment on column AF_IMPORT_CONFIG_LOG.RECORD_NUM is
'导入记录数';
comment on column AF_IMPORT_CONFIG_LOG.FLAG is
'标志位';
comment on column AF_IMPORT_CONFIG_LOG.IMPORT_TIME is
'导入时间';    
      