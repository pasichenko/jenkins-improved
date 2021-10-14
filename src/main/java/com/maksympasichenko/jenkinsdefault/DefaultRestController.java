package com.maksympasichenko.jenkinsdefault;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultRestController {
    @GetMapping("/test")
    public String test() {
        return "Tesy cd cd Branch Jenkins Improved";
    }
}
