package com.project.services;

import com.project.dao.*;
import com.project.entities.Employee;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Home
 */
@Service("employeeService")
@Component
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeDAO dao;

    @Override
    public void addEmployee(Employee employee) {
        dao.addEmployee(employee);
    }

    @Override
    public void removeEmployee(Integer employeeId) {
        dao.removeEmployee(employeeId);
    }

    @Override
    public Employee getEmployee(Integer employeeId) {
        return dao.getEmployee(employeeId);
    }

    @Override
    public List<Employee> listEmployees() {
        return dao.listEmployees();
    }

}
