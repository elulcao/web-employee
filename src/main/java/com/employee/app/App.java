package com.employee.app;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.employee.app.authorization.AppAuthorizer;
import com.employee.app.authorization.AppBasicAuthenticator;
import com.employee.app.authorization.User;
import com.employee.app.configuration.ApplicationConfiguration;
import com.employee.app.configuration.ApplicationHealthCheck;
import com.employee.app.repository.EmployeeRepository;
import com.employee.app.resources.Constants;
import com.employee.app.web.ApiController;
import com.employee.app.web.EmployeeController;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;

public class App extends Application<ApplicationConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
    bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
        bootstrap.getConfigurationSourceProvider(), new EnvironmentVariableSubstitutor(true)));
  }

  @Override
  public void run(ApplicationConfiguration configuration, Environment environment)
      throws Exception {
    Client client = registerJerseyClient(configuration, environment);
    registerRestResources(environment);
    registerApplicationHealthCheck(configuration, environment, client);
    registerDropwizardSecurity(environment);
  }

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }

  public Client registerJerseyClient(ApplicationConfiguration configuration,
      Environment environment) {
    LOGGER.info("Registering Jersey Client");
    final Client client = new JerseyClientBuilder(environment)
        .using(configuration.getJerseyClientConfiguration()).build(getName());
    environment.jersey().register(new ApiController(configuration, client));
    return client;
  }

  public void registerRestResources(Environment environment) {
    LOGGER.info("Registering REST resources");
    environment.jersey()
        .register(new EmployeeController(environment.getValidator(), new EmployeeRepository()));
  }

  public void registerApplicationHealthCheck(ApplicationConfiguration configuration,
      Environment environment, Client client) {
    LOGGER.info("Registering Application Health Check");
    environment.healthChecks().register("application",
        new ApplicationHealthCheck(configuration, client));
  }

  public void registerDropwizardSecurity(Environment environment) {
    LOGGER.info("Registering Dropwizard security");
    environment.jersey()
        .register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new AppBasicAuthenticator()).setAuthorizer(new AppAuthorizer())
            .setRealm(Constants.BASIC_AUTH_REALM).buildAuthFilter()));
    environment.jersey().register(RolesAllowedDynamicFeature.class);
    environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
  }

}
