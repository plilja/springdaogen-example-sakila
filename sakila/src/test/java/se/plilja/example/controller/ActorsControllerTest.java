package se.plilja.example.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpStatusCodeException;
import se.plilja.example.TestConfig;
import se.plilja.example.controllers.ActorController;
import se.plilja.example.model.Actor;
import se.plilja.example.model.ActorDao;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = {TestConfig.class, ActorDao.class, ActorController.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ActorsControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ActorDao actorDao;
    @Autowired
    private ActorController actorController;

    private TreeMap<Integer, Actor> savedActors = new TreeMap<>();

    @BeforeEach
    void setUp() {
        actorDao.findAll()
                .forEach(a -> actorDao.delete(a));

        for (int i = 1; i <= 10; i++) {
            Actor actor = new Actor();
            actor.setFirstName("FirstName" + i);
            actor.setLastName("LastName" + i);
            actorDao.save(actor);
            savedActors.put(actor.getActorId(), actor);
        }
    }

    @Test
    void list() {
        // when
        ResponseEntity<Actor[]> resp = restTemplate.getForEntity(String.format("http://localhost:%d/actors", port), Actor[].class);

        // then
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Actor[] actors = resp.getBody();
        assertEquals(10, actors.length);
        assertEquals("FirstName1", actors[0].getFirstName());
        assertEquals("LastName1", actors[0].getLastName());
    }

    @Test
    void listWithFilter() {
        // when
        ResponseEntity<Actor[]> resp = restTemplate.getForEntity(String.format("http://localhost:%d/actors?lastName=LastName3", port), Actor[].class);

        // then
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Actor[] actors = resp.getBody();
        assertEquals(1, actors.length);
        assertEquals("FirstName3", actors[0].getFirstName());
        assertEquals("LastName3", actors[0].getLastName());
    }

    @Test
    void listWithOrderBy() {
        // when
        ResponseEntity<Actor[]> resp = restTemplate.getForEntity(String.format("http://localhost:%d/actors?sortBy=actorId&sortOrder=desc", port), Actor[].class);

        // then
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Actor[] actors = resp.getBody();
        assertEquals(10, actors.length);
        assertEquals("FirstName10", actors[0].getFirstName());
        assertEquals("LastName10", actors[0].getLastName());
        assertEquals("FirstName9", actors[1].getFirstName());
        assertEquals("LastName9", actors[1].getLastName());
    }

    @Test
    void get() {
        // when
        ResponseEntity<Actor> resp = restTemplate.getForEntity(String.format("http://localhost:%d/actors/%d", port, savedActors.lastKey()), Actor.class);

        // then
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        Actor actor = resp.getBody();
        assertEquals("FirstName10", actor.getFirstName());
        assertEquals("LastName10", actor.getLastName());
    }

    @Test
    void getUnknownId() {
        // when
        ResponseEntity<Actor> resp = restTemplate.getForEntity(String.format("http://localhost:%d/actors/%d", port, savedActors.lastKey() + 1), Actor.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, resp.getStatusCode());
    }
}
