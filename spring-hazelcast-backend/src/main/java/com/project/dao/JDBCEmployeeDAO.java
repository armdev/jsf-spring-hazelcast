package com.project.dao;

import com.project.entities.Employee;
import com.project.services.HazelcastService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository("emlpoyeeDAO")
public class JDBCEmployeeDAO implements EmployeeDAO {
//https://docs.spring.io/spring/docs/current/spring-framework-reference/html/jdbc.html

    @Autowired
    private DataSource mySqlDataSource;

    @Autowired
    private HazelcastService hazelcastService;

    private JdbcTemplate jdbcTemplate;

    public JDBCEmployeeDAO() {
    }

    @PostConstruct
    public void init() throws Exception {
        if (mySqlDataSource == null) {
            throw new BeanCreationException("Must set mySqlDataSource on " + this.getClass().getName());
        }
        this.jdbcTemplate = new JdbcTemplate(mySqlDataSource);
    }

    @Override
    public void addEmployee(Employee employee) {
        System.out.println("Add employee");
        String query = "INSERT INTO EMPLOYEE (NAME, ROLE) VALUES(?,?)";
        Object[] args = new Object[]{employee.getName(), employee.getRole()};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            System.out.println("Employee saved with id : " + employee.getId());
            hazelcastService.addEmployee(employee);
            hazelcastService.removeList();
        }
    }

    @Override
    public void removeEmployee(Integer employeeId) {
        System.out.println("remove employee");
        String query = "DELETE FROM EMPLOYEE WHERE ID = ?";
        Object[] args = new Object[]{employeeId};
        int out = jdbcTemplate.update(query, args);
        if (out != 0) {
            System.out.println("Employee with id : " + employeeId + " deleted successfully");
            hazelcastService.removeList();
            hazelcastService.removeEmployee(employeeId);
        }

    }

    @Override
    public Employee getEmployee(final Integer employeeId) {
        System.out.println("get employee");
        Employee emp = hazelcastService.getEmployee(employeeId);
        if (emp == null) {
            System.out.println("employee in map is null ");
            String query = "SELECT ID, NAME, ROLE FROM EMPLOYEE WHERE ID = ?";
            Object[] args = new Object[]{employeeId};
            emp = jdbcTemplate.queryForObject(query, new Object[]{employeeId}, new RowMapper<Employee>() {
                @Override
                public Employee mapRow(ResultSet resultSet, int i) throws SQLException {
                    Employee employee = new Employee();
                    employee.setId(resultSet.getInt("id"));
                    return employee;
                }
            });
        }
        return emp;
    }

    @Override
    public List<Employee> listEmployees() {
        System.out.println("========List of employees======");
        String query = "SELECT * FROM EMPLOYEE";
        List<Employee> empList = hazelcastService.listEmployees();
        System.out.println("--------LIST SIZE FROM HAZELCAST--------- " + empList.size());
        if (empList.isEmpty()) {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(query);
            empList = new ArrayList<>();
            for (Map row : rows) {
                Employee employee = new Employee();
                Integer id = (Integer) row.get("ID");
                employee.setId(id);
                employee.setName((String) row.get("NAME"));
                employee.setRole((String) row.get("ROLE"));
                empList.add(employee);
            }
            System.out.println("Loading list from database: size is:  " + empList.size());
            System.out.println("=======Adding list to hazelcast=========");
            hazelcastService.addEmployees(empList);
        } else {
            System.out.println("Loading list from hazelcast: size is: " + empList.size());
        }
        return empList;
    }

}
