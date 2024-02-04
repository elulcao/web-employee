package com.employee.app.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.employee.app.model.Employee;

public class EmployeeRepository {

  public static HashMap<Integer, Employee> employees;

  public EmployeeRepository() {
    super();
    employees = new HashMap<Integer, Employee>();
    employees.put(1, new Employee(1, "John", "Doe", "jhon.doe@email.com"));
  }

  public List<Employee> getEmployees() {
    return new ArrayList<Employee>(employees.values());
  }

  public Employee getEmployee(Integer id) {
    return employees.get(id);
  }

  public void updateEmployee(Integer id, Employee employee) {
    employees.put(id, employee);
  }

  public void removeEmployee(Integer id) {
    employees.remove(id);
  }

  public void addEmployee(Employee employee) {
    employees.put(employees.size() + 1, employee);
  }

}
