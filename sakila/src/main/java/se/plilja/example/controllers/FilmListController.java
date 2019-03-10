package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.FilmList;

@RestController
@RequestMapping("film_lists")
public class FilmListController extends BaseController<FilmList>{
}
