package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Store;

@RestController
@RequestMapping("stores")
public class StoreController extends BaseCrudController<Store, Integer> {


}
