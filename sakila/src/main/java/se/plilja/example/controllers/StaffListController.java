package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.StaffList;

@RestController
@RequestMapping("staff_lists")
public class StaffListController extends BaseController<StaffList> {
}
