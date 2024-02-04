package com.employee.app.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;
import com.employee.app.authorization.User;
import com.employee.app.model.Employee;
import com.employee.app.repository.EmployeeRepository;
import com.employee.app.resources.Constants;

import io.dropwizard.auth.Auth;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {

  private Validator validator;
  private EmployeeRepository repository;

  public EmployeeController(Validator validator, EmployeeRepository repository) {
    this.validator = validator;
    this.repository = repository;
  }

  @GET
  @PermitAll
  public Response getEmployees(@Auth User user) {
    return Response.ok(repository.getEmployees()).build();
  }

  @GET
  @Path("/{id}")
  @PermitAll
  public Response getEmployeeById(@PathParam("id") Integer id, @Auth User user) {
    Employee employee = repository.getEmployee(id);
    if (employee != null) {
      return Response.ok(employee).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @POST
  @Path("/add")
  @RolesAllowed({Constants.ADMIN})
  public Response createEmployee(@NotNull Employee employee, @Auth User user)
      throws URISyntaxException {
    // validation
    Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
    if (violations.size() > 0) {
      ArrayList<String> validationMessages = new ArrayList<String>();
      for (ConstraintViolation<Employee> violation : violations) {
        validationMessages
            .add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
      }
      return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
    }
    Employee e = repository.getEmployee(employee.getId());
    if (e != null) {
      return Response.status(Status.CONFLICT).entity("Employee already exists").build();
    } else {
      repository.addEmployee(employee);
      return Response.status(Status.CREATED).entity("Employee created")
          .location(new URI("/employees/" + employee.getId())).build();
    }
  }

  @PUT
  @Path("/{id}")
  @PermitAll
  public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee,
      @Auth User user) {
    // validation
    Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
    if (violations.size() > 0) {
      ArrayList<String> validationMessages = new ArrayList<String>();
      for (ConstraintViolation<Employee> violation : violations) {
        validationMessages
            .add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
      }
      return Response.status(Status.BAD_REQUEST).entity(validationMessages).build();
    }
    Employee e = repository.getEmployee(employee.getId());
    if (e != null) {
      employee.setId(id);
      repository.updateEmployee(id, employee);
      return Response.ok(employee).build();
    } else {
      return Response.status(Status.NOT_FOUND).entity(id).build();
    }
  }

  @DELETE
  @Path("/{id}")
  @RolesAllowed({Constants.ADMIN})
  public Response removeEmployeeById(@PathParam("id") Integer id, @Auth User user) {
    Employee employee = repository.getEmployee(id);
    if (employee != null) {
      repository.removeEmployee(id);
      return Response.ok().build();
    } else {
      return Response.status(Status.NOT_FOUND).entity(id).build();
    }
  }

}
