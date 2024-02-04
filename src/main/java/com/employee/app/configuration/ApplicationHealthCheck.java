package com.employee.app.configuration;

import java.util.ArrayList;
import java.util.Base64;
import com.codahale.metrics.health.HealthCheck;
import com.employee.app.model.Employee;
import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ApplicationHealthCheck extends HealthCheck {

  private final Client client;
  private final ApplicationConfiguration configuration;

  public ApplicationHealthCheck(ApplicationConfiguration configuration, Client client) {
    super();
    this.client = client;
    this.configuration = configuration;
  }

  @Override
  @PermitAll
  protected Result check() throws Exception {
    try {
      WebTarget webTarget = client.target(configuration.getApiUrl());
      String authString = "admin" + ":" + "password";
      String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());

      Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON)
          .header(HttpHeaders.AUTHORIZATION, "Basic " + authStringEnc);
      Response response = invocationBuilder.get();
      if (response.getStatus() != 200) {
        return Result.unhealthy("API Failed with status: " + response.getStatus());
      }
      ArrayList<Employee> employees =
          response.readEntity(new GenericType<ArrayList<Employee>>() {});
      if (employees != null && !employees.isEmpty()) {
        return Result.healthy();
      }
      return Result.unhealthy("API Failed");
    } catch (WebApplicationException | ProcessingException e) {
      return Result.unhealthy("Exception: " + e.getMessage());
    }
  }
}
