package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.City;

@RestController
@RequestMapping("cities")
public class CityController extends BaseCrudController<City, Integer> {


}
