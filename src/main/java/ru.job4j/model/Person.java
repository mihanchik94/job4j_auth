package ru.job4j.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    @NotNull(message = "Login mustn't be null")
    @NotBlank(message = "Login mustn't be empty")
    private String login;
    @NotNull(message = "Password mustn't be null")
    @NotBlank(message = "Password mustn't be empty")
    private String password;
}