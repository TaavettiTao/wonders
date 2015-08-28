DROP SEQUENCE SEQ_AF_USER;

CREATE SEQUENCE SEQ_AF_USER
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_ROLE;

CREATE SEQUENCE SEQ_AF_ROLE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_ORGAN;

CREATE SEQUENCE SEQ_AF_ORGAN
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_GROUP;

CREATE SEQUENCE SEQ_AF_GROUP
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_PRIVILEGE;

CREATE SEQUENCE SEQ_AF_PRIVILEGE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;

DROP SEQUENCE SEQ_AF_RESOURCE;

CREATE SEQUENCE SEQ_AF_RESOURCE
  START WITH 1
  MAXVALUE 9999999999999999999999999999
  MINVALUE 1
  NOCYCLE
  CACHE 20
  ORDER;


drop table AF_GROUP cascade constraints;

drop table AF_ORGAN cascade constraints;

drop table AF_PRIVILEGE cascade constraints;

drop table AF_RESOURCE cascade constraints;

drop table AF_ROLE cascade constraints;

drop table AF_USER cascade constraints;

/*==============================================================*/
/* Table: AF_GROUP                                              */
/*==============================================================*/
create table AF_GROUP 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50),
   TYPE                 VARCHAR2(50),--groupType:用户组、角色组
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_GROUP primary key (ID)
);
alter table AF_GROUP add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_ORGAN                                              */
/*==============================================================*/
create table AF_ORGAN 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--organType:单位、部门
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_ORGAN primary key (ID)
);
alter table AF_ORGAN add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_PRIVILEGE                                          */
/*==============================================================*/
create table AF_PRIVILEGE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--privilegeType:查看、新增、修改、删除、所有权限、无权限
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_PRIVILEGE primary key (ID)
);
alter table AF_PRIVILEGE add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_RESOURCE                                           */
/*==============================================================*/
create table AF_RESOURCE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   TYPE                 VARCHAR2(50),--页面（菜单）、模块、功能、数据（字典项）
   PATH                 VARCHAR2(200),
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_RESOURCE primary key (ID)
);
alter table AF_RESOURCE add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_ROLE                                               */
/*==============================================================*/
create table AF_ROLE 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_ROLE primary key (ID)
);
alter table AF_ROLE add DESCRIPTION VARCHAR2(200);
/*==============================================================*/
/* Table: AF_USER                                               */
/*==============================================================*/
create table AF_USER 
(
   ID                   INTEGER              not null,
   NAME                 VARCHAR2(50) not null,
   LOGIN_NAME           VARCHAR2(50),
   PASSWORD             VARCHAR2(50),
   MOBILE1              NUMBER(11,0),
   MOBILE2              NUMBER(11,0),
   TELEPHONE            VARCHAR2(20),
   EMAIL                VARCHAR2(50),
   GENDER               VARCHAR2(10),
   REMOVED              INTEGER DEFAULT 0  not null,
   constraint PK_AF_USER primary key (ID)
);


insert into AF_OBJ_INFO (id, name, type, params, removed)
values (seq_af_obj_info.nextval, '组信息', 'group', '{"entity":"com.wonders.frame.core.model.bo.Group"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (seq_af_obj_info.nextval, '组织机构信息', 'organ', '{"entity":"com.wonders.frame.core.model.bo.Organ"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (seq_af_obj_info.nextval, '权限信息', 'privilege', '{"entity":"com.wonders.frame.core.model.bo.Privilege"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (seq_af_obj_info.nextval, '资源信息', 'resource', '{"entity":"com.wonders.frame.core.model.bo.Resource"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (seq_af_obj_info.nextval, '用户信息', 'user', '{"entity":"com.wonders.frame.core.model.bo.User"}', 0);
insert into AF_OBJ_INFO (id, name, type, params, removed)
values (seq_af_obj_info.nextval, '角色信息', 'role', '{"entity":"com.wonders.frame.core.model.bo.Role"}', 0);
commit;


delete from af_codes where ccate_id in(select id from af_ccate where type='gender');
delete from af_ccate where type='gender'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'gender','性别','性别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'male','男','男' ,1 from af_ccate where type='gender';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'female','女','女' ,2 from af_ccate where type='gender';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'unknow','未知','未知' ,3 from af_ccate where type='gender';   


delete from af_codes where ccate_id in(select id from af_ccate where type='groupType');
delete from af_ccate where type='groupType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'groupType','组类别','组类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'user','用户组','用户组' ,1 from af_ccate where type='groupType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'role','角色组','角色组' ,2 from af_ccate where type='groupType';   



delete from af_codes where ccate_id in(select id from af_ccate where type='organType');
delete from af_ccate where type='organType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'organType','机构类别','机构类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'unit','单位','单位' ,1 from af_ccate where type='organType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'dept','部门','部门' ,2 from af_ccate where type='organType';   


delete from af_codes where ccate_id in(select id from af_ccate where type='privilegeType');
delete from af_ccate where type='privilegeType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'privilegeType','权限类别','权限类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'view','查看','查看' ,1 from af_ccate where type='privilegeType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'add','新增','新增' ,2 from af_ccate where type='privilegeType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'update','修改','修改' ,3 from af_ccate where type='privilegeType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'del','删除','删除' ,4 from af_ccate where type='privilegeType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'all','所有权限','所有权限' ,5 from af_ccate where type='privilegeType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'none','无权限','无权限' ,6 from af_ccate where type='privilegeType';


delete from af_codes where ccate_id in(select id from af_ccate where type='resourceType');
delete from af_ccate where type='resourceType'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'resourceType','资源类别','资源类别字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'menu','菜单','菜单' ,1 from af_ccate where type='resourceType';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'module','模块','模块' ,2 from af_ccate where type='resourceType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'function','功能','功能' ,3 from af_ccate where type='resourceType';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'data','数据','数据' ,4 from af_ccate where type='resourceType';



insert into af_rule_type(id,name,obj_ids,obj_types,removed) values(seq_af_rule_type.nextval,'acdm','1,2,3,4,5,6','group,organ,privilege,resource,user,role',0);


insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'organ','organ',2,2,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'organ','user',2,5,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'organ','resource',2,4,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'organ','privilege',2,3,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'role','user',6,5,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'role','resource',6,4,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'role','privilege',6,3,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'group','user',1,5,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'group','role',1,6,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'group','resource',1,4,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'group','privilege',1,3,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'user','resource',5,4,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'user','privilege',5,3,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'privilege','resource',3,4,0);
insert into af_rule(id,rule_type_id,p_obj_type,n_obj_type,p_obj_id,n_obj_id,removed) values(seq_af_rule_type.nextval,1,'resource','resource',4,4,0);

--测试数据
insert into af_user(id,name,login_name,password,removed) values(seq_af_user.nextval,'接口测试用户','test','test',0);
insert into af_user(id,name,login_name,password,removed) values(seq_af_user.nextval,'接口测试用户2','test','1111',0);


insert into af_role(id,name,description,removed) values(seq_af_role.nextval,'安检员角色','接口测试角色1',0);
insert into af_role(id,name,description,removed) values(seq_af_role.nextval,'领航员角色','接口测试角色2',0);
insert into af_role(id,name,description,removed) values(seq_af_role.nextval,'排班员角色','接口测试角色3',0);

insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单1','menu','/page/url....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单1-1','menu','/page/url2....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单2','menu','/page/url3....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单3','menu','/page/url4....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单3-1','menu','/page/url5....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单3-2','menu','/page/url6....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'菜单3-4','menu','/page/url7....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'模块1','module','/module/1*....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'模块2','module','/module/2*',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'功能1','function','/f/1*....',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'功能2','function','/f/2*',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'数据','data','{''type'':1}.',0);
insert into af_resource(id,name,type,path,removed) values(seq_af_resource.nextval,'数据2','data','{''p'':''12''}',0);



insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'查看权限1','view',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'查看权限2','view',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'查看权限3','view',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'查看权限4','view',0);

insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'新增权限1','add',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'新增权限2','add',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'新增权限3','add',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'新增权限4','add',0);


insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'修改权限1','update',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'修改权限2','update',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'修改权限3','update',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'修改权限4','update',0);


insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'删除权限1','add',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'删除权限2','add',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'删除权限3','add',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'删除权限4','add',0);

insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'无权限1','none',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'无权限2','none',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'所有权限1','all',0);
insert into af_privilege(id,name,type,removed) values(seq_af_privilege.nextval,'所有权限2','all',0);


insert into af_group(id,name,type,removed) values(seq_af_group.nextval,'角色组1','role',0);
insert into af_group(id,name,type,removed) values(seq_af_group.nextval,'用户组1','user',0);

insert into af_group(id,name,type,removed) values(seq_af_group.nextval,'角色组2','role',0);
insert into af_group(id,name,type,removed) values(seq_af_group.nextval,'用户组2','user',0);
  
  
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'单位1','unit',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门1-1','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门1-2','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门1-1-1','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门1-1-2','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'单位2','unit',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门2-1','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门2-2','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门2-2-1','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'部门2-2-2','dept',0);
insert into af_organ(id,name,type,removed) values(seq_af_organ.nextval,'单位','unit',0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',1,'organ',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',1,'organ',3,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',2,'organ',4,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',2,'organ',5,1,0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',6,'organ',7,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',6,'organ',8,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',8,'organ',9,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',8,'organ',10,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',11,'organ',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',11,'organ',2,1,0);


insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',1,'resource',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',4,'resource',5,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',4,'resource',6,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',4,'resource',7,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',8,'resource',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',8,'resource',3,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',8,'resource',4,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',9,'resource',10,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',9,'resource',11,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',10,'resource',12,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'resource',10,'resource',13,1,0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'privilege',1,'resource',8,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'privilege',5,'resource',9,1,0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'group',1,'role',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'group',1,'role',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'group',1,'resource',10,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'role',2,'user',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'user',1,'privilege',1,1,0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'group',3,'role',3,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'role',3,'resource',1,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'role',3,'user',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'user',2,'privilege',5,1,0);

insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',2,'user',2,1,0);
insert into af_relation(id,p_type,p_id,n_type,n_id,rule_type_id,removed) values(seq_af_relation.nextval,'organ',10,'user',1,1,0);


