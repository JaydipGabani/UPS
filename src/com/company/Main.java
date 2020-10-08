package com.company;
import java.util.*;
public class Main {

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
        System.out.println("all the tables are ready");
    }

    public static void main(String[] args) {
	// write your code here
        Scanner input = new Scanner(System.in);
        Main o = new Main();
        o.setupSchema();
        while(true) {
            System.out.println("Who are you?");
            System.out.println("1. UPS");
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
                    System.exit(0);
                    break;
            }
        }
    }
}
