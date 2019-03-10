package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Language;

@RestController
@RequestMapping("languages")
public class LanguageController extends BaseCrudController<Language, Integer> {


}
