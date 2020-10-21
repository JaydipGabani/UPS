-- delete from PARKING_LOTS where NAME = 'Freedom Lot';
-- select * from PARKING_LOTS;
-- INSERT INTO Parking_Lots (zone_designation, address, name, NUMBER_OF_SPACES) values ('A, B, C, D', '2105 Daniel Allen St, NC 27505', 'Freedom Lot', 150);
-- INSERT INTO Parking_Lots (zone_designation, address, name, NUMBER_OF_SPACES) values ('A, B, C, D, AS, BS, CS, DS, V', '2108 McKent St, NC 27507','Premiere Lot', 200);
INSERT INTO Parking_Lots (zone_designation, address, name, NUMBER_OF_SPACES) values ('AS, BS, CS,DS, V' ,'2704 Ben Clark St, NC 26701','Justice Lot 1', 175);
--


-- delete from PERMIT where PERMIT_ID in ('20V0021L', '20V0026P', '20V0015J');
select sum(FEES), CITATION_DATE from CITATION where CITATION_DATE <= TO_DATE('2020-08-31','YYYY-MM-DD') GROUP BY CITATION_DATE;

select count(*), name from CITATION where CITATION_DATE >= TO_DATE('2020-07-1','YYYY-MM-DD') and CITATION_DATE <= TO_DATE('2020-09-30','YYYY-MM-DD') group by name;

select count(*), p.SPACE_TYPE from PERMIT p, VISITOR v where p.PERMIT_ID = v.PERMIT_ID and p.VEHICLE_NUMBER = v.VEHICLE_NUMBER and p.START_DATE >= TO_DATE('2020-08-12','YYYY-MM-DD') and p.START_DATE <= TO_DATE('2020-08-20','YYYY-MM-DD') GROUP BY p.SPACE_TYPE;

select * from NON_VISITOR;

select * from VISITOR;

select * from CITATION;

DELETE from NON_VISITOR where UNVID = '1007999';

select permit_id from Non_Visitor where unvid = '1007999' and S_E = 'E';

select * from SPACES where OCCUPIED = 'yes';

select * from PERMIT;

select * from PARKING_LOTS;

SELECT * FROM Visitor WHERE space_number = '200' and name = 'Premiere Lot' and address = '2108 McKent St, NC 27507' and vehicle_number = 'CDF5731';

CREATE TABLE TEST (sd integer, ed integer, allowed varchar(10));

create or replace trigger tr_test
    before insert on TEST
    for each row
declare n number(5);
begin
    select count(*) into n from TEST where sd = :new.sd;
    if :new.allowed = 'yes' and n >= 2 then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
    if :new.allowed = 'no' and n >= 1  then RAISE_APPLICATION_ERROR(num => -20000, msg => 'You are at capacity to enter vehicles');
    end if;
    --     select :new.sd + 30
--     into :new.ed
--     from dual;
end;

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

create or replace trigger trg_parking_lot
    after insert on PARKING_LOTS
    for each row
begin
    for i IN 1..:new.NUMBER_OF_SPACES loop
            insert into SPACES (SPACE_NUMBER, ZONE, ZONE_DESIGNATION, ADDRESS, NAME) values (i, regexp_substr(:new.zone_designation, '[^,]+', 1, 1), :new.zone_designation, :new.ADDRESS, :new.name);
        end loop;
end;

INSERT INTO Parking_Lots (zone_designation, address, name, NUMBER_OF_SPACES) values ('AS, BS, CS,DS, V' ,'2704 Ben Clark St, NC 26701','Justice Lot 1', 175);

delete from Parking_Lots where NAME = 'Justice Lot 1';
select * from SPACES where  NAME = 'Freedom Lot';

select regexp_substr(val, '[^,]+', 1, 1) from (select ZONE_DESIGNATION as val from PARKING_LOTS where  NAME = 'Justice Lot');

drop trigger trg_ai_citation;
insert into test (sd, ed, allowed) values (3, 1, 'no');

select * from test;
drop table TEST;
select * from NON_VISITOR;

select * from CITATION;
select * from NOTIFICATION;

select PERMIT_ID from PERMIT where VEHICLE_NUMBER = 'TRK1093' and PERMIT_ID = '20V0012B';

INSERT INTO CITATION(CAR_LICENSE_NUMBER, MODEL, COLOR, CITATION_DATE, ZONE_DESIGNATION, ADDRESS, NAME, CITATION_TIME, VIOLATION_CATEGORY, FEES, STATUS) VALUES ('PTL5642',	'Sentra',	'Black',TO_DATE('2020-09-14','YYYY-MM-DD'),'A, B, C, D', '2105 Daniel Allen St, NC 27505',  'Freedom Lot', TO_TIMESTAMP('2020-08-14 10:05:00','YYYY-MM-DD HH24:MI:SS'),	 'No Permit', 40 ,1);
INSERT INTO CITATION(CAR_LICENSE_NUMBER, MODEL, COLOR, CITATION_DATE, ZONE_DESIGNATION, ADDRESS, NAME, CITATION_TIME, VIOLATION_CATEGORY, FEES, DUE, STATUS) VALUES ('TRK1093',	'Rio',	'Blue', TO_DATE('2020-09-21','YYYY-MM-DD'),	 'A, B, C, D, AS, BS, CS, DS, V', '2108 McKent St, NC 27507','Premiere Lot' ,TO_TIMESTAMP('2020-08-14 14:00:00','YYYY-MM-DD HH24:MI:SS'), 'Expired', 	25, TO_DATE('2020-10-20','YYYY-MM-DD') ,0);
-- create or replace trigger ed_dates

select * from DBA_LOCK_INTERNAL;

alter session set ddl_lock_timeout = 0;

commit ;