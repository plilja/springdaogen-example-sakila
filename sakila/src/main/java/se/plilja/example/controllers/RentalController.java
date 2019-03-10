package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Rental;

@RestController
@RequestMapping("rentals")
public class RentalController extends BaseCrudController<Rental, Integer> {


}
