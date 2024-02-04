package com.employee.app.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EmployeeTest {

  private Employee employee;

  @BeforeEach
  public void setUp() {
    employee = new Employee(1, "John", "Doe", "john.doe@email.com");
  }

  @Test
  public void testGetId() {
    assertEquals(1, employee.getId());
  }

  @Test
  public void testGetName() {
    assertEquals("John", employee.getName());
  }

  @Test
  public void testGetLastName() {
    assertEquals("Doe", employee.getLastName());
  }

  @Test
  public void testGetEmail() {
    assertEquals("john.doe@email.com", employee.getEmail());
  }

  @Test
  public void testSetId() {
    employee.setId(2);
    assertEquals(2, employee.getId());
  }

  @Test
  public void testSetName() {
    employee.setName("Jane");
    assertEquals("Jane", employee.getName());
  }

  @Test
  public void testSetLastName() {
    employee.setLastName("Smith");
    assertEquals("Smith", employee.getLastName());
  }

  @Test
  public void testSetEmail() {
    employee.setEmail("jane.smith@email.com");
    assertEquals("jane.smith@email.com", employee.getEmail());
  }

  @Test
  public void testToString() {
    String expected = "Employee(id=1, name=John, lastName=Doe, email=" + employee.getEmail() + ")";
    assertEquals(expected, employee.toString());
  }
}
