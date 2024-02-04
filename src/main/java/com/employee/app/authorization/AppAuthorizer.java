package com.employee.app.authorization;

import org.checkerframework.checker.nullness.qual.Nullable;
import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;

public class AppAuthorizer implements Authorizer<User> {

  @Override
  public boolean authorize(User user, String role,
      @Nullable ContainerRequestContext containerRequestContext) {
    return user.getRoles() != null && user.getRoles().contains(role);
  }

}
