package com.employee.app.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.core.Configuration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class ApplicationConfiguration extends Configuration {

  public ApplicationConfiguration() {
    super();
  }

  @Getter
  @Valid
  @NotNull
  @JsonProperty("api")
  private ApiConfiguration api;

  @Getter
  @Valid
  @NotNull
  @JsonProperty("jerseyClient")
  private JerseyClientConfiguration jerseyClientConfiguration;

}
