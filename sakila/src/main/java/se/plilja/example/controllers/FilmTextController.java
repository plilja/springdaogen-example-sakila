package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.FilmText;

@RestController
@RequestMapping("film_texts")
public class FilmTextController extends BaseCrudController<FilmText, Integer> {


}
