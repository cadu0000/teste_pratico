package infra.dao;

import domain.Employee;
import infra.db.ConnectionHelper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class EmployeeDao {

    public void create(Employee emp) {
        String sql = "INSERT INTO employee (name, birth_date, salary, position) VALUES (?, ?, ?, ?);";
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, emp.getName());
            pst.setDate(2, java.sql.Date.valueOf(emp.getBirthDate()));
            pst.setBigDecimal(3, emp.getSalary());
            pst.setString(4, emp.getPosition());

            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao criar funcionários", e);
        }
    }

    public void delete(Employee emp) {
        String sql = "DELETE FROM employee WHERE name = ?;";
        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, emp.getName());

            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao deletar funcionários", e);
        }
    }

    public ArrayList<Employee> getAllEmployees() {
        String sql = "SELECT * FROM employee";

        try (Connection connection = ConnectionHelper.getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            ResultSet rs = pst.executeQuery();
            ArrayList<Employee> employees = new ArrayList<>();

            while(rs.next()){
                String nome = rs.getString("name");
                LocalDate date = rs.getDate("birth_date").toLocalDate();
                BigDecimal salary = rs.getBigDecimal("salary");
                String position = rs.getString("position");

                employees.add(new Employee(nome, date, salary, position));
            }
            return employees;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro ao consultar lista de funcionários", e);
        }
    }

    public void tenPercentIncrease(Employee emp) {
    String sql = "UPDATE employee SET salary = ? WHERE name = ?;";

    try (Connection connection = ConnectionHelper.getConnection();
         PreparedStatement pst = connection.prepareStatement(sql)) {

        pst.setBigDecimal(1, emp.getSalary());
        pst.setString(2, emp.getName());

        pst.executeUpdate();
    } catch (SQLException | ClassNotFoundException e) {
        throw new RuntimeException("Erro ao dar 10% de aumento ao funcionários", e);
    }
    }
}
