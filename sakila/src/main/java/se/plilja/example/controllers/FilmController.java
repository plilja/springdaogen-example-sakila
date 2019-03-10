package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Film;

@RestController
@RequestMapping("films")
public class FilmController extends BaseCrudController<Film, Integer> {


}
