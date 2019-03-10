package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Address;

@RestController
@RequestMapping("addresses")
public class AddressController extends BaseCrudController<Address, Integer> {
}
