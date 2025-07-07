package dev.boiarshinov.tests4tests.p2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/2")
public class Endpoint2 {

    @GetMapping("/g2")
    public ResponseEntity<String> g2(
            @RequestHeader("X-ROLE") String role
    ) {
        if (!role.equals("admin") && !role.equals("owner")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not enough permissions");
        }
        return ResponseEntity.ok("OK");
    }
}
