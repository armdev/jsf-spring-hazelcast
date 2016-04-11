package com.web.jsf.beans;

import com.project.entities.Employee;
import com.project.services.EmployeeService;
import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;

@ManagedBean(name = "userManagedBean")
@ViewScoped
public class UserManagedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    @ManagedProperty("#{employeeService}")
    private EmployeeService employeeService;

    @ManagedProperty("#{i18n}")
    private ResourceBundle bundle = null;
    private Employee employee = new Employee();

    public UserManagedBean() {

    }

    @PostConstruct
    public void init() {
        employee = new Employee();
    }

    public List<Employee> getUserList() {
//        if (!FacesContext.getCurrentInstance().getRenderResponse()) {
//            return null;
//        }
        List<Employee> userList = employeeService.listEmployees();
        return userList;
    }

    public String saveUser() {
        employeeService.addEmployee(employee);
        return "index";

    }

    public String deleteUser(Integer id) {
        if (id != null) {
            employeeService.removeEmployee(id);
        }
        return "index";
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

}
