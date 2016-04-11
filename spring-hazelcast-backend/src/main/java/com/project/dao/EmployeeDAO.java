package com.project.dao;

import com.project.entities.Employee;
import java.util.List;

/**
 *
 * @author Home
 */
public interface EmployeeDAO {

    public void addEmployee(Employee employee);

    public void removeEmployee(Integer employeeId);

    public Employee getEmployee(final Integer employeeId);

    public List<Employee> listEmployees();

}
