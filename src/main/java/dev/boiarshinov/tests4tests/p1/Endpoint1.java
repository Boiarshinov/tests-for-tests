package dev.boiarshinov.tests4tests.p1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/1")
public class Endpoint1 {

    @GetMapping("/g1/{id}")
    public ResponseEntity<String> g1(
            @PathVariable("id") String id,
            @RequestHeader("X-ROLE") String role
    ) {
        if (!role.equals("admin")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough permissions");
        }
        return ResponseEntity.ok("OK " + id);
    }
}
