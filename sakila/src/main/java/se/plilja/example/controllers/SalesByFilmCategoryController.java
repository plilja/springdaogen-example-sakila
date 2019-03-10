package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.SalesByFilmCategory;

@RestController
@RequestMapping("sales_by_film_categories")
public class SalesByFilmCategoryController extends BaseController<SalesByFilmCategory> {
}
