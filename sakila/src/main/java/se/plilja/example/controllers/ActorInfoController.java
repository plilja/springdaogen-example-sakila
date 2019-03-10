package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.ActorInfo;

@RestController
@RequestMapping("actor_infos")
public class ActorInfoController extends BaseController<ActorInfo> {
}
