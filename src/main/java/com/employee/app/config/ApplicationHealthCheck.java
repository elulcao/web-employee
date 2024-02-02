package com.employee.app.config;

import com.codahale.metrics.health.HealthCheck;
import com.employee.app.model.Employee;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;

public class ApplicationHealthCheck extends HealthCheck {

  private final Client client;

  public ApplicationHealthCheck(Client client) {
    super();
    this.client = client;
  }

  @Override
  @SuppressWarnings("unchecked")
  protected Result check() throws Exception {
    WebTarget webTarget = client.target("http://localhost:8080/employees");
    Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
    Response response = invocationBuilder.get();
    ArrayList<Employee> employees = response.readEntity(ArrayList.class);
    if (employees != null && !employees.isEmpty()) {
      return Result.healthy();
    }
    return Result.unhealthy("API Failed");
  }
}
