package ru.job4j.service;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.dto.PersonDto;
import ru.job4j.model.Person;
import ru.job4j.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimplePersonService implements PersonService, UserDetailsService {
    private static final Logger LOG = LoggerFactory.getLogger(SimplePersonService.class);
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder encoder;


    @Override
    public List<Person> findAll() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> findById(int id) {
        return personRepository.findById(id);
    }

    @Override
    public Optional<Person> save(Person person) {
        try {
            person.setPassword(encoder.encode(person.getPassword()));
            personRepository.save(person);
            return Optional.of(person);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public boolean update(Person person) {
        if (personRepository.findById(person.getId()).isPresent())  {
            person.setPassword(encoder.encode(person.getPassword()));
            personRepository.save(person);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        return personRepository.findById(id)
                .map(person -> {
                    personRepository.delete(person);
                    return true;
                }).orElse(false);
    }

    @Override
    public Optional<Person> patch(int id, PersonDto personDto) {
        return personRepository.findById(id)
                .map(person -> {
                    person.setPassword(encoder.encode(person.getPassword()));
                    return personRepository.save(person);
                });
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByLogin(username)
                .map(person -> new User(person.getLogin(), person.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}