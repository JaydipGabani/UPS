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
                    this.issueCitaion();
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
        System.out.println("changeRmpVehicleList");
    }

    private void checkNVValidParking() {
        System.out.println("check");
    }

    private void checkVValidParking() {
        System.out.println("check2");
    }

    private void assignPermit() {
        System.out.println("ap");
    }

    private void assignTypeToSpace() {
        System.out.println("atts");
    }

    private void assignZoneToLot() {
        System.out.println("aztl");
    }

    private void addLot() {
        System.out.println("al");
    }

    private void issueCitaion() {
        System.out.println("ic");
    }

    public void visitorFunction(){
        Scanner input = new Scanner(System.in);
        while(true){
            System.out.println("1. exitLot");
            System.out.println("2. PayCitation");
            System.out.println("Any other number to go back");
            int u = input.nextInt();
            switch (u){
                case 1:
                    this.exitLot();
                    break;
                case 2:
                    this.payCitation();
                    break;
                default:
                    return;
            }
        }
    }

    private void payCitation() {
        System.out.println("paycitation");
    }

    private void exitLot() {
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
