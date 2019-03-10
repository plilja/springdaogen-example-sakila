package se.plilja.example.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;
import se.plilja.example.dbframework.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseCrudController<T extends BaseEntity<ID>, ID> extends BaseController<T> {
    @Autowired
    private Dao<T, ID> dao;

    @GetMapping("/{id}")
    public ResponseEntity<T> index(@PathVariable("id") ID id) {
        return dao.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody  @Valid T updated) {
        dao.save(updated);
        return ResponseEntity.ok().body(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") ID id) {
        return dao.findOne(id)
                .map(o -> {
                    dao.delete(o);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable("id") ID id, @RequestBody @Valid T updated) {
        if (updated.getId() == null) {
            updated.setId(id);
        }
        if (!updated.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id of provided object is not same as id provided in path");
        }
        dao.save(updated);
        return ResponseEntity.created(URI.create("/" + updated.getId())).body(updated);
    }
}
