package com.company;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.sql.*;

public class Main {
    public Statement stmt = null;
    public static Scanner in = new Scanner(System.in);
    public Main(){
        String jdbcURL
                = "jdbc:oracle:thin:@orca.csc.ncsu.edu:1521:orcl01";
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");

            String user = "jgabani";    // For example, "jsmith"
            String passwd = "abcd1234";    // Your 9 digit student ID number


            ResultSet rs = null;
            conn = DriverManager.getConnection(jdbcURL, user, passwd);

            // Create a statement object that will be sending your
            // SQL statements to the DBMS

            this.stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void loginEmployee(int u){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your univid");
        //int uni = input.nextInt();
        //this.login(uni);
        this.employeeFunction();
    }

    private void employeeFunction() {
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.println("1. ChangeVehicleList");
            System.out.println("2. PayCitation");
            System.out.println("Any other number to go back");
            int u = input.nextInt();
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
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your univid");
        //int uni = input.nextInt();
        //this.login(uni);
        this.upsFunctions();

    }

    public void loginStudent(int u){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter your univid");
        //int uni = input.nextInt();
        //this.login(uni);
        this.studentFunction();
    }

    private void login(int uni) {
        // login logic
    }

    private void studentFunction() {
        Scanner input = new Scanner(System.in);
        while (true){
            System.out.println("1. ChangeVehicleList");
            System.out.println("2. PayCitation");
            System.out.println("Any other number to go back");
            int u = input.nextInt();
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
        System.out.println("changeStudentVehicleList");
         String carmanufacturer = in.nextLine();
         String model = in.nextLine();
         int year = in.nextInt();
         String color = in.nextLine();
         String vehiclenumber = in.nextLine();
        String query = "Update Permit P SET P.car_manufacturer = '" + carmanufacturer + "', P.model = '" + model +"', P.year = '"+year+"', P.color = '" + color +"', P.vehicle_number = '" +vehiclenumber+"' WHERE P.permit_id = '"+permitId+"'";
        this.stmt.executeUpdate(query);
        System.out.println("Updated the Student's Vehicle List");
    }


    public void upsFunctions(){
        //issueCitation()
        //addLot()
        //assignZoneToLot
        //assignTypeToSpace()
        //assignPermit()
        //CheckVValidParking()
        //CheckNVValidParking()
        Scanner input = new Scanner(System.in);
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
            int u = input.nextInt();
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
//        String permitNumber, int univId, boolean addOrRemove, String make, String model, String year, String color, String vehicleNumber
        System.out.println("changeRmpVehicleList");
    }

    private void checkNVValidParking() {
//        String currentTime, java.sql.Date date, String lotName, String type, String licensePlate
        System.out.println("checkNVValidParking");
    }

    private void checkVValidParking() {
//        String currentTime, java.sql.Date date, String lotName, int spaceNumber, String type, String licensePlate
        System.out.println("checkVValidParking");
    }

    private void assignPermit() {
//        int univId, String zone, String type, String vehicleNumber
        System.out.println("assignPermit");
    }

    private void assignTypeToSpace() {
//        String lotName, int spaceNumber, String type
        System.out.println("assignTypeToSpace");
    }

    private void assignZoneToLot() {
//        String name, String Designation, int numberOfSpaces, int beginNumber
        System.out.println("assignZoneToLot");
    }

    private void addLot() {
//        String name, String address, int numberOfSpaces, int beginNumber, String initialDesignation
        System.out.println("addLot");
    }

    private void issueCitation() {
//        String licensePlate, String violationCategory
        System.out.println("issueCitation");
    }

    public void visitorFunction(){
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("1. exitLot");
            System.out.println("2. PayCitation");
            System.out.println("3. getVisitorPermit");
            System.out.println("Any other number to go back");
            int u = input.nextInt();
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

    private void getVisitorPermit() {
//        String licensePlate, String type, String lotName, String duration
        System.out.println("getVisitorPermit");
    }

    private void payCitation() {
//        String citationNumber
        System.out.println("paycitation");
    }

    private void exitLot() {
//        String permitNumber
        System.out.println("exitLot");
    }



    public void setupSchema(){

        try{



            String citation_table = "CREATE TABLE Citation (citation_time TIMESTAMP(0), citation_date DATE, car_license_nunber VARCHAR(50), citation_no NUMBER(10, 0) NOT NULL, violation_category VARCHAR(5), paid_flag VARCHAR(2), fees NUMBER(10, 0), PRIMARY KEY (citation_no))";
            String notification_table = "CREATE TABLE Notification (citation_no number(10,0) NOT NULL, NotificationNumber NUMBER(10, 0) NOT NULL, PhoneNumber NUMBER(10, 0) NOT NULL, PRIMARY KEY (NotificationNumber), FOREIGN KEY(citation_no) REFERENCES Citation (citation_no) ON DELETE CASCADE)";
            //String vehicle_table = "CREATE TABLE Vehicle (car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number NUMBER(10, 0), PRIMARY KEY (vehicle_number) ON DELETE CASCADE)";
            String parking_lot_table = "CREATE TABLE Parking_Lots (zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (name, zone_designation, address))";

            String spaces_tables = "CREATE TABLE Spaces(space_number NUMBER(10, 0) NOT NULL, zone VARCHAR(10), designated_type VARCHAR(5), zone_designation VARCHAR(10), address VARCHAR(50), name VARCHAR(20), PRIMARY KEY (name, zone_designation, address, space_number), FOREIGN KEY (name, zone_designation, address) REFERENCES Parking_Lots(name, zone_designation, address) ON DELETE CASCADE)";

            String permit_table = "CREATE TABLE Permit(permit_id VARCHAR(8), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), car_manufacturer VARCHAR(20), model VARCHAR(10), year NUMBER(10, 0), color CHAR(20), vehicle_number varchar(10), PRIMARY KEY (permit_id, vehicle_number))";

            String non_visitor_table = "CREATE TABLE Non_Visitor(unvid NUMBER(10, 0), permit_id varchar(8), vehicle_number varchar(10), PRIMARY KEY(permit_id, vehicle_number), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number) ON DELETE CASCADE)";

            String visitor_table = "CREATE TABLE Visitor(permit_id varchar(8), vehicle_number varchar (10), lot VARCHAR(5), space_number VARCHAR(20), PRIMARY KEY (vehicle_number, permit_id), FOREIGN KEY (permit_id, vehicle_number) REFERENCES Permit(permit_id, vehicle_number)ON DELETE CASCADE)";

//            String employee_table = "CREATE TABLE Employee(unvid NUMBER(10, 0), permit_id NUMBER(10, 0), zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), primary_vehicle_number INT, flag VARCHAR(2), PRIMARY KEY(unvid, permit_id))";
//            String student_table = "CREATE TABLE Student(unvid INT, permit_id INT, zone VARCHAR(10), start_date DATE, space_type VARCHAR(10), expiry_date DATE, expiry_time TIMESTAMP(0), primary_vehicle_number INT, PRIMARY KEY(unvid, permit_id))";
//            String notify_relation = "CREATE TABLE Notify(CitationNumber INT NOT NULL, citation_no INT NOT NULL, PRIMARY KEY (CitationNumber, citation_no), FOREIGN KEY (citation_no) REFERENCES Citation(citation_no), FOREIGN KEY (CitationNumber) REFERENCES Notification(CitationNumber))";
//            String issuedat_relation = "CREATE TABLE IssuedAt(name VARCHAR(20), citation_no INT, PRIMARY KEY (name, citation_no), FOREIGN KEY (name) REFERENCES Parking_Lots(name), FOREIGN KEY (citation_no) REFERENCES Citation(citation_no))";
//            String issuedto_relation = "CREATE TABLE IssuedTo(citation_no INT, vehicle_number INT, PRIMARY KEY (vehicle_number, citation_no), FOREIGN KEY (citation_no) REFERENCES Citation(citation_no), FOREIGN KEY (vehicle_number) REFERENCES Vehicle(vehicle_number))";
//            String owns1_relation = "CREATE TABLE Owns1(unvid INT, permit_id INT, vehicle_number INT, PRIMARY KEY (unvid, permit_id, vehicle_number), FOREIGN KEY (unvid, permit_id) REFERENCES Employee(unvid, permit_id), FOREIGN KEY (vehicle_number) REFERENCES Vehicle(vehicle_number))";
//            String owns2_relation = "CREATE TABLE Owns2(unvid INT, permit_id INT, vehicle_number INT, PRIMARY KEY (unvid, permit_id, vehicle_number), FOREIGN KEY (unvid, permit_id) REFERENCES Student(unvid, permit_id), FOREIGN KEY (vehicle_number) REFERENCES Vehicle(vehicle_number))";
            //String assigned_relation = "CREATE TABLE Assigned(permit_id INT,space_number VARCHAR(20), name VARCHAR(20), PRIMARY KEY(permit_id, space_number), FOREIGN KEY (space_number, permit_id) REFERENCES Visitor(space_number, permit_id), FOREIGN KEY (name) REFERENCES Parking_lots(name))";
            //String made_of_relation = "CREATE TABLE made_of(space_number INT NOT NULL, zone VARCHAR(10), designated_type VARCHAR(5), name VARCHAR(20) PRIMARY KEY (name, space_number), FOREIGN KEY (name) REFERENCES Parking_Lots )";
            this.stmt.executeUpdate(citation_table);
            this.stmt.executeUpdate(notification_table);
//            this.stmt.executeUpdate(vehicle_table);
            this.stmt.executeUpdate(parking_lot_table);
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
        Scanner input = new Scanner(System.in);
        Main o = new Main();
        //o.setupSchema();
        while(true) {
            System.out.println("Who are you?");
            System.out.println("1. UPS Employee");
            System.out.println("2. Student");
            System.out.println("3. Employee");
            System.out.println("4. Visitor");
            System.out.println("Any other number to exit");
            int u = input.nextInt();
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
