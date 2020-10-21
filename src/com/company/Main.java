package com.company;
import com.sun.nio.sctp.AbstractNotificationHandler;

import javax.xml.transform.Result;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.StreamSupport;

public class Main {
    public Statement stmt = null;
    public Connection conn = null;
    public static String permitId = null;
    public static String univId = null;
    public static Scanner in = new Scanner(System.in);

    public Main() {
        String jdbcURL = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "jgabani";    // For example, "jsmith"
            String passwd = "abcd1234";    // Your 9 digit student ID number


            ResultSet rs = null;
            this.conn = DriverManager.getConnection(jdbcURL, user, passwd);

            // Create a statement object that will be sending your
            // SQL statements to the DBMS

            this.stmt = this.conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void loginEmployee(int u){
        System.out.println("Enter your univid");
        univId = in.nextLine();
        if (this.login(univId, "E")) {
            this.employeeFunction();
        }
    }

    private String generatePermitID(String zone){
        String permitid = String.valueOf(LocalDate.now().getYear());
        permitid = permitid.substring(permitid.length() - 2, permitid.length());
        permitid += zone;
        UUID idOne = UUID.randomUUID();
        permitid += String.valueOf(idOne);
        permitid = permitid.substring(0,8);
        return permitid;
    }
    private void employeeFunction() {
        while (true){
            System.out.println("1. ChangeVehicleList");
            System.out.println("2. PayCitation");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
            in.nextLine();
            switch (u){
                case 1:
                    this.changeEmpVehicleList();
                    break;
                case 2:
                    this.payCitation();
                    break;
                default:
                    return;
            }
        }
    }

    public void loginUPS(int u){
        this.upsFunctions();
    }

    public void loginStudent(int u){
        System.out.println("Enter your univid");
        univId = in.nextLine();
        if (this.login(univId, "S")) {
            this.studentFunction();
        }
        else{
            System.out.println("Student Univeristy does not exist ");
        }
    }

    private boolean login(String uni, String se) {
        // login logicNon_Visitor
        try{
            String s = String.format("select permit_id from Non_Visitor where unvid = '%s' and S_E = '%s'", uni, se);
//            String s = "Select * from NON_VISITOR";
//            System.out.println(s);
            ResultSet rs = this.stmt.executeQuery(s);
            if(!rs.next()){
//                System.out.println("User doesn't exists");
                return false;
            }
            else {
//                while(rs.next()){
//                    System.out.println(rs.getString("permit_id") + " " + rs.getString("unvid") + " " + rs.getString("S_E"));
//                }
                    permitId = rs.getString("permit_id");
//                    System.out.println(permitId);
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void studentFunction() {
        while (true){
//            System.out.println("1. ChangeVehicleList");
            System.out.println("1. PayCitation");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
            in.nextLine();
            switch (u){
//                case 1:
//                    System.out.println("Enter your univid");
//                    String uni = in.nextLine();
//                    this.changeStudentVehicleList(uni);
//                    break;
                case 1:
                    this.payCitation();
                    break;
                default:
                    return;
            }
        }
    }

    private void changeStudentVehicleList(String uni) {
//        String permitNumber, int univId, boolean addOrRemove, String make, String model, String year, String color, String vehicleNumber
        try {
            String s = String.format("select permit_id from Non_Visitor where unvid = '%s' and S_E = 'S'", uni);
            ResultSet rs = this.stmt.executeQuery(s);
            rs.next();
            String permitID = rs.getString("permit_id");

//            System.out.println("Enter your current Permit ID: ");
//            String permitID = in.nextLine();
            System.out.println("Enter Car Manufacturer: ");
            String carmanufacturer = in.nextLine();
            System.out.println("Enter Car Model: ");
            String model = in.nextLine();
            System.out.println("Enter Car Manufacturer year: ");
            int year = in.nextInt();
            in.nextLine();
            System.out.println("Enter Car Color: ");
            String color = in.nextLine();
            System.out.println("Enter Vehicle Number: ");
            String vehiclenumber = in.nextLine();
            String query = "Update Permit P SET P.car_manufacturer = '" + carmanufacturer + "', P.model = '" + model + "', P.year = '" + year + "', P.color = '" + color + "', P.vehicle_number = '" + vehiclenumber + "' WHERE P.permit_id = '" + permitID + "'";
            this.stmt.executeUpdate(query);
            System.out.println("Updated the Student's Vehicle List");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }


    public void upsFunctions(){
        //issueCitation()
        //addLot()
        //assignZoneToLot
        //assignTypeToSpace()
        //assignPermit()
        //CheckVValidParking()
        //CheckNVValidParking()
        while(true){
            System.out.println("1. issueCitation");
            System.out.println("2. addLot");
            System.out.println("3. assignZoneToLot");
            System.out.println("4. assignTypeToSpace");
            System.out.println("5. assignPermit");
            System.out.println("6. CheckVValidParking");
            System.out.println("7. CheckNVValidParking");
//            System.out.println("8. ChangeVehicleList");
            System.out.println("8. PayCitation");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
            in.nextLine();
            switch (u){
                case 1:
                    this.issueCitation();
                    break;
                case 2:
                    this.addLot();
                    break;
                case 3:
                    this.assignZoneToLot();
                    break;
                case 4:
                    this.assignTypeToSpace();
                    break;
                case 5:
                    this.assignPermit();
                    break;
                case 6:
                    this.checkVValidParking();
                    break;
                case 7:
                    this.checkNVValidParking();
                    break;
//                case 8:
//                    System.out.println("Enter your univid");
//                    String uni = in.nextLine();
//
//                    if (this.login(uni, "E")) {
//                        this.changeEmpVehicleList();
//                    }
////                    else if (this.login(uni, "S")) {
////                        this.changeStudentVehicleList(uni);
////                    }
////                    else{
////                        System.out.println("User does not exist");
////                    }
//                    break;
                case 8:
                    this.payCitation();
                    break;
                default:
                    return;
            }

        }
    }

    private void changeEmpVehicleList() {
//        String permitNumber, int univId, boolean addOrRemove, String make, String model, int year, String color, String vehicleNumber
       try {
           System.out.println("Do you want to add or remove a vehicle(a/r)?: ");
           String option = in.nextLine();
           if (option.equals("a")) {
               System.out.println(permitId);
               System.out.println("SELECT * FROM Non_Visitor WHERE permit_id LIKE '" + permitId + "'");
               ResultSet rs = this.stmt.executeQuery("SELECT * FROM Non_Visitor WHERE permit_id LIKE '" + permitId + "'");
               int size = 0;
               while(rs.next()){
                   ++size;
               }
               if (size < 2) {
                   System.out.println("Enter the make of your car: ");
                   String car_manufacturer = in.nextLine();
                   System.out.println("Enter the model of your car: ");
                   String model = in.nextLine();
                   System.out.println("Enter the color of your car: ");
                   String color = in.nextLine();
                   System.out.println("Enter the vehicle number of your car: ");
                   String vehicleNumber = in.nextLine();
                   System.out.println("Enter the year of your car: ");
                   String year = in.nextLine();
                   rs = this.stmt.executeQuery("SELECT * FROM Permit WHERE permit_id LIKE '" + permitId + "'");
                   if (rs.next()) {
                       String zone = rs.getString("zone");
                       java.sql.Date start_date = rs.getDate("start_date");
                       String space_type = rs.getString("space_type");
                       java.sql.Date expiry_date = rs.getDate("expiry_date");
                       String expiry_time = rs.getString("expiry_time");

                       String insert = String.format("INSERT INTO Permit Values('%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s',TO_DATE('%s','YYYY-MM-DD'),TO_TIMESTAMP('%s 23:59:00', 'YYYY-MM-DD HH24:MI:SS'),'%s','%s','%s','%s','%s')"
                               , permitId, zone, start_date, space_type, expiry_date, expiry_date, car_manufacturer, model, year, color, vehicleNumber);
                       System.out.println(insert);
                       this.stmt.executeUpdate(insert);

//                       this.stmt.executeUpdate("INSERT INTO Permit VALUES(" + permitId + "," + zone + ","
//                               + start_date + "," + space_type + "," + expiry_date + "," + expiry_time + ","
//                               + car_manufacturer + "," + model + "," + year + "," + color + "," + vehicleNumber + ")");

                       String s = String.format("INSERT INTO Non_Visitor Values('%s','%s','%s','%s')", univId, permitId, vehicleNumber, 'E');
                       System.out.println(s);
                       this.stmt.executeUpdate(s);
                   }
               } else {
                   System.out.println("The user already has 2 cars. Please remove one before adding any more.");
               }
           } else if (option.equals("r")) {
               ResultSet rs = this.stmt.executeQuery("SELECT * FROM Non_Visitor WHERE permit_id LIKE '" + permitId + "'");
               int size = 0;
               while(rs.next()){
                   ++size;
               }
               if(size == 1) {
                   System.out.println("You have only one car with the permit. If you remove it your permit will be deleted.");
                   System.out.println("Do you want to proceed (yes/no): ");
                   String o = in.nextLine();
                   if(o.equals("no")) {
                       return;
                   }
               }
               System.out.println("Enter the vehicle number that you want to remove: ");
               String vehicleNumber = in.nextLine();
               this.stmt.executeUpdate("DELETE FROM Permit WHERE vehicle_number LIKE '" + vehicleNumber + "'");
           }
           System.out.println("changeEmpVehicleList");
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
    }

//    String parking_lot_table = "CREATE TABLE Parking_Lots (zone_designation VARCHAR(100), address VARCHAR(50), name VARCHAR(20) UNIQUE, number_of_spaces NUMBER(10, 0), PRIMARY KEY (name, zone_designation, address))";
//
//    String spaces_tables = "CREATE TABLE Spaces(space_number NUMBER(10, 0) NOT NULL, zone VARCHAR(10) NOT NULL, constraint zone_ck check(zone in ('A', 'B', 'C', 'D','S', 'DS','BS', 'AS','V','CS','R')), designated_type VARCHAR(10) DEFAULT  'regular' NOT NULL, constraint designated_type_ct check(designated_type in ('regular', 'electric', 'handicapped')), occupied varchar(3) default 'no', constraint op_check check (occupied in ('yes', 'no')), zone_designation VARCHAR(100), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (name, zone_designation, address, space_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";
//
//    String permit_table = "CREATE TABLE Permit (permit_id VARCHAR(8), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10) default 'regular', constraint space_type_ct check(space_type in ('regular', 'electric', 'handicapped')), expiry_date DATE, expiry_time TIMESTAMP(2), car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number varchar(10), PRIMARY KEY (permit_id, vehicle_number))";
//
//    String non_visitor_table = "CREATE TABLE Non_Visitor(unvid NUMBER(10, 0), permit_id varchar(8), vehicle_number varchar(10), S_E varchar(2) default 'S' NOT NULL, constraint se_check check (S_E in ('S', 'E')), PRIMARY KEY(permit_id, vehicle_number), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number) ON DELETE CASCADE)";
//
//    String visitor_table = "CREATE TABLE Visitor(permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0) NOT NULL, zone_designation VARCHAR(100), address VARCHAR(50), name VARCHAR(20), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE , FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";


    private void checkNVValidParking() {
//        String currentTime, java.sql.Date date, String lotName, String type, String licensePlate
        System.out.println("Is this for a Student (S) or an Employee (E): ");
        String non_visitor = in.nextLine();
        Date current_time = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
        dateFormat.format(current_time);
        System.out.println("Enter Lot Name: ");
        String lot_name = in.nextLine();
        System.out.println("Enter Lot Address: ");
        String lot_address = in.nextLine();
        if (non_visitor.equals("E")){
            System.out.println("Enter Permit ID: ");
            String permit_id = in.nextLine();
            String query = String.format("SELECT * FROM Non_Visitor WHERE permit_id = '%s' and S_E = 'E'", permit_id);
            boolean isResultSet = false;
            try {
                isResultSet = this.stmt.execute(query);
                if (isResultSet) {
//                    System.out.println("Requested Car has a valid permit");
                    String query_permit = String.format("SELECT zone FROM Permit WHERE permit_id = '%s'", permit_id);
                    ResultSet rs1 = this.stmt.executeQuery(query_permit);
                    while (rs1.next()){
                        String zone = rs1.getString("ZONE");
                        System.out.println(zone);
                        String queryParkingLot = String.format("SELECT * FROM Parking_Lots WHERE name = '%s' and address = '%s' and zone_designation LIKE ", lot_name, lot_address) + "'%"+zone+"%'";
                        boolean rs2 = this.stmt.execute(queryParkingLot);
                        if(rs2){
                            System.out.println("Requested Car does have a valid permit");
                        }
                        else{
                            System.out.println("Requested Car does not have a valid permit");
                        }
                    }
                } else {
                    System.out.println("Requested Car does not have a valid permit");
                }
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        else if(non_visitor.equals("S")){
            System.out.println("Enter License Number: ");
            String license = in.nextLine();
            System.out.println("Enter Space Number: ");
            String spaceNumber = in.nextLine();
            System.out.println("Enter Zone: ");
            String zone = in.nextLine();
            String queryStudent = String.format("SELECT permit_id FROM Non_Visitor WHERE vehicle_number = '%s' and S_E = 'S'", license);
            try {
                ResultSet rs3 = this.stmt.executeQuery(queryStudent);
                if (!rs3.isBeforeFirst() )
                {
                    System.out.println("Requested Car does not have a valid permit");
                }
                else {
                    while (rs3.next()) {
                        String permitID = rs3.getString("permit_id");
//                        String query_permit = String.format("SELECT zone FROM Permit WHERE permit_id = '%s'", permitID);
//                        ResultSet rs4 = this.stmt.executeQuery(query_permit);
//                        while (rs4.next()) {
//                            String zone = rs4.getString("zone");
                        if (dateFormat.parse(dateFormat.format(current_time)).after(dateFormat.parse("17:00")) && dateFormat.parse(dateFormat.format(current_time)).before(dateFormat.parse("09:00"))) {
                            String queryParkingLot = String.format("SELECT * FROM Parking_Lots WHERE name = '%s' and address = '%s' and zone_designation LIKE ", lot_name, lot_address) + "'%" + zone + "%'";
                            boolean rs2 = this.stmt.execute(queryParkingLot);
                            if (rs2) {
                                System.out.println("Requested Car does have a valid permit");
                            } else {
                                System.out.println("Requested Car does not have a valid permit");
                            }
                        } else {
                            if (zone.equals("S")){
                                System.out.println("Requested Car does have a valid permit");
                            }
                            else{
                                System.out.println("Requested Car does not have a valid permit");
                            }
                        }



                    }
                }
            }catch(SQLException | ParseException se){
                se.printStackTrace();
            }

        }

    }
//            Visitor(permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0) NOT NULL, zone_designation VARCHAR(100), address VARCHAR(50), name VARCHAR(20), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id)
    private void checkVValidParking() {
//        String currentTime, java.sql.Date date, String lotName, int spaceNumber, String type, String licensePlate
        System.out.println("Enter Space Number: ");
        String space_number = in.nextLine();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
        //int space_number = in.nextInt();
        System.out.println("Enter Lot Name: ");
        String lot_name = in.nextLine();
        System.out.println("Enter Lot Address: ");
        String lot_address = in.nextLine();
        System.out.println("Enter License Number: ");
        String license = in.nextLine();
//        String query = "SELECT * FROM Visitor V, permit P WHERE P.expiry_time > '" +timestamp+ "', V.vehicle_number = '" +license+ "', V.lot = '" +lot+ "', V.space_number = '" +space_number+ "'";
        String query = String.format("SELECT * FROM Visitor WHERE space_number = '%s' and name = '%s' and address = '%s' and vehicle_number = '%s'", space_number, lot_name, lot_address, license);
//        System.out.println(query);

        try {
            ResultSet rs = this.stmt.executeQuery(query);
            if (rs.next()) {
//                query = String.format("SELECT permit_id FROM Visitor WHERE space_number = '%s' and name = '%s' and address = '%s' and vehicle_number = '%s'", space_number, lot_name, lot_address, license);
//                String pi = rs.getString("permit_id");
//                String s = String.format("select * from PERMIT where permit_id in ('%s') and vehicle_number = '%s'", query, license);
//                System.out.println(s);
                System.out.println("Requested Car has a valid permit");
            }
            else {
                System.out.println("Requested Car does not have a valid permit");
            }
        }catch(SQLException se){
            se.printStackTrace();
        }
    }

    private void assignPermit() {
//        int univId, String zone, String type, String vehicleNumber
        try {
            System.out.println("Enter univid");
            String univ = in.nextLine();

            System.out.println("Enter Zone");
            String zone = in.nextLine();

            System.out.println("enter space type (regular, electric, handicapped)");
            String type = in.nextLine();

            System.out.println("Enter vehicle number");
            String li = in.nextLine();
            System.out.println("Generated permit id is");
            // call generate permit id
            String permit = this.generatePermitID(zone);
            System.out.println("Enter color of the vehicle");
            String color = in.nextLine();
            System.out.println("Enter model of the vehicle");
            String model = in.nextLine();
            System.out.println("Enter year of the vehicle");
            String year = in.nextLine();
            System.out.println("Enter manufacturer of the vehicle");
            String manu = in.nextLine();

            LocalDate start_date = java.time.LocalDate.now();
            LocalDate end_date = null;
            System.out.println("is this a student permit? yes/no");
            String st = in.nextLine();
            String se = "S";
            if (!st.equals("yes")) {
                end_date = start_date.plusYears(1);
            } else {
                end_date = start_date.plusMonths(4);
                se = "E";
            }
            String per = String.format("insert into Permit (permit_id, zone, start_date, space_type, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number) values('%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s',TO_DATE('%s','YYYY-MM-DD'),TO_TIMESTAMP('%s 23:59:00', 'YYYY-MM-DD HH24:MI:SS'),'%s','%s','%s','%s','%s','%s','%s','%s')",permit, zone, start_date, type, end_date, end_date, manu, model, year, color, li);
            String non = String.format("insert into Non_Visitor (unvid, permit_id, vehicle_number, S_E) values('%s','%s','%s', '%s')", univ, permit, li, se);
            this.stmt.executeUpdate(per);
            this.stmt.executeUpdate(non);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("assignPermit");
    }

    private void assignTypeToSpace() {
//        String lotName, int spaceNumber, String type
        try {
            System.out.println("Enter Name of the parking lot, space number, type, address and zone designation");
            String name = in.nextLine(), space = in.nextLine(), type = in.nextLine(), address = in.nextLine(), zone_desi = in.nextLine();
            this.stmt.executeUpdate(String.format("UPDATE Spaces set designated_type ='%s' where name = '%s' and address = '%s' and space_number = '%s' and zone_designation = '%s'", type, name, address, space, zone_desi));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("assignTypeToSpace");
    }

    private void assignZoneToLot() {
//        String name, String Designation, int numberOfSpaces, int beginNumber
        System.out.println("assignZoneToLot");
        try {
            System.out.println("Enter the name of the Lot: ");
            String name = in.nextLine();
            System.out.println("Enter the address of the Lot: ");
            String address = in.nextLine();
            System.out.println("Enter the current designation of the Lot: ");
            String designation = in.nextLine();
            System.out.println("Enter the new zone designation: ");
            String newZone = in.nextLine();

            System.out.println("Enter the starting space for the new zone: ");
            int start_number = in.nextInt();

            System.out.println("Enter the ending space for the new zone: ");
            int last_number = in.nextInt();

            String zones_update = "UPDATE Spaces SET zone = '"+newZone+"' WHERE name = '"+name+"' and address = '"+address+"' and zone_designation = '"+designation+"' and space_number = ?";
            System.out.println(zones_update);
            PreparedStatement ps = this.conn.prepareStatement(zones_update);

            for (int i = start_number;i<=last_number;i++) {
                ps.setInt(1, i);
                System.out.println(ps.toString());
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            ps.close();
            String newDesignation = designation + ", " + newZone;

            ResultSet rs = this.stmt.executeQuery(String.format("SELECT number_of_spaces FROM Parking_Lots WHERE name = '%s' and address = '%s' and zone_designation = '%s'",name ,address ,designation));

            int number = 0;

            if(rs.next()){
                number = rs.getInt("number_of_spaces");
            }

            String s = String.format("INSERT INTO Parking_Lots VALUES('%s', '%s', '%s', '%s')", newDesignation, address, name, number);
            this.stmt.executeUpdate(s);
            rs.close();

            String designation_update = "UPDATE Spaces SET zone_designation = '"+newDesignation+"' WHERE name = '"+name+"' and address = '"+address+"' and zone_designation = '"+designation+"'";
            this.stmt.executeUpdate(designation_update);

            this.stmt.executeUpdate(String.format("DELETE FROM Parking_Lots WHERE name = '%s' and address = '%s' and zone_designation = '%s'",name ,address ,designation));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void addLot() {
//        String name, String address, int numberOfSpaces, String initialDesignation
        System.out.println("addLot");
        try {
            System.out.println("Enter the name of the new lot: ");
            String name = in.nextLine();
            System.out.println("Enter the address of the new lot: ");
            String address = in.nextLine();
            System.out.println("Enter the initial zone designation of the new lot: ");
            String initialDesignation = in.nextLine();
            System.out.println("Enter the number of spaces in the new lot: ");
            int numberOfSpaces = in.nextInt();
            String s = String.format("INSERT INTO Parking_Lots VALUES('%s', '%s', '%s', '%s')", initialDesignation, address, name, numberOfSpaces);
            System.out.println(s);
            this.stmt.executeUpdate(s);

            String spaces_insert = "INSERT INTO Spaces (space_number, zone, zone_designation, address, name) VALUES (?, ?, ?, ?, ?)";
            System.out.println(spaces_insert);
            PreparedStatement ps = this.conn.prepareStatement(spaces_insert);
            for (int i = 1;i <= numberOfSpaces;i++) {
                ps.setInt(1, i);
                ps.setString(2, initialDesignation);
                ps.setString(3, initialDesignation);
                ps.setString(4, address);
                ps.setString(5, name);
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            ps.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void issueCitation() {
//        String licensePlate, String violationCategory
        System.out.println("issueCitation");
        try{
            System.out.println("Enter Car number");
            String li = in.nextLine();
            System.out.println("Enter Permit_id");
            String pi = in.nextLine();
            // issue of having multiple permits belonging to same vehicle for visitor
            ResultSet rs = this.stmt.executeQuery("SELECT * from Permit where vehicle_number = '"+li+"'" + "and PERMIT_ID = '" + pi +"'");
            if(!rs.next()){
                System.out.println("Permit dont exists");
                System.out.println("Enter Car model");
                String model = in.nextLine();
                System.out.println("Enter color");
                String color=in.nextLine();
                String fees = "40";
                String violation_category = "No permit";
                System.out.println("Enter name of the parking lot");
                String name= in.nextLine();
                System.out.println("Enter address of the parking lot");
                String address= in.nextLine();
                Timestamp citation_time= new Timestamp(System.currentTimeMillis());
                String t = citation_time.toString().split("\\.")[0];
                LocalDate citation_date= java.time.LocalDate.now();
                System.out.println("Enter zone designation of the parking lot");
                String zone= in.nextLine();
                LocalDate due=citation_date.plusDays(30);
                String phone = "", univ = "";
                String c = String.format("insert into Citation (citation_time, citation_date, car_license_number, violation_category, fees, Due, zone_designation, address, name, model, color) Values(TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS'),To_Date('%s', 'YYYY-MM-DD'),'%s','%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s','%s','%s','%s','%s')", t, citation_date, li, violation_category, fees, due, zone, address, name, model, color);
                System.out.println(c);
                this.stmt.execute(c);
                rs = this.stmt.executeQuery("select * from Citation where citation_no = (select max(citation_no) from citation)");
                String ci_no="";
                if(rs.next()){
                     ci_no = rs.getString("citation_no");
                }
                String n = String.format("insert into Notification (citation_no, PhoneNumber, univ) values ('%s','%s','%s')", ci_no, phone, univ);
                this.stmt.execute(n);
            }
            else{
                Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("expiry_time"));
                //Date d = new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS").format(new Date());
                String fees = "";
                Date d = new Date();
                if(et.after(d)){
//                    System.out.println(et.toString());
//                    System.out.println("Enter lot info to see if it is properly parked");
//                    System.out.println("Enter name of the zone");
//                    String name= in.nextLine();
//                    System.out.println("Enter address");
//                    String address= in.nextLine();
//                    System.out.println("Enter zone");
//                    String zone= in.nextLine();
                    //check for the valid parking
//                    if(name.equals(rs.getString("name")) && address.equals(rs.getString("address")) && zone.equals(rs.getString("zone_designation"))) {
//                        // check time
//                    }
//                    else {
//                        //issue invalid permit citation
//                    }
                    System.out.println("Permit is Invalid");
                    fees = "20";

                }
                else
                {
                    System.out.println("Permit expired");
                    fees = "25";

                }
                String t = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
                String c = "";
                System.out.println("Enter name of the parking lot");
                String name= in.nextLine();
                System.out.println("Enter address of the parking lot");
                String address= in.nextLine();
                System.out.println("Enter zone designation of the parking lot");
                String zone= in.nextLine();
                c = String.format("insert into Citation (citation_time, citation_date, car_license_number, violation_category, fees, Due, zone_designation, address, name, model, color) Values(TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS'),To_Date('%s', 'YYYY-MM-DD'),'%s','%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s','%s','%s','%s','%s')", t, LocalDate.now(), li, "Expired", fees, LocalDate.now().plusDays(30), zone, address, name, rs.getString("model"), rs.getString("color"));

                String phone = "", univ = "";
                System.out.println("is this visitor parking? yes/no");
                String x ="";
                if(in.nextLine().equals("yes")){
                    System.out.println(rs.getString("permit_id") + ":" +rs.getString("vehicle_number"));
                    x = String.format("Select * from Visitor where permit_id = '%s' and vehicle_number = '%s'", rs.getString("permit_id"), rs.getString("vehicle_number"));
                    rs = this.stmt.executeQuery(x);
                    if(rs.next()){
                        phone = rs.getString("Phone_number");
                    }
                }
                else{
                    System.out.println(rs.getString("permit_id") + ":" + rs.getString("vehicle_number"));
                    x = String.format("Select * from Non_Visitor where permit_id = '%s' and vehicle_number = '%s'", rs.getString("permit_id"), rs.getString("vehicle_number"));
                    rs = this.stmt.executeQuery(x);
                    if (rs.next()) {
                        univ = rs.getString("unvid");
                        System.out.println(univ);
                    }
                }
                System.out.println(c);
                this.stmt.execute(c);
                ResultSet cs = this.stmt.executeQuery("select * from Citation where citation_no = (select max(citation_no) from citation)");
                String ci_no="";
                if(cs.next()){
                    ci_no = cs.getString("citation_no");
                }
                String n = String.format("insert into Notification (citation_no, PhoneNumber, univ) values ('%s','%s','%s')", ci_no, phone, univ);
                System.out.println(n);
//                this.stmt.execute(n);
                System.out.println("Citation issued" + ci_no);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void visitorFunction(){
        while(true){
            System.out.println("1. exitLot");
            System.out.println("2. PayCitation");
            System.out.println("3. getVisitorPermit");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
            in.nextLine();
            switch (u){
                case 1:
                    this.exitLot();
                    break;
                case 2:
                    this.payCitation();
                    break;
                case 3:
                    this.getVisitorPermit();
                default:
                    return;
            }
        }
    }
// VISITOR: permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0), lot VARCHAR(5), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE)";
    //PERMIT: permit_id VARCHAR(8), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number varchar(10), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (permit_id, vehicle_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";
    //Parking_Lots (zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20) UNIQUE, number_of_spaces NUMBER(10, 0), PRIMARY KEY (name, zone_designation, address))";
    //Spaces(space_number NUMBER(10, 0) NOT NULL, zone VARCHAR(10), designated_type VARCHAR(10) DEFAULT  'regular' NOT NULL, constraint designated_type_ct check(designated_type in ('regular', 'electric', 'handicapped')), occupied varchar(3) default 'no', constraint op_check check (occupied in ('yes', 'no')), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (name, zone_designation, address, space_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";
    private void getVisitorPermit() {
//        String licensePlate, String type, String lotName, String duration
        try {
            System.out.println("Enter Name:");
            String name = in.nextLine();
            System.out.println("Enter Address");
            String address = in.nextLine();
            System.out.println("Enter Vehicle Number:");
            String vehicle_number = in.nextLine();
            System.out.println("Enter Phone Number:");
            String phone_number = in.nextLine();
            System.out.println("Enter Space Type:");
            String space_type = in.nextLine();
            System.out.println("Enter Car Color:");
            String color = in.nextLine();
            System.out.println("Enter Car Model:");
            String model = in.nextLine();
            System.out.println("Enter Car Manufacture Year:");
            String car_manufacture_year = in.nextLine();
            System.out.println("Enter Car Manufacturer:");
            String car_manufacturer = in.nextLine();
            System.out.println("Enter the number of hours for which you want parking permit:");
            String requested_hours = in.nextLine();
            //String name = "a", address = "a", zone_desi = "a";
            LocalDate start_date = java.time.LocalDate.now();
            LocalDate end_date = null;
            Instant current_time = Instant.now();
            Instant expiry_time = current_time.plus(Long.parseLong(requested_hours), ChronoUnit.HOURS);
            //String expiry = expiry_time.toString();
            //String[] parts = expiry.split("T");
            LocalDate expiry_part1 = LocalDateTime.ofInstant(expiry_time, ZoneOffset.UTC).toLocalDate();
//            LocalDate expiry_part1 = LocalDateTime.ofInstant(expiry_time, ZoneOffset.UTC).toLocalDate();
            //Timestamp expiry_part1 = Timestamp.from(expiry_time);

//             = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(expiry_part1);
//            System.out.println(formatted);
//            Timestamp expiry_part2 = Timestamp.from(expiry_time); // 034556
            boolean flag = false;
            //String query_parkinglots = "select P.zone_designation from Parking_Lots P where P.name = `" + name + "` and P.address = `"+ address + "` and P.zone_designation LIKE '%V%'";
            String query_parkinglots = String.format("select P.zone_designation from Parking_Lots P where P.name = '%s' and P.address = '%s' and P.zone_designation LIKE ", name, address) + "'%V%'";
//            System.out.println(query_parkinglots);
            ResultSet rs = this.stmt.executeQuery(query_parkinglots);
            while (rs.next()) {
                //Retrieve by column name
                String zoneDesignation = rs.getString("zone_designation");
                //String query_space = "select S.zone, S.space_number from Spaces where S.name = `" + name + "` and S.address = `"+ address + "`and S.zone_designation = `"+ zoneDesignation+"`and S.designated_type = `"+space_type+"`";
                String query_space = String.format("select S.zone, S.space_number from Spaces S where S.name = '%s' and S.address = '%s' and S.zone_designation = '%s' and S.designated_type = '%s' and S.occupied = 'no'", name, address, zoneDesignation, space_type);
                ResultSet rs1 = this.stmt.executeQuery(query_space);
                if (!rs1.isBeforeFirst() ) {
//                    System.out.println("No data");
                    continue;
                }
                else{
                    while (rs1.next()) {
//                        String zone = rs1.getString("zone");
                        String zone = "V";
                        String spaceNumber = rs1.getString("space_number");
                        String permitID = generatePermitID("V");
//                        System.out.println(permitID);
//                        System.out.println(expiry_part2);
//                        System.out.println(expiry_part1);
                        //Visitor(permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0) NOT NULL, zone_designation VARCHAR(100), address VARCHAR(50), name VARCHAR(20), space_number VARCHAR(20)

                        String per = String.format("insert into Permit (permit_id, zone, start_date, space_type, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number) values('%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s',TO_DATE('%s','YYYY-MM-DD'),TO_TIMESTAMP('%s 23:59:00', 'YYYY-MM-DD HH24:MI:SS'),'%s','%s','%s','%s','%s')", permitID, zone, start_date, space_type, expiry_part1, expiry_part1, car_manufacturer, model, car_manufacture_year, color, vehicle_number);
                        this.stmt.executeUpdate(per);
                        this.stmt.executeUpdate("UPDATE Spaces SET occupied = 'yes' where space_number =" + spaceNumber);
                        String visitor_update = String.format("insert into Visitor (permit_id, vehicle_number, Phone_number, zone_designation, address, name, space_number) values('%s','%s','%s','%s','%s','%s','%s')", permitID, vehicle_number, phone_number, zoneDesignation, address, name, spaceNumber);
                        this.stmt.executeUpdate(visitor_update);
                        System.out.println("Permit Assigned, Your Space Number is: "+ spaceNumber +", and your zone is: V");
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag){
                System.out.println("No spaces are available! Sorry.");
            }


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void payCitation() {
//        String citationNumber
        try{
            System.out.println("Insert citation number");
            String ci = in.nextLine();
            ResultSet rs_permit = this.stmt.executeQuery("SELECT * FROM Citation WHERE citation_no =" +ci);
            if(!rs_permit.next()){
                System.out.println("Citation does not exist, Enter correct citation number");

            }
            else {
                ResultSet rs_citation = this.stmt.executeQuery("SELECT * FROM Citation WHERE status = 0 and citation_no =" +ci);
                if(!rs_citation.next()){
                    System.out.println("Citation already paid");

                }
                else {
                    this.stmt.executeUpdate("UPDATE Citation SET status = 1 where citation_no =" + ci);
                    System.out.println("Citation Paid");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("paycitation");
    }

    private void exitLot() {
//        String permitNumber
        try {
            System.out.println("Enter the permit number: ");
            String permit = in.nextLine();
            ResultSet rs_permit = this.stmt.executeQuery("SELECT * FROM Permit WHERE permit_id LIKE '"+permit+"'");
            ResultSet rs_visit = this.stmt.executeQuery("SELECT * FROM Visitor WHERE permit_id LIKE '"+permit+"'");
            if(rs_visit.next() && rs_permit.next()) {
                this.stmt.executeUpdate(String.format("UPDATE Spaces SET occupied = 'no' WHERE name = '%s' and zone_designation = '%s' and address = '%s' and space_number = '%s'", rs_visit.getString("name"), rs_visit.getString("zone_designation"), rs_visit.getString("address"), rs_visit.getInt("space_number")));
            }
            Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:ms").parse(rs_permit.getString("expiry_time"));
            Date now = new Date();
            String fees = "25";
            if (et.after(now)) {
                System.out.println("No Time Overage !!");
            } else {
                System.out.println("Permit expired");
                String t = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];

                String cite = String.format("INSERT INTO Citation (citation_time, citation_date, car_license_number, violation_category, fees, Due, zone_designation, address, name, model, color) Values(TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS'),To_Date('%s', 'YYYY-MM-DD'),'%s','%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s','%s','%s','%s','%s')", t, LocalDate.now(), rs_visit.getString("vehicle_number"), "Expired", fees, LocalDate.now().plusDays(30), rs_visit.getString("zone_designation"), rs_visit.getString("address"), rs_visit.getString("name"), rs_permit.getString("model"), rs_permit.getString("color"));
                System.out.println(cite);
                this.stmt.executeUpdate(cite);
                ResultSet cs = this.stmt.executeQuery("select * from Citation where citation_no = (select max(citation_no) from citation)");
                String ci_no="";
                if(cs.next()){
                    ci_no = cs.getString("citation_no");
                }
                String n = String.format("insert into Notification (citation_no, PhoneNumber, univ) values ('%s','%s','%s')", ci_no, rs_visit.getString("Phone_number"), "");
                System.out.println(n);
                this.stmt.executeUpdate(n);
                System.out.println("Citation issued: " + ci_no);
            }
            System.out.println("exitLot");
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
	// write your code here
        Main o = new Main();
//        o.setupSchema();
//        try{
//            for (int i = 1; i <= 150; i++){
//                String zone[] = {"A", "B", "C", "D"};
//
//                String s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name) values ('%s', '%s', '%s', '%s', '%s');", i, zone[i/40], "A, B, C, D", "2105 Daniel Allen St, NC 27505", "Freedom Lot");
//                System.out.println(s);
////                o.stmt.executeUpdate(s);
//            }
//            for (int i = 1; i <= 175; i++){
//                String zone[] = {"AS", "BS", "CS", "DS", "DS", "V"};
//                String s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name) values ('%s', '%s', '%s', '%s', '%s');", i, zone[i/35], "AS, BS, CS,DS, V" ,"2704 Ben Clark St, NC 26701","Justice Lot");
//                if(i > 150)
//                {
//                    s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name) values ('%s', '%s', '%s', '%s', '%s');", i, 'V', "AS, BS, CS,DS, V" ,"2704 Ben Clark St, NC 26701","Justice Lot");
//                    if ( i > 150 && i < 156)
//                    {
//                        s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name, designated_type) values ('%s', '%s', '%s', '%s', '%s', '%s');", i, 'V', "AS, BS, CS,DS, V" ,"2704 Ben Clark St, NC 26701","Justice Lot", "handicapped");
//                    }
//                    if ( i > 171 )
//                    {
//                        s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name, designated_type) values ('%s', '%s', '%s', '%s', '%s', '%s');", i, 'V', "AS, BS, CS,DS, V" ,"2704 Ben Clark St, NC 26701","Justice Lot", "electric");
//                    }
//
//                }
//                System.out.println(s);
////                o.stmt.executeUpdate(s);
//            }
//                for (int i = 1; i <= 200; i++){
//                                    String zone[] = {"A","B","C","D","AS", "BS", "CS", "DS", "DS"};
//                String s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name) values ('%s', '%s', '%s', '%s', '%s');", i, zone[i/25], "A, B, C, D, AS, BS, CS, DS, V", "2108 McKent St, NC 27507","Premiere Lot");
//                if (i==200){
//                    s = String.format("insert into Spaces (space_number, zone, zone_designation, address, name) values ('%s', '%s', '%s', '%s', '%s');", i, 'V', "A, B, C, D, AS, BS, CS, DS, V", "2108 McKent St, NC 27507","Premiere Lot");
//                }
//                System.out.println(s);
//                o.stmt.executeUpdate(s);
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        while(true) {
            System.out.println("Who are you?");
            System.out.println("1. UPS Employee");
            System.out.println("2. Student");
            System.out.println("3. Employee");
            System.out.println("4. Visitor");
            System.out.println("Any other number to exit");
            int u = in.nextInt();
            in.nextLine();
            switch (u){
                case 1:
                    o.loginUPS(u);
                    break;
                case 2:
                    o.loginStudent(u);
                    break;
                case 3:
                    o.loginEmployee(u);
                    break;
                case 4:
                    o.visitorFunction();
                    break;
                default:
                    o.stmt.close();
                    System.exit(0);
                    break;
            }
        }
    }
}
