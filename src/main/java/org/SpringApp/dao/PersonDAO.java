package org.SpringApp.dao;

import org.SpringApp.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.stream.*;
import java.sql.*;

import java.util.List;

@Component
public class PersonDAO {
    private static int PEOPLE_COUNT=1;

    @Autowired
    private final JdbcTemplate jdbcTemplate;


    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("select * from person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.query("select * from person where id =?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("insert into person values (?,?,?,?)",++PEOPLE_COUNT, person.getName(),person.getAge(),
                person.getEmail());

    }

    public void update(int id, Person updatedPerson) {
        jdbcTemplate.update("update person set name=?,age=?,email=? where id=?",updatedPerson.getName(),
                updatedPerson.getAge(),updatedPerson.getEmail(),updatedPerson.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("delete  from person where id=?", id);


    }
}
