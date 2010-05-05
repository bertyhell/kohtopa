--**********************************
--remove previous tables
--**********************************
delete from persons cascade;
drop table addresses cascade constraints;
drop table buildings cascade constraints;
drop table constants cascade constraints;
drop table consumption cascade constraints;
drop table contracts cascade constraints;
drop table contract cascade constraints;
drop table furniture cascade constraints;
drop table invoice cascade constraints;
drop table invoices cascade constraints;
drop table messages cascade constraints;
drop table persons cascade constraints;
drop table pictures cascade constraints;
drop table rentables cascade constraints;
drop table role cascade constraints;
drop table tasks cascade constraints;
drop view rentablesView;
drop view personsView;
drop view buildingsView;
drop view addressesView;
drop view constantsView;
drop view consumptionView;
drop view contractsView;
drop view furnitureView;
drop view invoicesView;
drop view invoiceView;
drop view messagesView;
drop view picturesView;
drop view CHANGEPERSONSVIEW;
drop view changePersonsView;

--**********************************
-- creation of the main tables 
--**********************************

--------------------------------------------------------
--  DDL for Table ADDRESSES
--------------------------------------------------------

  CREATE TABLE "ADDRESSES" 
   (	"ADDRESSID" NUMBER(10,0), 
	"STREET_NUMBER" VARCHAR2(5), 
	"STREET" VARCHAR2(50), 
	"ZIPCODE" VARCHAR2(20), 
	"CITY" VARCHAR2(50), 
	"COUNTRY" VARCHAR2(50)
   ) ;
--------------------------------------------------------
--  DDL for Table BUILDINGS
--------------------------------------------------------

  CREATE TABLE "BUILDINGS" 
   (	"BUILDINGID" NUMBER(10,0), 
	"ADDRESSID" NUMBER(10,0), 
	"LATITUDE" NUMBER(10,8), 
	"LONGITUDE" NUMBER(10,8),
  "IPADDRESS" VARCHAR(15)
   ) ;
--------------------------------------------------------
--  DDL for Table CONSTANTS
--------------------------------------------------------

  CREATE TABLE "CONSTANTS" 
   (	"BUILDINGID" NUMBER(10,0), 
	"GASPRICE" FLOAT(5), 
	"WATERPRICE" FLOAT(5), 
	"ELECTRICITYPRICE" FLOAT(5)
   ) ;
--------------------------------------------------------
--  DDL for Table CONSUMPTION
--------------------------------------------------------

  CREATE TABLE "CONSUMPTION" 
   (	"CONSUMPTIONID" NUMBER(10,0), 
	"RENTABLEID" NUMBER(10,0), 
	"GAS" NUMBER(10,0), 
	"WATER" NUMBER(10,0), 
	"ELECTRICITY" NUMBER(10,0), 
	"DATE_CONSUMPTION" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table contracts
--------------------------------------------------------

  CREATE TABLE "CONTRACTS" 
   (	"CONTRACTID" NUMBER(10,0), 
	"RENTABLEID" NUMBER(10,0), 
	"RENTERID" NUMBER(10,0), 
	"CONTRACT_START" DATE, 
	"CONTRACT_END" DATE, 
	"PRICE" FLOAT(5), 
	"MONTHLY_COST" FLOAT(5), 
	"GUARANTEE" FLOAT(5)
   ) ;
--------------------------------------------------------
--  DDL for Table FURNITURE
--------------------------------------------------------

  CREATE TABLE "FURNITURE" 
   (	"FURNITUREID" NUMBER(10,0), 
	"RENTABLEID" NUMBER(10,0), 
	"NAME" VARCHAR2(20), 
	"PRICE" FLOAT(10), 
	"DAMAGED" DATE
   ) ;
--------------------------------------------------------
--  DDL for Table INVOICE
--------------------------------------------------------

  CREATE TABLE "INVOICES" 
   (	
   "INVOICEID" NUMBER(10,0), 
	 "CONTRACTID" NUMBER(10,0), 
	 "INVOICEDATE" DATE, 
	 "PAID" NUMBER(1,0),
   "INVOICE_XML" BLOB,
   "SEND" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table MESSAGES
--------------------------------------------------------

  CREATE TABLE "MESSAGES" 
   (	"SENDERID" NUMBER(10,0), 
	"RECIPIENTID" NUMBER(10,0), 
	"SUBJECT" VARCHAR2(100), 
	"DATE_SENT" TIMESTAMP (6), 
	"MESSAGE_READ" VARCHAR2(1) DEFAULT 0, 
	"TEXT" VARCHAR2(2048)
   ) ;
--------------------------------------------------------
--  DDL for Table PERSONS
--------------------------------------------------------

  CREATE TABLE "PERSONS" 
   (	"PERSONID" NUMBER(10,0), 
	"ADDRESSID" NUMBER(10,0), 
	"ROLEID" VARCHAR2(20), 
	"NAME" VARCHAR2(50), 
	"FIRST_NAME" VARCHAR2(50), 
	"EMAIL" VARCHAR2(50), 
	"TELEPHONE" VARCHAR2(20), 
	"CELLPHONE" VARCHAR2(20), 
	"USERNAME" VARCHAR2(20), 
	"PASSWORD" VARCHAR2(20)
   ) ;
--------------------------------------------------------
--  DDL for Table PICTURES
--------------------------------------------------------

  CREATE TABLE "PICTURES" 
   (	"PICTUREID" NUMBER(10,0), 
	"RENTABLE_BUILDING_ID" NUMBER(10,0), 
	"TYPE_FLOOR" NUMBER(2,0), 
	"PICTURE" BLOB
   ) ;
--------------------------------------------------------
--  DDL for Table RENTABLES
--------------------------------------------------------

  CREATE TABLE "RENTABLES" 
   (	"RENTABLEID" NUMBER(10,0), 
	"BUILDINGID" NUMBER(10,0), 
	"OWNERID" NUMBER(10,0), 
	"TYPE" NUMBER(1,0), 
	"DESCRIPTION" VARCHAR2(50), 
	"AREA" NUMBER(10,0), 
	"WINDOW_DIRECTION" VARCHAR2(2), 
	"WINDOW_AREA" NUMBER(10,0), 
	"INTERNET" NUMBER(1,0), 
	"CABLE" NUMBER(1,0), 
	"OUTLET_COUNT" NUMBER(2,0), 
	"FLOOR" NUMBER(1,0), 
	"RENTED" NUMBER(1,0), 
	"PRICE" FLOAT(5)
   ) ;
--------------------------------------------------------
--  DDL for Table ROLE
--------------------------------------------------------

  CREATE TABLE "ROLE" 
   (	"TYPE" VARCHAR2(20), 
	"ISUSER" NUMBER(1,0), 
	"ISOWNER" NUMBER(1,0), 
	"ISADMIN" NUMBER(1,0)
   ) ;
--------------------------------------------------------
--  DDL for Table TASKS
--------------------------------------------------------

  CREATE TABLE "TASKS" 
   (	"TASKID" NUMBER(10,0), 
	"RENTABLEID" NUMBER(10,0), 
	"DESCRIPTION" VARCHAR2(255), 
	"START_TIME" TIMESTAMP (6), 
	"END_TIME" TIMESTAMP (6), 
	"REPEATS_EVERY" NUMBER(3,0)
   ) ;

--------------------------------------------------------
--  Constraints for Table ADDRESSES
--------------------------------------------------------

  ALTER TABLE "ADDRESSES" MODIFY ("ADDRESSID" NOT NULL DISABLE);
 
  ALTER TABLE "ADDRESSES" MODIFY ("STREET" NOT NULL DISABLE);
 
  ALTER TABLE "ADDRESSES" MODIFY ("ZIPCODE" NOT NULL DISABLE);
 
  ALTER TABLE "ADDRESSES" ADD PRIMARY KEY ("ADDRESSID") ENABLE;
 
  ALTER TABLE "ADDRESSES" ADD CONSTRAINT "UNIQUE_ADDRESS" UNIQUE ("STREET_NUMBER", "STREET", "ZIPCODE", "CITY", "COUNTRY") ENABLE;
--------------------------------------------------------
--  Constraints for Table BUILDINGS
--------------------------------------------------------

  ALTER TABLE "BUILDINGS" MODIFY ("BUILDINGID" NOT NULL DISABLE);
 
  ALTER TABLE "BUILDINGS" MODIFY ("ADDRESSID" NOT NULL DISABLE);
 
  ALTER TABLE "BUILDINGS" ADD PRIMARY KEY ("BUILDINGID") ENABLE;
 
  ALTER TABLE "BUILDINGS" ADD CONSTRAINT "UNIQUE_BUILDING" UNIQUE ("ADDRESSID") ENABLE;
--------------------------------------------------------
--  Constraints for Table CONSTANTS
--------------------------------------------------------

  ALTER TABLE "CONSTANTS" MODIFY ("BUILDINGID" NOT NULL DISABLE);
 
  ALTER TABLE "CONSTANTS" ADD CONSTRAINT "UNIQUE_CONSTANT" UNIQUE ("BUILDINGID") ENABLE;
--------------------------------------------------------
--  Constraints for Table CONSUMPTION
--------------------------------------------------------

  ALTER TABLE "CONSUMPTION" MODIFY ("CONSUMPTIONID" NOT NULL DISABLE);
 
  ALTER TABLE "CONSUMPTION" MODIFY ("RENTABLEID" NOT NULL DISABLE);
 
  ALTER TABLE "CONSUMPTION" ADD PRIMARY KEY ("CONSUMPTIONID") ENABLE;
 
  ALTER TABLE "CONSUMPTION" ADD CONSTRAINT "UNIQUE_CONSUMPTION" UNIQUE ("RENTABLEID", "DATE_CONSUMPTION", "GAS", "WATER", "ELECTRICITY") ENABLE;
--------------------------------------------------------
--  Constraints for Table contracts
--------------------------------------------------------

  ALTER TABLE "CONTRACTS" ADD CONSTRAINT "END_AFTER_START" CHECK (CONTRACT_END > CONTRACT_START) ENABLE;
 
  ALTER TABLE "CONTRACTS" MODIFY ("CONTRACTID" NOT NULL DISABLE);
 
  ALTER TABLE "CONTRACTS" MODIFY ("RENTABLEID" NOT NULL DISABLE);
 
  ALTER TABLE "CONTRACTS" ADD PRIMARY KEY ("CONTRACTID") ENABLE;
 
  ALTER TABLE "CONTRACTS" ADD CONSTRAINT "UNIQUE_CONTRACT" UNIQUE ("RENTABLEID", "CONTRACT_START", "CONTRACT_END") ENABLE;
--------------------------------------------------------
--  Constraints for Table FURNITURE
--------------------------------------------------------

  ALTER TABLE "FURNITURE" MODIFY ("FURNITUREID" NOT NULL DISABLE);
 
  ALTER TABLE "FURNITURE" MODIFY ("RENTABLEID" NOT NULL DISABLE);
 
  ALTER TABLE "FURNITURE" MODIFY ("PRICE" NOT NULL DISABLE);
 
  ALTER TABLE "FURNITURE" ADD PRIMARY KEY ("FURNITUREID") ENABLE;
--------------------------------------------------------
--  Constraints for Table INVOICE
--------------------------------------------------------

  ALTER TABLE "INVOICES" MODIFY ("INVOICEID" NOT NULL DISABLE);
 
  ALTER TABLE "INVOICES" MODIFY ("CONTRACTID" NOT NULL DISABLE);
 
  ALTER TABLE "INVOICES" MODIFY ("INVOICEDATE" NOT NULL DISABLE);
 
  ALTER TABLE "INVOICES" MODIFY ("PAID" NOT NULL DISABLE);
 
  ALTER TABLE "INVOICES" ADD PRIMARY KEY ("INVOICEID") ENABLE;
 
  ALTER TABLE "INVOICES" ADD CONSTRAINT "UNIQUE_INVOICE" UNIQUE ("CONTRACTID", "INVOICEDATE") ENABLE;
--------------------------------------------------------
--  Constraints for Table MESSAGES
--------------------------------------------------------

  ALTER TABLE "MESSAGES" MODIFY ("SENDERID" NOT NULL DISABLE);
 
  ALTER TABLE "MESSAGES" MODIFY ("RECIPIENTID" NOT NULL DISABLE);
 
  ALTER TABLE "MESSAGES" MODIFY ("DATE_SENT" NOT NULL DISABLE);
 
  ALTER TABLE "MESSAGES" ADD PRIMARY KEY ("DATE_SENT") ENABLE;
--------------------------------------------------------
--  Constraints for Table PERSONS
--------------------------------------------------------

  ALTER TABLE "PERSONS" MODIFY ("PERSONID" NOT NULL DISABLE);
 
  ALTER TABLE "PERSONS" MODIFY ("ADDRESSID" NOT NULL DISABLE);
 
  ALTER TABLE "PERSONS" MODIFY ("ROLEID" NOT NULL DISABLE);
 
  ALTER TABLE "PERSONS" MODIFY ("NAME" NOT NULL DISABLE);
 
  ALTER TABLE "PERSONS" MODIFY ("FIRST_NAME" NOT NULL DISABLE);
 
  ALTER TABLE "PERSONS" MODIFY ("TELEPHONE" NOT NULL DISABLE);
 
  ALTER TABLE "PERSONS" ADD PRIMARY KEY ("PERSONID") ENABLE;
 
  ALTER TABLE "PERSONS" ADD CONSTRAINT "UNIQUE_PERSON" UNIQUE ("ADDRESSID", "NAME", "FIRST_NAME", "USERNAME") ENABLE;
 
  ALTER TABLE "PERSONS" ADD CONSTRAINT "UNIQUE_USERNAME" UNIQUE ("USERNAME") ENABLE;
--------------------------------------------------------
--  Constraints for Table PICTURES
--------------------------------------------------------

  ALTER TABLE "PICTURES" MODIFY ("PICTUREID" NOT NULL DISABLE);
 
  ALTER TABLE "PICTURES" MODIFY ("RENTABLE_BUILDING_ID" NOT NULL DISABLE);
 
  ALTER TABLE "PICTURES" ADD PRIMARY KEY ("PICTUREID") ENABLE;
--------------------------------------------------------
--  Constraints for Table RENTABLES
--------------------------------------------------------

  ALTER TABLE "RENTABLES" MODIFY ("RENTABLEID" NOT NULL DISABLE);
 
  ALTER TABLE "RENTABLES" MODIFY ("BUILDINGID" NOT NULL DISABLE);
 
  ALTER TABLE "RENTABLES" MODIFY ("OWNERID" NOT NULL DISABLE);
 
  ALTER TABLE "RENTABLES" MODIFY ("TYPE" NOT NULL DISABLE);
 
  ALTER TABLE "RENTABLES" MODIFY ("AREA" NOT NULL DISABLE);
 
  ALTER TABLE "RENTABLES" ADD PRIMARY KEY ("RENTABLEID") ENABLE;
 
  ALTER TABLE "RENTABLES" ADD CONSTRAINT "UNIQUE_RENTABLE" UNIQUE ("BUILDINGID", "DESCRIPTION", "FLOOR") ENABLE;
--------------------------------------------------------
--  Constraints for Table ROLE
--------------------------------------------------------

  ALTER TABLE "ROLE" ADD CONSTRAINT "OWNER" PRIMARY KEY ("TYPE") ENABLE;
 
  ALTER TABLE "ROLE" MODIFY ("TYPE" NOT NULL DISABLE);
 
  ALTER TABLE "ROLE" MODIFY ("ISUSER" NOT NULL DISABLE);
 
  ALTER TABLE "ROLE" MODIFY ("ISADMIN" NOT NULL DISABLE);
--------------------------------------------------------
--  Constraints for Table TASKS
--------------------------------------------------------

  ALTER TABLE "TASKS" MODIFY ("TASKID" NOT NULL DISABLE);
 
  ALTER TABLE "TASKS" MODIFY ("RENTABLEID" NOT NULL DISABLE);
 
  ALTER TABLE "TASKS" MODIFY ("DESCRIPTION" NOT NULL DISABLE);
 
  ALTER TABLE "TASKS" MODIFY ("START_TIME" NOT NULL DISABLE);
 
  ALTER TABLE "TASKS" MODIFY ("END_TIME" NOT NULL DISABLE);
 
  ALTER TABLE "TASKS" ADD PRIMARY KEY ("TASKID") ENABLE;
 
  ALTER TABLE "TASKS" ADD CONSTRAINT "UNIQUE_TASK" UNIQUE ("RENTABLEID", "DESCRIPTION", "START_TIME", "END_TIME", "REPEATS_EVERY") ENABLE;

--------------------------------------------------------
--  Ref Constraints for Table BUILDINGS
--------------------------------------------------------

  ALTER TABLE "BUILDINGS" ADD CONSTRAINT "FKBUILDINGS277766" FOREIGN KEY ("ADDRESSID")
	  REFERENCES "ADDRESSES" ("ADDRESSID") ON DELETE SET NULL DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONSTANTS
--------------------------------------------------------

  ALTER TABLE "CONSTANTS" ADD CONSTRAINT "FKCONSTANTS623609" FOREIGN KEY ("BUILDINGID")
	  REFERENCES "BUILDINGS" ("BUILDINGID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table CONSUMPTION
--------------------------------------------------------

  ALTER TABLE "CONSUMPTION" ADD CONSTRAINT "FKCONSUMPTIO739054" FOREIGN KEY ("RENTABLEID")
	  REFERENCES "RENTABLES" ("RENTABLEID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table contracts
--------------------------------------------------------

  ALTER TABLE contracts ADD CONSTRAINT "FKCONTRACT294721" FOREIGN KEY ("RENTERID")
	  REFERENCES "PERSONS" ("PERSONID") ON DELETE CASCADE DISABLE;
 
  ALTER TABLE contracts ADD CONSTRAINT "FKCONTRACT9736" FOREIGN KEY ("RENTABLEID")
	  REFERENCES "RENTABLES" ("RENTABLEID") ON DELETE CASCADE DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table FURNITURE
--------------------------------------------------------

  ALTER TABLE "FURNITURE" ADD CONSTRAINT "FKFURNITURE492021" FOREIGN KEY ("RENTABLEID")
	  REFERENCES "RENTABLES" ("RENTABLEID") ON DELETE CASCADE DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table INVOICE
--------------------------------------------------------

  ALTER TABLE "INVOICES" ADD CONSTRAINT "FKINVOICE415428" FOREIGN KEY ("CONTRACTID")
	  REFERENCES "CONTRACTS" ("CONTRACTID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table MESSAGES
--------------------------------------------------------

  ALTER TABLE "MESSAGES" ADD CONSTRAINT "FKMESSAGES594672" FOREIGN KEY ("SENDERID")
	  REFERENCES "PERSONS" ("PERSONID") DISABLE;
 
  ALTER TABLE "MESSAGES" ADD CONSTRAINT "FKMESSAGES630139" FOREIGN KEY ("RECIPIENTID")
	  REFERENCES "PERSONS" ("PERSONID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table PERSONS
--------------------------------------------------------

  ALTER TABLE "PERSONS" ADD CONSTRAINT "FKPERSONS193239" FOREIGN KEY ("ADDRESSID")
	  REFERENCES "ADDRESSES" ("ADDRESSID") ON DELETE SET NULL DISABLE;
 
  ALTER TABLE "PERSONS" ADD CONSTRAINT "FKPERSONS776125" FOREIGN KEY ("ROLEID")
	  REFERENCES "ROLE" ("TYPE") ON DELETE CASCADE DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table PICTURES
--------------------------------------------------------

  ALTER TABLE "PICTURES" ADD CONSTRAINT "FKPICTURES789886" FOREIGN KEY ("RENTABLE_BUILDING_ID")
	  REFERENCES "RENTABLES" ("RENTABLEID") DISABLE;
--------------------------------------------------------
--  Ref Constraints for Table RENTABLES
--------------------------------------------------------

  ALTER TABLE "RENTABLES" ADD CONSTRAINT "FKRENTABLES515648" FOREIGN KEY ("BUILDINGID")
	  REFERENCES "BUILDINGS" ("BUILDINGID") ON DELETE CASCADE DISABLE;
 
  ALTER TABLE "RENTABLES" ADD CONSTRAINT "FKRENTABLES548839" FOREIGN KEY ("OWNERID")
	  REFERENCES "PERSONS" ("PERSONID") DISABLE;

--------------------------------------------------------
--  Ref Constraints for Table TASKS
--------------------------------------------------------

  ALTER TABLE "TASKS" ADD CONSTRAINT "FKTASKS482084" FOREIGN KEY ("RENTABLEID")
	  REFERENCES "RENTABLES" ("RENTABLEID") DISABLE;

--**********************************
-- grant extra priviliges to system
-- needed to create users dynamically
--**********************************
grant create user to system with admin option;
grant drop user to system with admin option;

--**********************************
-- triggers and procedures to create and remove the users dynamically
--**********************************

CREATE OR REPLACE PROCEDURE "SYSTEM"."CREATE_USER" 
( user_name VARCHAR, password VARCHAR )
IS
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  EXECUTE IMMEDIATE 'CREATE USER ' || user_name || ' IDENTIFIED BY "' || password || '"';
  EXECUTE IMMEDIATE 'grant owner_role to ' || user_name;
END;
/

CREATE OR REPLACE PROCEDURE "SYSTEM"."REMOVE_USER" 
( user_name IN VARCHAR2
) AS
PRAGMA AUTONOMOUS_TRANSACTION;
BEGIN
  EXECUTE IMMEDIATE 'drop user ' || user_name || ' cascade';
END REMOVE_USER;
/ 

CREATE OR REPLACE TRIGGER "SYSTEM"."USERCREATIONTRIGGER" 
AFTER INSERT ON PERSONS
FOR EACH ROW 
BEGIN
if :new.roleid = 'owner' then
    create_user(:new.username,:new.password);
end if;
END;
/

ALTER TRIGGER "SYSTEM"."USERCREATIONTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."USERREMOVETRIGGER" 
AFTER DELETE ON PERSONS
FOR EACH ROW 
BEGIN
  if :old.roleid = 'owner' then
    remove_user(:old.username);
end if;
END;
/

ALTER TRIGGER "SYSTEM"."USERREMOVETRIGGER" ENABLE;
  
--**********************************
-- views so the owners only can see what's theirs
--**********************************

CREATE OR REPLACE FORCE VIEW "SYSTEM"."RENTABLESVIEW" ("RENTABLEID", "BUILDINGID", "OWNERID", "TYPE", "DESCRIPTION", "AREA", "WINDOW_DIRECTION", "WINDOW_AREA", "INTERNET", "CABLE", "OUTLET_COUNT", "FLOOR", "RENTED", "PRICE") AS 
  SELECT "RENTABLEID","BUILDINGID","OWNERID","TYPE","DESCRIPTION","AREA","WINDOW_DIRECTION","WINDOW_AREA","INTERNET","CABLE","OUTLET_COUNT","FLOOR","RENTED","PRICE"
FROM rentables
where ownerid = (select personid from persons where UPPER(username) =(select user from dual)); 


CREATE OR REPLACE FORCE VIEW "SYSTEM"."PERSONSVIEW" ("PERSONID", "ADDRESSID", "ROLEID", "NAME", "FIRST_NAME", "EMAIL", "TELEPHONE", "CELLPHONE", "USERNAME", "PASSWORD") AS 
with x as(
select c.renterid
from contracts c
 join rentables r on c.rentableid = r.rentableid    
where r.ownerid = (select personid from persons where (select user from dual) = upper(username))
)
select personid,addressid,roleid,name,first_name,email,telephone,cellphone,username,password
from persons
  join x on renterid = personid or personid = (select personid from persons where (select user from dual) = upper(username))
group by personid,addressid,roleid,name,first_name,email,telephone,cellphone,username,password;
 

CREATE OR REPLACE FORCE VIEW "SYSTEM"."BUILDINGSVIEW" ("BUILDINGID", "ADDRESSID", "LATITUDE", "LONGITUDE", "IPADDRESS") AS 
  SELECT b.buildingid,b.addressid,b.latitude,b.longitude,b.ipaddress
FROM buildings b
 join rentables r on b.buildingid = r.buildingid and ownerid = (select personid from persons where UPPER(username) = (select user from dual));
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."ADDRESSESVIEW" ("ADDRESSID", "STREET_NUMBER", "STREET", "ZIPCODE", "CITY", "COUNTRY") AS 
  with x as(
select c.renterid
from contracts c
 join rentables r on c.rentableid = r.rentableid    
where r.ownerid = (select personid from persons where (select user from dual) = upper(username))
), persons_view as(
select personid,addressid,roleid,name,first_name,email,telephone,cellphone,username,password
from persons
  join x on renterid = personid or personid = (select personid from persons where (select user from dual) = upper(username))
group by personid,addressid,roleid,name,first_name,email,telephone,cellphone,username,password
), buildings_view as(
select b.buildingid,b.addressid,b.latitude,b.longitude,b.ipaddress
from buildings b
 join rentables r on b.buildingid = r.buildingid and ownerid = (select personid from persons where UPPER(username) = (select user from dual))
group by b.buildingid,b.addressid,b.latitude,b.longitude,b.ipaddress
), buildings_and_persons as(
  select b.addressid as building_address, p.addressid as person_address
  from buildings_view b,persons_view p
)
select a.addressid,a.street_number,a.street,a.zipcode,a.city,a.country
from addresses a
 join buildings_and_persons on addressid = building_address or addressid = person_address
group by a.addressid,a.street_number,a.street,a.zipcode,a.city,a.country;
 

CREATE OR REPLACE FORCE VIEW "SYSTEM"."CONSTANTSVIEW" ("BUILDINGID", "GASPRICE", "WATERPRICE", "ELECTRICITYPRICE") AS 
  with x as(
select b.buildingid,b.addressid,b.latitude,b.longitude,b.ipaddress
from buildings b
 join rentables r on b.buildingid = r.buildingid and ownerid = (select personid from persons where UPPER(username) = (select user from dual))
)
select c.buildingid,c.gasprice,c.waterprice,c.electricityprice
from constants c 
   join x  x on x.buildingid = c.buildingid;

CREATE OR REPLACE FORCE VIEW "SYSTEM"."CONSUMPTIONVIEW" ("CONSUMPTIONID", "RENTABLEID", "GAS", "WATER", "ELECTRICITY", "DATE_CONSUMPTION") AS 
  with rentable_view as(
select "RENTABLEID"
from rentables
where ownerid = (select personid from persons where UPPER(username) =(select user from dual))
)
select c.consumptionid,c.rentableid,c.gas,c.water,c.electricity,c.date_consumption
from consumption c
 join rentable_view r on r.rentableid = c.rentableid
group by c.consumptionid,c.rentableid,c.gas,c.water,c.electricity,c.date_consumption;


CREATE OR REPLACE FORCE VIEW "SYSTEM"."CONTRACTSVIEW" ("CONTRACTID", "RENTABLEID", "RENTERID", "CONTRACT_START", "CONTRACT_END", "PRICE", "MONTHLY_COST", "GUARANTEE") AS 
  with rentable_view as(  
select "RENTABLEID"
from rentables
where ownerid = (select personid from persons where UPPER(username) =(select user from dual))
)
select c.contractid,c.rentableid,c.renterid,c.contract_start,c.contract_end,c.price,c.monthly_cost,c.guarantee
from contracts c
   join rentable_view r on r.rentableid = c.rentableid
group by c.contractid,c.rentableid,c.renterid,c.contract_start,c.contract_end,c.price,c.monthly_cost,c.guarantee;



CREATE OR REPLACE FORCE VIEW "SYSTEM"."FURNITUREVIEW" ("FURNITUREID", "RENTABLEID", "NAME", "PRICE", "DAMAGED") AS 
  with rentable_view as(  
select "RENTABLEID"
from rentables
where ownerid = (select personid from persons where UPPER(username) =(select user from dual))
)
select f.furnitureid,f.rentableid,f.name,f.price,f.damaged
from furniture f
  join rentable_view r on r.rentableid = f.rentableid
group by f.furnitureid,f.rentableid,f.name,f.price,f.damaged;
 

CREATE OR REPLACE FORCE VIEW "SYSTEM"."INVOICESVIEW" ("INVOICEID", "CONTRACTID", "INVOICEDATE", "PAID", "INVOICE_XML") AS 
  with rentable_view as(  
select "RENTABLEID"
from rentables
where ownerid = (select personid from persons where UPPER(username) =(select user from dual))
), contracts_view as(
select c.contractid
from contracts c
   join rentable_view r on r.rentableid = c.rentableid
group by c.contractid,c.rentableid,c.renterid,c.contract_start,c.contract_end,c.price,c.monthly_cost,c.guarantee
)
select i.invoiceid,i.contractid,i.invoicedate,i.paid,i.invoice_xml
 from invoices i
join contracts_view c on c.contractid = i.contractid;
 


CREATE OR REPLACE FORCE VIEW "SYSTEM"."PICTURESVIEW" ("PICTUREID", "RENTABLE_BUILDING_ID", "TYPE_FLOOR", "PICTURE") AS 
  with x as(
   select r.rentableid,b.buildingid
   from rentablesview r,buildingsview b
),
y as(
select distinct p.pictureid
from pictures p
   join x x on (p.type_floor < -2  and p.rentable_building_id = x.buildingid) or (p.type_floor >= -2  and p.rentable_building_id = x.rentableid)
)
select p.pictureid,p.rentable_building_id,p.type_floor,p.picture
from pictures p
   join y y on p.pictureid = y.pictureid;
 


CREATE OR REPLACE FORCE VIEW "SYSTEM"."MESSAGESVIEW" ("SENDERID", "RECIPIENTID", "SUBJECT", "DATE_SENT", "MESSAGE_READ", "TEXT") AS 
  with x as(
select c.renterid
from contracts c
 join rentables r on c.rentableid = r.rentableid    
where r.ownerid = (select personid from persons where (select user from dual) = upper(username))
), persons_view as(
select personid
from persons
  join x on renterid = personid or personid = (select personid from persons where (select user from dual) = upper(username))
where roleid = 'owner'
group by personid
)
select m.senderid,m.recipientid,m.subject,m.date_sent,m.message_read,m.text
from messages m
  join persons_view p on p.personid = m.senderid or p.personid = m.recipientid;
 

CREATE OR REPLACE FORCE VIEW "SYSTEM"."TASKSVIEW" ("TASKID", "RENTABLEID", "DESCRIPTION", "START_TIME", "END_TIME", "REPEATS_EVERY") AS 
  select distinct t.taskid, t.rentableid, t.description, t.start_time, t.end_time, t.repeats_every
from tasks t
   join rentablesview r on t.rentableid = t.rentableid;

--**********************************
-- views so the owners only can insert,update what's theirs
--**********************************

CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEPERSONSVIEW" ("PERSONID", "ADDRESSID", "NAME", "FIRST_NAME", "EMAIL", "TELEPHONE", "CELLPHONE", "USERNAME", "PASSWORD") AS 
SELECT "PERSONID","ADDRESSID","NAME","FIRST_NAME","EMAIL","TELEPHONE","CELLPHONE","USERNAME","PASSWORD"
FROM persons;

CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEADDRESSESVIEW" ("ADDRESSID", "STREET_NUMBER", "STREET", "ZIPCODE", "CITY", "COUNTRY") AS 
  select "ADDRESSID","STREET_NUMBER","STREET","ZIPCODE","CITY","COUNTRY" from
addresses;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEBUILDINGSVIEW" ("BUILDINGID", "ADDRESSID", "LATITUDE", "LONGITUDE", "IPADDRESS") AS 
  SELECT 
    "BUILDINGID","ADDRESSID","LATITUDE","LONGITUDE","IPADDRESS"
FROM buildings;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGECONSTANTSVIEW" ("BUILDINGID", "GASPRICE", "WATERPRICE", "ELECTRICITYPRICE") AS 
  SELECT 
    "BUILDINGID","GASPRICE","WATERPRICE","ELECTRICITYPRICE"
FROM constants;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEFURNITUREVIEW" ("FURNITUREID", "RENTABLEID", "NAME", "PRICE", "DAMAGED") AS 
  SELECT 
    "FURNITUREID","RENTABLEID","NAME","PRICE","DAMAGED"
FROM furniture;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEINVOICESVIEW" ("INVOICEID", "CONTRACTID", "INVOICEDATE", "PAID", "INVOICE_XML", "SEND") AS 
  SELECT 
    "INVOICEID","CONTRACTID","INVOICEDATE","PAID","INVOICE_XML","SEND"
FROM
invoices;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEMESSAGESVIEW" ("SENDERID", "RECIPIENTID", "SUBJECT", "DATE_SENT", "MESSAGE_READ", "TEXT") AS 
  SELECT 
    "SENDERID","RECIPIENTID","SUBJECT","DATE_SENT","MESSAGE_READ","TEXT"
FROM messages;

CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGEPICTURESVIEW" ("PICTUREID", "RENTABLE_BUILDING_ID", "TYPE_FLOOR", "PICTURE") AS 
  SELECT 
    "PICTUREID","RENTABLE_BUILDING_ID","TYPE_FLOOR","PICTURE"
FROM
pictures;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGERENTABLESVIEW" ("RENTABLEID", "BUILDINGID", "OWNERID", "TYPE", "DESCRIPTION", "AREA", "WINDOW_DIRECTION", "WINDOW_AREA", "INTERNET", "CABLE", "OUTLET_COUNT", "FLOOR", "RENTED", "PRICE") AS 
  SELECT 
    "RENTABLEID","BUILDINGID","OWNERID","TYPE","DESCRIPTION","AREA","WINDOW_DIRECTION","WINDOW_AREA","INTERNET","CABLE","OUTLET_COUNT","FLOOR","RENTED","PRICE"
FROM
RENTABLES;
 
CREATE OR REPLACE FORCE VIEW "SYSTEM"."CHANGETASKSVIEW" ("TASKID", "RENTABLEID", "DESCRIPTION", "START_TIME", "END_TIME", "REPEATS_EVERY") AS 
  SELECT 
    "TASKID","RENTABLEID","DESCRIPTION","START_TIME","END_TIME","REPEATS_EVERY"
FROM
tasks;
 
--**********************************
-- triggers so the owners can update and add users (not owners)
--**********************************

CREATE OR REPLACE TRIGGER "SYSTEM"."ADDUSERTRIGGER" 
INSTEAD OF INSERT ON CHANGEPERSONSVIEW
BEGIN
  insert into persons values(0,:new.addressid,'user',:new.name,:new.first_name,:new.email,:new.telephone,:new.cellphone,:new.username,:new.password);  
END;
/

ALTER TRIGGER "SYSTEM"."ADDUSERTRIGGER" ENABLE;

  CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATEUSERTRIGGER" 
INSTEAD OF UPDATE ON CHANGEPERSONSVIEW
declare aantal integer;
BEGIN
  select count(1) into aantal from personsview where personid = :new.personid and roleid = 'user'; 
  if aantal = 1 then
    update persons set 
      addressid = :new.addressid,
      roleid = 'user',
      name = :new.addressid,
      first_name = :new.first_name,
      email = :new.email,
      telephone = :new.telephone,
      cellphone = :new.cellphone
      where personid = :new.personid;  
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."UPDATEUSERTRIGGER" ENABLE;

--**********************************
-- triggers so the owners only can insert what's theirs
--**********************************

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTCONSTANTSTRIGGER"
INSTEAD OF INSERT ON CHANGECONSTANTSVIEW
 declare aantal integer;
BEGIN  
  select count(1) into aantal from buildingsview b where b.buildingid = :new.buildingid; 
  if aantal > 0 then
    insert into constants values(:new.buildingid,:new.gasprice,:new.waterprice,:new.electricityprice);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTCONSTANTSTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTFURNITURETRIGGER"
INSTEAD OF INSERT ON CHANGEFURNITUREVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from rentablesview r where r.rentableid = :new.rentableid; 
  if aantal > 0 then
    insert into furniture values(0,:new.rentableid,:new.name,:new.price,:new.damaged);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTFURNITURETRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTINVOICESTRIGGER"
INSTEAD OF INSERT ON CHANGEINVOICESVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from contractsview c where c.contractid = :new.contractid; 
  if aantal > 0 then
    insert into invoices values(0,:new.contractid,:new.invoicedate,:new.paid,:new.invoice_xml,:new.send);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTINVOICESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTMESSAGESTRIGGER"
INSTEAD OF INSERT ON CHANGEMESSAGESVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from personsview p where p.personid = :new.senderid and UPPER(p.username) = (select user from dual); 
  if aantal > 0 then
    insert into messages values(:new.senderid,:new.recipientid,:new.subject,:new.date_sent,:new.message_read,:new.text);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTMESSAGESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTPICTURESTRIGGER"
INSTEAD OF INSERT ON CHANGEPICTURESVIEW
 declare aantal integer;
BEGIN
  if :new.type_floor < -2 then
    select count(1) into aantal from buildingsview b where b.buildingid = :new.rentable_building_id; 
  else
    select count(1) into aantal from rentablesview r where r.rentableid = :new.rentable_building_id; 
  end if;  
  if aantal > 0 then
    insert into pictures values(0,:new.rentable_building_id,:new.type_floor,:new.picture);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTPICTURESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTRENTABLESTRIGGER"
INSTEAD OF INSERT ON CHANGERENTABLESVIEW
 declare aantal integer;
BEGIN  
  select count(1) into aantal from buildingsview b where b.buildingid = :new.buildingid and :new.ownerid = (select personid from personsview pv where pv.roleid='owner');   
  if aantal > 0 then
    insert into rentables values(0,:new.buildingid,:new.ownerid,:new.type,:new.description,:new.area,:new.window_direction,:new.window_area,:new.internet,:new.cable,:new.outlet_count,:new.floor,:new.rented,:new.price);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTRENTABLESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."INSERTTASKSTRIGGER"
INSTEAD OF INSERT ON CHANGETASKSVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from rentablesview r where r.rentableid = :new.rentableid; 
  if aantal > 0 then
    insert into tasks values(0,:new.rentableid,:new.description,:new.start_time,:new.end_time,:new.repeats_every);
  end if;
END;

/
ALTER TRIGGER "SYSTEM"."INSERTTASKSTRIGGER" ENABLE;

--**********************************
-- triggers so the owners only can update what's theirs
--**********************************

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATEADDRESSESTRIGGER"
INSTEAD OF UPDATE ON CHANGEADDRESSESVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from addressesview a where a.addressid = :new.addressid; 
  if aantal > 0 then
    update addresses set
    street_number =  :new.street_number,
    street = :new.street,
    zipcode = :new.zipcode,
    city = :new.city,
    country = :new.country
    where addressid = :new.addressid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATEADDRESSESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATEBUILDINGSTRIGGER"
INSTEAD OF UPDATE ON CHANGEBUILDINGSVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from buildingsview b where b.buildingid = :new.buildingid; 
  if aantal > 0 then
    update buildings set    
    addressid = :new.addressid,
    latitude = :new.latitude,
    longitude = :new.longitude,
    ipaddress = :new.ipaddress
    where buildingid = :new.buildingid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATEBUILDINGSTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATECONSTANTSTRIGGER"
INSTEAD OF UPDATE ON CHANGECONSTANTSVIEW
 declare aantal integer;
BEGIN
  select count(1) into aantal from buildingsview b where b.buildingid = :new.buildingid; 
  if aantal > 0 then
    update constants set        
    gasprice = :new.gasprice,
    waterprice = :new.waterprice,
    electricityprice = :new.electricityprice
    where buildingid = :new.buildingid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATECONSTANTSTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATEFURNITURETRIGGER"
INSTEAD OF UPDATE ON CHANGEFURNITUREVIEW
 declare aantal integer;
 aantal2 integer;
BEGIN
  select count(1) into aantal from furnitureview f where f.furnitureid = :new.furnitureid;   
  select count(1) into aantal2 from rentablesview r where r.rentableid = :new.rentableid;   
  if aantal > 0 and aantal2 > 0 then    
    update furniture set        
    rentableid = :new.rentableid,
    name = :new.name,
    price = :new.price,
    damaged = :new.damaged
    where furnitureid = :new.furnitureid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATEFURNITURETRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATEINVOICESTRIGGER"
INSTEAD OF UPDATE ON CHANGEINVOICESVIEW
 declare aantal integer;
 aantal2 integer;
BEGIN
  select count(1) into aantal from invoicesview i where i.invoiceid = :new.invoiceid; 
  select count(1) into aantal2 from contractsview c where c.contractid = :new.contractid; 
  if aantal > 0 and aantal2 > 0 then
    update invoices set        
    contractid = :new.contractid,
    invoicedate = :new.invoicedate,
    paid = :new.paid,
    invoice_xml = :new.invoice_xml,
    send = :new.send
    where invoiceid = :new.invoiceid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATEINVOICESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATEPICTURESTRIGGER"
INSTEAD OF UPDATE ON CHANGEPICTURESVIEW
 declare aantal integer;
 aantal2 integer; 
BEGIN
  select count(1) into aantal from picturesview p where p.pictureid = :new.pictureid; 
   if :new.type_floor < -2 then
    select count(1) into aantal2 from buildingsview b where b.buildingid = :new.rentable_building_id; 
  else
    select count(1) into aantal2 from rentablesview r where r.rentableid = :new.rentable_building_id; 
  end if;  
  if aantal > 0 and aantal2 > 0 then
    update pictures set        
    rentable_building_id = :new.rentable_building_id,
    type_floor = :new.type_floor,
    picture = :new.picture
    where pictureid = :new.pictureid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATEPICTURESTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATETASKSTRIGGER"
INSTEAD OF UPDATE ON CHANGETASKSVIEW
 declare aantal integer;
 aantal2 integer;
BEGIN
  select count(1) into aantal from tasksview t where t.taskid = :new.taskid; 
  select count(1) into aantal2 from rentablesview r where r.rentableid = :new.rentableid; 
  if aantal > 0 and aantal2 > 0 then
    update tasks set        
    rentableid = :new.rentableid,
    description = :new.description,
    start_time = :new.start_time,
    end_time = :new.end_time,
    repeats_every = :new.repeats_every
    where taskid = :new.taskid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATETASKSTRIGGER" ENABLE;

CREATE OR REPLACE TRIGGER "SYSTEM"."UPDATERENTABLESTRIGGER"
INSTEAD OF UPDATE ON CHANGERENTABLESVIEW
 declare aantal integer;
 aantal2 integer;
 aantal3 integer;
BEGIN
  select count(1) into aantal from rentablesview r where r.rentableid = :new.rentableid; 
  select count(1) into aantal2 from buildingsview b where b.buildingid = :new.buildingid; 
  select count(1) into aantal3 from personsview p where p.personid = :new.ownerid and UPPER(p.username) = (select user from dual); 
  if aantal > 0 and aantal2 > 0  and aantal3 > 0 then
    update rentables set        
    buildingid = :new.rentableid,
    ownerid = :new.ownerid,
    type = :new.type,
    description = :new.description,
    area = :new.area,
    window_direction = :new.window_direction,
    window_area = :new.window_area,
    internet = :new.internet,
    cable = :new.cable,
    outlet_count = :new.outlet_count,
    floor = :new.floor,
    rented = :new.rented,
    price = :new.price
    where rentableid = :new.rentableid;
  end if;
END;
/
ALTER TRIGGER "SYSTEM"."UPDATERENTABLESTRIGGER" ENABLE;

--**********************************
-- creation of the role for the owners
--**********************************
drop role owner_role;
create role owner_role;
grant create session to owner_role;
grant select on rentablesView to owner_role;
grant select on personsView to owner_role;
grant select on buildingsview to owner_role;
grant select on addressesview to owner_role;
grant select on constantsview to owner_role;
grant select on consumptionview to owner_role;
grant select on contractsview to owner_role;
grant select on furnitureview to owner_role;
grant select on invoicesview to owner_role;
grant select on messagesview to owner_role;
grant select on picturesview to owner_role;
grant select on tasksview to owner_role;
grant insert,update on CHANGEPERSONSVIEW to owner_role;
grant insert,update on changeaddressesview to owner_role;
grant insert,update on changebuildingsview to owner_role;
grant insert,update on changeconstantsview to owner_role;
grant insert,update on changefurnitureview to owner_role;
grant insert,update on changeinvoicesview to owner_role;
grant insert on changemessagesview to owner_role; --messages are not meant to be changed
grant insert,update on changepicturesview to owner_role;
grant insert,update on changerentablesview to owner_role;
grant insert,update on changetasksview to owner_role;
 
--**********************************
-- triggers for an automated id creation
--**********************************

  CREATE OR REPLACE TRIGGER "START_AFTER_PREVIOUS" 
BEFORE INSERT OR UPDATE ON CONTRACTS 
FOR EACH ROW 
DECLARE x DATE;
BEGIN  
  select max(contract_end) into x from contracts where rentableId = :NEW.rentableId;
  if x > :New.contract_start then
    RAISE_APPLICATION_ERROR(0,'new contract starts before the previous contract ends');
  end if;
END;
/
ALTER TRIGGER "START_AFTER_PREVIOUS" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDADDRESSES
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDADDRESSES" 
BEFORE INSERT ON ADDRESSES
for each row
declare new_id number(10);
BEGIN
  with x as ( select ADDRESSID, rank() over(order by ADDRESSID)-2147483649 as rn from ADDRESSES),
  y as (select ADDRESSID, case  when rn <> ADDRESSID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.ADDRESSID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDADDRESSES" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDBUILDINGS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDBUILDINGS" 
BEFORE INSERT ON BUILDINGS
for each row
declare new_id number(10);
BEGIN
  with x as ( select BUILDINGID, rank() over(order by BUILDINGID)-2147483649 as rn from BUILDINGS),
  y as (select BUILDINGID, case  when rn <> BUILDINGID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.BUILDINGID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDBUILDINGS" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDCONSUMPTION
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDCONSUMPTION" 
BEFORE INSERT ON CONSUMPTION
for each row
declare new_id number(10);
BEGIN
  with x as ( select CONSUMPTIONID, rank() over(order by CONSUMPTIONID)-2147483649 as rn from CONSUMPTION),
  y as (select CONSUMPTIONID, case  when rn <> CONSUMPTIONID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.CONSUMPTIONID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDCONSUMPTION" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDCONTRACT
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDCONTRACT" 
BEFORE INSERT ON CONTRACTS
for each row
declare new_id number(10);
BEGIN
  with x as ( select CONTRACTID, rank() over(order by CONTRACTID)-2147483649 as rn from CONTRACTS),
  y as (select CONTRACTID, case  when rn <> CONTRACTID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.CONTRACTID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDCONTRACT" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDFURNITURE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDFURNITURE" 
BEFORE INSERT ON FURNITURE
for each row
declare new_id number(10);
BEGIN
  with x as ( select FURNITUREID, rank() over(order by FURNITUREID)-2147483649 as rn from FURNITURE),
  y as (select FURNITUREID, case  when rn <> FURNITUREID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.FURNITUREID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDFURNITURE" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDINVOICE
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDINVOICES" 
BEFORE INSERT ON INVOICES
for each row
declare new_id number(10);
BEGIN
  with x as ( select INVOICEID, rank() over(order by INVOICEID)-2147483649 as rn from INVOICES),
  y as (select INVOICEID, case  when rn <> INVOICEID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.INVOICEID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDINVOICES" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDPERSONS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDPERSONS" 
BEFORE INSERT ON PERSONS
for each row
declare new_id number(10);
BEGIN
  with x as ( select PERSONID, rank() over(order by PERSONID)-2147483649 as rn from PERSONS),
  y as (select PERSONID, case  when rn <> PERSONID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.PERSONID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDPERSONS" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDPICTURES
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDPICTURES" 
BEFORE INSERT ON PICTURES
for each row
declare new_id number(10);
BEGIN
  with x as ( select PICTUREID, rank() over(order by PICTUREID)-2147483649 as rn from PICTURES),
  y as (select PICTUREID, case  when rn <> PICTUREID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.PICTUREID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDPICTURES" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDRENTABLES
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDRENTABLES" 
BEFORE INSERT ON RENTABLES
for each row
declare new_id number(10);
BEGIN
  with x as ( select RENTABLEID, rank() over(order by RENTABLEID)-2147483649 as rn from RENTABLES),
  y as (select RENTABLEID, case  when rn <> RENTABLEID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.RENTABLEID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDRENTABLES" ENABLE;
--------------------------------------------------------
--  DDL for Trigger TRIGGERSETIDTASKS
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "TRIGGERSETIDTASKS" 
BEFORE INSERT ON TASKS
for each row
declare new_id number(10);
BEGIN
  with x as ( select TASKID, rank() over(order by TASKID)-2147483649 as rn from TASKS),
  y as (select TASKID, case  when rn <> TASKID then rn else null end as rn from x)
  select case when min(rn) is null then case when count(1) is null then -2147483648 else count(1) - 2147483648 end else min(rn) end as id into new_id from y;
  :new.TASKID := new_id;
END;
/
ALTER TRIGGER "TRIGGERSETIDTASKS" ENABLE;
