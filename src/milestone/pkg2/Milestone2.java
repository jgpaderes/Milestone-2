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
import java.util.ArrayList;
import java.util.HashMap;



public class Milestone2 {
    
    static HashMap<String, String[]> dataStorage = new HashMap<>();
    // Initializes a Hashmap to store the necessary employee data.
    // Stored outside in order to be accessible to every method.
    
    static ArrayList<String[]> attendanceStorage = new ArrayList<>();
    // Initializes an ArrayList to store the necessary attendance data.
    // Stored outside in order to be accessible to every method.

    public static void getEmployeeData(String employeeNumber){
            boolean checker = false;

                for (String empNum : dataStorage.keySet()) {
                    // Checks the keys of the array (employee number) which connects to the array that holds the employee data.
                    String[] tempArray = dataStorage.get(empNum);
                    // Sets a temporary array to store the values from the dataStorage hashmap based on the key (employee number).
                    if (empNum.equals(employeeNumber)){
                        checker = true;
                        System.out.println("************************************** \n"); 
                        System.out.println("Employee Number: " + empNum +"\n" + "Employee Name: " + tempArray[0] + "\n" + "Birthday: " + tempArray[1] + "\n");
                        System.out.println("**************************************"); 
                        break;
                    }       
                }
                if (checker != true){
                    System.out.println("Employee number does not exist.");
                    
                 // Checks the checker value is returns false. If it does, exits the loop and prints "Employee Number does not exist.    
                 
                }
            // Uses the dataStorage hashmap to read the employee data and prints the necessary ones.
    }
    public static void getSingleSalary(String employeeNumber){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
            boolean checker = false;
            double firstHalf = 0;
            double secondHalf = 0; 
            int monthCount = 6;
        
            for (String row[] : attendanceStorage){
                // Reads each array inside attendanceStorage one by one.
                // Each array contains one line from the CSV file.
                if (row[0].equals(employeeNumber)){
                    
                    String[] dateSplit = row[3].split("/");
                    int monthNo = Integer.parseInt(dateSplit[0]);
                    int date = Integer.parseInt(dateSplit[1]);
                    LocalTime logIn = LocalTime.parse(row[4], format); LocalTime logOut = LocalTime.parse(row[5], format);
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
                            
                            // Sets the first and second half values to 0 at the end of each month to prepare them for use for the following month.
     
                        }   
                        if(date <=15){
                            firstHalf += hours;
                                    

                        }else{
                            secondHalf += hours;
                        }
                        // Uses the variables "firstHalf" and "secondHalf" as a placholder variable to hold the hours value and add it to itself until the set cutoff date is reached.
                        // (firstHalf = firstHalf + hours || secondHalf = secondHalf + hours)
                        }
                }         
            }
            if (checker != true){
                System.out.println("Employee Number does not exist.");
                System.exit(0);
                // Checks the checker value is returns false. If it does, exits the loop and prints "Employee Number does not exist. 
            }   
            printPayroll(firstHalf,secondHalf,employeeNumber,monthCount); 
             // Repeated outside the while loop to catch December as the loop stops after reading the last item in the CSV
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
        // Gets the array from the hashmap (dataStorage) based on the key (employeeNumber). Returns the 3rd value in the array which is the rate
    }
    
    public static double getHours(LocalTime logIn, LocalTime logOut){
            LocalTime gracePeriod = LocalTime.of(8,5);
            LocalTime cutOff = LocalTime.of(17,0);
            // Adds a grace period time of 8:05 and a cutoff time of 17:00.
            if(logOut.isAfter(cutOff)){
                logOut = cutOff;
                // Checks if the logout time exceeds the cutoff. Sets the logout time to cutoff if true.
            }    
            if(logIn.isBefore(gracePeriod)){
                logIn = LocalTime.of(8,0);
                // Checks if the login time is within the grace period. Sets the login time 8:00.
        
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
     
    public static void getAllSalary(){
            DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");  
            String employeeNumber = null;
            int empCheck = 10001;
            String [] lastArray = null;
            String lastId = null;
            
            do{
                // Used Do-while loop to force the loop to run once and get the last ID value
                double firstHalf = 0;
                double secondHalf = 0;
                int monthCount = 6;
                lastArray = attendanceStorage.get(attendanceStorage.size()- 1);
                // Finds the last array inside the ArrayList (attendanceStorage).
                lastId = lastArray[0];
                // Gets the last value inside the last array to be used for the while conditional.
                for (String row[] : attendanceStorage){
                   
                        employeeNumber = row[0];
 
                        if (empCheck == (Integer.parseInt(employeeNumber))){

                            String[] dateSplit = row[3].split("/");
                            int monthNo = Integer.parseInt(dateSplit[0]);
                            int date = Integer.parseInt(dateSplit[1]);
                            LocalTime logIn = LocalTime.parse(row[4], format); LocalTime logOut = LocalTime.parse(row[5], format);
                            double hours = getHours(logIn,logOut);
                    
  
                            if (monthCount <= 12){
                            
                                if (monthCount != monthNo){  
                                    printPayroll(firstHalf,secondHalf,employeeNumber,monthCount);
                                    // Checks for unequal months to know to print the payroll summary at the end of each month
                                    monthCount++;
                                    firstHalf = 0;
                                    secondHalf = 0;
                                    // Sets the first and second half values to 0 at the end of each month to prepare them for use for the following month.
                                
                                }   
                                if(date <=15){
                                    firstHalf += hours;
                                    

                                }else{
                                    secondHalf += hours;
                                    
                                }
                            // Uses the variables "firstHalf" and "secondHalf" as a placholder variable to hold the hours value and add it to itself until the set cutoff date is reached.

                            }    
                        }

                }    
 
                printPayroll(firstHalf,secondHalf,employeeNumber,monthCount);
                empCheck += 1;
                
                // Repeated outside the while loop to catch December as the loop stops after reading the last item in the CSV
        }while (empCheck <= Integer.parseInt(lastId));
            // Continues until the checker value (empCheck) goes above the last id
            // Breaks the loop going above the last id.
    }
    
    public static double getSSS(double grossSalary){
            double baseRange = 3250;
            double SSSContribution = 135.00;
        
            while (baseRange <= 24750){
                if (grossSalary <= baseRange){
                    return SSSContribution;
                
                }else{
                baseRange += 500;
                SSSContribution += 22.50;
                }  
            }
            return SSSContribution;
            // Uses the constant difference between the value range and contribution amount to add it to itself until the value cap is reached.
    }
    
    public static double getPhilHealth(double grossSalary){
            double philHealthContribution = 0;
            if (grossSalary >= 60000 ){
                philHealthContribution = ((grossSalary * 0.03) / 2);
                    if (philHealthContribution > 1800){
                        philHealthContribution = (1800 / 2);  
                    }               
            }
            if (grossSalary > 10000.01 && grossSalary < 59999.99){
                philHealthContribution = ((grossSalary *0.03)/ 2);
            }
            if (grossSalary <= 10000 ){
                philHealthContribution = ((grossSalary * 0.03) / 2);
                    if (philHealthContribution > 300){
                        philHealthContribution = (300 / 2);
                    }
            }
            return philHealthContribution;
            // Takes a grossSalary value to use for the computation of the PhilHealth dedcution.
    }
    
    public static double getPagIbig(double grossSalary){
            double pagIbigContribution = 0;
            if (grossSalary > 1500){
                pagIbigContribution = (grossSalary * 0.02);
                if (pagIbigContribution > 100){
                    pagIbigContribution = 100;  
                }    
            }
            if (grossSalary >= 1000 && grossSalary <= 1500){
                pagIbigContribution = (grossSalary *0.02);
            }
            if (grossSalary < 1000 ){
                pagIbigContribution = 0;
                }
            return pagIbigContribution; 
            // Takes a grossSalary value to use for the computation of the Pag-IBIG dedcution.
    }
    
    public static double getWithholdingTax(double grossSalary){
            double taxContribution = 0;
            double taxableIncome = (grossSalary - (getSSS(grossSalary) + getPhilHealth(grossSalary) + getPagIbig(grossSalary)));

            if (grossSalary > 666667){
                taxContribution = (200833.33 + ((taxableIncome - 666667) *.35 ));
            }
            if (grossSalary >= 166667 && grossSalary < 666667){    
                taxContribution = (40833.33+ ((taxableIncome - 166667) *.32 ));    
            }
            if (grossSalary >= 66667 && grossSalary < 166667){
                taxContribution = (10833 + ((taxableIncome - 66667) *.30 ));    
            }
            if (grossSalary >= 33333 && grossSalary < 66667){
                taxContribution = (2500 + ((taxableIncome - 33333) *.25 ));    
            }   
            if (grossSalary >= 20833 && grossSalary < 33333){
                taxContribution = ((taxableIncome - 20833) *.20 );    
            }
            if (grossSalary <= 20832){
                return taxContribution;
            }

            return taxContribution;
        /* 
            Gets the taxable income by subtracting the deductions from the gross salary, 
            then finds the tax bracket based on the gross salary, then uses the taxable income to calculate the tax contribution 
        */
    }
        
    public static void readEmployeeData(String employeeNumber){
                     
            for (String empNum : dataStorage.keySet()) {
                // Checks the keys of the array (employee number) which connects to the array that holds the employee data.
                String[] tempArray = dataStorage.get(empNum);
                // Sets a temporary array to store the values from the dataStorage hashmap based on the key (employee number)
                    
                if (empNum.equals(employeeNumber)){
                       
                    System.out.println("Employee Number: " + empNum +"\n" + "Employee Name: " + tempArray[0] + "\n" + "Birthday: " + tempArray[1] + "\n");
                }
                // Initializes a temporary array to hold the information needed based on the key (employee number). Prints the necessary information afterwards.
        }
    }
    public static void storeEmployeeData(){
   
            String filePath = "src/Resources/EmployeeData.csv";
            String row;
            String delimiter = ",";

            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                // Reads each line from the CSV file
                br.readLine();
                // Added to skip the header values

                while ((row = br.readLine()) != null) {

                    String[] values = row.split(delimiter);
                    int len = values.length;
                    String empNumber = values[0];
                    String name = (values[1] +", "+ values[2]);
                    String birthday = values[3];
                    String rate = values[len-1];

                dataStorage.put(empNumber, new String[]{name, birthday, rate});
            }

            } catch (IOException e) {   
                System.out.println("An error occurred while reading the file.");
                // General catch block for errors.
            }    
        // Reads the CSV file and saves the necessary values in a hashmap <String, String[]>.
    }
        
    public static void storeAttendanceData() {
            String filePath = "src/resources/AttendanceData.csv"; 
            String row;
            String delimiter = ","; 
        
       
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            // Reads each line from the CSV file
            
            br.readLine();
            // Added to skip the header values
            
            while ((row = br.readLine()) != null) {
                
                String[] values = row.trim().split(delimiter);
              
                attendanceStorage.add(values);
            } 
            }catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                // General catch block for errors.
        }
            // Reads every line in the CSV file and stores it in an Arraylist for accessing.
    }
    
    public static double firstGrossSalary(double firstHalf, String employeeNumber){
            return firstHalf * Double.parseDouble(getRate(employeeNumber));
            // Takes the work hours (firstHalf) for the first cutoff and multiplies it with the rate given the employee number in order to get the gross salary for that half.
    }
    
    public static double secondGrossSalary(double secondHalf, String employeeNumber){
            return secondHalf * Double.parseDouble(getRate(employeeNumber));
            // Takes the work hours (secondHalf) for the second cutoff and multiplies it with the rate given the employee number in order to get the gross salary for that half.
    }
    
    public static double grossSalary(double firstGrossSalary, double secondGrossSalary){
            return firstGrossSalary + secondGrossSalary;
            // Adds the first and second half salaries in order to get the gross salary
    }
    
    public static double totalDeductions(double grossSalary, double firstHalf, double secondHalf, String employeeNumber){
            return  getSSS(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber))) +
                    getPhilHealth(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)))+
                    getPagIbig(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)))+
                    getWithholdingTax(grossSalary(firstGrossSalary(firstHalf,employeeNumber),secondGrossSalary(secondHalf,employeeNumber)));
                    // Adds all the deductions.
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
            // Takes in the parameter values needed by the other methods and variables then prints the payroll details.
    }
    
    
    public static void main(String[] args){
        storeEmployeeData();
        storeAttendanceData();
        // Reads the employee and attendenace data once and stores the needed information in their respective Hashmap/ArrayList.
      
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter your username and password:  ");
        System.out.print("Username:  ");
        String workerType = input.nextLine();
        System.out.print("Password:  ");
        String password = input.nextLine();
        
        // Asks for both username and password. If one or both are incorrect prints "Incorrect username and/or password" and exits the program.
        
        if(workerType.isEmpty()){
            System.out.println("Incorrect username and/or password.");     
          
        }else{
            switch (workerType) {
                case "employee" :               
                    if("12345".equals(password)){
                        System.out.println("************************************** \n"); 
                        System.out.println("[1]. Enter your employee number");
                        System.out.println("[2]. Exit the Program\n");
                        System.out.println("**************************************");
                        
                        System.out.print("What do you want to do? ");
                        String choiceEmployee = input.nextLine();
                            switch (choiceEmployee){
                                case "1" : 
                                    System.out.print("Enter your employee number: ");
                                    String employeeNumber = input.nextLine();
                                    getEmployeeData(employeeNumber);
                                case "2" :
                                    System.exit(0);
                                default: 
                                    System.out.println("Invalid Input!");
                                    break;
                            } 
                        /* If the input is employee and the password is correct, gives the choice for the employee number input or to exit the program. 
                         * If the employee number is correct, proceeds with getting the employee data with (getEmployeeData(employeeNumber)).  
                         * Exits if the input is none of the given choices, if case 2 is chosen,and if the employee number is incorrect. 
                         */
                          
                    }else{
                        System.out.println("Incorrect username and/or password.");
                        break;
                    }                     
                case "payroll_staff":                  
                    if("12345".equals(password)){
                        System.out.println("************************************** \n"); 
                        System.out.println("[1]. Process Payroll");
                        System.out.println("[2]. Exit the Program\n");
                        System.out.println("**************************************");
                        
                        System.out.print("What do you want to do? ");
                        String choicePayroll = input.nextLine();
                        
                        /* If the input is payroll_staff and the password is correct, gives the choice for processing the payroll or to exit the program. 
                         * Exits if the input is none of the given choices,and if case 2 is chosen.
                         */
                        
                        switch (choicePayroll){
                            case "1" : 
                                System.out.println("************************************** \n"); 
                                System.out.println("[1]. One employee");
                                System.out.println("[2]. All Employees");
                                System.out.println("[3]. Exit the Program\n");
                                System.out.println("**************************************");
                                
                                System.out.print("How many do you want to process? ");
                                String choiceEmployee = input.nextLine();
                                /*  
                                *    If process payroll is chosen, gives the choice to process [1] one or [2] all employees.
                                *   Exits if [3] is chosen or if the input are non of the given choices.
                                */
                                
                                switch (choiceEmployee){
                                    case "1" : 
                                        System.out.println("************************************** \n"); 
                                        System.out.println("[1]. Enter the Employee Number.");
                                        System.out.println("[2]. Exit the Program\n");
                                        System.out.println("**************************************");
                                        System.out.print("What do you want to do? ");
                                        String choice = input.nextLine();
                                        /*  
                                        *    If one employee is chosen, [1] asks for the employee number
                                        *   Exits the program if [2] is chosen or if the input is not one of the given. 
                                        */

                                        switch (choice){
                                            
                                            case "1" :
                                                Scanner empInput = new Scanner(System.in);   
                                                System.out.print("Input Employee No: ");
                                                String employeeNumber = empInput.nextLine();
                                                getSingleSalary(employeeNumber);
                                                break;
                                                /*
                                                    Asks for employee number input and passes the number to the getSingleSalary() method in order to get the payroll info.
                                                */
                                                
                                            case "2" :
                                                System.exit(0);     
                                                // Choice for an exit clause if needed.
                                        default : 
                                            System.out.println("Invalid Input!");
                                            break;
                                            // Terminates the program if the the input is not from the choices.
                                        }    
                                        break;
                                        
                                    case "2" :
                                        getAllSalary();
                                        break;
                                        /*
                                            If [2] is chosen, calls the getAllSalary() method in order to print all the payroll information 
                                        */
                                    case "3" : 
                                        System.exit(0);
                                         // Choice for an exit clause if needed.
                                        
                                    default : 
                                        System.out.println("Invalid Input!");  
                                        // Terminates the program if the the input is not from the choices.
                                }
                                break;
                            
                            case "2" :
                                System.exit(0);
                             
                            default: 
                                System.out.println("Invalid Input!");             
                        }   
    
                        break;
                        
                    }else{
                        System.out.println("Incorrect username and/or password.");   
                        break;
                        // Terminates the program if one or both the password and username are incorrect.
                    }
                default: 
                    System.out.println("Incorrect username and/or password.");
                    // Terminates the program if one or both the password and username are incorrect.
            }       
        } 
    } 
}  