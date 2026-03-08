/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package milestone.pkg2;

/**
 *
 * @authors 
 * Paderes, Juan Gabriel   --- jgpaderes
 * Yasoña, Keichelle       --- Keichelle
 * Mansueto, Jabez Anthony --- JabezAnthony
 * Layug, Katrina Carell   --- katlayug
 * 
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.time.YearMonth;
import java.time.LocalTime;
import java.time.Month;
import java.time.Duration;
import java.util.HashMap;


public class Milestone2 {
    
    static HashMap<String, String[]> dataStorage = new HashMap<>();
    // Initializes a Hashmap to store the employee data.

    public static void getEmployeeData(){
            boolean checker = false;
            Scanner input = new Scanner(System.in);
        
            System.out.print("Enter your Employee Number: ");
            String employeeNumber = input.nextLine();
            
            String[] tempArray = dataStorage.get(employeeNumber);
               
                for (String empNum : dataStorage.keySet()) {
                
                    if (empNum.equals(employeeNumber)){
                        checker = true;
                        System.out.println("************************************** \n"); 
                        System.out.println("Employee Number: " + empNum +"\n" + "Employee Name: " + tempArray[0] + "\n" + "Birthday: " + tempArray[1] + "\n");
                        System.out.println("**************************************"); 
                        break;
                    }       
                }
                if (checker != true){
                    System.out.println("Employee Number does not exist!");
                }
    }
    public static void getSingleSalary(String employeeNumber){
            String filePath = "src/Resources/AttendanceData.csv";
            String column;
            String delimiter = ",";
            DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
            boolean checker = false;
            double firstHalf = 0;
            double secondHalf = 0; 
            int monthCount = 6;

            
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                
                br.readLine();
                // Added to skip the header values
                while ((column = br.readLine()) != null) {
                    String[] values = column.split(delimiter);
                   
                    
                    if (values[0].equals(employeeNumber)){
                        String[] dateSplit = values[3].split("/");
                        int monthNo = Integer.parseInt(dateSplit[0]);
                        int date = Integer.parseInt(dateSplit[1]);
                        LocalTime logIn = LocalTime.parse(values[4], format); LocalTime logOut = LocalTime.parse(values[5], format);
                        double hours = getHours(logIn,logOut);
                        checker = true;
                         // Add a checker value that returns true if the Employee number is found

                        
                        if (monthCount <= 12){
                            
                            if (monthCount != monthNo){ 
                                printPayroll(firstHalf,secondHalf,employeeNumber,monthCount);
                                // Prints payroll summary at the end of each month
                                monthCount++;
                                firstHalf = 0;
                                secondHalf = 0;
                                // Resets the total every start of the new month
                                
                                
                            }   
                            if(date <=15){
                                    firstHalf += hours;
                                    

                            }else{
                                    secondHalf += hours;
                            }
                        }
                    }         
                }
                if (checker != true){
                    System.out.println("Employee Number does not exist.");
                    return;
                    // Checks the checker value if the Employee Number exists. If not, ends the loop.

                }
                printPayroll(firstHalf,secondHalf,employeeNumber,monthCount);
                // Repeated outside the while loop to catch December as the loop stops after reading the last item in the CSV

            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
     } 
    public static String getRate(String employeeNumber){
            String[] tempArray = dataStorage.get(employeeNumber);
            String rate = null;
                for (String empNum : dataStorage.keySet()) {
                
                    if (empNum.equals(employeeNumber)){
                        rate = tempArray[2]; 
                    }
                }
        return rate;
        // Gets the array from the hashmap (dataStorage) based on the key (employeeNumber). Returns the 3rd values in the array which is the rate
    }
    
    public static double getHours(LocalTime logIn, LocalTime logOut){
        LocalTime gracePeriod = LocalTime.of(8,5);
        LocalTime cutOff = LocalTime.of(17,0);
        // Adds a grace period time of 8:05 and a cutoff time of 17:00.
        if(logOut.isAfter(cutOff)){
            logOut = cutOff;
        }    
        if(logIn.isBefore(gracePeriod)){
            logIn = LocalTime.of(8,0);
        
        }
        Duration timeDifference = Duration.between(logIn,logOut);
        double diffHours = timeDifference.toHours();      
        double diffMinutes = timeDifference.toMinutes() % 60;
        double hoursWorked = (diffHours + (diffMinutes / 60));
        // Reads the difference in duration in H : mm then converts the mm value to decimal 
        
        if (hoursWorked > 1){
            hoursWorked -= 1;
            // Subtracts an hour for lunch considering the hours worked is above an hour.
        }

        if ( hoursWorked > 8.0){
            hoursWorked = 8.0;
            return hoursWorked;
            // Checks for overtime, if it happens, returns the max working hours which is 8.
        }else{
            return hoursWorked;
        }
    }
    public static void storeEmployeeData(){
   
            String filePath = "src/Resources/EmployeeData.csv";
            String column;
            String delimiter = ",";

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

            br.readLine();
                // Added to skip the header values

                while ((column = br.readLine()) != null) {

                    String[] values = column.split(delimiter);
                    int len = values.length;
                    String empNumber = values[0];
                    String name = (values[1] +", "+ values[2]);
                    String birthday = values[3];
                    String rate = values[len-1];

                dataStorage.put(empNumber, new String[]{name, birthday, rate});
            }

        } catch (IOException e) {   
        }    
    }     
    public static void getAllSalary(){
            String filePath = "src/Resources/AttendanceData.csv";
            String column;
            String delimiter = ",";
            DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
             
            
            String employeeNumber = null;
            int empCheck = 10001;
            String lastId = null;
            
            do{
                // Used Do-while loop to force the loop to run once and get the last ID value
                double firstHalf = 0;
                double secondHalf = 0;
                int monthCount = 6;
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                
                    br.readLine();
                // Added to skip the header values
     
                    while ((column = br.readLine()) != null) {
                        String[] values = column.split(delimiter);
                   
                    
                        employeeNumber = values[0];
                        
                        if (empCheck == (Integer.parseInt(employeeNumber))){

                            String[] dateSplit = values[3].split("/");
                            int monthNo = Integer.parseInt(dateSplit[0]);
                            int date = Integer.parseInt(dateSplit[1]);
                            LocalTime logIn = LocalTime.parse(values[4], format); LocalTime logOut = LocalTime.parse(values[5], format);
                            double hours = getHours(logIn,logOut);
                    
                        
                        
                            if (monthCount <= 12){
                            
                                if (monthCount != monthNo){  
                                    printPayroll(firstHalf,secondHalf,employeeNumber,monthCount);
                                    // Checks for unequal months to know to print the payroll summary at the end of each month
                                    monthCount++;
                                    firstHalf = 0;
                                    secondHalf = 0;
                                    // Resets the total every start of the new month
                                
                                
                                }   
                                if(date <=15){
                                    firstHalf += hours;
                                    

                                }else{
                                    secondHalf += hours;
                                    
                                }
                            }    
                        }
                    lastId = values[0];      
                    // Gets the last Employee number from the list to use as an end case for the while loop.
                }
                printPayroll(firstHalf,secondHalf,employeeNumber,monthCount);
                empCheck += 1;
                
                // Repeated outside the while loop to catch December as the loop stops after reading the last item in the CSV
 
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
        
        }while (empCheck <= Integer.parseInt(lastId));
    }
    
    public static double getSSS(double grossSalary){
        double baseRange = 3250;
        double contribution = 135.00;
        
        while (baseRange <= 24750){
            if (grossSalary <= baseRange){
                return contribution;
            }
            else{
                baseRange += 500;
                contribution += 22.50;
                // Uses the constant difference between the value range and contribution amount to add it to itself until the value cap is reached.
            }  
        }
        return contribution;
    }
    
    public static double getPhilHealth(double grossSalary){
        double contribution = 0;
        if (grossSalary >= 60000 ){
            contribution = ((grossSalary * 0.03) / 2);
                if (contribution > 1800){
                    contribution = (1800 / 2);  
                }            
        }
        if (grossSalary > 10000.01 && grossSalary < 59999.99){
            contribution = ((grossSalary *0.03)/ 2);
        }
        if (grossSalary <= 10000 ){
            contribution = ((grossSalary * 0.03) / 2);
                if (contribution > 300){
                    contribution = (300 / 2);
                }
        }
        return contribution;
    }
    
    public static double getPagIbig(double grossSalary){
        double contribution = 0;
        if (grossSalary > 1500){
            contribution = (grossSalary * 0.02);
            if (contribution > 100){
                contribution = 100;  
            }    
        }
        if (grossSalary >= 1000 && grossSalary <= 1500){
            contribution = (grossSalary *0.02);
        }
        if (grossSalary < 1000 ){
            contribution = 0;
            }
        return contribution;      
    }
    
    public static double getWithholdingTax(double grossSalary){
        double contribution = 0;
        double taxableIncome = (grossSalary - (getSSS(grossSalary) + getPhilHealth(grossSalary) + getPagIbig(grossSalary)));

        if (grossSalary > 666667){
            contribution = (200833.33 + ((taxableIncome - 666667) *.35 ));
        }
        if (grossSalary >= 166667 && grossSalary < 666667){    
            contribution = (40833.33+ ((taxableIncome - 166667) *.32 ));    
        }
        if (grossSalary >= 66667 && grossSalary < 166667){
            contribution = (10833 + ((taxableIncome - 66667) *.30 ));    
        }
        if (grossSalary >= 33333 && grossSalary < 66667){
            contribution = (2500 + ((taxableIncome - 33333) *.25 ));    
        }
        if (grossSalary >= 20833 && grossSalary < 33333){
            contribution = ((taxableIncome - 20833) *.20 );    
        }
        if (grossSalary <= 20832){
            return contribution;
        }

        return contribution;
    }
    public static void readEmployeeData(String employeeNumber){
                
                String[] tempArray = dataStorage.get(employeeNumber);
                for (String empNum : dataStorage.keySet()) {
                
                    if (empNum.equals(employeeNumber)){
                       
                        System.out.println("Employee Number: " + empNum +"\n" + "Employee Name: " + tempArray[0] + "\n" + "Birthday: " + tempArray[1] + "\n");
                    }
        }
    }
    public static double firstGrossSalary(double firstHalf, String employeeNumber){
        return firstHalf * Double.parseDouble(getRate(employeeNumber));
    }
    public static double secondGrossSalary(double secondHalf, String employeeNumber){
        return secondHalf * Double.parseDouble(getRate(employeeNumber));
    }
    
    public static double grossSalary(double firstGrossSalary, double secondGrossSalary){

            return firstGrossSalary + secondGrossSalary;
    }
    public static double totalDeductions(double grossSalary, double firstHalf, double secondHalf, String employeeNumber){
        return getSSS(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber))) +
               getPhilHealth(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)))+
               getPagIbig(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)))+
               getWithholdingTax(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)));
    }
    public static void printPayroll(double firstHalf, double secondHalf, String employeeNumber, int monthCount){
            System.out.println("**************************************\n");
            readEmployeeData(String.valueOf(employeeNumber));
            // Prints the employee data based on the employee number.
            System.out.println("Cutoff Date: " + Month.of(monthCount) + " 1 to " + Month.of(monthCount)+" 15");
            System.out.println("Total Hours Worked: " + firstHalf);
            System.out.println("Gross Salary: " + firstGrossSalary(firstHalf,employeeNumber));
            System.out.println("Net Salary: "+ firstGrossSalary(firstHalf,employeeNumber));
            System.out.println("Cutoff Date: " + Month.of(monthCount) + " 16 to " + Month.of(monthCount)+" "+ YearMonth.of(2024, monthCount).lengthOfMonth());
            System.out.println("Total Hours Worked: " + secondHalf);
            System.out.println("Gross Salary: " +  secondGrossSalary(secondHalf,employeeNumber));
            System.out.println("Each Decuction:");
            System.out.println("\tSSS: " + getSSS(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber))));
            System.out.println("\tPhilHealth: " + getPhilHealth(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber))));
            System.out.println("\tPag-IBIG: "+ getPagIbig(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber))));
            System.out.println("\tTax: "+ getWithholdingTax(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber))));
            System.out.println("Total Deductions: " + totalDeductions(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)), firstHalf, secondHalf, employeeNumber));
            System.out.println("Net Salary: " + (grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)) - totalDeductions(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)), firstHalf, secondHalf, employeeNumber)));
            System.out.println("\n**************************************");
    }
    
    public static void main(String[] args){
        storeEmployeeData();
        // Reads the employee data once and stores the needed information.
      
        Scanner input = new Scanner(System.in);
        
        System.out.print("Enter your username:  ");
        String workerType = input.nextLine();
        
        
        if(workerType.isEmpty()){
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
                        System.out.println("Incorrect username and/or password.");
                        break;
                    }                     
                case "payroll_staff":                  
                    System.out.print("Enter your password:  ");
                    String payrollPassword = input.nextLine();
                    if("12345".equals(payrollPassword)){
                        System.out.println("************************************** \n"); 
                        System.out.println("[1]. Process Payroll");
                        System.out.println("[2]. Exit the Program\n");
                        System.out.println("**************************************");
                        
                        System.out.print("What do you want to do? ");
                        String choicePayroll = input.nextLine();
                        
                        switch (choicePayroll){
                            case "1" : 
                                System.out.println("************************************** \n"); 
                                System.out.println("[1]. One employee");
                                System.out.println("[2]. All Employees");
                                System.out.println("[3]. Exit the Program\n");
                                System.out.println("**************************************");
                                
                                System.out.print("How many do you want to process? ");
                                String choiceEmployee = input.nextLine();
                                
                                switch (choiceEmployee){
                                    case "1" : 
                                        System.out.println("************************************** \n"); 
                                        System.out.println("[1]. Enter the Employee Number.");
                                        System.out.println("[2]. Exit the Program\n");
                                        System.out.println("**************************************");
                                        System.out.print("What do you want to do? ");
                                        String choice = input.nextLine();

                                        switch (choice){
                                            
                                            case "1" :
                                                Scanner empInput = new Scanner(System.in);   
                                                System.out.print("Input Employee No: ");
                                                String employeeNumber = empInput.nextLine();
                                                getSingleSalary(employeeNumber);
                                                break;
                                                
                                            case "2" :
                                                System.exit(0);     
                                        default : 
                                            System.out.println("Invalid Input!");
                                            break;
                                        }    
                                        break;
                                    case "2" :
                                        getAllSalary();
                                        break;
                                        
                                    case "3" : 
                                        break;
                                        
                                    default : 
                                        System.out.println("Invalid Input!");  
                                }
                                break;
                            
                            case "2" :
                                break;
                             
                            default: 
                                System.out.println("Invalid Input!");             
                        }   
    
                        break;
                    }else{
                        System.out.println("Incorrect username and/or password.");   
                        break;
                    }
                default: 
                    System.out.println("Incorrect username and/or password.");
            }
        } 
    } 
}  