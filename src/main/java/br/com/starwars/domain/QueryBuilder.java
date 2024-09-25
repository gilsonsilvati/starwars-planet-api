package br.com.starwars.domain;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

public class QueryBuilder {

    private QueryBuilder() { }

    public static Example<Planet> makeQuery(Planet planet) {

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreCase()
                .withIgnoreNullValues();

        return Example.of(planet, matcher);
    }
}
