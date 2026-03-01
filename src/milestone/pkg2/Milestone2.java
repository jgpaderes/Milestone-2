/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package milestone.pkg2;

/**
 *
 * @author Gab Paderes
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Milestone2 {

    public static void getEmployeeData(){
            String filePath = "src/Resources/EmployeeData.csv";
            String column;
            String delimiter = ",";
            boolean checker = false;
            
            
            Scanner input = new Scanner(System.in);

            System.out.print("Input Employee No: ");

            String employeeNumber = input.nextLine();

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                
                br.readLine();
                // Added to skip the header values
                while ((column = br.readLine()) != null) {
                    String[] values = column.split(delimiter);
                    
                    
                    String[] employeeInfo;
                    employeeInfo = new String[4];
                    // Initiates an array to fill in the necessary info
                    int i = 0;
   
                    if (values[0].equals(employeeNumber)){ 
                        checker = true;    
                    
                        for (String data : values){
          
                            employeeInfo[i] = data;   
                            i++;
                            if (i == 4){
                                System.out.println("************************************** \n"); 
                                System.out.println("Employee Number: " + employeeInfo[0] +"\n" + "Employee Name: " + employeeInfo[1] + ", " + employeeInfo[2] + "\n" + "Birthday: " + employeeInfo[3] + "\n");
                                System.out.println("**************************************");   
                                break;
                            }                
                        }     
                    }
                }
                if (checker != true){
                    System.out.println("Employee does not exist.");
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
    }
 
    public static void main(String[] args) {
      
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter your username:  ");
        String workerType = input.nextLine();
        
        
        if(null == workerType){
            System.out.println("Invalid Username!");
          
        }else{
            switch (workerType) {
                case "employee" :               
                    System.out.print("Enter your password:  ");
                    String password = input.nextLine();
                    if("12345".equals(password)){
                        getEmployeeData();
                        break;
                    }else{
                        System.out.println("Invalid Password!");
                        break;
                    }                     
                case "payroll_staff":                  
                    System.out.print("Enter your password:  ");
                    String pswrd = input.nextLine();
                    if("12345".equals(pswrd)){
                        System.out.println("************************************** \n"); 
                        System.out.println("1. Process Payroll");
                        System.out.println("2. Exit the Program\n");
                        System.out.println("**************************************");
                        
                        System.out.print("What do you want to do? ");
                        String choice = input.nextLine();
                        
                        switch (choice){
                            case "1" : 
                                System.out.println("WIP");
                                break;
                            
                            case "2" :
                                break;
                             
                            default: 
                                System.out.println("Invalid Input!");
                        }       
                        break;
                    }else{
                        System.out.println("Invalid Password!");   
                        break;
                    }
                default: 
                    System.out.println("Invalid Username!");
            }
        } 
    } 
}
