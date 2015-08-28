drop sequence SEQ_AF_TEST;


drop table AF_TEST cascade constraints;


create sequence SEQ_AF_TEST
increment by 1
start with 1
 maxvalue 9999999999999999999999999999
 minvalue 1
nocycle
 cache 20
order;

create table AF_TEST 
(
   	ID                  	INTEGER not null,   
	F_STR			VARCHAR2(200) ,--字符型
	F_CLOB      			CLOB,	--大字段型

	F_CODE      			VARCHAR2(50),	--字典项,testCode
   	F_NUM      		NUMBER(12,2),	--数值   	
	F_DATE      	DATE,	--日期型
   	F_DATETIME        		DATE,   		--日期时间型
   	REMOVED             	INTEGER default 0  not null,--逻辑删除标志位
   	constraint PK_AF_TEST primary key (ID)
);

insert into af_obj_info(id,name,type,params,removed) select seq_af_obj_info.nextval,'测试','test','{"entity":"com.wonders.frame.test.model.bo.Test"}',0 from dual;

delete from af_codes where ccate_id in(select id from af_ccate where type='testCode');
delete from af_ccate where type='testCode'; 
insert into af_ccate(id,type,name,description) select seq_af_ccate.nextval,'testCode','测试','测试字典类' from dual;
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'test1','选项1','选项1' ,1 from af_ccate where type='testCode';
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'test2','选项2','选项2' ,2 from af_ccate where type='testCode';   
insert into af_codes(id,ccate_id,code,display,description,orders) select seq_af_codes.nextval,id,'test3','选项3','选项3' ,3 from af_ccate where type='testCode';   
