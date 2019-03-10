package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Inventory;

@RestController
@RequestMapping("inventories")
public class InventoryController extends BaseCrudController<Inventory, Integer> {


}
