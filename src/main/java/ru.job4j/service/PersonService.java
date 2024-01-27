package ru.job4j.service;

import ru.job4j.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    Optional<Person> findById(int id);
    Person save(Person person);
    boolean update(Person person);
    boolean delete(int id);
}