package app;

import domain.Employee;
import infra.dao.EmployeeDao;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
    private static final Scanner sc = new Scanner(System.in);
    private static final List<Employee> employees = new ArrayList<>();
    private static final Map<String, Set<Employee>> employeesByPositionMap = new LinkedHashMap<>();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final EmployeeDao empDao = new EmployeeDao();

    public void menu() {
        while (true) {
            int option = sc.nextInt();

            switch (option) {
                case 1 -> insertStandardData();
                case 2 -> removeEmployee();
                case 3 -> getAllEmployees();
                case 4 -> giveRaiseToEmployees();
                case 5 -> mapEmployees();
                case 6 -> getEmployeesByPosition();
                case 7 -> getEmployeesByBirthDay();
                case 8 -> getOldestEmployee();
                case 9 -> getEmployeesByAlphabeticalOrder();
                case 10 -> getEmployeesSalary();
                case 11 -> countMinSalaryForEachEmployee();
                case 0 -> {
                    return;
                }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void insertStandardData() {
        employees.add(new Employee("Maria", LocalDate.parse("18/10/2000", formatter),
                new BigDecimal("2009.44"), "Operador"));
        employees.add(new Employee("João", LocalDate.parse("12/05/1990", formatter),
                new BigDecimal("2284.38"), "Operador"));
        employees.add(new Employee("Caio", LocalDate.parse("02/05/1961", formatter),
                new BigDecimal("9836.14"), "Coordenador"));
        employees.add(new Employee("Miguel", LocalDate.parse("14/10/1988", formatter),
                new BigDecimal("19119.88"), "Diretor"));
        employees.add(new Employee("Alice", LocalDate.parse("05/01/1995", formatter),
                new BigDecimal("2234.68"), "Recepcionista"));
        employees.add(new Employee("Heitor", LocalDate.parse("19/11/1999", formatter),
                new BigDecimal("1582.72"), "Operador"));
        employees.add(new Employee("Arthur", LocalDate.parse("31/03/1993", formatter),
                new BigDecimal("4071.84"), "Contador"));
        employees.add(new Employee("Laura", LocalDate.parse("08/07/1994", formatter),
                new BigDecimal("3017.45"), "Gerente"));
        employees.add(new Employee("Heloísa", LocalDate.parse("24/05/2003", formatter),
                new BigDecimal("1606.85"), "Eletricista"));
        employees.add(new Employee("Helena", LocalDate.parse("02/09/1996", formatter),
                new BigDecimal("2799.93"), "Gerente"));

        try{
            employees.forEach(empDao::create);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void removeEmployee(){
        List<Employee> employeesToRemove = employees.stream()
                .filter(employee -> employee.getName().equals("João"))
                .toList();

        try {
            employeesToRemove.forEach(empDao::delete);
            employees.removeAll(employeesToRemove);
        } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    public static void getAllEmployees() {
        try{
            employees.clear();
            employees.addAll(empDao.getAllEmployees());
            employees.forEach(System.out::println);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void giveRaiseToEmployees() {
        try{
            employees.forEach(Employee::updateSalary);
            employees.forEach(empDao::tenPercentIncrease);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void mapEmployees() {
        Set<String> employeesPosition = new HashSet<>();

        for (Employee employee : employees) {
            employeesPosition.add(employee.getPosition());
        }

        for (String position : employeesPosition) {
            employeesByPositionMap.put(position, new HashSet<>());
        }

        for (Employee employee : employees) {
            employeesByPositionMap.get(employee.getPosition()).add(employee);
        }
    }

    public static void getEmployeesByPosition() {
        employeesByPositionMap.forEach((position, employeeList) -> {
            System.out.println("Cargo: " + position);
            employeeList.forEach(employee -> System.out.println(" - " + employee.getName()));
        });
    }

    public static void getEmployeesByBirthDay() {
        System.out.println("Funcionários que fazem aniversário em Outubro ou Dezembro: ");
        employees.forEach(emp -> {
            Month empMonth = emp.getBirthDate().getMonth();
            if(empMonth.equals(Month.OCTOBER) || empMonth.equals(Month.DECEMBER)){
                System.out.println(emp.getName() + "no dia" +
                        emp.getBirthDate().getDayOfMonth() + "de" +
                        emp.getBirthDate().getMonth());
            }
        });
    }

    public static void getOldestEmployee(){
        employees
                .stream().min(Comparator.comparing(Employee::getBirthDate))
                .ifPresent(oldestEmployee
                        -> System.out.println(oldestEmployee.getName() + " - "
                        + Period.between(oldestEmployee.getBirthDate(), LocalDate.now()).getYears()));
    }

    public static void getEmployeesByAlphabeticalOrder(){
        List<Employee> sortedEmployees = employees.stream()
                .sorted(Comparator.comparing(Employee::getName))
                .toList();

        sortedEmployees.forEach(emp -> System.out.println(" - " + emp.getName()));
    }

    public static void getEmployeesSalary(){
        employees.forEach(employee -> System.out.println(employee.getName() + " - Salario: " + employee.getSalary()));
    }

    public static void countMinSalaryForEachEmployee(){
        BigDecimal minimumWage = new BigDecimal("1212.00");
        employees.forEach(employee -> System.out.println(employee.getName() +
                " - Quantidade de salários mínimos: " + employee.getSalary().divide(minimumWage, 2, RoundingMode.HALF_UP)));
    }
}

