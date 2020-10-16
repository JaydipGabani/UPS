package com.company;
import com.sun.nio.sctp.AbstractNotificationHandler;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;
import java.util.Date;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

import java.util.*;
import java.sql.*;
import java.util.stream.StreamSupport;

public class Main {
    public Statement stmt = null;
    public Connection conn = null;
    public static String permitId = null;
    public static Scanner in = new Scanner(System.in);

    public Main() {
        String jdbcURL
                = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
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
        int uni = in.nextInt();
        if (this.login(uni)) {
            this.employeeFunction();
        }
    }

    private void employeeFunction() {
        while (true){
            System.out.println("1. ChangeVehicleList");
            System.out.println("2. PayCitation");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
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
        System.out.println("Enter your univid");
        int uni = in.nextInt();
        if (this.login(uni)){
            this.upsFunctions();
        }

    }

    public void loginStudent(int u){
        System.out.println("Enter your univid");
        int uni = in.nextInt();
        if (this.login(uni)) {
            this.studentFunction();
        }
    }

    private boolean login(int uni) {
        // login logicNon_Visitor
        try{
            String s = "select permit_id from Non_Visitor where unvid = " + uni;
            ResultSet rs = this.stmt.executeQuery(s);
            if(rs.next() == false){
                System.out.println("User doesn't exists");
                return false;
            }
            else {
                while(rs.next()){
                    permitId = rs.getString("permit_id");
                    System.out.println(permitId);
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void studentFunction() {
        while (true){
            System.out.println("1. ChangeVehicleList");
            System.out.println("2. PayCitation");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
            switch (u){
                case 1:
                    this.changeStudentVehicleList();
                    break;
                case 2:
                    this.payCitation();
                    break;
                default:
                    return;
            }
        }
    }

    private void changeStudentVehicleList() {
//        String permitNumber, int univId, boolean addOrRemove, String make, String model, String year, String color, String vehicleNumber
        try {
            System.out.println("changeStudentVehicleList");
            String carmanufacturer = in.nextLine();
            String model = in.nextLine();
            int year = in.nextInt();
            String color = in.nextLine();
            String vehiclenumber = in.nextLine();
            String query = "Update Permit P SET P.car_manufacturer = '" + carmanufacturer + "', P.model = '" + model + "', P.year = '" + year + "', P.color = '" + color + "', P.vehicle_number = '" + vehiclenumber + "' WHERE P.permit_id = '" + permitId + "'";
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
            System.out.println("8. ChangeVehicleList");
            System.out.println("9. PayCitation");
            System.out.println("Any other number to go back");
            int u = in.nextInt();
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
                case 8:
                    this.changeEmpVehicleList();
                    break;
                case 9:
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
               ResultSet rs = this.stmt.executeQuery("SELECT * FROM Non_Visitor WHERE permit_id IS LIKE `" + permitId + "`");
               int size = 0;
               if (rs != null) {
                   rs.last();
                   size = rs.getRow();
               }
               if (size < 5) {
                   System.out.println("Enter the make of your car: ");
                   String car_manufacturer = in.nextLine();
                   System.out.println("Enter the model of your car: ");
                   String model = in.nextLine();
                   System.out.println("Enter the year of your car: ");
                   int year = in.nextInt();
                   System.out.println("Enter the color of your car: ");
                   String color = in.nextLine();
                   System.out.println("Enter the vehicle number of your car: ");
                   String vehicleNumber = in.nextLine();
                   rs = this.stmt.executeQuery("SELECT * FROM Permit WHERE permit_id IS LIKE `" + permitId + "`");
                   if (rs.next()) {
                       String zone = rs.getString("zone");
                       String start_date = rs.getString("start_date");
                       String space_type = rs.getString("space_type");
                       String expiry_date = rs.getString("expiry_date");
                       String expiry_time = rs.getString("expiry_time");
                       this.stmt.executeUpdate("INSERT INTO Permit VALUES(" + permitId + "," + zone + ","
                               + start_date + "," + space_type + "," + expiry_date + "," + expiry_time + ","
                               + car_manufacturer + "," + model + "," + year + "," + color + "," + vehicleNumber + ")");
                   }
               } else {
                   System.out.println("The user already has 5 cars. Please remove one before adding any more.");
               }
           } else if (option.equals("r")) {
               ResultSet rs = this.stmt.executeQuery("SELECT * FROM Non_Visitor WHERE permit_id IS LIKE `" + permitId + "`");
               System.out.println("Enter the vehicle number that you want to remove: ");
               String vehicleNumber = in.nextLine();
               this.stmt.executeUpdate("DELETE FROM Permit WHERE vehicle_number IS LIKE `" + vehicleNumber + "`");
           }
           System.out.println("changeEmpVehicleList");
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
    }

    private void checkNVValidParking() {
//        String currentTime, java.sql.Date date, String lotName, String type, String licensePlate
        System.out.println("checkNVValidParking");
        System.out.println("Enter Space Number: ");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
        int space_number = in.nextInt();
        System.out.println("Enter Lot Number: ");
        String lot = in.nextLine();
        System.out.println("Enter License Number: ");
        String license = in.nextLine();
        String query = "SELECT * FROM Non_Visitor V, permit_table P WHERE P.expiry_time > '" +timestamp+ "', V.vehicle_number = '" +license+ "', V.lot = '" +lot+ "', V.space_number = '" +space_number+ "'";
        boolean isResultSet = false;
        try {
            isResultSet = this.stmt.execute(query);
            if (isResultSet) {
                System.out.println("Requested Car has a valid permit");
            } else {
                System.out.println("Requested Car does not have a valid permit");
            }
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
//            String visitor_table = "CREATE TABLE Visitor(permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0), lot VARCHAR(5), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE)";
    private void checkVValidParking() {
//        String currentTime, java.sql.Date date, String lotName, int spaceNumber, String type, String licensePlate
        System.out.println("Enter Space Number: ");
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Date date = new Date();
        int space_number = in.nextInt();
        System.out.println("Enter Lot Number: ");
        String lot = in.nextLine();
        System.out.println("Enter License Number: ");
        String license = in.nextLine();
        String query = "SELECT * FROM Visitor V, permit_table P WHERE P.expiry_time > '" +timestamp+ "', V.vehicle_number = '" +license+ "', V.lot = '" +lot+ "', V.space_number = '" +space_number+ "'";
        boolean isResultSet = false;
        try {
            isResultSet = this.stmt.execute(query);
            if (isResultSet) {
                System.out.println("Requested Car has a valid permit");
            } else {
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
            System.out.println("type");
            String type = in.nextLine();
            System.out.println("Enter Zone");
            String zone = in.nextLine();
            System.out.println("Enter vehicle number");
            String li = in.nextLine();
            System.out.println("Generated permit id is");
            // call generate permit id
            String permit = in.nextLine();
            System.out.println("Enter color of the vehicle");
            String color = in.nextLine();
            System.out.println("Enter modla of the vehicle");
            String model = in.nextLine();
            System.out.println("Enter year of the vehicle");
            String year = in.nextLine();
            System.out.println("Enter manufacturer of the vehicle");
            String manu = in.nextLine();
            System.out.println("Enter name of the parking lot");
            String name = in.nextLine();
            System.out.println("Enter address of the parking lot");
            String address = in.nextLine();
            System.out.println("Enter zone designation of the parking lot");
            String zone_desi = in.nextLine();
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
            String per = String.format("insert into Permit (permit_id, zone, start_date, space_type, expiry_date, expiry_time, car_manufacturer, model, year, color, vehicle_number, zone_designation, address, name) values('%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s',TO_DATE('%s','YYYY-MM-DD'),TO_TIMESTAMP('%s 23:59:00', 'YYYY-MM-DD HH24:MI:SS'),'%s','%s','%s','%s','%s','%s','%s','%s')",permit, zone, start_date, type, end_date, end_date, manu, model, year, color, li, zone_desi, address, name);
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

            String zones_update = "UPDATE spaces_tables SET zone = `"+newZone+"` WHERE name IS LIKE `"+name+"` and address IS LIKE `"+address+"` and zone_designation IS LIKE `"+designation+"` and space_number = ?";
            PreparedStatement ps = this.conn.prepareStatement(zones_update);

            for (int i = start_number;i<=last_number;i++) {
                ps.setInt(1, i);
                ps.addBatch();
            }
            int[] result = ps.executeBatch();
            ps.close();

            String newDesignation = designation + "/" + newZone;

            String designation_update = "UPDATE spaces_tables SET zone_designation = `"+newDesignation+"` WHERE name IS LIKE `"+name+"` and address IS LIKE `"+address+"` and zone_designation IS LIKE `"+designation+"`";

            this.stmt.executeUpdate(designation_update);

        } catch (Exception throwables) {
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
            System.out.println("Enter the number of spaces in the new lot: ");
            int numberOfSpaces = in.nextInt();
            System.out.println("Enter the intitial zone designation of the new lot: ");
            String initialDesignation = in.nextLine();
            this.stmt.executeUpdate("INSERT INTO parking_lot_table VALUES("+initialDesignation+","+address+","+name+","+numberOfSpaces+","+")");

            String spaces_insert = "INSERT INTO spaces_tables (space_number, zone, zone_designation, address, name) VALUES (?, ?, ?, ?, ?)";
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
            String li = in.nextLine();
            // issue of having multiple permits belonging to same vehicle for visitor
            ResultSet rs = this.stmt.executeQuery("SELECT * from Permit where vehicle_number = '"+li+"'");
            if(!rs.next()){
                System.out.println("dont exists");
                System.out.println("Enter Car model, color");
                String model = in.nextLine();
                String color=in.nextLine();
                String fees = "40";
                String violation_category = "No permit";
                String name= in.nextLine();
                String address= in.nextLine();
                Timestamp citation_time= new Timestamp(System.currentTimeMillis());
                String t = citation_time.toString().split("\\.")[0];
                LocalDate citation_date= java.time.LocalDate.now();
                String zone= in.nextLine();
                LocalDate due=citation_date.plusDays(30);
                String phone = "", univ = "";
                String c = String.format("insert into Citation (citation_time, citation_date, car_license_nunber, violation_category, fees, Due, zone_designation, address, name, model, color) Values(TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS'),To_Date('%s', 'YYYY-MM-DD'),'%s','%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s','%s','%s','%s','%s')", t, citation_date, li, violation_category, fees, due, zone, address, name, model, color);
                System.out.println(c);
                this.stmt.execute(c);
                rs = this.stmt.executeQuery("select * from Citation where citation_no = (select max(citation_no) from citation)");
                String ci_no="";
                if(rs.next()){
                     ci_no = rs.getString("citation_no");
                }
                String n = String.format("insert into Notification (citation_no, PhoneNumber, univ) values ('%s','%s','%s')", ci_no, phone, univ);
                this.stmt.execute(n);
//                rs = this.stmt.executeQuery("select * from Notification where NotificationNumber = (select max(NotificationNumber) from Notification)");
//                if(rs.next()){
//
//                    System.out.println(rs.getString("NotificationNumber"));
//                }
            }
            else{
                Date et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rs.getString("expiry_time"));
                //Date d = new SimpleDateFormat("YYYY-MM-DD HH24:MI:SS").format(new Date());
                Date d = new Date();
                if(!et.after(d)){
                    System.out.println(et.toString());
                    System.out.println("Enter lot info to see if it is properly parked");
                    String name= in.nextLine();
                    String address= in.nextLine();
                    String zone= in.nextLine();
                    //check for the valid parking
//                    if(name.equals(rs.getString("name")) && address.equals(rs.getString("address")) && zone.equals(rs.getString("zone_designation"))) {
//                        // check time
//                    }
//                    else {
//                        //issue invalid permit citation
//                    }
                }
                else
                {
                    System.out.println("Permit expired");
//                    String c = "insert into Citation Values()";
//                    String n = "insert into Notification Values()";
                    String t = new Timestamp(System.currentTimeMillis()).toString().split("\\.")[0];
                    String c = "";

                        c = String.format("insert into Citation (citation_time, citation_date, car_license_nunber, violation_category, fees, Due, zone_designation, address, name, model, color) Values(TO_TIMESTAMP('%s','YYYY-MM-DD HH24:MI:SS'),To_Date('%s', 'YYYY-MM-DD'),'%s','%s','%s',TO_DATE('%s','YYYY-MM-DD'),'%s','%s','%s','%s','%s')", t, LocalDate.now(), li, "Expired", "25", LocalDate.now().plusDays(30), rs.getString("zone_designation"), rs.getString("address"), rs.getString("name"), rs.getString("model"), rs.getString("color"));

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
                    this.stmt.execute(n);
                    System.out.println("Citation issued" + ci_no);
                }

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
//permit_id VARCHAR(8), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number varchar(10), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (permit_id, vehicle_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";
    private void getVisitorPermit() {
//        String licensePlate, String type, String lotName, String duration
        try {
            System.out.println("Permit Information:");
            String query = "select * from Permit P where P.permit_id = `" + permitId + "`";
            ResultSet rs = this.stmt.executeQuery(query);
            while (rs.next()) {
                //Retrieve by column name
                String permitid = rs.getString("permit_id");
                String zone = rs.getString("zone");
                String start_date = rs.getString("start_date");
                String space_type = rs.getString("space_type");
                String expiry_date = rs.getString("expiry_date");
                String expiry_time = rs.getString("expiry_time");
                //Display values
                System.out.print("Permit id: " + permitid);
                System.out.print(", Zone: " + zone);
                System.out.print(", Start Date: " + start_date);
                System.out.println(", Space Type: " + space_type);
                System.out.println(", Expiry Date: " + expiry_date);
                System.out.println(", Expiry Time: " + expiry_time);
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
            this.stmt.executeUpdate("UPDATE Citation SET status = 1 where citation_no =" + ci);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("paycitation");
    }

    private void exitLot() {
//        String permitNumber
        System.out.println("exitLot");
    }



    public void setupSchema(){

        try{

//            this.stmt.executeQuery("drop table Notification");
//            this.stmt.executeQuery("drop table Citation");
//            this.stmt.executeQuery("drop table Non_visitor");
//            this.stmt.executeQuery("drop table Visitor");
//            this.stmt.executeQuery("drop table Permit");
//            this.stmt.executeQuery("drop table Spaces");
//            this.stmt.executeQuery("drop table Parking_Lots");
//            this.stmt.executeQuery("drop sequence Citaion_seq");
//            this.stmt.executeQuery("drop sequence Notification_seq");
//            String citation_table = "CREATE TABLE Citation (citation_time TIMESTAMP(0), citation_date DATE, car_license_number VARCHAR(50), citation_no NUMBER(10,0) NOT NULL, violation_category VARCHAR(5), fees NUMBER(10, 0), PRIMARY KEY (citation_no))";
            String citation_seq = "CREATE SEQUENCE Citation_seq START WITH 1 INCREMENT BY 1";

            String citation_table = "CREATE TABLE Citation (model varchar(10), color char(20), citation_time TIMESTAMP(0), citation_date DATE, car_license_nunber VARCHAR(50), citation_no NUMBER(10, 0) NOT NULL, violation_category VARCHAR(10), fees NUMBER(10, 0), Due DATE, status NUMBER(1,0) DEFAULT 0, zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (citation_no), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";

            String notification_table = "CREATE TABLE Notification (citation_no number(10,0) NOT NULL, NotificationNumber NUMBER(10, 0) NOT NULL, PhoneNumber NUMBER(10, 0), univ NUMBER(10,0), PRIMARY KEY (NotificationNumber), FOREIGN KEY(citation_no) REFERENCES Citation (citation_no) ON DELETE CASCADE)";
            String noti_seq = "CREATE SEQUENCE Notification_seq START WITH 1 INCREMENT BY 1";
            //String vehicle_table = "CREATE TABLE Vehicle (car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number NUMBER(10, 0), PRIMARY KEY (vehicle_number) ON DELETE CASCADE)";
            String parking_lot_table = "CREATE TABLE Parking_Lots (zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20) UNIQUE, number_of_spaces NUMBER(10, 0), PRIMARY KEY (name, zone_designation, address))";

            String spaces_tables = "CREATE TABLE Spaces(space_number NUMBER(10, 0) NOT NULL, zone VARCHAR(10), designated_type VARCHAR(10) DEFAULT  'regular' NOT NULL, constraint designated_type_ct check(designated_type in ('regular', 'electric', 'handi')), occupied varchar(3) default 'no', constraint op_check check (occupied in ('yes', 'no')), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (name, zone_designation, address, space_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";

            String permit_table = "CREATE TABLE Permit (permit_id VARCHAR(8), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(2), car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number varchar(10), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (permit_id, vehicle_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";

            String non_visitor_table = "CREATE TABLE Non_Visitor(unvid NUMBER(10, 0), permit_id varchar(8), vehicle_number varchar(10), S_E varchar(2) default 'S' NOT NULL, constraint se_check check (S_E in ('S', 'E')), PRIMARY KEY(permit_id, vehicle_number), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number) ON DELETE CASCADE)";

            String visitor_table = "CREATE TABLE Visitor(permit_id varchar(8), vehicle_number varchar (10), Phone_number number(10,0) NOT NULL, zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE)";

//            String employee_table = "CREATE TABLE Employee(unvid NUMBER(10, 0), permit_id NUMBER(10, 0), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), primary_vehicle_number INT, flag VARCHAR(2), PRIMARY KEY(unvid, permit_id))";
//            String student_table = "CREATE TABLE Student(unvid INT, permit_id INT, zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), primary_vehicle_number INT, PRIMARY KEY(unvid, permit_id))";
//            String notify_relation = "CREATE TABLE Notify(CitationNumber INT NOT NULL, citation_no INT NOT NULL, PRIMARY KEY (CitationNumber, citation_no), FOREIGN KEY (citation_no) REFERENCES Citation(citation_no), FOREIGN KEY (CitationNumber) REFERENCES Notification(CitationNumber))";
//            String issuedat_relation = "CREATE TABLE IssuedAt(name VARCHAR(20), citation_no INT, PRIMARY KEY (name, citation_no), FOREIGN KEY (name) REFERENCES Parking_Lots(name), FOREIGN KEY (citation_no) REFERENCES Citation(citation_no))";
//            String issuedto_relation = "CREATE TABLE IssuedTo(citation_no INT, vehicle_number INT, PRIMARY KEY (vehicle_number, citation_no), FOREIGN KEY (citation_no) REFERENCES Citation(citation_no), FOREIGN KEY (vehicle_number) REFERENCES Vehicle(vehicle_number))";
//            String owns1_relation = "CREATE TABLE Owns1(unvid INT, permit_id INT, vehicle_number INT, PRIMARY KEY (unvid, permit_id, vehicle_number), FOREIGN KEY (unvid, permit_id) REFERENCES Employee(unvid, permit_id), FOREIGN KEY (vehicle_number) REFERENCES Vehicle(vehicle_number))";
//            String owns2_relation = "CREATE TABLE Owns2(unvid INT, permit_id INT, vehicle_number INT, PRIMARY KEY (unvid, permit_id, vehicle_number), FOREIGN KEY (unvid, permit_id) REFERENCES Student(unvid, permit_id), FOREIGN KEY (vehicle_number) REFERENCES Vehicle(vehicle_number))";
            //String assigned_relation = "CREATE TABLE Assigned(permit_id INT,space_number VARCHAR(20), name VARCHAR(20), PRIMARY KEY(permit_id, space_number), FOREIGN KEY (space_number, permit_id) REFERENCES Visitor(space_number, permit_id), FOREIGN KEY (name) REFERENCES Parking_lots(name))";
            //String made_of_relation = "CREATE TABLE made_of(space_number INT NOT NULL, zone VARCHAR(10), designated_type VARCHAR(5), name VARCHAR(20) PRIMARY KEY (name, space_number), FOREIGN KEY (name) REFERENCES Parking_Lots )";
            this.stmt.executeUpdate(parking_lot_table);
            this.stmt.executeUpdate(citation_table);
            this.stmt.executeUpdate(notification_table);
//          this.stmt.executeUpdate(vehicle_table);
            this.stmt.executeUpdate(spaces_tables);
            this.stmt.executeUpdate(permit_table);
            this.stmt.executeUpdate(non_visitor_table);
            this.stmt.executeUpdate(visitor_table);
            //this.stmt.executeUpdate(employee_table);
            //this.stmt.executeUpdate(student_table);
            //this.stmt.executeUpdate(notify_relation);
            //this.stmt.executeUpdate(issuedat_relation);
            //this.stmt.executeUpdate(issuedto_relation);
            //this.stmt.executeUpdate(owns1_relation);
            //this.stmt.executeUpdate(owns2_relation);
//            this.stmt.executeUpdate(assigned_relation);
            //STEP 5: Extract data from result set
//            this.stmt.execute(noti_seq);
//            this.stmt.execute(citation_seq);

        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(this.stmt!=null)
                    this.stmt.close();
            }catch(SQLException se2){
            }// nothing we can do

        }//end try
        //System.out.println("Goodbye!");
        System.out.println("All the tables are ready");
    }

    public static void main(String[] args) throws SQLException {
	// write your code here
        Main o = new Main();
//        o.setupSchema();
        while(true) {
            System.out.println("Who are you?");
            System.out.println("1. UPS Employee");
            System.out.println("2. Student");
            System.out.println("3. Employee");
            System.out.println("4. Visitor");
            System.out.println("Any other number to exit");
            int u = in.nextInt();
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
