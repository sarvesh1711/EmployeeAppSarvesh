package com.aurionpro.test;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.stream.Collectors;

import com.aurionpro.model.Employee;

public class Main {
    private static List<Employee> employees = new ArrayList<>();

    public static void main(String[] args) throws ParseException {
        loadDataFromFile();

        List<Employee> employeesInDepartment = findEmployeesInDepartment(30);
        System.out.println("\n Employees in department 30: ");
        employeesInDepartment.forEach(System.out::println);

        Map<Integer, Integer> employeesCountByDepartment = countEmployeesByDepartment();
        System.out.println("\n Employees count by department: ");
        employeesCountByDepartment.forEach((deptId, count) ->
                System.out.println("Department " + deptId + ": " + count));

        Map<Integer, Employee> highestPaidByDepartment = findHighestPaidByDepartment();
        System.out.println("\n Highest paid employees by department: ");
        highestPaidByDepartment.forEach((deptId, emp) ->
                System.out.println("Department " + deptId + ": " + emp));

        Map<String, Employee> highestPaidByRole = findHighestPaidByRole();
        System.out.println("\n Highest paid employees by role: ");
        highestPaidByRole.forEach((role, emp) ->
                System.out.println("Role " + role + ": " + emp));

        List<Employee> employeesJoinedBetween = findEmployeesJoinedBetweenDates("01-MAY-81", "31-DEC-81");
        System.out.println("\n Employees joined between May 1, 1981, and December 31, 1981: ");
        employeesJoinedBetween.forEach(System.out::println);

        Employee highestCommissionSalesman = findSalesmanWithHighestCommission();
        System.out.println("\n Salesman with highest commission: " + highestCommissionSalesman);

        double totalSalary = calculateTotalSalary();
        System.out.println("\n Total salary of all employees: " + totalSalary);
    }

    private static Map<Integer, Integer> countEmployeesByDepartment() {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (Employee employee : employees) {
            int departmentId = employee.getDepartmentNo();
            countMap.put(departmentId, countMap.getOrDefault(departmentId, (int) 0) + 1);
        }
        return countMap;
    }

    private static void saveDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\Batch 22\\aurionpro 22\\59-testapp\\src\\com\\aurionpro\\test\\Emp.txt"))) {
            for (Employee employee : employees) {
                String line = employee.getId() + "," +
                              employee.getName() + "," +
                              employee.getDepartment() + "," +
                              employee.getManagerId() + "," +
                              new SimpleDateFormat("dd-MMM-yy").format(employee.getDate()) + "," +
                              employee.getSalary() + "," +
                              employee.getCommission() + "," +
                              employee.getDepartmentNo();
                
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void loadDataFromFile() throws ParseException {
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\Batch 22\\aurionpro 22\\59-testapp\\src\\com\\aurionpro\\test\\Emp.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                String department = parts[2];
                int managerId = parseInteger(parts[3]); 
                String dateString = parts[4].replace("'", ""); 
                Date date = parseDate(dateString);
                
                double salary = parseDouble(parts[5]);
                double commission = parseDouble(parts[6]);
                
                int departmentNo = parseInteger(parts[7]); 
                
                Employee employee = new Employee(id, name, department, managerId, date, salary, commission, departmentNo);
                employees.add(employee);
               
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int parseInteger(String string) {
        if (string.equalsIgnoreCase("NULL")) {
            return 0; 
        }
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return 0; 
        }
    }

    private static double parseDouble(String string1) {
        if (string1.equalsIgnoreCase("NULL")) {
            return 0.0; 
        }
        try {
            return Double.parseDouble(string1);
        } catch (NumberFormatException e) {
            return 0.0; 
        }
    }
    private static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat[] dateFormats = {
            new SimpleDateFormat("d-MMM-yy"), 
            new SimpleDateFormat("d MMM yy"), 
            new SimpleDateFormat("dd-MMM-yy"), 
            new SimpleDateFormat("dd MMM yy") 
        };

        for (SimpleDateFormat dateFormat : dateFormats) {
            try {
                return dateFormat.parse(dateString);
            } catch (ParseException e) {
               e.printStackTrace();
            }
        }

        throw new ParseException("Unparseable date: " + dateString, 0);
    }


    private static List<Employee> findEmployeesInDepartment(int departmentId) {
        return employees.stream()
                .filter(employee -> employee.getDepartmentNo() == departmentId)
                .collect(Collectors.toList());
    }

    private static Map<Integer, Employee> findHighestPaidByDepartment() {
        Map<Integer, Employee> highestPaidMap = new HashMap<>();
        for (Employee employee : employees) {
            int departmentId = employee.getDepartmentNo();
            if (!highestPaidMap.containsKey(departmentId) || highestPaidMap.get(departmentId).getSalary() < employee.getSalary()) {
                highestPaidMap.put(departmentId, employee);
            }
        }
        return highestPaidMap;
    }

    private static Map<String, Employee> findHighestPaidByRole() {
        Map<String, Employee> highestPaidMap = new HashMap<>();
        for (Employee employee : employees) {
            String role = employee.getDepartment(); 
            if (!highestPaidMap.containsKey(role) || highestPaidMap.get(role).getSalary() < employee.getSalary()) {
                highestPaidMap.put(role, employee);
            }
        }
        return highestPaidMap;
    }

    private static List<Employee> findEmployeesJoinedBetweenDates(String startDate, String endDate) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy");
        Date start = dateFormat.parse(startDate);
        Date end = dateFormat.parse(endDate);

        return employees.stream()
                .filter(employee -> employee.getDate() != null &&
                        !employee.getDate().before(start) &&
                        !employee.getDate().after(end))
                .collect(Collectors.toList());
    }

    private static Employee findSalesmanWithHighestCommission() {
        Employee highestCommissionSalesman = null ;
        double highestCommission = 0;
        for (Employee employee : employees) {
            if (!"Salesman".equals(employee.getDepartment()) && employee.getCommission() > highestCommission) {
            	
                highestCommission = employee.getCommission();
                highestCommissionSalesman = employee;
            }
        }
        return highestCommissionSalesman;
    }

    private static double calculateTotalSalary() {
        return employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
    }

}
