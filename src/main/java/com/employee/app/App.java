package com.employee.app;

import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.ws.rs.client.Client;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.employee.app.auth.AppAuthorizer;
import com.employee.app.auth.AppBasicAuthenticator;
import com.employee.app.auth.User;
import com.employee.app.config.ApplicationConfiguration;
import com.employee.app.config.ApplicationHealthCheck;
import com.employee.app.repository.EmployeeRepository;
import com.employee.app.web.APIController;
import com.employee.app.web.EmployeeController;

public class App extends Application<ApplicationConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  @Override
  public void initialize(Bootstrap<ApplicationConfiguration> b) {}

  @Override
  public void run(ApplicationConfiguration c, Environment e) throws Exception {

    LOGGER.info("Registering Jersey Client");
    final Client client =
        new JerseyClientBuilder(e).using(c.getJerseyClientConfiguration()).build(getName());
    e.jersey().register(new APIController(client));

    LOGGER.info("Registering REST resources");
    e.jersey().register(new EmployeeController(e.getValidator(), new EmployeeRepository()));

    LOGGER.info("Registering Application Health Check");
    e.healthChecks().register("application", new ApplicationHealthCheck(client));

    /*
     * LOGGER.info("Registering Apache HttpClient"); final HttpClient httpClient = new
     * HttpClientBuilder(e) .using(c.getHttpClientConfiguration()) .build(getName());
     * e.jersey().register(new APIController(httpClient));
     */

    // ****** Dropwizard security - custom classes ***********/
    e.jersey()
        .register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
            .setAuthenticator(new AppBasicAuthenticator()).setAuthorizer(new AppAuthorizer())
            .setRealm("BASIC-AUTH-REALM").buildAuthFilter()));
    e.jersey().register(RolesAllowedDynamicFeature.class);
    e.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
  }

  public static void main(String[] args) throws Exception {
    new App().run(args);
  }
}
