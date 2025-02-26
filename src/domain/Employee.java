package domain;

import errors.SalaryValueException;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Employee extends Person {
    private BigDecimal salary;
    private String position;

    public Employee(String name, LocalDate birthDate, BigDecimal salary, String position) {
        super(name, birthDate);
        validateSalary(salary);
        this.salary = salary;
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        validateSalary(salary);
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void validateSalary(BigDecimal salary) {
        if (salary.compareTo(new BigDecimal("1212")) < 0) {
            throw new SalaryValueException("Salário abaixo do mínimo (1212.00 R$)");
        }
    }
}
