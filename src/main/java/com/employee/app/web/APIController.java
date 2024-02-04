package com.employee.app.web;

import java.util.ArrayList;
import com.employee.app.configuration.ApplicationConfiguration;
import com.employee.app.model.Employee;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("client-root-path")
public class APIController {

  private Client jerseyClient;
  private ApplicationConfiguration configuration;

  public APIController() {
    super();
  }

  public APIController(ApplicationConfiguration configuration, Client jerseyClient) {
    this.jerseyClient = jerseyClient;
    this.configuration = configuration;
  }

  @GET
  @Path("/employees")
  public String getEmployees() {
    WebTarget webTarget = jerseyClient.target(configuration.getApiUrl());
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    Response response = invocationBuilder.get();
    ArrayList<Employee> employees = response.readEntity(new GenericType<ArrayList<Employee>>() {});
    return employees.toString();
  }

  @GET
  @Path("/employees/{id}")
  public String getEmployeeById(@PathParam("id") int id) {
    WebTarget webTarget = jerseyClient.target(configuration.getApiUrl() + id);
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    Response response = invocationBuilder.get();
    Employee employee = response.readEntity(Employee.class);
    return employee.toString();
  }

}
