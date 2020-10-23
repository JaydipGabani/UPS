select ZONE_DESIGNATION, name from PARKING_LOTS;
select * from PERMIT where PERMIT_ID in (select PERMIT_ID from NON_VISITOR where UNVID = 1006020);
select MODEL, COLOR, YEAR, CAR_MANUFACTURER, VEHICLE_NUMBER from PERMIT where PERMIT_ID in (select PERMIT_ID from NON_VISITOR where UNVID = 1006003);
select SPACE_NUMBER from SPACES where NAME = 'Justice Lot' and DESIGNATED_TYPE = 'electric' and OCCUPIED = 'no';
select * from CITATION where STATUS = 0;
select count(*) from (select distinct p.PERMIT_ID from PERMIT p, NON_VISITOR n where p.PERMIT_ID = n.PERMIT_ID and p.VEHICLE_NUMBER = n.VEHICLE_NUMBER and zone = 'D');

select count(*), name from CITATION where CITATION_DATE >= TO_DATE('2020-07-1','YYYY-MM-DD') and CITATION_DATE <= TO_DATE('2020-09-30','YYYY-MM-DD') group by name;
select count(*), p.SPACE_TYPE from PERMIT p, VISITOR v where p.PERMIT_ID = v.PERMIT_ID and p.VEHICLE_NUMBER = v.VEHICLE_NUMBER and p.START_DATE >= TO_DATE('2020-08-12','YYYY-MM-DD') and p.START_DATE <= TO_DATE('2020-08-20','YYYY-MM-DD') GROUP BY p.SPACE_TYPE;
select sum(FEES), CITATION_DATE from CITATION where CITATION_DATE <= TO_DATE('2020-08-31','YYYY-MM-DD') GROUP BY CITATION_DATE;