package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Category;

@RestController
@RequestMapping("categories")
public class CategoryController extends BaseCrudController<Category, Integer> {
}
