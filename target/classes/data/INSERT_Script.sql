-------------------------------------------
---------------------------------------------

insert into `autoDB`.`COLOURS` (colour,CCODE) value ('BLACK CHERRY','G8 METALLIC');
insert into `autoDB`.`COLOURS` (colour,CCODE) value ('PETROL BLUE','LM5V METALLIC');


insert into `autoDB`.`FUELS` (fdesc,fuel) value ('DIESEL REGULAR','DIESEL-REG');
insert into `autoDB`.`FUELS` (fdesc,fuel) value ('DIESEL-ULTRA LOW SULFUR','DIESEL-ULS');
insert into `autoDB`.`FUELS` (fuel) value ('GASOLINE');

insert into `autoDB`.`MSR` (MSR,MDESC) value ('M','METER');
insert into `autoDB`.`MSR` (MSR,MDESC)  value ('CM','CENTIMETER');
insert into `autoDB`.`MSR` (MSR,MDESC)  value ('IN','INCH');
insert into `autoDB`.`MSR` (MSR,MDESC)  value ('MM','MILLIMETER');
insert into `autoDB`.`MSR`(MSR,MDESC)  value ('U','UNITS');


insert into `autoDB`.`ECONFIG` (config) value ('I6');
insert into `autoDB`.`ECONFIG` (config) value ('I4');
insert into `autoDB`.`ECONFIG` (config) value ('V6');
insert into `autoDB`.`ECONFIG` (config) value ('V8');
insert into `autoDB`.`ECONFIG` (config) value ('V10');

INSERT INTO USTATUS( status) values ('INACTIVE');
INSERT INTO USTATUS( status) values ('ACTIVE');

INSERT INTO SSTATUS( status) values ('PENDING');
INSERT INTO SSTATUS( status) values ('STARTED');
INSERT INTO SSTATUS( status) values ('COMPLETED');
INSERT INTO SSTATUS( status,description) values ('INACTIVE','INACTIVE STATUS');

INSERT INTO ROLE( role_desc ) values ('ADMIN');
INSERT INTO ROLE( role_desc ) values ('USER');

INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(1,'GENERAL MOTORS','GM');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(2,'FORD MOTOR COMPANY','FORD');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(3,'VOLKSWAGEN GMBH','VW');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(4,'AUDI AG','AUDI');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(5,'BAYERISCHE MOTOREN WERKE AG','BMW');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(6,'DAIMLER AG','DAIMLER');					--
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(7,'HYUNDAI MOTOR COMPANY','HYUNDAI');--
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(8,'TOYOTA MOTOR CORPORATION','TOYOTA');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(9,'NISSAN MOTOR COMPANY','NISSAN');	
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(10,'HONDA MOTOR COMPANY','HONDA');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(11,'MAZDA MOTOR CORPORATION','MAZDA');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(12,'SUZUKI MOTOR CORPORATION','SUZUKI');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(13,'FIAT CHRYSLER AUTOMOBILES','FIAT');
INSERT INTO MAKE (ID,LONGNAME, SHORTNAME) VALUES(14,'CHRYSLER MOTORS','CHRYSLER');

INSERT INTO COUNTRY( name,a2code,a3code ) values ('CANADA','CA','CAN');
INSERT INTO COUNTRY( name,a3code,a2code ) values ('United States of America','USA','US');
INSERT INTO COUNTRY( name,a3code,a2code ) values ('MEXICO','MEX','MX');
INSERT INTO COUNTRY( name,a3code,a2code ) values ('GHANA','GHA','GH');
INSERT INTO COUNTRY( name,a3code,a2code ) values ('FRANCE','FRA','FR');
INSERT INTO COUNTRY( name,a3code,a2code ) values ('AUSTRALIA','AUS','AU');

INSERT INTO autodb.bodytype(btype) VALUES ('2-DOOR');
INSERT INTO autodb.bodytype(btype) VALUES ('3-DOOR');
INSERT INTO autodb.bodytype(btype) VALUES ('4-DOOR');
INSERT INTO autodb.bodytype(btype) VALUES ('5-DOOR');
INSERT INTO autodb.bodytype(btype) VALUES ('6-DOOR');

insert into region (name,country_id,abbrev2l) values('ALBERTA',(select c.id from COUNTRY c WHERE c.name='CANADA'),'AB');
insert into region (name,country_id,abbrev2l) values('BRITISH Columbia',(select c.id from COUNTRY c WHERE c.name='CANADA'),'BC');
insert into region (name,country_id,abbrev2l) values('MANITOBA',(select c.id from COUNTRY c WHERE c.name='CANADA'),'MB');
insert into region (name,country_id,abbrev2l) values('ONTARIO',(select c.id from COUNTRY c WHERE c.name='CANADA'),'ON');
insert into region (name,country_id,abbrev2l) values('QUEBEC',(select c.id from COUNTRY c WHERE c.name='CANADA'),'QC');
insert into region (name,country_id,abbrev2l) values('NEWFOUNDLAND AND LABRADOR',(select c.id from COUNTRY c WHERE c.name='CANADA'),'NL');
insert into region (name,country_id,abbrev2l) values('NEW BRUNSWICK',(select c.id from COUNTRY c WHERE c.name='CANADA'),'NB');
insert into region (name,country_id,abbrev2l) values('NORTHWEST TERRITORIES',(select c.id from COUNTRY c WHERE c.name='CANADA'),'NT');
insert into region (name,country_id,abbrev2l) values('NOVA SCOTIA',(select c.id from COUNTRY c WHERE c.name='CANADA'),'NS');
insert into region (name,country_id,abbrev2l) values('NUNAVUT',(select c.id from COUNTRY c WHERE c.name='CANADA'),'NU');
insert into region (name,country_id,abbrev2l) values('PRINCE EDWARD ISLAND',(select c.id from COUNTRY c WHERE c.name='CANADA'),'PE');
insert into region (name,country_id,abbrev2l) values('SASKATCHEWAN',(select c.id from COUNTRY c WHERE c.name='CANADA'),'SK');
insert into region (name,country_id,abbrev2l) values('YUKON',(select c.id from COUNTRY c WHERE c.name='CANADA'),'YT');

---================
INSERT INTO `autoDB`.`DRIVETYPE` (dtype) VALUES ('ALL-WHEEL'); 
INSERT INTO `autoDB`.`DRIVETYPE` (dtype) VALUES ('FRT-WHEEL'); 
INSERT INTO `autoDB`.`DRIVETYPE` (dtype) VALUES ('REAR-WHEEL');     
       
--=====================
INSERT INTO autodb.TRIM(level) VALUES ('PICK-UP');
INSERT INTO autodb.TRIM(level) VALUES ('COUPE');
INSERT INTO autodb.TRIM(level) VALUES ('CONVERTIBLE');
INSERT INTO autodb.TRIM(level) VALUES ('WAGON');
INSERT INTO autodb.TRIM(level) VALUES ('HATCHBACK');
INSERT INTO autodb.TRIM(level) VALUES ('SEDAN');
INSERT INTO autodb.TRIM(level) VALUES ('STATION WAGON');
INSERT INTO autodb.TRIM(level) VALUES ('SUV');
INSERT INTO autodb.TRIM(level) VALUES ('CROSSOVER');
INSERT INTO autodb.TRIM(level) VALUES ('VAN');
INSERT INTO autodb.TRIM(level) VALUES ('MINIVAN');
--========================

INSERT INTO `autoDB`.`MODELS` (NAME,ref) VALUES ('GOLF','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('FIESTA','FORD'); 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('JETTA','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('PASSSAT','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ESCORT','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('F150','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('F250','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('F350','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('COROLLA','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CAMRY','TOYOTA'); 

INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('GENESIS','HYUNDAI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ELANTRA','HYUNDAI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SONATA','HYUNDAI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ACCENT','HYUNDAI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('AZERA','HYUNDAI');

INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SLS','DAIMLER');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CLA','DAIMLER');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('GLA','DAIMLER');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('C63','DAIMLER');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('GLK','DAIMLER');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('A4','AUDI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('A8','AUDI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('A3','AUDI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('A6','AUDI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('Q7','AUDI');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('Q5','AUDI');
 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('M3','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('X3','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('Z4','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('M5','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('X5','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('3','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('5','BMW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('7','BMW');
 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('MICRA','NISSAN');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('VERSA','NISSAN');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SENTRA','NISSAN');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('JUKE','NISSAN');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ALTIMA','NISSAN');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ROGUE','NISSAN');

INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('NIRO','KIA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SPORTAGE','KIA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SORENTO','KIA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('OPTIMA','KIA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('BEETLE','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CC','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('TIGUAN','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('TOUAREG','VW');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ROUTAN','VW');
 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('FOCUS','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('EXPLORER','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('GT40','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('TAURUS','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('RANGER','FORD');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('2000GT','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('TUNDRA','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('RAV4','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('MASTERACE','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SIENNA','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('FJ CRUISER','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CAMRY HYBRID','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('YARIS','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('TACOMA','TOYOTA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('PRIUS','TOYOTA');

INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ILX','ACURA'); 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('MDX','ACURA'); 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('NSX','ACURA'); 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('RDX','ACURA'); 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('RLX','ACURA'); 
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('TLX','ACURA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ACCORD','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CR-V','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CIVIC','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CIVIC SI','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CIVIC TYPE R','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CLARITY','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ODYSSEY','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('PILOT','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('FIT','HONDA');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('ATS COUPE','CADILLAC');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('SPORT TOURING','BUICK');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('REGAL','BUICK');
INSERT INTO `autoDB`.`MODELS` ( NAME, REF) VALUES ('CRUZE','CHEVROLET');

------==========================================================
--		SEED INPUTS
INSERT INTO `autoDB`.`ADDRESS` (REGION_ID,mailcode,city,other,street) VALUES((select R.id from REGION R WHERE R.name='ONTARIO'),'M92 2W3','DUMMY','DUMP','225 Dummy Road');
INSERT INTO `autoDB`.`OWNER` (address_ID, PHONE, lastname, firstname) VALUES((select a.id from Address a WHERE a.city='DUMMY' AND a.other='DUMP'),'99999999','ASKSEF', 'ASKSEF');
INSERT INTO `autoDB`.`SOURCE` (name, ADDRESS_ID, phone) VALUES ('ASKSEF', (select a.id from Address a WHERE a.city='DUMMY' AND a.other='DUMP'), '999000999');
INSERT INTO `autoDB`.`CATEGORY` (description) VALUES ('GENERAL');
INSERT INTO `autoDB`.`CATEGORY` (description) VALUES ('TUNE-UP');
INSERT INTO `autoDB`.`CATEGORY` (description) VALUES ('EXHAUST');
INSERT INTO `autoDB`.`INVENTORY` (partnr,description,  redist, quantity, CATEGORY_ID) VALUES ('GEN99999', 'DUMMY', 999999, 999, (select c.id from CATEGORY c WHERE c.description='GENERAL'));
insert into `autoDB`.`INVENTORY_SOURCE` (SOURCE_ID, INVENTORY_ID) VALUES ( (select s.id from SOURCE s WHERE s.name='ASKSEF'), (select n.id from INVENTORY n WHERE n.partnr='GEN99999') );

--Test INSERT
INSERT INTO `autoDB`.`INVENTORY` (partnr,description,costprice,redist,quantity,CATEGORY_ID) VALUES ('089IOP', 'BRAKE PADS', 124.25, 36000, 17, (select c.id from CATEGORY c WHERE c.description='GENERAL'));
insert into `autoDB`.`INVENTORY_SOURCE` (SOURCE_ID, INVENTORY_ID) VALUES ( (select s.id from SOURCE s WHERE s.name='ASKSEF'), (select n.id from INVENTORY n WHERE n.partnr='089iop') );

INSERT INTO `autoDB`.`INVENTORY` (partnr,description,costprice,redist,quantity,CATEGORY_ID) VALUES ('X893W2', 'OIL FILTER', 89.26, 12000, 23, (select c.id from CATEGORY c WHERE c.description='TUNE-UP'));
insert into `autoDB`.`INVENTORY_SOURCE` (SOURCE_ID, INVENTORY_ID) VALUES ( (select s.id from SOURCE s WHERE s.name='ASKSEF'), (select n.id from INVENTORY n WHERE n.partnr='x893w2') );

INSERT INTO `autoDB`.`INVENTORY` (partnr,description,costprice,redist,quantity,CATEGORY_ID) VALUES ('OIKUIS', 'MUFFLER MOUNTING BRACKETS', 12.59, 6000, 21, (select c.id from CATEGORY c WHERE c.description='EXHAUST'));
insert into `autoDB`.`INVENTORY_SOURCE` (SOURCE_ID, INVENTORY_ID) VALUES ( (select s.id from SOURCE s WHERE s.name='ASKSEF'), (select n.id from INVENTORY n WHERE n.partnr='OIKUIS') );