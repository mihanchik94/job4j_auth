package ru.job4j.service;

import ru.job4j.dto.PersonDto;
import ru.job4j.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    List<Person> findAll();
    Optional<Person> findById(int id);
    Optional<Person> save(Person person);
    boolean update(Person person);
    boolean delete(int id);
    Optional<Person> patch(int id, PersonDto personDto);
}