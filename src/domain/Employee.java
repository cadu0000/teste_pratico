package domain;

import errors.SalaryValueException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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

    public void updateSalary() {
        salary = salary.add(salary.multiply(new BigDecimal("10")).divide(new BigDecimal("100")));
    }
    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        String formattedSalary = nf.format(salary);

        return "name='" + super.getName() + '\'' +
                " ,birthDate=" + super.getBirthDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                " ,salary=" + formattedSalary +
                " ,position='" + position + '\'' +
                '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(super.getName(), employee.getName()) &&
                Objects.equals(position, employee.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.getName(), position);
    }
}
