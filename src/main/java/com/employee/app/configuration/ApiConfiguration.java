package com.employee.app.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


public class ApiConfiguration {

  @Getter
  @Valid
  @NotNull
  @JsonProperty("url")
  private String url;

  public ApiConfiguration() {
    super();
  }
}
