package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Customer;

@RestController
@RequestMapping("customers")
public class CustomerController extends BaseCrudController<Customer, Integer> {


}
