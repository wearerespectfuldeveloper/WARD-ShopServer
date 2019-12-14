package com.ward.wardshop;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleController {

    @GetMapping("/simple")
    public String simple() {
        return "Hello, This is Ward Server";
    }
}
