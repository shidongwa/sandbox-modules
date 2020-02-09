package com.stone.studio.sandbox.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.lang.Thread.sleep;

@RestController
public class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Sandbox target app!";
    }

    @PostMapping(value = "/demo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> process(@RequestBody MultiValueMap<String, String> formParams) {
//        System.out.println("formParams = [" + formParams + "]");
        try {
            sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>("SUCCESS!", HttpStatus.OK);
    }
}
