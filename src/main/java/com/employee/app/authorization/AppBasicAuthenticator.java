package com.employee.app.authorization;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import com.employee.app.resources.Constants;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

public class AppBasicAuthenticator implements Authenticator<BasicCredentials, User> {

  public AppBasicAuthenticator() {
    super();
  }

  private static final Map<String, Set<String>> VALID_USERS =
      ImmutableMap.of("guest", ImmutableSet.of(), "user", ImmutableSet.of(Constants.USER), "admin",
          ImmutableSet.of(Constants.ADMIN, Constants.USER));

  @Override
  public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
    if (VALID_USERS.containsKey(credentials.getUsername())
        && "password".equals(credentials.getPassword())) {
      return Optional
          .of(new User(credentials.getUsername(), VALID_USERS.get(credentials.getUsername())));
    }
    return Optional.empty();
  }

}
