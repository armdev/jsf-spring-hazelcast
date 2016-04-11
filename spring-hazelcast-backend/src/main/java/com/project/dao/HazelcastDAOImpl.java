package com.project.dao;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.project.entities.Employee;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository("hazelcastDAO")
public class HazelcastDAOImpl implements HazelcastDAO {

    @Autowired
    private HazelcastInstance hazelcastInstance;

    public HazelcastDAOImpl() {
    }

    @Override
    public Employee getEmployee(Integer employeeId) {
        IMap<Integer, Employee> dataStore = hazelcastInstance.getMap("employeeMap");
        return dataStore.get(employeeId);
    }

    @Override
    public void removeEmployee(Integer employeeId) {
        IMap<Integer, Employee> dataStore = hazelcastInstance.getMap("employeeMap");
        dataStore.remove(employeeId);
    }

    @Override
    public void addEmployee(Employee employee) {
        IMap<Integer, Employee> dataStore = hazelcastInstance.getMap("employeeMap");
        dataStore.put(employee.getId(), employee);
    }

    @Override
    public List<Employee> listEmployees() {
        IList<Employee> dataStore = hazelcastInstance.getList("employeeList");
        return dataStore;
    }

    @Override
    public void addEmployees(List<Employee> empList) {
        IList<Employee> dataStore = hazelcastInstance.getList("employeeList");
        dataStore.addAll(empList);
    }

    @Override
    public void removeList() {
        IList<Employee> dataStore = hazelcastInstance.getList("employeeList");
        dataStore.clear();
    }

    public void shutDown() {
        hazelcastInstance.getLifecycleService().shutdown();
    }
}
