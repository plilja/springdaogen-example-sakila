package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Actor;

@RestController
@RequestMapping("actors")
public class ActorController extends BaseCrudController<Actor, Integer> {


}
