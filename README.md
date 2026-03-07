# Milestone 2: Motor Ph Basic payroll system

Motor Ph Basic payroll system This Java application manages employee records and attendance data by reading from local CSV files and displaying payroll information via the console.

## Repository Structure
The project is organized into the following directory structure:

* **src/milestone/pkg2/**: Contains the main application logic in `Milestone2.java`.
* **src/Resources/**: Contains the data source files used by the program:
    * `EmployeeData.csv` — Stores employee details.
    * `AttendanceData.csv` — Stores login and logout records.
* **nbproject/**: Contains NetBeans project configuration and metadata.

## Program Functionality
Based on the source code in `Milestone2.java`, the system performs the following tasks:

### 1. Employee Data Retrieval (getEmployeeData)
The program reads `EmployeeData.csv` using a `BufferedReader`.
* It uses a comma (`,`) as a delimiter to split data into an array.
* It searches for a matching Employee Number.
* **Output:** If found, it prints the Employee Number, Name (First and Last), and Birthday inside a formatted block.
* **Error Handling:** If the ID does not exist, it displays: `"Employee does not exist."`.

### 2. Salary Calculation (calculateSingleSalary)
The program accesses `src/Resources/AttendanceData.csv`.
* It uses `DateTimeFormatter` with the pattern `"H:mm"` to parse attendance times.
* It prompts the user with `"Input Employee No: "` using a `Scanner`.
* **Tracking:** It initializes variables to track `firstHalf` and `secondHalf` of the month (beginning at `monthCount = 6`).
* **Calculations:** The method includes variables for `grossSalary`, `totalDeductions`, and `netSalary` to be processed per employee.


## How to Run and Test 

**Clone the repository:**

```bash
git clone https://github.com/jgpaderes/Milestone-2.git
```

**Environment**: Open the project in NetBeans IDE.

**Execution**:

Ensure the CSV files remain in the src/Resources/ directory.

Run Milestone2.java.

Enter a valid Employee Number from your EmployeeData.csv when prompted in the console.

Program Function Summary:

The system is a MotorPH payroll program that reads employee and attendance data from CSV files to compute payroll information. It allows users to log in either as an employee, who can view their personal information, or as payroll staff, who can process payroll for one employee or all employees.

Our program calculates hours worked based on attendance records, separates them into two payroll cutoffs (1–15 and 16–end of the month), computes the gross salary using the employee’s hourly rate, and then deducts government contributions such as SSS, PhilHealth, Pag-IBIG, and withholding tax to determine the final net salary.

Team Contribution:

1 Requirement Finalization- Juan  Gabriel Paderes & Keichelle Yasoña

1.1 Meet with the group for planning- Keichelle Yasoña

2 Log-in & Authentication- Juan Gabriel Paderes

2.1 Develop log-in functionality- Juan Gabriel Paderes

3 Implement password authentication- Juan Gabriel Paderes

4 Dashboard interface- Jabez Anthony Mansueto III

5 Develop dashboard interface- Jabez Anthony Mansueto III

6 Employee detail screen- Katrina Carell Layug

6.1 Develop employee detail screen- Katrina Carell Layug

7 Cutoff Date & Deductions- Keichelle Yasoña

8 Exit program- Keichelle Yasoña

9 Payroll System- Juan Gabriel Paderes, Keichelle Yasoña, Jabez Anthony Mansueto & Katrina Carell Layug

9.1 Develop Payroll Management System- Juan Gabriel Paderes, Keichelle Yasoña, Jabez Anthony Mansueto & Katrina Carell Layug

10 Intial testing- Juan Gabriel Paderes, Keichelle Yasoña, Jabez Anthony Mansueto & Katrina Carell Layug

10.1 Final Testing and Review- Juan Gabriel Paderes, Keichelle Yasoña, Jabez Anthony Mansueto & Katrina Carell Layug

11 Full system testing- Juan Gabriel Paderes, Keichelle Yasoña, Jabez Anthony Mansueto & Katrina Carell Layug

Readme file- Keichelle Yasoña

Project Plan- Keichelle Yasoña & Jabez Anthony Mansueto III

Project Plan Link:
https://docs.google.com/spreadsheets/d/1MIQMJH0ssWdQh6hd9r4pphKoMBp5BF-ocaBjURpECzc/edit?usp=sharing
