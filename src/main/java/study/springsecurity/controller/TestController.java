package study.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/a")
    public String postEndpointA() {
        return "POST Works!";
    }

    @GetMapping("/a")
    public String getEndpointA() {
        return "GET Works!";
    }

    @GetMapping("/a/b")
    public String getEndpointB() {
        return "B Works!";
    }

    @GetMapping("/a/b/c")
    public String getEndpointC() {
        return "C Works!";
    }
}
