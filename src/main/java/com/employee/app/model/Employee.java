package com.employee.app.model;

import org.checkerframework.common.value.qual.IntRangeFromPositive;
import org.hibernate.validator.constraints.Length;

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
  @Pattern(regexp = "[0-9]+")
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
