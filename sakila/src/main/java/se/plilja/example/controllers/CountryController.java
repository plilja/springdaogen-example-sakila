package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Country;

@RestController
@RequestMapping("countries")
public class CountryController extends BaseCrudController<Country, Integer> {


}
