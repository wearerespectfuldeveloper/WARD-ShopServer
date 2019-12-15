package com.ward.wardshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SimpleController {

    @GetMapping("/simple")
    public String simple() {
        log.info("jenkins-test");
        return "Hello, This is Ward Server";
    }
}
