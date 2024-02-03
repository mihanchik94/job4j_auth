package ru.job4j.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.dto.PersonDto;
import ru.job4j.model.Person;
import ru.job4j.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/person")
@AllArgsConstructor
public class PersonController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private final PersonService personService;
    private final ObjectMapper objectMapper;


    @GetMapping("/")
    public ResponseEntity<List<Person>> findAll() {
        return new ResponseEntity<>(personService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        return personService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Person not found. Please check id"));
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@Valid @RequestBody Person person) {
        return personService.save(person)
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.CREATED))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.CONFLICT, "This login is already taken. Please come up with another one"));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() {{
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }

    @PutMapping("/")
    public ResponseEntity<Person> update(@Valid @RequestBody Person person) {
        return personService.update(person)
                ? ResponseEntity.ok().build()
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable int id) {
        return personService.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Person> patch(@PathVariable int id, @Valid @RequestBody PersonDto personDto) {
        return personService.patch(id, personDto)
                .map(person -> ResponseEntity.ok(person))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}