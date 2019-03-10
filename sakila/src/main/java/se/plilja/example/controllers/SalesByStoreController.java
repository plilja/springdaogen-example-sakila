package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.SalesByStore;

@RestController
@RequestMapping("sales_by_stores")
public class SalesByStoreController extends BaseController<SalesByStore> {
}
