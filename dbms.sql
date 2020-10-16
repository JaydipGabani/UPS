

CREATE TABLE Parking_Lots (zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20) UNIQUE, number_of_spaces NUMBER(10, 0), PRIMARY KEY (name, zone_designation, address));

CREATE TABLE Spaces(space_number NUMBER(10, 0) NOT NULL, zone VARCHAR(10) NOT NULL, constraint zone_ck check(zone in ('A', 'B', 'C', 'S', 'BS', 'AS','V','CS','R')), designated_type VARCHAR(10) DEFAULT  'regular' NOT NULL, constraint designated_type_ct check(designated_type in ('regular', 'electric', 'handi')), occupied varchar(3) default 'no', constraint op_check check (occupied in ('yes', 'no')), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (name, zone_designation, address, space_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE);

CREATE TABLE Permit (permit_id VARCHAR(8), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(2), car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number varchar(10), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (permit_id, vehicle_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE);

CREATE TABLE Non_Visitor(unvid NUMBER(10, 0), permit_id varchar(8), vehicle_number varchar(10), S_E varchar(2) default 'S' NOT NULL, constraint se_check check (S_E in ('S', 'E')), PRIMARY KEY(permit_id, vehicle_number), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number) ON DELETE CASCADE);

CREATE TABLE Visitor(permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0) NOT NULL, zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE);

CREATE TABLE Citation (model varchar(10), color char(20), citation_time TIMESTAMP(0), citation_date DATE, car_license_nunber VARCHAR(50), citation_no NUMBER(10, 0) NOT NULL, violation_category VARCHAR(10), constraint vio_check check(violation_category in ('Invalid', 'Expired', 'No Permit')), fees NUMBER(10, 0) NOT NULL, constraint fees_check check (fees in ('20', '25', '40')), Due DATE, status NUMBER(1,0) DEFAULT 0, zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (citation_no), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE);

CREATE TABLE Notification (citation_no number(10,0) NOT NULL, NotificationNumber NUMBER(10, 0) NOT NULL, PhoneNumber NUMBER(10, 0), univ NUMBER(10,0), PRIMARY KEY (NotificationNumber), FOREIGN KEY(citation_no) REFERENCES Citation (citation_no) ON DELETE CASCADE);

CREATE SEQUENCE Citation_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE Notification_seq START WITH 1 INCREMENT BY 1;

-- Trigger for citation_no
create trigger trg_citation
 before insert on Citation
   for each row
  begin
    select Citation_seq.nextval
      into :new.citation_no
      from dual;
  end;
 /
-- Trigger for notification_no
create trigger trg_Notification
 before insert on Notification
   for each row
  begin
    select Notification_seq.nextval
      into :new.NotificationNumber
      from dual;
  end;
 /

drop sequence Citaion_seq;
drop sequence Notification_seq;
-- CREATE TABLE Vehicle (car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number NUMBER(10, 0), PRIMARY KEY (vehicle_number) ON DELETE CASCADE);

-- Trigger for citation due date: TODO
create or replace trigger citation_due_date
 before insert on citation
 for each row
begin
    update productlist
       set OrdDateDelivery = :new.dateOrdRecieved + 14
     where ordid = :new.ordid;
end;
/
-- 

INSERT INTO Parking_Lots (zone_designation, address, name) values ('A/B/C', '123 test', 'test');

INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(1, 'A', 'regular', 'A/B/C', '123 test', 'test');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(2, 'A', 'regular', 'A/B/C', '123 test', 'test');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(3, 'A', 'regular', 'A/B/C', '123 test', 'test');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(4, 'A', 'regular', 'A/B/C', '123 test', 'test');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(5, 'A', 'regular', 'A/B/C', '123 test', 'test');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(6, 'A', 'regular', 'A/B/C', '123 test', 'test');

INSERT INTO Parking_Lots (zone_designation, address, name) values ('A/B/V', '124 test', 'test1');

INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(1, 'V', 'regular', 'A/B/V', '124 test', 'test1');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(2, 'V', 'regular', 'A/B/V', '124 test', 'test1');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(3, 'V', 'regular', 'A/B/V', '124 test', 'test1');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(4, 'V', 'regular', 'A/B/V', '124 test', 'test1');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(5, 'V', 'regular', 'A/B/V', '124 test', 'test1');

INSERT INTO Parking_Lots (zone_designation, address, name) values ('A/S/V', '125 test', 'test3');

INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(1, 'S', 'regular', 'A/S/V', '125 test', 'test3');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(2, 'S', 'regular', 'A/S/V', '125 test', 'test3');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(3, 'S', 'regular', 'A/S/V', '125 test', 'test3');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(4, 'S', 'regular', 'A/S/V', '125 test', 'test3');
INSERT INTO Spaces (space_number, zone, designated_type, zone_designation, address, name) values(5, 'S', 'regular', 'A/S/V', '125 test', 'test3');

-- permit entry for emploee
INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12345', 'A', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','1234','A/B/C', '123 test', 'test');

INSERT INTO Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('1234', '20A12345', '1234', 'E');

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12346', 'B', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','1235','A/B/C', '123 test', 'test');

INSERT INTO Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('1235', '20A12346', '1235', 'E');

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12347', 'C', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','1236','A/B/C', '123 test', 'test');

INSERT INTO Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('1236', '20A12347', '1236', 'E');

-- permit entr for students

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12311', 'A', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','1237','A/S/V', '125 test', 'test3');

INSERT INTO Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('1237', '20A12311', '1237', 'S');

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12312', 'B', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','1238','A/S/V', '125 test', 'test3');

INSERT INTO Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('1238', '20A12312', '1238', 'S');

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12313', 'C', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','1239','A/S/V', '125 test', 'test3');

INSERT INTO Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('1239', '20A12313', '1239', 'S');

-- permit entry for Visitor

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12321', 'A', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','2323','A/B/V', '124 test', 'test1');

INSERT INTO Visitor (Phone_number, permit_id, vehicle_number, space_number, zone_designation, address, name) values('1341432134', '20A12321', '2323', '1', 'A/B/V', '124 test', 'test1');

Update Spaces set occupied = 'yes' where name = 'test1' and address = '124 test' and zone_designation = 'A/B/V' and space_number = '1';

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12322', 'B', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','2324','A/B/V', '124 test', 'test1');

INSERT INTO Visitor (Phone_number, permit_id, vehicle_number, space_number, zone_designation, address, name) values('45141124', '20A12322', '2324', '2', 'A/B/V', '124 test', 'test1');

Update Spaces set occupied = 'yes' where name = 'test1' and address = '124 test' and zone_designation = 'A/B/V' and space_number = '2';

INSERT INTO Permit (permit_id, zone, start_date, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values ('20A12323', 'C', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2021-10-15 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), 'tesla', 'S', '2020','black','2325','A/B/V', '124 test', 'test1');

INSERT INTO Visitor (Phone_number, permit_id, vehicle_number, space_number, zone_designation, address, name) values('23452134', '20A12323', '2325', '3', 'A/B/V', '124 test', 'test1');

Update Spaces set occupied = 'yes' where name = 'test1' and address = '124 test' and zone_designation = 'A/B/V' and space_number = '3';

select * from Spaces where occupied = 'yes';


-- Issue citation entry

INSERT INTO Citation (model, color, citation_time , citation_date , car_license_nunber , violation_category, due, fees , zone_designation , address , name) values ('S', 'black', TO_DATE('2020-10-16','YYYY-MM-DD'), TO_TIMESTAMP('2020-10-16 23:59:59', 'YYYY-MM-DD HH24:MI:SS'), '2325','Expired', TO_DATE('2020-11-16','YYYY-MM-DD'),'25', 'A/B/V', '124 test', 'test1');

INSERT INTO Notification (citation_no ,PhoneNumber) values ('1', '23452134');


select * from Parking_Lots;


drop table Notification;
drop table Non_visitor;
drop table Visitor;
drop table Citation;
drop table Permit;
drop table Spaces;
drop table Parking_Lots;
drop sequence Citaion_seq;

SELECT owner, table_name FROM all_tables;
SELECT owner, trigger_name from ALL_TRIGGERS;