

CREATE TABLE Parking_Lots (zone_designation CHAR(100) NOT NULL, address VARCHAR(50) NOT NULL, name VARCHAR(20) NOT NULL, number_of_spaces NUMBER(10, 0) NOT NULL, PRIMARY KEY (name, zone_designation, address));

CREATE TABLE Spaces(space_number NUMBER(10, 0) NOT NULL, zone VARCHAR(10) NOT NULL, constraint zone_ck check(zone in ('A', 'B', 'C', 'D','S', 'DS','BS', 'AS','V','CS','R')), designated_type VARCHAR(20) DEFAULT  'regular' NOT NULL, constraint designated_type_ct check(designated_type in ('regular', 'electric', 'handicapped')), occupied varchar(3) default 'no' NOT NULL, constraint op_check check (occupied in ('yes', 'no')), zone_designation CHAR(100) NOT NULL, address VARCHAR(50) NOT NULL, name VARCHAR(20) NOT NULL, PRIMARY KEY (name, zone_designation, address, space_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE);

CREATE TABLE Permit (permit_id VARCHAR(8) NOT NULL, zone VARCHAR(10) NOT NULL, constraint zone_ck2 check(zone in ('A', 'B', 'C', 'D','S', 'DS','BS', 'AS','V','CS','R')), start_date DATE default sysdate NOT NULL, space_type VARCHAR(20) default 'regular', constraint space_type_ct check(space_type in ('regular', 'electric', 'handicapped')), expiry_date DATE NOT NULL, constraint ed_check check ( expiry_date <= ADD_MONTHS(start_date, 12) ), constraint ed_check2 check ( expiry_date >= start_date  ), expiry_time TIMESTAMP(2), car_manufacturer VARCHAR(20) NOT NULL, model VARCHAR(10) NOT NULL, year NUMBER(10, 0) NOT NULL, color CHAR(20) NOT NULL, vehicle_number varchar(10) NOT NULL, PRIMARY KEY (permit_id, vehicle_number));

CREATE TABLE Non_Visitor(unvid NUMBER(10, 0) NOT NULL, permit_id varchar(8), vehicle_number varchar(10), S_E varchar(2) default 'S' NOT NULL, constraint se_check check (S_E in ('S', 'E')), PRIMARY KEY(permit_id, vehicle_number), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number) ON DELETE CASCADE);

CREATE TABLE Visitor(permit_id varchar(8) NOT NULL, vehicle_number varchar (10) NOT NULL , Phone_number number(10,0) NOT NULL, zone_designation CHAR(100), address VARCHAR(50), name VARCHAR(20), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE , FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE);

CREATE TABLE Citation (model varchar(10) NOT NULL, color char(20) NOT NULL, citation_time TIMESTAMP(0) NOT NULL, citation_date DATE default SYSDATE NOT NULL, car_license_number VARCHAR(50) NOT NULL, citation_no NUMBER(10, 0) NOT NULL, violation_category VARCHAR(10) NOT NULL, constraint vio_check check(violation_category in ('Invalid', 'Expired', 'No Permit')), fees NUMBER(10, 0) NOT NULL, constraint fees_check check (fees in ('20', '25', '40')), Due DATE NOT NULL, constraint check_due check ( Due = citation_date + 30 ), status NUMBER(1,0) DEFAULT 0 NOT NULL, constraint ck_status check (status in (0,1)),zone_designation CHAR(100) NOT NULL, address VARCHAR(50) NOT NULL, name VARCHAR(20) NOT NULL, PRIMARY KEY (citation_no), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE);

CREATE TABLE Notification (citation_no number(10,0) NOT NULL, NotificationNumber NUMBER(10, 0) NOT NULL, PhoneNumber NUMBER(10, 0), univ NUMBER(10,0), PRIMARY KEY (NotificationNumber), FOREIGN KEY(citation_no) REFERENCES Citation (citation_no) ON DELETE CASCADE);

CREATE SEQUENCE Citation_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE Notification_seq START WITH 1 INCREMENT BY 1;

-- LIMIT THE NUMBER OF VEHICLES PER PERMIT FOR STUDENTS AND EMPLOYEE ACCORDINGLY FOR PERMIT
create or replace trigger trg_permit
    before insert on PERMIT
    for each row
declare n varchar(5);
begin
    select count(*) into n from PERMIT where PERMIT.PERMIT_ID = :new.permit_id and zone = :new.zone;
    if :new.zone = 'S' and n >= 1 then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
    if :new.zone = 'V' and n >= 1 then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
    if n >= 2 then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
end;

-- LIMIT THE NUMBER OF VEHICLES PER PERMIT FOR STUDENTS AND EMPLOYEE ACCORDINGLY FOR NON_VISITOR
create or replace trigger trg_non_visitor
    before insert on NON_VISITOR
    for each row
declare n number(5);
begin
    select count(*) into n from NON_VISITOR where PERMIT_ID = :new.permit_id;
    if :new.S_E = 'E' and n >= 2 then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
    if :new.S_E = 'S' and n >= 1 then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
end;

-- AUTO INCREMENT CITATION NUMBER AND SET DUE DATE TO 30 DAYS FROM CITATION DATE
create or replace trigger trg_citation
    before insert on Citation
    for each row
begin
    select Citation_seq.nextval
    into :new.citation_no
    from dual;
    select :new.citation_date + 30
    into :new.Due
    from dual;
end;

-- AUTO INCREMENT NOTIFICATION NUMBER
create trigger trg_Notification
 before insert on Notification
   for each row
  begin
    select Notification_seq.nextval
      into :new.NotificationNumber
      from dual;
  end;

-- AUTO INSERT IN NOTIFICATION AFTER CITATION IS GENERATED
create or replace trigger trg_ai_citation
    after insert on CITATION
    for each row
declare
    phone number(10);
    univid number(10);
    n number(10);
begin
    select count(*) into n from NON_VISITOR where VEHICLE_NUMBER = :new.CAR_LICENSE_NUMBER;
    if n > 0 then
        select UNVID into univid from NON_VISITOR where VEHICLE_NUMBER = :new.CAR_LICENSE_NUMBER and ROWNUM <= 1;
        insert into NOTIFICATION (CITATION_NO, NOTIFICATIONNUMBER, UNIV) values (CITATION_SEQ.currval, CITATION_SEQ.currval, univid);
    end if;
    select count(*) into n from VISITOR where VEHICLE_NUMBER = :new.CAR_LICENSE_NUMBER;
    if n > 0 then
        select PHONE_NUMBER into phone from VISITOR where VEHICLE_NUMBER = :new.CAR_LICENSE_NUMBER and ROWNUM <= 1;
        insert into NOTIFICATION (CITATION_NO, NOTIFICATIONNUMBER, PHONENUMBER) values (CITATION_SEQ.currval, CITATION_SEQ.currval, phone);
    end if;
    select count(*) into n from PERMIT where VEHICLE_NUMBER = :new.CAR_LICENSE_NUMBER;
    if n = 0 then
        insert into NOTIFICATION (CITATION_NO, NOTIFICATIONNUMBER) values (CITATION_SEQ.currval, CITATION_SEQ.currval);
    end if;
end;

-- AUTO INSERT IN SPACES WITH FIRST ZONE DESIGNATION AS DEFAULT ZONE
-- create or replace trigger trg_parking_lot
--     after insert on PARKING_LOTS
--     for each row
-- begin
--     for i IN 1..:new.NUMBER_OF_SPACES loop
--             insert into SPACES (SPACE_NUMBER, ZONE, ZONE_DESIGNATION, ADDRESS, NAME) values (i, regexp_substr(:new.zone_designation, '[^,]+', 1, 1), :new.zone_designation, :new.ADDRESS, :new.name);
--         end loop;
-- end;


-- drop sequence Citation_seq;
-- drop sequence Notification_seq;
-- CREATE TABLE Vehicle (car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number NUMBER(10, 0), PRIMARY KEY (vehicle_number) ON DELETE CASCADE);

-- Trigger for citation due date: TODO
-- create or replace trigger citation_due_date
--  before insert on citation
--  for each row
-- begin
--     update productlist
--        set OrdDateDelivery = :new.dateOrdRecieved + 14
--      where ordid = :new.ordid;
-- end;
-- /
-- 


-- drop table Notification;
-- drop table Non_visitor;
-- drop table Visitor;
-- drop table Citation;
-- drop table Permit;
-- drop table Spaces;
-- drop table Parking_Lots;
-- drop sequence CITATION_SEQ;
-- drop sequence Notification_seq;
--
-- SELECT owner, table_name FROM all_tables;
-- SELECT owner, trigger_name from ALL_TRIGGERS;
