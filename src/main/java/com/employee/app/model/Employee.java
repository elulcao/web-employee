package com.employee.app.model;

import org.hibernate.validator.constraints.Length;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Employee {

  @NotNull
  @Min(1)
  @Max(Integer.MAX_VALUE)
  private Integer id;

  @NotNull
  @Length(min = 2, max = 255)
  private String name;

  @NotNull
  @Length(min = 2, max = 255)
  private String lastName;

  @Pattern(regexp = ".+@.+\\.[a-z]+")
  private String email;

}
