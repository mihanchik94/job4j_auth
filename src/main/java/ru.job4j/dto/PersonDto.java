package ru.job4j.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PersonDto {
    @EqualsAndHashCode.Include
    @NotNull(message = "Password mustn't be null")
    @NotBlank(message = "Password mustn't be empty")
    String password;
}