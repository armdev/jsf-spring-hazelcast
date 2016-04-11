package com.project.services;

import com.project.entities.Employee;
import java.util.List;

/**
 *
 * @author Home
 */
public interface EmployeeService {

    public void addEmployee(Employee employee);

    public void removeEmployee(Integer employeeId);

    public Employee getEmployee(final Integer employeeId);

    public List<Employee> listEmployees();

}
