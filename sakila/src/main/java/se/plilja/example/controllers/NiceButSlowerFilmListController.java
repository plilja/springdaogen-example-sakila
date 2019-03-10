package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.NicerButSlowerFilmList;

@RestController
@RequestMapping("nice_but_slower_film_lists")
public class NiceButSlowerFilmListController extends BaseController<NicerButSlowerFilmList> {
}
