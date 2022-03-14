package com.pomidor.boil.controller;

import com.pomidor.boil.calculation.Activity;
import com.pomidor.boil.calculation.Happening;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller("boil")
public class BoilController {

    @Autowired
    private Validator validator;

    @PostMapping("/cpn")
    public ResponseEntity<List<Happening>> calculate(@RequestBody List<Activity> activities) {

        validator.validate(activities);

        // TUTAJ TRZA WYWOLAC JAKAS METODE DO OBLICZANIA

        return null;
    }
}
