package se.plilja.example.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.plilja.example.model.Payment;

@RestController
@RequestMapping("payments")
public class PaymentController extends BaseCrudController<Payment, Integer> {


}
