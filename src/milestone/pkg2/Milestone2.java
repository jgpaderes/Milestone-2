/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package milestone.pkg2;

/**
 *
 * @authors 
 * Paderes, Juan Gabriel --- jgpaderes
 * Yasoña, Keichelle     --- Keichelle
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
    public static void calculateSingleSalary(){
        String filePath = "src/Resources/AttendanceData.csv";
            String column;
            String delimiter = ",";
            DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
            boolean checker = false;
            double firstHalf = 0;
            double secondHalf = 0; 
            double firstGrossSalary;
            double secondGrossSalary;
            double grossSalary;
            double totalDeductions;
            int monthCount = 6;
            Scanner input = new Scanner(System.in);

            System.out.print("Input Employee No: ");

            String employeeNumber = input.nextLine();
            
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
                        
                        
                        if (monthCount <= 12){
                            
                            if (monthCount != monthNo){ 
                                firstGrossSalary = firstHalf * Double.valueOf(getRate(employeeNumber));
                                secondGrossSalary = secondHalf * Double.valueOf(getRate(employeeNumber));
                                grossSalary = firstGrossSalary + secondGrossSalary;
                                totalDeductions = (getSSS(grossSalary) + getPhilHealth(grossSalary) + getPagIbig(grossSalary) + getWithholdingTax(grossSalary));
                                System.out.println("**************************************\n");
                                System.out.println(readEmployeeData(employeeNumber) + "Cutoff Date: " + Month.of(monthCount) + " 1 to " + Month.of(monthCount)+" 15\n" + "Total Hours Worked: " + firstHalf + "\n" + "Gross Salary: " + firstGrossSalary + "\n" + "Net Salary: "+ firstHalf * Double.valueOf(getRate(employeeNumber))+ "\n");
                                System.out.println("Cutoff Date: " + Month.of(monthCount) + " 16 to " + Month.of(monthCount)+" "+ YearMonth.of(2024, monthCount).lengthOfMonth() +"\n" + "Total Hours Worked: " + secondHalf + "\n" + "Gross Salary: " +  secondGrossSalary + "\n" + "Each Decuction: \n" + "\tSSS: " + getSSS(grossSalary) + "\tPhilHealth: " + getPhilHealth(grossSalary) + "\n" + "\tPag-IBIG: "+ getPagIbig(grossSalary) + "\n" + "\tTax: " + getWithholdingTax(grossSalary) + "Total Deductions: " + totalDeductions + "\n" + "Net Salary: " + (grossSalary - totalDeductions) + "\n");
                                System.out.println("**************************************"); 
                                
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
                    System.out.println("Employee does not exist.");
                    return;
                }
                firstGrossSalary = firstHalf * Double.valueOf(getRate(employeeNumber));
                secondGrossSalary = secondHalf * Double.valueOf(getRate(employeeNumber));
                grossSalary = firstGrossSalary + secondGrossSalary;
                totalDeductions = (getSSS(grossSalary) + getPhilHealth(grossSalary) + getPagIbig(grossSalary) + getWithholdingTax(grossSalary));
                System.out.println("**************************************\n");
                System.out.println(readEmployeeData(employeeNumber) + "Cutoff Date: " + Month.of(monthCount) + " 1 to " + Month.of(monthCount)+" 15\n" + "Total Hours Worked: " + firstHalf + "\n" + "Gross Salary: " + firstGrossSalary + "\n" + "Net Salary: "+ firstHalf * Double.valueOf(getRate(employeeNumber))+ "\n");
                System.out.println("Cutoff Date: " + Month.of(monthCount) + " 16 to " + Month.of(monthCount)+" "+ YearMonth.of(2024, monthCount).lengthOfMonth() +"\n" + "Total Hours Worked: " + secondHalf + "\n" + "Gross Salary: " +  secondGrossSalary + "\n" + "Each Decuction: \n" + "\tSSS: " + getSSS(grossSalary) + "\n" + "\tPhilHealth: " + getPhilHealth(grossSalary) + "\n" + "\tPag-IBIG: "+ getPagIbig(grossSalary) + "\n" +"\tTax: "+ getWithholdingTax(grossSalary) + "\n" + "Total Deductions: " + totalDeductions +"\n" + "Net Salary: " + (grossSalary - totalDeductions) + "\n");
                System.out.println("**************************************");
                // placed outside the while loop to catch December as the loop stops after reading the last item in the CSV
                
                
                    
                
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
            
    
     } 
    public static String getRate(String employeeNumber){
            String filePath = "src/Resources/EmployeeData.csv";
            String column;
            String delimiter = ",";
            String rate = null; 
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                
                br.readLine();
                // Added to skip the header values
                while ((column = br.readLine()) != null) {
                    String[] values = column.split(delimiter);
                    

                    if (values[0].equals(employeeNumber)){ 
                        int len = values.length;
                        rate = values[len-1];
                        return rate;
                    }
                }
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
                    
            }
    
        return rate;
    }
    public static double getHours(LocalTime logIn, LocalTime logOut){
        LocalTime gracePeriod = LocalTime.of(8,10);
        LocalTime cutOff = LocalTime.of(17,0);
        
        if(logOut.isAfter(cutOff)){
            logOut = cutOff;
        }    
        if(logIn.isBefore(gracePeriod)){
            logIn = LocalTime.of(8,0);
        
        }
        Duration difference = Duration.between(logIn,logOut);
        double diffHours = difference.toHours();      
        double diffMinutes = difference.toMinutes() % 60;
        double hoursWorked = (diffHours + (diffMinutes / 60));
        // Reads the difference in duration in H : mm then converts the mm value to decimal 
        
        if (hoursWorked > 1){
            hoursWorked -= 1;
            // Subtracts an hour for lunch considering the hours worked is above an hour.
        }

        if ( hoursWorked > 8.0){
            hoursWorked = 8.0;
            return hoursWorked;
            // checks for overtime, returns the max working hours which is 8 
        }else{
            return hoursWorked;
        }
    }
    public static String readEmployeeData(String employeeNumber){
            String filePath = "src/Resources/EmployeeData.csv";
            String column;
            String delimiter = ",";

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
                          
                    
                        for (String data : values){
                            
                            employeeInfo[i] = data;   
                            i++;
                            if (i == 4){
                                 
                                System.out.println("Employee Number: " + employeeInfo[0] +"\n" + "Employee Name: " + employeeInfo[1] + ", " + employeeInfo[2] + "\n" + "Birthday: " + employeeInfo[3] + "\n");
                                break;
                            }                
                        }     
                    }
                }
            
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
        return "";
    }
    public static void calculateAllSalary(){
        String filePath = "src/Resources/AttendanceData.csv";
            String column;
            String delimiter = ",";
            DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm");
             
            
            String employeeNumber = null;
            int empCheck = 10001;
            String lastId = null;
            
            do{
                double firstHalf = 0;
                double secondHalf = 0;
                double firstGrossSalary;
                double secondGrossSalary;
                double grossSalary;
                double totalDeductions;
                int monthCount = 6;
                try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                
                    br.readLine();
                // Added to skip the header values
     
                    while ((column = br.readLine()) != null) {
                        String[] values = column.split(delimiter);
                   
                    
                        employeeNumber = values[0];
                        
                        if (empCheck == (Integer.valueOf(employeeNumber))){

                            String[] dateSplit = values[3].split("/");
                            int monthNo = Integer.parseInt(dateSplit[0]);
                            int date = Integer.parseInt(dateSplit[1]);
                            LocalTime logIn = LocalTime.parse(values[4], format); LocalTime logOut = LocalTime.parse(values[5], format);
                            double hours = getHours(logIn,logOut);
                    
                        
                        
                            if (monthCount <= 12){
                            
                                if (monthCount != monthNo){  
                                    firstGrossSalary = firstHalf * Double.valueOf(getRate(employeeNumber));
                                    secondGrossSalary = secondHalf * Double.valueOf(getRate(employeeNumber));
                                    grossSalary = firstGrossSalary + secondGrossSalary;
                                    totalDeductions = (getSSS(grossSalary) + getPhilHealth(grossSalary) + getPagIbig(grossSalary) + getWithholdingTax(grossSalary));
                                    System.out.println("**************************************\n");
                                    System.out.println(readEmployeeData(employeeNumber) + "Cutoff Date: " + Month.of(monthCount) + " 1 to " + Month.of(monthCount)+" 15\n" + "Total Hours Worked: " + firstHalf + "\n" + "Gross Salary: " + firstGrossSalary + "\n" + "Net Salary: "+ firstHalf * Double.valueOf(getRate(employeeNumber))+ "\n");
                                    System.out.println("Cutoff Date: " + Month.of(monthCount) + " 16 to " + Month.of(monthCount)+" "+ YearMonth.of(2024, monthCount).lengthOfMonth() +"\n" + "Total Hours Worked: " + secondHalf + "\n" + "Gross Salary: " +  secondGrossSalary + "\n" + "Each Decuction: \n" + "\tSSS: " + getSSS(grossSalary) + "\n" + "\tPhilHealth: " + getPhilHealth(grossSalary) + "\n" + "\tPag-IBIG: "+ getPagIbig(grossSalary)+ "\n" +"\tTax: "+ getWithholdingTax(grossSalary) +"\n"+ "Total Deductions: " + totalDeductions +"\n" + "Net Salary: " + (grossSalary - totalDeductions) + "\n");
                                    System.out.println("**************************************"); 
                                
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
                    lastId = values[0];      
                    // Gets the last Employee number from the list
                }
                firstGrossSalary = firstHalf * Double.valueOf(getRate(employeeNumber));
                secondGrossSalary = secondHalf * Double.valueOf(getRate(employeeNumber));
                grossSalary = firstGrossSalary + secondGrossSalary;
                totalDeductions = (getSSS(grossSalary) + getPhilHealth(grossSalary) + getPagIbig(grossSalary) + getWithholdingTax(grossSalary));
                System.out.println("**************************************\n");
                System.out.println(readEmployeeData(String.valueOf(empCheck)) + "Cutoff Date: " + Month.of(monthCount) + " 1 to " + Month.of(monthCount)+" 15\n" + "Total Hours Worked: " + firstHalf + "\n" + "Gross Salary: " + firstGrossSalary + "\n" + "Net Salary: "+ firstHalf * Double.valueOf(getRate(String.valueOf(empCheck)))+ "\n");
                System.out.println("Cutoff Date: " + Month.of(monthCount) + " 16 to " + Month.of(monthCount)+" "+ YearMonth.of(2024, monthCount).lengthOfMonth() +"\n" + "Total Hours Worked: " + secondHalf + "\n" + "Gross Salary: " +  secondGrossSalary + "\n" + "Each Decuction: \n" + "\tSSS: " + getSSS(grossSalary) + "\n" + "\tPhilHealth: " + getPhilHealth(grossSalary) + "\n" + "\tPag-IBIG: "+ getPagIbig(grossSalary) + "\n" +"\tTax: "+ getWithholdingTax(grossSalary) +"\n" + "Total Deductions: " + totalDeductions +"\n" + "Net Salary: " + (grossSalary - totalDeductions) + "\n");
                System.out.println("**************************************");
                empCheck += 1;
                
                // placed outside the while loop to catch December as the loop stops after reading the last item in the CSV
                
                
                
            } catch (IOException e) {
                System.out.println("An error occurred while reading the file.");
            }
        
        }while (empCheck <= Integer.valueOf(lastId));
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
    
    public static void main(String[] args){
      
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
                    String payrollPassword = input.nextLine();
                    if("12345".equals(payrollPassword)){
                        System.out.println("************************************** \n"); 
                        System.out.println("1. Process Payroll");
                        System.out.println("2. Exit the Program\n");
                        System.out.println("**************************************");
                        
                        System.out.print("What do you want to do? ");
                        String choicePayroll = input.nextLine();
                        
                        switch (choicePayroll){
                            case "1" : 
                                System.out.println("************************************** \n"); 
                                System.out.println("1. One employee");
                                System.out.println("2. All Employees");
                                System.out.println("3. Exit the Program\n");
                                System.out.println("**************************************");
                                
                                System.out.print("How many do you want to process? ");
                                String choiceEmployee = input.nextLine();
                                
                                switch (choiceEmployee){
                                    case "1" : 
                                        calculateSingleSalary();
                                        break;
                                        
                                    case "2" :
                                        calculateAllSalary();
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
                        System.out.println("Invalid Password!");   
                        break;
                    }
                default: 
                    System.out.println("Invalid Username!");
            }
        } 
    } 
}  