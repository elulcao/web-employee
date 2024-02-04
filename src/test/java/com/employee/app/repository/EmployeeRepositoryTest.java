package com.employee.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.employee.app.model.Employee;


public class EmployeeRepositoryTest {

  private EmployeeRepository employeeRepository;

  @BeforeEach
  public void setUp() {
    employeeRepository = new EmployeeRepository();
  }

  @Test
  public void testGetEmployees() {
    List<Employee> employees = employeeRepository.getEmployees();
    assertNotNull(employees);
    assertEquals(1, employees.size());
  }

  @Test
  public void testGetEmployee() {
    Employee employee = employeeRepository.getEmployee(1);
    assertNotNull(employee);
    assertEquals("John", employee.getName());
    assertEquals("Doe", employee.getLastName());
    assertEquals("jhon.doe@email.com", employee.getEmail());
  }

  @Test
  public void testUpdateEmployee() {
    Employee employee = new Employee(1, "Jane", "Smith", "jane.smith@email.com");
    employeeRepository.updateEmployee(1, employee);
    Employee updatedEmployee = employeeRepository.getEmployee(1);
    assertNotNull(updatedEmployee);
    assertEquals("Jane", updatedEmployee.getName());
    assertEquals("Smith", updatedEmployee.getLastName());
    assertEquals(employee.getEmail(), updatedEmployee.getEmail());
  }

  @Test
  public void testRemoveEmployee() {
    employeeRepository.removeEmployee(1);
    Employee removedEmployee = employeeRepository.getEmployee(1);
    assertNull(removedEmployee);
  }

  @Test
  public void testAddEmployee() {
    Employee employee = new Employee(2, "Alice", "Johnson", "alice.johnson@email.com");
    employeeRepository.addEmployee(employee);
    Employee addedEmployee = employeeRepository.getEmployee(2);
    assertNotNull(addedEmployee);
    assertEquals(employee.getName(), addedEmployee.getName());
    assertEquals(employee.getLastName(), addedEmployee.getLastName());
    assertEquals(employee.getEmail(), addedEmployee.getEmail());
  }
}
