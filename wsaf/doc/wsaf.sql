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
  id           INTEGER not null,
  name         VARCHAR2(50) not null,
  objs 		   VARCHAR2(1000),
  removed      INTEGER default 0 not null,
  constraint PK_AF_RULE_TYPE primary key (ID)
);
comment on table AF_RULE_TYPE is
'关联规则类型表';
comment on column AF_RULE_TYPE.NAME is
'规则名';
comment on column AF_RULE_TYPE.OBJS is
'包含对象类型';

/*==============================================================*/
/* Table: AF_RULE                                       */
/*==============================================================*/
create table AF_RULE
(
  id      INTEGER not null,
  rule_type_id    INTEGER not null,
  p_type  VARCHAR2(50) not null,
  n_type  VARCHAR2(50) not null,
  removed INTEGER default 0 not null,
  constraint PK_AF_RULE primary key (ID)
);
comment on table AF_RULE is
'关联规则表';

comment on column AF_RULE.RULE_TYPE_ID is
'规则类型外键ID';

comment on column AF_RULE.P_TYPE is
'前置对象（主对象）';

comment on column AF_RULE.N_TYPE is
'后置对象（从属对象）';


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
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_RELATION primary key (ID)
);

comment on table AF_RELATION is
'级联关系表';

/*==============================================================*/
/* Table: AF_OBJ_INFO                                */
/*==============================================================*/
create table AF_OBJ_INFO 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50)  not null,
   TYPE                 VARCHAR2(20)  not null,
   PARAMS               VARCHAR2(500)  not null,
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_OBJ_INFO primary key (ID)
);

comment on table AF_OBJ_INFO is
'对象信息表';
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

/*==============================================================*/
/* Table: AF_CCATE                                                 */
/*==============================================================*/
create table AF_CCATE 
(
   ID                   INTEGER              not null,
   TYPE                 VARCHAR2(20) not null,
   NAME                 VARCHAR2(50) not null,
   DESCRIPTION          VARCHAR2(100),
   REMOVED              INTEGER default 0,
   constraint PK_AF_CCATE primary key (ID)
);

comment on table AF_CCATE is
'字典类';

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
   REMOVED              INTEGER default 0,
   ORDERS               INTEGER,
   constraint PK_AF_CODES primary key (ID)
);

comment on table AF_CODES is
'字典项';

alter table AF_CODES
   add constraint FK_CODES_REFERENCE_CCATE foreign key (CCATE_ID)
      references AF_CCATE (ID);
      
/*==============================================================*/
/* Table: AF_IMPORT_CONFIG                                                 */
/*==============================================================*/      
create table AF_IMPORT_CONFIG
(
   ID INTEGER NOT NULL,
   Entity VARCHAR2(20) NOT NULL,
   ENTITY_FIELD VARCHAR2(50) NOT NULL,
   EXCEL_FIELD VARCHAR2(50) NOT NULL,
   TYPE VARCHAR2(20) NOT NULL,
   HAS_TITLE INTEGER NOT NULL,   
   REMOVED INTEGER DEFAULT(0) NOT NULL,
   constraint PK_AF_IMPORT_CONFIG primary key (ID)
);


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

    
      