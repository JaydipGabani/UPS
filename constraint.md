Constraint implemented as part of table definitions
- Constraints for columns per table:
  - Parking_Lots:
    - Primary key: name, zone_designation, address
    - Not NULL: name, zone_designation, address, number_of_spaces
  - Spaces:
    - Primary key: name, zone_designation, address, space_number
    - Foreign key: REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE
    - Not NULL: name, zone_designation, address, space_number, zone, designated_type, occupied
    - Default value:
      - Occupied: no
      - designated_type: regular
      - zone: 'A', 'B', 'C', 'D','S', 'DS','BS', 'AS','V','CS','R'
    - Allowed value:
      - Occupied: yes, no
      - designated_type: regular, electric, handicapped
  - Permit:
    - Primary key: permit_id, vehicle_number
    - Default value:
      - start_date: SYSDATE
      - space_type: regular
    - Not NULL: permit_id, vehicle_number, zone, start_date, space_type, expiry_date, car_manufacturer, mode, year, color
    - Allowed values:
      - space_type: regular, electric, handicapped
      - zone: 'A', 'B', 'C', 'D','S', 'DS','BS', 'AS','V','CS','R'
    - Addition constraints:
      - expiry_date cannot exceed start_date + one year
      - expiry_date cannot be less than start_date
  - Non_Visitor:
    - Primary key: permit_id, vehicle_number
    - Foreign key: REFERENCES Permit(permit_id, vehicle_number) ON DELETE CASCADE
    - Default value: 
      - S_E: S
    - Not NULL: S_E
    - Allowed values:- Default value:
      - S_E: S, E
  - Visitor:
    - Primary key: vehicle_number, permit_id
    - Foreign key: REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE, REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE
    - Not NULL: Phone_number
  - Citation:- Default value:
    - Primary key: citation_no
    - Foreign key: REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE
    - Default value:
      - citation_date: SYSDATE
      - Status: 0
    - Not NULL: model, color, citation_time, citation_date, car_license_number, citation_no, violation_category, fees, Due, status, zone_designation, address, name
    - Allowed values: 
      - fees: 20, 25, 40
      - violation_category: Invalid, Expired, No permit
      - Status: 0, 1
    - Additional constraints:
      - Due date has to be 30 days from citation date
  - Notification:
    - Primary key: NotificationNumber
    - Foreign key: REFERENCES Citation (citation_no) ON DELETE CASCADE
    - Not NULL: citation_no, NotificationNumber
  

Constraint not implemented as part of table and implemented in final design
- No visitor or student can have more than one vehicle in a single permit: 
  - Implemented as a trigger before insertion on Permit, visitor and non visitor table which checks number of entry present in the table for a single permit for that student or visitor, and if the row is present then do not carry out the insert operation.
  - Reason: oracle sql does not allow nested queries in check constraint to implement the same
  
- Employee can have up to two vehicles per single permit not more than two vehicles: 
  - Implemented through a trigger before insert on Permit and non visitor table to check number of entries present in the table for a single permit for that employee, and if there are two rows present then do not carry out the insert operation.
  - Reason: oracle sql does not allow nested queries in check constraint to implement the same 
  
- Set default value of due date in citation:
  - Implemented a trigger to set due date to 30 days from citation_date on an insert in citation table.
  - Reason: oracle sql does not allow dynamic default value constraint based on other column value.
 
- Auto increment notification primary key and citation primary key
  - Implemented a sequence, and a trigger for notificationNumber and citation_no each to auto increment value of primary keys
  - Reason: oracle 11g do not allow `AUTO INCREMENT`.

Constraint implemented as part of code
- Default zone value for any given space from zone designation of that lot and auto insert spaces into space table
  - Implemented in a code:
  - Reason: zone_designation in Parking_lots is a part of primary key, updating the same would result in referential integrity constraint for spaces table and vice versa is also true. The work-around in code is to add a lot with updated zone_designation, update respective rows of spaces table and delete old parking_lot row

